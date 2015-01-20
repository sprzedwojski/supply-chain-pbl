package structure;


public class DistributionCenter extends Node {

    public DistributionCenter() {

    }

    public DistributionCenter(int[] demand, int baseStockLevel, int initialStockLevel,
                              double holdingCost, double purchaseCost, int periodLength) throws Exception {
        super();
        if (demand.length != periodLength) {
            throw new Exception();
        }
        this.initialStockLevel = initialStockLevel;

        this.demand = demand;
        this.baseStockLevel = baseStockLevel;
        this.holdingCost = holdingCost;
        this.purchaseCost = purchaseCost;

        inventoryLevel = new int[demand.length+1];
        onOrderInventory = new int[demand.length+1];
        orderHistory = new int[demand.length+1];

    }

    public DistributionCenter( int baseStockLevel, int initialStockLevel,
                              double holdingCost, double purchaseCost,int periodLength) {
        super();
        this.initialStockLevel = initialStockLevel;
        this.baseStockLevel = baseStockLevel;
        this.holdingCost = holdingCost;
        this.purchaseCost = purchaseCost;

        inventoryLevel = new int[periodLength+1];
        onOrderInventory = new int[periodLength+1];
        orderHistory = new int[periodLength+1];
    }

}
