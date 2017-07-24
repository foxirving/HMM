/**
 * 
 * This class takes an sentence, an emission table, a transition table and
 * generates the most likely sequence of POS tags for the given test sentence.
 * 
 * @author Amy Irving
 * @version July 22, 2017
 *
 */
public class Decoding {

	private static String myPrevStateWord = "";

	private Decoding() {
		// Private constructor.
	}

	public static void Decode() {
		generateProbabilityNodeGraph();
		traverseGraph();
	}

	private static void traverseGraph() {
		final StringBuilder sb = new StringBuilder();
		ProbabilityNode node = new ProbabilityNode("", null, 0, 0.0);
		final int state = Metadata.INSTANCE.getStringToDecode().split(" ").length;

		// Get the max from the highest state.
		for (final Tags tag : Tags.getTags(false)) {
			final String key = Metadata.INSTANCE.getMyPrevWord() + tag.getMyString() + state;
			node = (node.getMyProbability() < Metadata.INSTANCE.getMyGraph().get(key).getMyProbability())
					? Metadata.INSTANCE.getMyGraph().get(key) : node;
		}
		depthFirstSearch(node, sb);
		Metadata.INSTANCE.setMyDecodedString(reverseString(sb.toString()));
	}

	private static void depthFirstSearch(final ProbabilityNode theNode, final StringBuilder theSB) {
		if (null == theNode.getMyParentNode()) {
			theSB.append(theNode.getMyTag().getMyString());
			//theSB.append(Metadata.TAG_DIVIDER);
			return;
		}
		theSB.append(theNode.getMyTag().getMyString());
		theSB.append(Metadata.TAG_DIVIDER);
		depthFirstSearch(theNode.getMyParentNode(), theSB);
	}

	private static String reverseString(final String theString) {
		final String str[] = theString.toString().split(Metadata.TAG_DIVIDER);
		String finalStr = "";
		for (int i = str.length - 1; i >= 0; i--) {
			finalStr += str[i] + Metadata.TAG_DIVIDER;
		}
		return finalStr;
	}

	/**
	 * 
	 * 
	 * @param theWord
	 * @param theTag
	 * @param thePrevTagProbability
	 * @param thecurrentTagProbability
	 * @param theStringBuilder
	 */
	private static void generateProbabilityNodeGraph() {
		final ProbabilityNode startNode = new ProbabilityNode(0 + Tags.START.getMyString(), Tags.START, 0,
				Metadata.INSTANCE.getMyStartProbability());
		Metadata.INSTANCE.myGraph.put(startNode.getMyWord(), startNode);

		final String[] wordArray = Metadata.INSTANCE.getStringToDecode().split("\\s+");
		int state = 1;

		for (final String word : wordArray) {
			for (final Tags tag : Tags.getTags(false)) {
				final ProbabilityNode node = new ProbabilityNode(word, tag, state, 0);
				populateProbabilityNode(node);
				Metadata.INSTANCE.myGraph.put(node.getMyWord() + tag.getMyString() + state, node);
			}
			Metadata.INSTANCE.setMyPrevWord(word);
			state++;
		}

	}

	private static void populateProbabilityNode(final ProbabilityNode theCurrentNode) {
		ProbabilityNode maxNode = null;
		double max = 0.0;
		for (final ProbabilityNode node : Metadata.INSTANCE.myGraph.values()) {
			if (node.getMyState() == (theCurrentNode.getMyState() - 1)) {
				final double probability = node.getMyProbability()
						* Metadata.INSTANCE.getTransitionTableProbability(theCurrentNode.getMyTag(), node.getMyTag());
				if (max < probability) {
					max = probability;
					maxNode = node;
				}
			}
		}
		theCurrentNode.setMyProbability(Metadata.INSTANCE.getEmissionTableProbability(theCurrentNode.getMyWord(),
				theCurrentNode.getMyTag()) * maxNode.getMyProbability()
				* Metadata.INSTANCE.getTransitionTableProbability(theCurrentNode.getMyTag(), maxNode.getMyTag()));
		theCurrentNode.setMyParentNode(maxNode);
	}

}
