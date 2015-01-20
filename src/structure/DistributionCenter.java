package structure;


public class DistributionCenter extends Node {

    public DistributionCenter() {

    }

    /**
     * 
     * @param demand
     * @param baseStockLevel
     * @param initialStockLevel
     * @param holdingCost
     * @param purchaseCost
     * @param negativeStockLevelCost
     * @param periodLength Musi być równy: demand.length+1 (do sprawdzania po stronie GUI)
     * @throws Exception
     */
    public DistributionCenter(int[] demand, int baseStockLevel, int initialStockLevel,
                              double holdingCost, double purchaseCost, double negativeStockLevelCost,
                              int periodLength) throws Exception {
        super();
//        if (demand.length+1 != periodLength) {
//            throw new Exception();
//        }
        this.initialStockLevel = initialStockLevel;

        this.demand = demand;
        this.baseStockLevel = baseStockLevel;
        this.holdingCost = holdingCost;
        this.purchaseCost = purchaseCost;

        inventoryLevel = new int[periodLength];
        onOrderInventory = new int[periodLength];
        orderHistory = new int[periodLength];

    }

    public DistributionCenter( int baseStockLevel, int initialStockLevel, double holdingCost, 
    		double purchaseCost, double negativeStockLevelCost, int periodLength) {
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
