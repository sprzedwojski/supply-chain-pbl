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
	protected int[] inventoryLevel;
	
	/**
	 * The searched optimal value of the stock level (yd).
	 */
	protected int baseStockLevel;
	
	/**
	 * The on-order inventory is the number of units
	 * that we ordered in previous periods that we have not yet received (omega).
	 */
	protected int onOrderInventory;
	
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

	public int[] getInventoryLevel() {
		return inventoryLevel;
	}

	public void setInventoryLevel(int[] inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}

	public int getBaseStockLevel() {
		return baseStockLevel;
	}

	public void setBaseStockLevel(int baseStockLevel) {
		this.baseStockLevel = baseStockLevel;
	}

	public int getOnOrderInventory() {
		return onOrderInventory;
	}

	public void setOnOrderInventory(int onOrderInventory) {
		this.onOrderInventory = onOrderInventory;
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
