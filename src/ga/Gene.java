package ga;

import structure.Node;

/**
 * Created by Marta on 2015-01-10.
 */
public class Gene {

    private Node node;
    private byte[] gene;
    public Gene(){

    }

    public Gene(byte[] gene, Node node) {

        this.node = node;
        this.gene = gene;

    }
    public Gene(int numOfBits, Node node) {

        this.node = node;
        gene = new byte[numOfBits];
        for (int j = 0; j < gene.length; j++) {
            byte singleGene = (byte) Math.round(Math.random());
            gene[j] = singleGene;

        }

    }

    public void setGene(byte[] gene) {
        this.gene = gene;
    }

    public byte[] getGene() {
        return gene;
    }
    public int getGeneIntValue() {
        return BitIntConversion.binaryToInt(gene);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    public void setBaseStockLevel(){
       int baseStockLevel = BitIntConversion.binaryToInt(gene);
        System.out.println("Base stock Level "+baseStockLevel);
        this.node.setBaseStockLevel(baseStockLevel);

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
