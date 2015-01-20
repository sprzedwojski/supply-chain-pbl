package ga;

import structure.Node;

/**
 * Created by Marta on 2015-01-10.
 */
public class Population {
    private Chromosome[] solutions;
    private double overallFitnessFunction;

    //Constructors
    public Population() {

    }

    public Population(int populationSize, Node[] nodes, int numOfBits) {
        solutions = new Chromosome[populationSize];

        initPopulation(nodes, numOfBits);
    }

    public Population(int populationSize) {
        solutions = new Chromosome[populationSize];
    }

    //init population
    //set initial genes

    private void initPopulation(Node[] nodes, int numofBits) {
        for (int i = 0; i < solutions.length; i++) {

            Chromosome chromosome = new Chromosome();
            chromosome.generateGenes(nodes, numofBits);
            solutions[i] = chromosome;
        }
    }
//Getters setters

    /**
     * Getter
     *
     * @return overall fitness function for whole population
     */
    public double getOverallFitnessFunction() {
        return overallFitnessFunction;
    }

    /**
     * getter
     *
     * @return array of chromosomes - solutions for given population
     */
    public Chromosome[] getSolutions() {
        return solutions;
    }

    /**
     * setter
     *
     * @param solutions array of chromosomes- solutions for population
     */
    public void setSolutions(Chromosome[] solutions) {
        this.solutions = solutions;
    }
    //End

    /**
     * Calculation of overall fitness function sum of fitness function for all
     * chromosomes
     */
    private void calculateOverallFitnessFunction() {
        this.overallFitnessFunction = 0;
        int i = 0;
        for (Chromosome solution : solutions) {


            this.overallFitnessFunction += solution.getFitnessFunctionResult();
        }

    }

    /**
     * Set selection probability for all chromosomes in population
     */
    private void setSelectionProbabilityOfChromosomes() {

        for (Chromosome solution : solutions) {


            solution.calculateSelectionProbality(overallFitnessFunction);
        }
    }

    /**
     * cacluate distribution function for all chromosomes in population
     */
    private void calculateDistributionFunction() {
        double distribution = 0;

        for (int i = 0; i < solutions.length; i++) {
            distribution += solutions[i].getSelectionProbality();
            solutions[i].setDistributionFunction(distribution);
        }
    }


    public void performCalculationsToRoulette(int demandPeriod) {
        calculateChromosomesFitnessFunction(demandPeriod);
        calculateOverallFitnessFunction();
        setSelectionProbabilityOfChromosomes();
        calculateDistributionFunction();
    }

    private void calculateChromosomesFitnessFunction(int demandTimePeriod) {

        for (Chromosome solution : solutions) {

            solution.inventoryCalculation(demandTimePeriod);
            solution.calculateFittnessFunction();

        }
    }
}

