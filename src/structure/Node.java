package structure;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    /**
     * Array holding the values of the demand for this node
     * for each day of the considered time period.
     */
    protected int[] demand;

    /**
     * Array holding the stock level for this node
     * for each day of the considered time period.
     * y(k)
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
    /**
     * u(k)
     */
    protected int[] orderHistory;

    /**
     * The cost of holding an item in the stock (pi h).
     */
    protected double holdingCost;

    /**
     * The cost of purchasing (delivery of?) an item (pi p).
     */
    protected double purchaseCost;
    protected double costFunction;


    protected int initialStockLevel;

    protected List<Edge> outgoingEdges = new ArrayList<Edge>();
    protected List<Edge> incomingEdges = new ArrayList<Edge>();


    public int calculateInventoryLevel(int periodIndex) {
        int incomingOrders = getAllIncomingOrders(periodIndex);
        int outgoingOrders = getAllOutgoingOrders(periodIndex);
        if( periodIndex > 0){
            int singleDemand = demand == null? 0 : demand[periodIndex - 1];
            inventoryLevel[periodIndex] = inventoryLevel[periodIndex - 1] + incomingOrders - outgoingOrders - singleDemand; // periodIndex[0] = 20 by default
        }
        else{
            inventoryLevel[periodIndex] = initialStockLevel;
        }


        return 0;
    }

    private int getAllIncomingOrders(int periodIndex) {
        int incomingOrders = 0;
        for (Edge edge : incomingEdges) {
            int periodIndexWithDelay = periodIndex - 1 - edge.getDelay();
            incomingOrders += (periodIndexWithDelay >= 0) ? edge.getFraction() * orderHistory[periodIndexWithDelay] : 0;
        }
        return incomingOrders;
    }

    private int getAllOutgoingOrders(int periodIndex) {
        int outgoingOrders = 0;
        if (periodIndex - 1 >= 0) {
            for (Edge edge : outgoingEdges) {

                outgoingOrders += edge.getFraction() * edge.getReceiver().getOrderHistory()[periodIndex - 1];
            }
        }
        return outgoingOrders;
    }


    public int calculateOrderAmount(int periodIndex) {
        int orderAmount = baseStockLevel - inventoryLevel[periodIndex] - onOrderInventory[periodIndex];
        orderAmount = orderAmount < 0 ? 0 : orderAmount;
        orderHistory[periodIndex] = orderAmount;

        return orderAmount;
    }

    public int calculateOnOrderInventory(int periodIndex) {
        int sum = 0;
        for (Edge edge : incomingEdges) {
            int diff = periodIndex - edge.getDelay();
            int startIndex = diff > 0 ? diff : 0;

            for (int i = startIndex; i < periodIndex; i++) {

                sum += orderHistory[i] * edge.getFraction();
            }
        }
        onOrderInventory[periodIndex] = sum;

        return sum;
    }

    /*TODO set purchase cost for edge not for node */
    public double calculateCostFunction() {

        double cost = 0;
        for (int i = 0; i < inventoryLevel.length; i++) {

            cost += (inventoryLevel[i]<0? 0: holdingCost * inventoryLevel[i]) + purchaseCost * orderHistory[i];
        }

        costFunction = cost;
        return costFunction;
    }

    // Getters and setters
    public int getInitialStockLevel() {
        return initialStockLevel;
    }

    public void setInitialStockLevel(int initialStockLevel) {
        this.initialStockLevel = initialStockLevel;
    }

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

    public double getCostFunction() {
        return costFunction;
    }
//	public void setOrderHistory(int[] orderHistory) {
//		this.orderHistory = orderHistory;
//	}


}
