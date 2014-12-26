package structure;

import java.util.ArrayList;

public  class Node {

	/**
	 * Array holding the values of the demand for this node for each day of the
	 * considered time period.
	 */
	protected int[] demand;

	/**
	 * Array holding the stock level for this node for each day of the
	 * considered time period.
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
	protected int onOrderInventory;

	/**
	 * The cost of holding an item in the stock (pi h).
	 */
	protected double holdingCost;

	/**
	 * The cost of purchasing (delivery of?) an item (pi p).
	 */
	protected double purchaseCost;

	protected int[] orderLevel;
	public int[] getOrderLevel() {
		return orderLevel;
	}

	public void setOrderLevel(int[] orderLevel) {
		this.orderLevel = orderLevel;
	}

	protected ArrayList<Edge> incomingEdges;
	protected ArrayList<Edge> outcomingEdges;

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

	public Node() {

	}

	public Node(int[] demand) {
		this.demand = demand;
		initArrays();
	}

	public Node(int considerPeriodLength) {
		this.demand = new int[considerPeriodLength];
		initArrays();
	}

	public Node(int considerPeriodLength, int initStockLevel, double holdingCost) {

		this.holdingCost = holdingCost;
		this.demand = new int[considerPeriodLength];
		initArrays();
		this.inventoryLevel[0] = initStockLevel;
	}

	public Node(int[] demand, int initStockLevel, double holdingCost) {
		this.demand = demand;

		this.holdingCost = holdingCost;
		initArrays();
		this.inventoryLevel[0] = initStockLevel;
	}

	private void initArrays() {
		this.inventoryLevel = new int[this.demand.length];
		this.orderLevel = new int[this.demand.length];
		outcomingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
	}

	public void calculateOrderLevel(int time) {
		calculateOnOrderInventory(time);
		orderLevel[time] = this.baseStockLevel - this.inventoryLevel[time]
				- this.onOrderInventory;
//		System.out.println("bs " +this.baseStockLevel );
//		System.out.println("in " +this.inventoryLevel[time] );
//		System.out.println("oi " +orderLevel[time] );
	}

	protected void calculateOnOrderInventory(int time) {
		int sum = 0;
		for(int i =0; i<incomingEdges.size(); i++){
		for (int j = time-incomingEdges.get(i).getDelay()< 0 ? 0 : time-incomingEdges.get(i).getDelay(); j < time; j++) {
			sum += incomingEdges.get(i).getFraction()*orderLevel[j];
		}
		}

//		System.out.println("omega" +sum );
		this.onOrderInventory = sum;
	
	}

	public void calculateInventoryLevel(int time) {
		if(this.inventoryLevel.length>time+1){
		this.inventoryLevel[time + 1] = this.inventoryLevel[time]
				- this.demand[time] + deliveredOrder(time) - servedOrder(time);
//		System.out.println("il " + this.inventoryLevel[time + 1]);
		}
		
	}

	protected int deliveredOrder(int time) {
		int sum = 0;
		for (int i = 0; i < incomingEdges.size(); i++) {
			if (time - incomingEdges.get(i).getDelay() >= 0) {
				sum += incomingEdges.get(i).getFraction()
						* this.orderLevel[time - incomingEdges.get(i).getDelay()];
			}
		}
//		System.out.println("delivered" +sum );
		return sum;
	}

	public ArrayList<Edge> getIncomingEdges() {
		if(incomingEdges==null){
			incomingEdges = new ArrayList<Edge>();
		}
		return incomingEdges;
	}

	public void setIncomingEdges(ArrayList<Edge> incomingEdges) {
		this.incomingEdges = incomingEdges;
	}

	public ArrayList<Edge> getOutcomingEdges() {
		if(outcomingEdges==null){
			outcomingEdges = new ArrayList<Edge>();
		}
		return outcomingEdges;
	}

	public void setOutcomingEdges(ArrayList<Edge> outcomingEdges) {
		this.outcomingEdges = outcomingEdges;
	}

	protected int servedOrder(int time) {
		int sum = 0;
		for (int i = 0; i < outcomingEdges.size(); i++) {
			
				sum += outcomingEdges.get(i).getFraction()
						* outcomingEdges.get(i).getReceiver().getOrderLevel()[time];
			
		}
//		System.out.println("served" +sum );
		return sum;
	}

	public int calculateCosts() {
		int sum = 0;
		for (int time = 0; time < this.inventoryLevel.length; time++) {
			
			sum += this.holdingCost * this.inventoryLevel[time]
					+ this.purchaseCost * this.orderLevel[time];

		}
//		System.out.println("cost " + sum);
		return sum;
	}

}
