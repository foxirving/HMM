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
		//traverseGraph();
	}

	private static void traverseGraph() {

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
		Metadata.INSTANCE.myGraph.put(startNode.getMyName(), startNode);

		final String[] wordArray = Metadata.INSTANCE.getStringToDecode().split("\\s+");
		int state = 1;

		for (final String word : wordArray) {
			for (final Tags tag : Tags.getTags(false)) {
				final ProbabilityNode node = new ProbabilityNode(word + tag.getMyString() + state, tag, state, 0);
				populateProbabilityNode(node);
				Metadata.INSTANCE.myGraph.put(node.getMyName(), node);
			}
			state++;
		}

	}

	private static void populateProbabilityNode(final ProbabilityNode theCurrentNode) {
		ProbabilityNode maxNode = null;
		double max = 0.0;
		for (final ProbabilityNode node : Metadata.INSTANCE.myGraph.values()) {
			if (node.getMyState() == (theCurrentNode.getMyState() - 1)) {
				System.out.println(node.getMyName());
				final double probability = node.getMyProbability()
						* Metadata.INSTANCE.getTransitionTableProbability(theCurrentNode.getMyTag(), node.getMyTag());
				if (max < probability) {
					max = probability;
					maxNode = node;
				}
			}
		}
		theCurrentNode.setMyParentNode(maxNode);
	}

}
