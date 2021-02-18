

public class Edge {
	static int idCounter;
    public long idNum;
    public String sLabel;
    public String dLabel;
    public long weight;
    public String eLabel;

    public Node srcNode;
    public Node destNode;

    public Edge(String sLabel, String dLabel, long weight, String eLabel) {
        this.idNum = idCounter;
        this.sLabel = sLabel;
        this.dLabel = dLabel;
        this.weight = weight;
        this.eLabel = eLabel;
        idCounter++;
    }
    
}
