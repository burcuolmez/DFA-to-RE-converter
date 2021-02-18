

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


import java.util.ArrayList;

public class DiGraph implements DiGraphInterface {
	
    private HashSet < Long > edge_ids = new HashSet < Long > ();

    Map < Long, String > edgeMap = new HashMap < Long, String > ();
    Map < Long, String > NodeMap = new HashMap < Long, String > ();

    Map < String, Node > NodeComp = new HashMap < String, Node > ();
    Map < String, Edge > edgeComp = new HashMap < String, Edge > ();

    ArrayList < Node > nodeList = new ArrayList < Node > ();
    ArrayList < Edge > edgeList = new ArrayList < Edge > ();

    public DiGraph() {}

    public boolean addNode(String label) {
      
         if (label == null) {
            return false;
        }

        if (NodeMap.containsValue(label)) {
            return false;
        } else {
            Node NodeConObj = new Node(label);
            nodeList.add(NodeConObj);
            NodeMap.put(NodeConObj.idNum, label);
            NodeComp.put(label, NodeConObj);
            return true;
        }
    }

    public boolean addEdge( String sLabel, String dLabel, long weight, String eLabel) {
        Edge edgeVar = new Edge(sLabel, dLabel, weight, eLabel);

        

        if (edgeMap.containsKey(edgeVar.idNum)) {
        	 System.out.println("id bulunuyo");
            return false;
           
        }

        if (NodeMap.containsValue(dLabel) == false || NodeMap.containsValue(sLabel) == false) {
        	System.out.println("nodelar yok");
            return false;
        }

        if (edgeComp.containsKey(labelCombiner(sLabel, dLabel))) {
            return false;
        }

        if (NodeComp.get(sLabel).label.equals(sLabel) || NodeComp.get(dLabel).label.equals(dLabel)) {
            edgeVar.destNode = NodeComp.get(dLabel);
            edgeVar.srcNode = NodeComp.get(sLabel);
            edgeVar.destNode.inEdges.add(edgeVar);
            edgeVar.srcNode.outEdges.add(edgeVar);

            edgeMap.put(edgeVar.idNum, eLabel);
            edgeList.add(edgeVar);
            edgeComp.put(labelCombiner(sLabel, dLabel), edgeVar);
            return true;
        }
        return false;
    }


    public boolean delNode(String label) {
        if (NodeMap.containsValue(label) == false) {
            return false;
        } else {
            Node localNode = NodeComp.get(label);
            NodeMap.remove(localNode.idNum);
            nodeList.remove(localNode);
            NodeComp.remove(label);

            for (Edge edgy: localNode.inEdges) {
                edgy.srcNode.outEdges.remove(edgy);
                edge_ids.remove(edgy.idNum);
                edgeMap.remove(edgy.idNum);
                edgeList.remove(edgy);
                edgeComp.remove(labelCombiner(edgy.srcNode.label, edgy.destNode.label));
            }

            for (Edge edgy: localNode.outEdges) {
                edgy.destNode.inEdges.remove(edgy);
                edge_ids.remove(edgy.idNum);
                edgeMap.remove(edgy.idNum);
                edgeList.remove(edgy);
                edgeComp.remove(labelCombiner(edgy.srcNode.label, edgy.destNode.label));
            }
            return true;
        }
    }





    public long numNodes() {
        return NodeMap.size();
    }

    public long numEdges() {
        return edgeMap.size();
    }



    public String labelCombiner(String l1, String l2) {
        return l1 + "filler" + l2;
    }

	

  
	

}

	