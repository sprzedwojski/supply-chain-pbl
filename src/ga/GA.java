package ga;

import structure.Node;

/**
 * Created by Marta on 2015-01-10.
 */
public class GA {


    private Chromosome[] newSolutions;
    private int populationSize;
    private Population currentPopulation, nextPopulation;
    private GeneticOperator geneticOperator;
    public Node[] nodes;
    public int demandPeriodLength;
    private Chromosome bestSolution;


    public GA(int populationSize, Node[] nodes, int demandPeriodLength, int intervalEnd) {
        this.populationSize = populationSize;
        this.nodes = nodes;
        this.demandPeriodLength = demandPeriodLength;
        geneticOperator = new GeneticOperator();
        createStartingPopulation(intervalEnd);
    }


    private void createStartingPopulation(int intervalEnd) {
        int numOfBits = BitIntConversion.calculateNumOfBits(intervalEnd);
        this.currentPopulation = this.nextPopulation = new Population(this.populationSize, this.nodes, numOfBits);
    }

    public void runGA() {
        int count = 0;
        while (count < 500) {
            algorithm();
            count++;
            getBestFit();
        }
    }

    /**
     * set next population as current calculate fitness function for each
     * solution calculate overall fitness function for population calculate
     * distribution function for chromosomes choose genetic operation if i
     * (initially 0) is equal to population size set new solutions for next
     * population
     */
    private void algorithm() {

        currentPopulation = nextPopulation;
        this.newSolutions = new Chromosome[this.populationSize];
        this.currentPopulation.performCalculationsToRoulette(demandPeriodLength);
        int iteration = 0;
        while (iteration != populationSize) {
            int chromosomeNum = iteration;
            Chromosome[] chromosomesToInsert = geneticOperator.performGeneticOperation(iteration, currentPopulation);
            for (int i = 0; i < chromosomesToInsert.length; i++) {
                newSolutions[chromosomeNum + i] = chromosomesToInsert[i];
            }
            iteration += chromosomesToInsert.length;
        }
        nextPopulation = new Population(this.populationSize);
        nextPopulation.setSolutions(newSolutions);

    }


    /**
     * Get the best solution after genetic algorithm terminates search for
     * biggest fitness function and return solution for which it assign
     */
    private void getBestFit() {
        bestSolution = null;
        double bestFF = Double.NEGATIVE_INFINITY;
        double maximum = Double.NEGATIVE_INFINITY;
        for (Chromosome solution : currentPopulation.getSolutions()) {
            if (maximum < solution.getFitnessFunctionResult()) {
                maximum = solution.getFitnessFunctionResult();
                bestSolution = solution;
            }
        }

       // System.out.println(1.0 / bestSolution.getFitnessFunctionResult());
    }

    public int[] getBestSolution() {
        int[] bestStockLevel = new int[bestSolution.getGenes().length];
        for (int i = 0; i < bestSolution.getGenes().length; i++) {
            bestStockLevel[i]=bestSolution.getGenes()[i].getGeneIntValue();
            System.out.println(bestStockLevel[i]);
        }
        return bestStockLevel;
    }
}
