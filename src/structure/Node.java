package structure;

import java.util.ArrayList;
import java.util.List;

public abstract class Node{
	
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
	protected int[] onOrderInventory;
	
	protected int[] orderHistory;
	
	/**
	 * The cost of holding an item in the stock (pi h).
	 */
	protected double holdingCost;
	
	/**
	 * The cost of purchasing (delivery of?) an item (pi p).
	 */
	protected double purchaseCost;
	
	protected List<Edge> outgoingEdges = new ArrayList<Edge>();
	protected List<Edge> incomingEdges = new ArrayList<Edge>();
	
	// FIXME simple implementation for 1 incoming node
	public int calculateInventoryLevel(int periodIndex) {
		int periodIndexWithDelay = periodIndex - 1 - incomingEdges.get(0).getDelay();
		int orderHistoryUpTo = (periodIndexWithDelay >= 0) ? orderHistory[periodIndexWithDelay] : 0;
		
		
		inventoryLevel[periodIndex] = periodIndex > 0 ? 
				inventoryLevel[periodIndex-1] + orderHistoryUpTo - demand[periodIndex-1]
				: 20; // periodIndex[0] = 20 by default
		
		return 0;
	}
	
	// FIXME simple implementation for 1 incoming node
	public int calculateOrderAmount(int periodIndex) {
		int orderAmount = baseStockLevel - inventoryLevel[periodIndex] - onOrderInventory[periodIndex];
		
		orderHistory[periodIndex] = orderAmount;
		
		return orderAmount;
	}
	
	// FIXME simple implementation for 1 incoming node
	public int calculateOnOrderInventory(int periodIndex) {
		Edge edge = incomingEdges.get(0);
		int diff = periodIndex - edge.getDelay();
		int startIndex = diff > 0 ? diff : 0;
		
		int sum = 0;
		
		for(int i=startIndex; i<periodIndex; i++) {
			sum += orderHistory[i] * edge.getFraction();
		}
		
		onOrderInventory[periodIndex] = sum;
		
		return sum;
	}
	
	
	// Getters and setters

	public void addOutgoingEdge(Edge edge) {
		outgoingEdges.add(edge);
	}
	
	public void addIncomingEdge(Edge edge) {
		incomingEdges.add(edge);
	}
	
	public int[] getDemand() {
		return demand;
	}

	public void setDemand(int[] demand) {
		this.demand = demand;
	}

	public int getInventoryLevel(int periodIndex) {
		return inventoryLevel[periodIndex];
	}

	public int[] getInventoryLevel() {
		return inventoryLevel;
	}
	
//	public void setInventoryLevel(int[] inventoryLevel) {
//		this.inventoryLevel = inventoryLevel;
//	}

	public int getBaseStockLevel() {
		return baseStockLevel;
	}

	public void setBaseStockLevel(int baseStockLevel) {
		this.baseStockLevel = baseStockLevel;
	}

	public int getOnOrderInventory(int periodIndex) {
		return onOrderInventory[periodIndex];
	}
	
	public int[] getOnOrderInventory() {
		return onOrderInventory;
	}	

//	public void setOnOrderInventory(int[] onOrderInventory) {
//		this.onOrderInventory = onOrderInventory;
//	}

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

	public int getOrderHistory(int periodIndex) {
		return orderHistory[periodIndex];
	}
	
	public int[] getOrderHistory() {
		return orderHistory;
	}	

//	public void setOrderHistory(int[] orderHistory) {
//		this.orderHistory = orderHistory;
//	}
	
	
}
