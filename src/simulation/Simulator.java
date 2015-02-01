package simulation;

import ga.GA;
import plotting.Plotter;
import structure.DistributionCenter;
import structure.Edge;
import structure.Node;

/**
 * Main class used for running simulations.
 *
 * @author szymon
 */
public class Simulator {

    public static void main(String[] args) {


        //int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35, 20, 10, 5, 3, 14, 17, 29, 0, 5, 3, 18, 46, 12};
        //int[] demand = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        int[] demand = {70,70,70,70,70,70,40, 40, 40, 40, 40,40, 40, 40, 40, 10, 10, 10, 10, 10, 10,40, 40, 40, 40,40, 40, 40, 40, 40,40, 40, 40, 40,};

        DistributionCenter cd1 = null;
        try {
            Node[] nodes = new Node[2];
            cd1 = new DistributionCenter(demand, 150, 20, 10, 40, 120, demand.length + 1);
            DistributionCenter cd2 = new DistributionCenter(150, 30, 20, 60, 150, demand.length + 1);

            Edge edge21 = new Edge(0.6, 5, cd2, cd1);

            Edge edgeExt2 = new Edge(1, 2, null, cd2);
            Edge edgeExt1 = new Edge(0.4, 8, null, cd1);
            cd1.addIncomingEdge(edge21);
            cd1.addIncomingEdge(edgeExt1);

            cd2.addIncomingEdge(edgeExt2);
            cd2.addOutgoingEdge(edge21);
            nodes[0] = cd1;
            nodes[1] = cd2;

            GA ga = new GA(200, nodes, demand.length, 1600);
            ga.runGA();
            int[] solutions = ga.getBestSolution();
            for (int i = 0; i < nodes.length; i++) {
                System.out.println(solutions[i]);
                nodes[i].setBaseStockLevel(solutions[i]);
            }
            for (int i = 0; i < demand.length; i++) {
                for (int j = 0; j < nodes.length; j++) {
                    nodes[j].calculateInventoryLevel(i);
                    nodes[j].calculateOnOrderInventory(i);
                    nodes[j].calculateOrderAmount(i);

//                    System.out.println(i + ": inventory level " + nodes[j].getInventoryLevel(i));
//                    System.out.println(i + ": on order inventory " + nodes[j].getOnOrderInventory(i));
//                    System.out.println(i + ": order amount " + nodes[j].getOrderHistory(i) + "\n");
                }



            }
            cd1.calculateCostFunction();
            cd2.calculateCostFunction();
            double c = cd1.getCostFunction() + cd2.getCostFunction();

            System.out.println(c);


            for (int i = 0; i < demand.length; i++) {
                for (int j = 0; j < nodes.length; j++) {
                    nodes[j].calculateInventoryLevel(i);
                    nodes[j].calculateOnOrderInventory(i);
                    nodes[j].calculateOrderAmount(i);

//                    System.out.println(i + ": inventory level " + nodes[j].getInventoryLevel(i));
//                    System.out.println(i + ": on order inventory " + nodes[j].getOnOrderInventory(i));
//                    System.out.println(i + ": order amount " + nodes[j].getOrderHistory(i) + "\n");
                }



            }
            cd1.calculateCostFunction();
            cd2.calculateCostFunction();
            c = cd1.getCostFunction() + cd2.getCostFunction();
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
