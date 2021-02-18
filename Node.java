
import java.util.HashSet;


public class Node {
	static int idCounter;
    public long idNum;
    public String label;
    public long dist = Long.MAX_VALUE;
    public HashSet < Edge > inEdges;
    public HashSet < Edge > outEdges;
    boolean visited = false;

    public Node( String label) {
        this.idNum = idCounter;
        this.label = label;

        inEdges = new HashSet < Edge > ();
        outEdges = new HashSet < Edge > ();
        idCounter++;
    }
    
}