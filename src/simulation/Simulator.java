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
        Plotter p = new Plotter();

        //int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35, 20, 10, 5, 3, 14, 17, 29, 0, 5, 3, 18, 46, 12};
        //int[] demand = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
        int[] demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5};

        DistributionCenter cd1 = null;
        try {
            Node[] nodes = new Node[2];
            cd1 = new DistributionCenter(demand, 150, 150, 10, 30, 100, demand.length + 1);
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

            GA ga = new GA(200, nodes, demand.length, 300);
            ga.runGA();
            int[] solutions = ga.getBestSolution();
            for (int i = 0; i < nodes.length; i++) {
                nodes[i].setBaseStockLevel(solutions[i]);
            }
            for (int i = 0; i < demand.length; i++) {
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
            System.out.println(c);
            p.plotPointsWithIntegerXFromZero(cd1.getInventoryLevel(), "Inventory level y(k)");
            p.plotPointsWithIntegerXFromZero(cd1.getOrderHistory(), "Order history u(k)");
            p.draw("Fluctuating demand during 14 days | Delay = 2 | Demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
