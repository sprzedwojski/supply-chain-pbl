package ga;

/**
 * Created by Marta on 2015-01-10.
 */

import structure.Node;

import java.util.Random;

public class Chromosome {
    private Gene[] genes;
    private double fitnessFunctionResult;
    private double selectionProbality;
    private double distributionFunction;

    /**
     * Getter
     *
     * @return distribution for given solution
     */
    public double getDistributionFunction() {
        return distributionFunction;
    }

    /**
     * @param distributionFunction calculated distribution
     */
    public void setDistributionFunction(double distributionFunction) {
        this.distributionFunction = distributionFunction;
    }

    /**
     * Getter
     *
     * @return selection Probability
     */
    public double getSelectionProbality() {
        return selectionProbality;
    }

    /**
     * Setter calculate selection probability fitness function/ overall ff for
     * population
     *
     * @param overallFF overall fitness function for population
     */
    public void calculateSelectionProbality(double overallFF) {
        this.selectionProbality = this.fitnessFunctionResult / overallFF;

    }

    /**
     * Getter
     *
     * @return array of byte, genes
     */
    public Gene[] getGenes() {
        return genes;
    }

    /**
     * Setter
     *
     * @param genes
     */
    public void setGenes(Gene[] genes) {
        this.genes = genes;
    }

    public double getFitnessFunctionResult() {
        return fitnessFunctionResult;
    }

    public void setFitnessFunctionResult(double fitnessFunctionResult) {
       // this.fitnessFunctionResult = fitnessFunctionResult;
    }


    public void generateGenes(Node[] nodes, int numberOfBits) {
        genes = new Gene[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            Gene gene = new Gene(numberOfBits, nodes[j]);
            genes[j] = gene;

        }
    }

    public void calculateFittnessFunction() {
        double totalCostFunction = 0;
        for (int singleGene = 0; singleGene < genes.length; singleGene++) {
          //  System.out.print(genes[singleGene].getValue() + " value ");
            totalCostFunction += genes[singleGene].getCostFunction();
           // System.out.print(genes[singleGene].getCostFunction()+" cost single\n ");
        }
        fitnessFunctionResult = 1.0/totalCostFunction;
       // System.out.print("\n ");
    }

    public void inventoryCalculation(int demandPeriodLength) {
        System.out.println(genes.length);
        for (int singleGene = 0; singleGene < genes.length; singleGene++) {
            System.out.println(singleGene);
            genes[singleGene].setBaseStockLevel();
        }
        for (int time = 0; time < demandPeriodLength; time++) {

            for (int singleGene = 0; singleGene < genes.length; singleGene++) {
                genes[singleGene].performPeriodCalc(time);
            }
        }
    }
}

