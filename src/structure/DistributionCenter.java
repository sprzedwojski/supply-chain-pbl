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

        inventoryLevel = new int[demand.length];
        onOrderInventory = new int[demand.length];
        orderHistory = new int[demand.length];

    }

    public DistributionCenter( int baseStockLevel, int initialStockLevel,
                              double holdingCost, double purchaseCost,int periodLength) {
        super();
        this.initialStockLevel = initialStockLevel;
        this.baseStockLevel = baseStockLevel;
        this.holdingCost = holdingCost;
        this.purchaseCost = purchaseCost;

        inventoryLevel = new int[periodLength];
        onOrderInventory = new int[periodLength];
        orderHistory = new int[periodLength];
    }

}
