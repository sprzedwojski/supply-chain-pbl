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

        //int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35, 20, 10, 5, 3, 14, 17, 29, 0, 5, 3, 18, 46, 12};
        //int[] demand = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        //int[] demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5};

    	
    	
        //DistributionCenter cd1 = null;
        try {
            //Node[] nodes = new Node[2];
            /*cd1 = new DistributionCenter(demand, 150, 150, 10, 30, 100, demand.length + 1);
            DistributionCenter cd2 = new DistributionCenter(150, 150, 2, 5, 50, demand.length + 1);

            Edge edge21 = new Edge(0.6, 2, cd2, cd1);

            Edge edgeExt2 = new Edge(1, 2, null, cd2);
            Edge edgeExt1 = new Edge(0.4, 1, null, cd1);
            cd1.addIncomingEdge(edge21);
            cd1.addIncomingEdge(edgeExt1);

            cd2.addIncomingEdge(edgeExt2);
            cd2.addOutgoingEdge(edge21);
            nodes[0] = cd1;
            nodes[1] = cd2;
*/
            //Creating adapter
            Adapter ad = new Adapter();
            //Taking converted node list from the adapter
            List<Node> DCs = ad.ConvertNodes(args.length > 0 ? args[0] : "nodes.xml");
            //Setting nodes
            //cd1 = DCs.get(0);
            //DistributionCenter cd2 = DCs.get(1);
            
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
            
            // FIXME temporary
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
            
            for(Node n : DCs) {
            	System.out.println("Node " + n.getId());
            	
            	for(Edge e : n.getIncomingEdges()) {
            		System.out.println("-- incoming: " + (e.getSender() != null ? e.getSender().getId() : "external") + " | to: " + e.getReceiver().getId());
            	}
            	
            	for(Edge e : n.getOutgoingEdges()) {
            		System.out.println("-- outgoing: " + e.getReceiver().getId() + " | from: " + (e.getSender() != null ? e.getSender().getId() : "external"));
            	}
            }
            
            /*cd1.addIncomingEdge(Edges.get(0));
            cd1.addIncomingEdge(Edges.get(1));
            cd2.addIncomingEdge(Edges.get(2));
            cd2.addOutgoingEdge(Edges.get(0));
            nodes[0]=cd1;
            nodes[1]=cd2;*/
            
            //int[] demand = null;
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

//                        System.out.println(i + ": inventory level " + nodes[j].getInventoryLevel(i));
//                        System.out.println(i + ": on order inventory " + nodes[j].getOnOrderInventory(i));
//                        System.out.println(i + ": order amount " + nodes[j].getOrderHistory(i) + "\n");

                }
            }

            double totalCost = 0.0;

//            System.out.println("--------------");

            for(Node node : nodes) {
            	node.calculateCostFunction();
            	totalCost += node.getCostFunction();

//            	System.out.println(node.getBaseStockLevel()); // printowane juz w GA
            }

//            System.out.println("--------------");

            totalCost /= nodes.length;

            //cd1.calculateCostFunction();
            //cd2.calculateCostFunction();
            //double c = cd1.getCostFunction() + cd2.getCostFunction();

            //In case of saving modified nodes, re-setting nodes.
            //DCs.set(0, cd1);
            //DCs.set(1, cd2);
            //Saving calculated nodes.
            //ad.ConvertBackendNodes();

            System.out.println("total cost: " + totalCost);

            if(args.length > 2 && args[2].equals("plot")) {
	            for(Node node : nodes) {
	                Plotter p = new Plotter();
	                p.plotPointsWithIntegerXFromZero(node.getInventoryLevel(), "Inventory level y(k)");
	                p.plotPointsWithIntegerXFromZero(node.getOrderHistory(), "Order history u(k)");
	                p.draw("Node " + node.getId());
	            }
            }
            
            /*Plotter p1 = new Plotter();
            p1.plotPointsWithIntegerXFromZero(cd1.getInventoryLevel(), "Inventory level y(k)");
            p1.plotPointsWithIntegerXFromZero(cd1.getOrderHistory(), "Order history u(k)");
            p1.draw("Node 1");
            
            Plotter p2 = new Plotter();
            p2.plotPointsWithIntegerXFromZero(cd2.getInventoryLevel(), "Inventory level y(k)");
            p2.plotPointsWithIntegerXFromZero(cd2.getOrderHistory(), "Order history u(k)");
            p2.draw("Node 2");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
