package simulation;

import structure.DistributionCenter;
import structure.Edge;

/**
 * Main class used for running simulations.
 * @author szymon
 *
 */
public class Simulator {

	public static void main(String[] args) {
		int[] demand = {10, 5, 15, 20, 45, 10, 5, 10, 50, 35};
		
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
	}
	
}
