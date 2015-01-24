package adapter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import structure.Edge;



@XmlRootElement(name = "node")
@XmlAccessorType (XmlAccessType.FIELD)
public class FrontNode {
	String name;
	int type;
	int id;

	// /Backendowe zmienne
	protected int periodLength;
	/**
	 * Array holding the values of the demand for this node for each day of the
	 * considered time period.
	 */
	protected int[] demand;

	/**
	 * Array holding the stock level for this node for each day of the
	 * considered time period. y(k)
	 */
	protected int[] inventoryLevel;

	/**
	 * The searched optimal value of the stock level (yd).
	 */
	protected int baseStockLevel;

	/**
	 * The on-order inventory is the number of units that we ordered in previous
	 * periods that we have not yet received (omega).
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

	/**
	 * The cost of having negative stock level (for each unit of the product).
	 */
	protected double negativeStockLevelCost;

	protected double costFunction;

	protected int initialStockLevel;

	protected List<Edge> outgoingEdges = new ArrayList<Edge>();
	protected List<Edge> incomingEdges = new ArrayList<Edge>();

	// constructors
	public FrontNode() {
		System.out
				.println("You seem to have called a default constructor - you sure what you are doing?");
		System.out.println("By the way no node was created!");
	}

	public FrontNode(String _name, int _type) {
		name = _name;
		type = _type;
	}

	public FrontNode(String name, int type,int[] demand, int baseStockLevel, int initialStockLevel,
			double holdingCost, double purchaseCost,
			double negativeStockLevelCost, int periodLength) throws Exception {
		this.name = name;
		this.type = type;
		this.initialStockLevel = initialStockLevel;

		this.periodLength = periodLength;
		this.demand = demand;
		this.baseStockLevel = baseStockLevel;
		this.holdingCost = holdingCost;
		this.purchaseCost = purchaseCost;

		inventoryLevel = new int[periodLength];
		onOrderInventory = new int[periodLength];
		orderHistory = new int[periodLength];

	}

	public FrontNode(String name, int type,int baseStockLevel, int initialStockLevel, double holdingCost,
			double purchaseCost, double negativeStockLevelCost, int periodLength) {
		this.name = name;
		this.type = type;
		this.initialStockLevel = initialStockLevel;
		this.baseStockLevel = baseStockLevel;
		this.holdingCost = holdingCost;
		this.purchaseCost = purchaseCost;

		inventoryLevel = new int[periodLength];
		onOrderInventory = new int[periodLength];
		orderHistory = new int[periodLength];
	}

	// methods for getting data about node
	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	// methods for setting data for node

	public void setName(String _name) {
		name = _name;
	}

	public void setType(int _type) {
		type = _type;
	}

	public void setId(int _id) {
		id = _id;
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", type=" + type + ", id=" + id + "]";
	}

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

	public int[] getOnOrderInventory() {
		return onOrderInventory;
	}

	public void setOnOrderInventory(int[] onOrderInventory) {
		this.onOrderInventory = onOrderInventory;
	}

	public int[] getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(int[] orderHistory) {
		this.orderHistory = orderHistory;
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

	public double getNegativeStockLevelCost() {
		return negativeStockLevelCost;
	}

	public void setNegativeStockLevelCost(double negativeStockLevelCost) {
		this.negativeStockLevelCost = negativeStockLevelCost;
	}

	public double getCostFunction() {
		return costFunction;
	}

	public void setCostFunction(double costFunction) {
		this.costFunction = costFunction;
	}

	public int getInitialStockLevel() {
		return initialStockLevel;
	}

	public void setInitialStockLevel(int initialStockLevel) {
		this.initialStockLevel = initialStockLevel;
	}

	public List<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(List<Edge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	public List<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	public void setIncomingEdges(List<Edge> incomingEdges) {
		this.incomingEdges = incomingEdges;
	}

	public int getPeriodLength() {
		return periodLength;
	}

	public void setPeriodLength(int periodLength) {
		this.periodLength = periodLength;
	}

	public int getId() {
		return id;
	}
}
