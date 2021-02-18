

public interface DiGraphInterface {
	  boolean addNode(String label);
	  boolean addEdge(String sLabel, String dLabel, long weight, String eLabel);
	  boolean delNode(String label);
	  long numNodes();
	  long numEdges();
	}