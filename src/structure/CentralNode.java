package structure;

public class CentralNode extends Node{

	public CentralNode() {
		
	}

	public CentralNode(int[] demand, int[] stockLevel, int referenceStockLevel,
			double holdingCost, double purchaseCost) {
		this.demand = demand;
		this.stockLevel = stockLevel;
		this.referenceStockLevel = referenceStockLevel;
		this.holdingCost = holdingCost;
		this.purchaseCost = purchaseCost;
	}
	
}
