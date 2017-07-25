package src;

public class Edge {
	
	private final ProbabilityNode myProbabilityNode;
	private final double myEdgeProbability;
	
	public Edge(final ProbabilityNode myProbabilityNode, final double myEdgeProbability) {
		this.myProbabilityNode = myProbabilityNode;
		this.myEdgeProbability = myEdgeProbability;
	}

	public ProbabilityNode getMyProbabilityNode() {
		return myProbabilityNode;
	}

	public double getMyEdgeProbability() {
		return myEdgeProbability;
	}

	@Override
	public String toString() {
		return "[myProbabilityNode = " + myProbabilityNode + ",	myEdgeProbability = " + myEdgeProbability + "]";
	}
	
	
	
}
