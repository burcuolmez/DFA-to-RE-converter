

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.DefaultStyledDocument;


public class test {

	public static void main(String[] args) throws CloneNotSupportedException  {
		DiGraph d = new DiGraph();
		d.addNode("qstart");
		String[] accepts = null;
		try {
		      File myObj = new File("DFA.txt");
		      Scanner myReader = new Scanner(myObj);
		      int row=0;
		      
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        String[] sp =data.split("=");
		        if(sp[0].equalsIgnoreCase("S")) {
		        	d.addNode(sp[1]);
		        	d.addEdge("qstart", sp[1], 0, "€"); 
		        }
		        else if(sp[0].equalsIgnoreCase("A")) {
		        	accepts=sp[1].split(",");
		        	for (int i = 0; i < accepts.length; i++) {
						d.addNode(accepts[i]);
						 
					}
		        }
		        else if(sp[0].equalsIgnoreCase("Q")) {
		        	String[] states=sp[1].split(",");
		        	for (int i = 0; i < states.length; i++) {
						d.addNode(states[i]);
					}
		        }
		        
		        if(row>=4) {
		        	String[] transition=sp[0].split(",");
		        	 ArrayList < Edge > edgeList =(ArrayList<Edge>) d.edgeList.clone();
		        	for (Edge e : edgeList) {
		        		
						if (e.sLabel.equals(transition[0]) && e.dLabel.equals( sp[1]) && !e.eLabel.equals( transition[1])) {
							e.eLabel=e.eLabel+"U"+ transition[1];
						}
						else {
							d.addEdge(transition[0], sp[1], 0, transition[1]);
						}
					}
		        	
		        }
		        row++;
		      
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		    
		
		d.addNode("qfinal");
		for (int i = 0; i < accepts.length; i++) {
			d.addEdge(accepts[i], "qfinal", 0, "€");
		}
		
		ArrayList < Edge > edgeList=d.edgeList;
		
		for (Edge edge : edgeList) {
			for (Edge edge2 : edgeList) {
				if (edge.sLabel.equals(edge2.sLabel) && edge.dLabel.equals(edge2.dLabel) && !edge.eLabel.equals(edge2.eLabel)) {
					edge.eLabel=edge.eLabel+"U"+edge2.eLabel;
					edge2.dLabel="";
					
				}
			}
		}
		
		System.out.println("numEdges: " + d.numEdges());
		System.out.println("numNodes: " + d.numNodes());
	
		int total=(int) d.numNodes();
		ArrayList < Node > nodeList = d.nodeList;
		int i=1;
		int a=1;
		while(total-2!=0) {
			System.out.println("*****eliminate "+nodeList.get(i).label+" *****");
			System.out.println(nodeList.get(i).label);
			ArrayList < Edge > outEdgeList = new ArrayList<Edge>();
			boolean flag = false; // if there is no self loop
			Edge self = null;
			for (Edge outEdge : nodeList.get(i).outEdges) {
				if(outEdge.sLabel.equals(outEdge.dLabel)) {
					flag=true;
					self=outEdge;
				}
				else {
					outEdgeList.add(outEdge);
				}
				
				
			}
			for (Edge inEdge : nodeList.get(i).inEdges) {
				if(flag ) {
					if(!inEdge.sLabel.equals(self.sLabel)) {
						for (Edge out : outEdgeList) {
							System.out.print(inEdge.sLabel+"-"+inEdge.eLabel+"->"+inEdge.dLabel);
							System.out.print("-"+self.eLabel+"->"+self.dLabel);
		                    System.out.print("-"+out.eLabel+"->"+out.dLabel);
		                    System.out.println();
		                    if(d.edgeComp.containsKey(d.labelCombiner(inEdge.sLabel,out.dLabel))) {

	                            String oldLabel = d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel;
	                        	if(inEdge.eLabel.equals("€") && !out.eLabel.equals("€") ) {
	                         	   d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel="("+self.eLabel+")*"+out.eLabel+"U"+oldLabel;
		                    	}
		                    	else if(out.eLabel.equals("€") && !inEdge.eLabel.equals("€") ) {
		                    		 d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel=inEdge.eLabel+"("+self.eLabel+")*"+"U"+oldLabel;
		                    	}
		                    	else {
		                    		d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel=inEdge.eLabel+"("+self.eLabel+")*"+out.eLabel+"U"+oldLabel;
		                    	}
	                          

	                        }
		                    else {
		                    	if(inEdge.eLabel.equals("€") && !out.eLabel.equals("€") ) {
		                    		d.addEdge( inEdge.sLabel, out.dLabel, 0, "("+self.eLabel+")*"+out.eLabel);
		                    	}
		                    	else if(out.eLabel.equals("€") && !inEdge.eLabel.equals("€") ) {
		                    		d.addEdge(inEdge.sLabel, out.dLabel, 0, inEdge.eLabel+"("+self.eLabel+")*");
		                    	}
		                    	else {
		                    		
		                    		d.addEdge( inEdge.sLabel, out.dLabel, 0, inEdge.eLabel+"("+self.eLabel+")*"+out.eLabel);
		                    	}
		                    	
		                    }
						}
					}
				}
				else {
					for (Edge out : outEdgeList) {
						System.out.print(inEdge.sLabel+"-"+inEdge.eLabel+"->"+inEdge.dLabel);
	                    System.out.print("-"+out.eLabel+"->"+out.dLabel);
	                    System.out.println();
	                    if(d.edgeComp.containsKey(d.labelCombiner(inEdge.sLabel,out.dLabel))) {

                            String oldLabel = d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel;
                         	if(inEdge.eLabel.equals("€") && !out.eLabel.equals("€") ) {
                         	    d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel="("+out.eLabel+"U"+oldLabel+")";
	                    		
	                    	}
	                    	else if(out.eLabel.equals("€") && !inEdge.eLabel.equals("€") ) {
	                    		 d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel="("+inEdge.eLabel+"U"+oldLabel+")";
	                    		
	                    	}
	                    	else {
	                    		d.edgeComp.get(d.labelCombiner(inEdge.sLabel, out.dLabel)).eLabel="("+inEdge.eLabel+out.eLabel+"U"+oldLabel+")";
	                    		
	                    	}
                          

                        }
	                    else {
	                    	if(inEdge.eLabel.equals("€") && !out.eLabel.equals("€") ) {
	                    		d.addEdge(inEdge.sLabel, out.dLabel, 0, out.eLabel);
	                    	}
	                    	else if(out.eLabel.equals("€") && !inEdge.eLabel.equals("€") ) {
	                    		d.addEdge( inEdge.sLabel, out.dLabel, 0, inEdge.eLabel);
	                    	}
	                    	else {
	                    		d.addEdge(inEdge.sLabel, out.dLabel, 0, inEdge.eLabel+out.eLabel);
	                    	}
	                    	
	                    }
					}
					
				}
				
			}
		
			d.delNode(nodeList.get(i).label);
			total--;
			a++;
			
			ArrayList < Node > nodeList2 = d.nodeList;
			System.out.println("Nodes after elimination");
			for (Node node : nodeList2) {
				System.out.println(node.label);
			}
			ArrayList < Edge > edges = d.edgeList;
			System.out.println("Edges after elimination");
			for (Edge edge : edges) {
				System.out.println("Source: "+edge.sLabel+" Destination: "+edge.dLabel+" : "+edge.eLabel);
			}
			
		
			
		}
		
	
		
	}

}
