public class ProbabilityNode {

	private final String myName;
	private final Tags myTag;
	private final int myState;
	private final double myProbability;
	private  ProbabilityNode myParentNode;

	public ProbabilityNode(final String theName, final Tags myTag,  final int theState, final double myProbability) {
		this.myName = theName;
		this.myTag = myTag;
		this.myState = theState;
		this.myProbability = myProbability;
	}	
	
	
	
	public String getMyName() {
		return myName;
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
		return "ProbabilityNode [myName=" + myName + ", myTag=" + myTag + ", myProbability=" + myProbability
				+ ", myParentNode=" + myParentNode + "]";
	}



	public int getMyState() {
		return myState;
	}
	
	

}
