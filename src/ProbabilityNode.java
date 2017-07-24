public class ProbabilityNode {

	private final String myWord;
	private final Tags myTag;
	private final int myState;
	private double myProbability;
	private ProbabilityNode myParentNode;

	public ProbabilityNode(final String theWord, final Tags myTag, final int theState, final double myProbability) {
		this.myWord = theWord;
		this.myTag = myTag;
		this.myState = theState;
		this.myProbability = myProbability;
	}

	public String getMyWord() {
		return myWord;
	}

	public ProbabilityNode getMyParentNode() {
		return myParentNode;
	}

	public void setMyParentNode(final ProbabilityNode myParentNode) {
		this.myParentNode = myParentNode;
	}

	public Tags getMyTag() {
		return myTag;
	}

	public double getMyProbability() {
		return myProbability;
	}

	@Override
	public String toString() {
		final String parentName = (myParentNode == null) ? "None"
				: myParentNode.getMyWord() + myParentNode.getMyTag().getMyString() + (myState - 1);
		return "ProbabilityNode [myName=" + myWord + ", myTag=" + myTag + ", myProbability=" + myProbability
				+ ", myParentNode=" + parentName + "]";
	}

	public int getMyState() {
		return myState;
	}

	public void setMyProbability(final double myProbability) {
		this.myProbability = myProbability;
	}

}
