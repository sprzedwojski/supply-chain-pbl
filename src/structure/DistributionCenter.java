package structure;

public class DistributionCenter extends Node{

	public DistributionCenter() {
		
	}

	public DistributionCenter(int[] demand, int[] inventoryLevel, int baseStockLevel,
			double holdingCost, double purchaseCost) {
		this.demand = demand;
		this.inventoryLevel = inventoryLevel;
		this.baseStockLevel = baseStockLevel;
		this.holdingCost = holdingCost;
		this.purchaseCost = purchaseCost;
	}
	
}
