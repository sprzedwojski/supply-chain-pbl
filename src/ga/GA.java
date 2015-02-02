package ga;

import structure.Node;

import java.util.ArrayList;

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
    private ArrayList<Chromosome> bestSolutions;

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
        bestSolutions = new ArrayList<Chromosome>();
        double averageFFCurrent = Double.POSITIVE_INFINITY;
        double averageFFPrevious = Double.NEGATIVE_INFINITY;
        while (count < 1000) {
            averageFFPrevious = averageFFCurrent;
            algorithm();
            averageFFCurrent = getAverage();
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

        double maximum = Double.NEGATIVE_INFINITY;
        for (Chromosome solution : currentPopulation.getSolutions()) {
            int[] bestStockLevel = new int[solution.getGenes().length];
            for (int i = 0; i < solution.getGenes().length; i++) {
                bestStockLevel[i] = solution.getGenes()[i].getGeneIntValue();
            }
            if (maximum < solution.getFitnessFunctionResult()) {
                maximum = solution.getFitnessFunctionResult();
                bestSolution = solution;

            }
        }

        bestSolutions.add(bestSolution);


    }

    private double getAverage() {
        double avg = 0;
        for (Chromosome solution : currentPopulation.getSolutions()) {
            avg += 1d/solution.getFitnessFunctionResult();
        }
        return avg / currentPopulation.getSolutions().length;
    }



    public int[] getBestSolution() {
        int[] bestStockLevel = new int[bestSolution.getGenes().length];
        for (int i = 0; i < bestSolution.getGenes().length; i++) {
            bestStockLevel[i] = bestSolution.getGenes()[i].getGeneIntValue();
            System.out.println(bestStockLevel[i]);
        }
        return bestStockLevel;
    }
}
