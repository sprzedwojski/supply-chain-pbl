package simulation;

import ga.GA;

import java.util.List;

import plotting.Plotter;
import structure.DistributionCenter;
import structure.Edge;
import structure.Node;
import adapter.Adapter;

/**
 * Main class used for running simulations.
 *
 * @author szymon
 */
public class Simulator {

    public static void main(String[] args) {

        //int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35, 20, 10, 5, 3, 14, 17, 29, 0, 5, 3, 18, 46, 12};
        //int[] demand = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        //int[] demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5};

        DistributionCenter cd1 = null;
        try {
            Node[] nodes = new Node[2];
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
            nodes[1] = cd2;*/

            //Creating adapter
            Adapter ad = new Adapter();
            //Taking converted node list from the adapter
            List<DistributionCenter> DCs = ad.ConvertNodes();
            //Setting nodes
            cd1 = DCs.get(1);
            DistributionCenter cd2 = DCs.get(2);
            
            //taking converted edges list from the adapter
            List<Edge> Edges = ad.ConvertConnections();
            cd1.addIncomingEdge(Edges.get(0));
            cd1.addIncomingEdge(Edges.get(1));
            cd2.addIncomingEdge(Edges.get(2));
            cd2.addOutgoingEdge(Edges.get(0));
            nodes[0]=cd1;
            nodes[1]=cd2;
            
            //int[] demand = null;
            int periodLength = 0;
            
            for(DistributionCenter dc : DCs) {
            	if(dc.getDemand() != null) {
            		periodLength = dc.getDemand().length;
            		break;
            	}
            }
            
            GA ga = new GA(200, nodes, periodLength, 300);
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

                    System.out.println(i + ": inventory level " + nodes[j].getInventoryLevel(i));
                    System.out.println(i + ": on order inventory " + nodes[j].getOnOrderInventory(i));
                    System.out.println(i + ": order amount " + nodes[j].getOrderHistory(i) + "\n");
                }
            }
            cd1.calculateCostFunction();
            cd2.calculateCostFunction();
            double c = cd1.getCostFunction() + cd2.getCostFunction();
            
            //In case of saving modified nodes, re-setting nodes.
            DCs.set(1, cd1);
            DCs.set(2, cd2);
            //Saving calculated nodes.
            ad.ConvertBackendNodes();
            
            System.out.println("cost function: " + c);
            
            Plotter p1 = new Plotter();
            p1.plotPointsWithIntegerXFromZero(cd1.getInventoryLevel(), "Inventory level y(k)");
            p1.plotPointsWithIntegerXFromZero(cd1.getOrderHistory(), "Order history u(k)");
            p1.draw("Node 1");
            
            Plotter p2 = new Plotter();
            p2.plotPointsWithIntegerXFromZero(cd2.getInventoryLevel(), "Inventory level y(k)");
            p2.plotPointsWithIntegerXFromZero(cd2.getOrderHistory(), "Order history u(k)");
            p2.draw("Node 2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
