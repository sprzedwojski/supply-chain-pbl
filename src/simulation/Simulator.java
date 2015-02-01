package simulation;

import ga.GA;

import java.util.ArrayList;
import java.util.List;

import plotting.Plotter;
import structure.Edge;
import structure.ExternalNode;
import structure.Node;
import adapter.Adapter;

/**
 * Main class used for running simulations.
 *
 * @author szymon
 */
public class Simulator {

	static int maxDemand=0;
	static int maxDelay=0;
    static int populationSize = 200;
	
    public static void main(String[] args) {

        try {
            //Creating adapter
            Adapter ad = new Adapter();
            
            //Taking converted node list from the adapter
            List<Node> DCs = ad.ConvertNodes(args.length > 0 ? args[0] : "nodes.xml");
            
            //taking converted edges list from the adapter
            List<Edge> Edges = ad.ConvertConnections(args.length > 1 ? args[1] : "connections.xml");
            
            //remove already unnecessary external nodes & searching for max demand value
            List<Node> toRemove = new ArrayList<Node>();
            for(Node n : DCs) {
            	if(n instanceof ExternalNode) {
            		toRemove.add(n);
            	} else {
                    if(n.getDemand()!=null) {
                        for (int d : n.getDemand()) {
                            if (d > maxDemand)
                                maxDemand = d;
                        }
                    }
            	}
            }
            for(Node n : toRemove) {
            	DCs.remove(n);
            }

            int k=1;
            for(Node dc : DCs) {
            	dc.setId(k);
            	k++;
            }
            
            // Allocating edges to nodes & searching for max delay
            for(Edge e : Edges) {
            	if(e.getReceiver() != null) {
            		DCs.get(e.getReceiver().getId()-1).addIncomingEdge(e);
            	}
            	if(e.getSender() != null) {
            		DCs.get(e.getSender().getId()-1).addOutgoingEdge(e);
            	}
            	
            	// searching for max delay
            	if(e.getDelay() > maxDelay) 
            		maxDelay = e.getDelay();
            }
            
//            for(Node n : DCs) {
//            	System.out.println("Node " + n.getId());
//            	
//            	for(Edge e : n.getIncomingEdges()) {
//            		System.out.println("-- incoming: " + (e.getSender() != null ? e.getSender().getId() : "external") + " | to: " + e.getReceiver().getId());
//            	}
//            	
//            	for(Edge e : n.getOutgoingEdges()) {
//            		System.out.println("-- outgoing: " + e.getReceiver().getId() + " | from: " + (e.getSender() != null ? e.getSender().getId() : "external"));
//            	}
//            }
            
            int periodLength = 0;
            
            for(Node dc : DCs) {
            	if(dc.getDemand() != null) {
            		periodLength = dc.getDemand().length+1;
            		break;
            	}
            }
            
            Node[] nodes = DCs.toArray(new Node[0]);
            int maxInterval = 2*maxDemand*(maxDelay+1);

            GA ga = new GA(populationSize, nodes, periodLength, 300);
            ga.runGA();
            
            int[] solutions = ga.getBestSolution();
            for (int i = 0; i < nodes.length; i++) {
                nodes[i].setBaseStockLevel(solutions[i]);
            }
            for (int i = 0; i < periodLength; i++) {
                for (int j = 0; j < nodes.length; j++) {
                    nodes[j].calculateInventoryLevel(i);
                    nodes[j].calculateOnOrderInventory(i);
                    nodes[j].calculateOrderAmount(i);
                }
            }

            double totalCost = 0.0;

            for(Node node : nodes) {
            	node.calculateCostFunction();
            	totalCost += node.getCostFunction();
            }

            totalCost /= nodes.length;

//            System.out.println("total cost: " + totalCost);

            if(args.length > 2 && args[2].equals("plot")) {
	            for(Node node : nodes) {
	                Plotter p = new Plotter();
	                p.plotPointsWithIntegerXFromZero(node.getInventoryLevel(), "Inventory level y(k)");
	                p.plotPointsWithIntegerXFromZero(node.getOrderHistory(), "Order history u(k)");
	                p.draw("Node " + node.getId());
	            }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
