package simulation;

import plotting.Plotter;
import structure.DistributionCenter;
import structure.Edge;

/**
 * Main class used for running simulations.
 * @author szymon
 *
 */
public class Simulator {
	
	public static void main(String[] args) {
		Plotter p = new Plotter();
		
		//int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35, 20, 10, 5, 3, 14, 17, 29, 0, 5, 3, 18, 46, 12};
		//int[] demand = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		int[] demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5};
		
		DistributionCenter node = new DistributionCenter(demand, 60, 0, 0);
		Edge edge = new Edge(1.0, 2, null, node);
		node.addIncomingEdge(edge);
		
		for(int i=0; i<demand.length; i++) {
			
			node.calculateInventoryLevel(i);
			node.calculateOnOrderInventory(i);
			node.calculateOrderAmount(i);
			
			System.out.println(i + ": inventory level " + node.getInventoryLevel(i));
			System.out.println(i + ": on order inventory " + node.getOnOrderInventory(i));
			System.out.println(i + ": order amount " + node.getOrderHistory(i) + "\n");
		}
		
		p.plotPointsWithIntegerXFromZero(node.getInventoryLevel(), "Inventory level y(k)");
		p.plotPointsWithIntegerXFromZero(node.getOrderHistory(), "Order history u(k)");
		p.draw("Fluctuating demand during 14 days | Delay = 2 | Demand = {5, 5, 10, 10, 15, 15, 20, 20, 30, 30, 40, 50, 20, 10, 5}");
	}
	
}
