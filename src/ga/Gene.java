package ga;

import structure.Node;

/**
 * Created by Marta on 2015-01-10.
 */
public class Gene {

    private Node node;
    private int value;
    public Gene(){

    }

    public Gene(int value, Node node) {

        this.node = node;
        this.value = value;

    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    public void setBaseStockLevel(){
        this.node.setBaseStockLevel(value);
    }
    public void performPeriodCalc(int currentTime){

        this.node.calculateInventoryLevel(currentTime);
        this.node.calculateOnOrderInventory(currentTime);
        this.node.calculateOrderAmount(currentTime);


    }

    public double getCostFunction(){
        return this.node.calculateCostFunction();
        //System.out.println("end");
        //return this.node.getCostFunction();
    }


}
