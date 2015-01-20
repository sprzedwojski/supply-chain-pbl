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
            cd1 = new DistributionCenter(demand, 70,0, 10, 30, demand.length);
            DistributionCenter cd2 = new DistributionCenter(69, 0, 2, 5, demand.length);

            Edge edge21 = new Edge(0.6, 2, cd2, cd1);

            Edge edgeExt2 = new Edge(1, 2, null, cd2);
            Edge edgeExt1 = new Edge(0.4, 1, null, cd1);
            cd1.addIncomingEdge(edge21);
            cd1.addIncomingEdge(edgeExt1);

            cd2.addIncomingEdge(edgeExt2);
            cd2.addOutgoingEdge(edge21);
            nodes[0]=cd1;
            nodes[1]=cd2;

      //     GA ga = new GA(200, nodes, demand.length,500);
         //   ga.runGA();
           /*for (int i = 0; i <= demand.length; i++) {

                cd1.calculateInventoryLevel(i);
                cd1.calculateOnOrderInventory(i);
                cd1.calculateOrderAmount(i);

                cd2.calculateInventoryLevel(i);
                cd2.calculateOnOrderInventory(i);
                cd2.calculateOrderAmount(i);
                System.out.println("SIM \n");

                System.out.println(i + ": inventory level " + cd1.getInventoryLevel(i));
                System.out.println(i + ": on order inventory " + cd1.getOnOrderInventory(i));
                System.out.println(i + ": order amount " + cd1.getOrderHistory(i) + "\n");

                System.out.println(i + ": inventory level 2 " + cd2.getInventoryLevel(i));
                System.out.println(i + ": on order inventory 2 " + cd2.getOnOrderInventory(i));
                System.out.println(i + ": order amount 2 " + cd2.getOrderHistory(i) + "\n");

            }
            cd1.calculateCostFunction();
            cd2.calculateCostFunction();
            double c = cd1.getCostFunction() + cd2.getCostFunction();
            System.out.println(c);
            p.plotPointsWithIntegerXFromZero(cd1.getInventoryLevel(), "Inventory level y(k)");
            p.plotPointsWithIntegerXFromZero(cd1.getOrderHistory(), "Order history u(k)");
            p.draw("Fluctuating demand during 14 days | Delay = 2 | Demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5}");
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
           }

}
