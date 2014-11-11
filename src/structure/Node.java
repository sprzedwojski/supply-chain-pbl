package structure;

public abstract class Node {
	
	/**
	 * Array holding the values of the demand for this node
	 * for each day of the considered time period.
	 */
	protected int[] demand;
	
	/**
	 * Array holding the stock level for this node
	 * for each day of the considered time period.
	 */
	protected int[] stockLevel;
	
	/**
	 * The searched optimal value of the stock level (yd).
	 */
	protected int referenceStockLevel;
	
	/**
	 * The cost of holding an item in the stock (pi h).
	 */
	protected double holdingCost;
	
	/**
	 * The cost of purchasing (delivery of?) an item (pi p).
	 */
	protected double purchaseCost;
	
	

	public int[] getDemand() {
		return demand;
	}

	public void setDemand(int[] demand) {
		this.demand = demand;
	}

	public int[] getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int[] stockLevel) {
		this.stockLevel = stockLevel;
	}

	public int getReferenceStockLevel() {
		return referenceStockLevel;
	}

	public void setReferenceStockLevel(int referenceStockLevel) {
		this.referenceStockLevel = referenceStockLevel;
	}

	public double getHoldingCost() {
		return holdingCost;
	}

	public void setHoldingCost(double holdingCost) {
		this.holdingCost = holdingCost;
	}

	public double getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(double purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	
	
}
