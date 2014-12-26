package structure;

public class DistributionCenter extends Node{

	public DistributionCenter() {
		
	}

	public DistributionCenter(int[] demand, int baseStockLevel,
			double holdingCost, double purchaseCost) {
		super();
		this.demand = demand;
		this.baseStockLevel = baseStockLevel;
		this.holdingCost = holdingCost;
		this.purchaseCost = purchaseCost;
		
		inventoryLevel = new int[demand.length];
		onOrderInventory = new int[demand.length];
		orderHistory = new int[demand.length];
	}
	
}
