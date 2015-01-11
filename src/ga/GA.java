package ga;

import structure.Node;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marta on 2015-01-10.
 */
public class GA {

    private double mutationProbability = 0.2;
    private double crossingProbability = 0.4;
    private Chromosome[] newSolutions;
    private static int iteration;
    private int populationSize;
    private Population currentPopulation, nextPopulation;
    private ArrayList<Double> averageFF;
    private static double threshold = 0.00001;
    public Node[] nodes;
    public int demandPeriodLength;


    public GA(){

    }
    public GA(int populationSize, Node[] nodes, int demandPeriodLength){
        this.populationSize = populationSize;
        this.currentPopulation = this.nextPopulation = new Population(populationSize, nodes);
        this.nodes = nodes;
        this.averageFF = new ArrayList<Double>();
        this.demandPeriodLength = demandPeriodLength;
    }

    public void runGA() {
        /**/double averageFFDiff = Double.POSITIVE_INFINITY;
        int count = 0;
        while (count < 100) {
            algorithm();
            count++;
          /*  if (averageFF.size() > 2) {
                averageFFDiff = Math.abs(averageFF.get(averageFF.size() - 1)
                        - averageFF.get(averageFF.size() - 2));

            }*/
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

        iteration = 0;
        while (iteration != populationSize) {
           chooseGeneticOperation();
         /*   newSolutions[iteration] = mutation();
            iteration++;*/
        }
        nextPopulation = new Population(this.populationSize);
        nextPopulation.setSolutions(newSolutions);

    }

    /**
     * Choose genetic operation from mutation, crossing and reproduction basing
     * on predefined probabilities increase i, if crossing by 2, else by 1 add
     * new solutions to new population
     */
    private void chooseGeneticOperation() {
        Random random = new Random();
        double randomValue = random.nextDouble();
        if (randomValue < crossingProbability
                && populationSize - iteration >= 2) {
            Chromosome[] chromosomeToInsert = crossing();
            System.out.println("crossing");
            newSolutions[iteration] = chromosomeToInsert[0];
            newSolutions[iteration + 1] = chromosomeToInsert[1];
            iteration += 2;
        } else if (randomValue < (crossingProbability + mutationProbability)) {
            newSolutions[iteration] = mutation();
            System.out.println("mutation "+iteration);
            iteration++;
        } else {
            System.out.println("reproduction");
            newSolutions[iteration] = reproduction();
            iteration++;
        }

    }

    /**
     * Reproduction Choose solution using roulette method Copy current solution
     * to the new one
     *
     * @return new solution
     */
    private Chromosome reproduction() {

        Chromosome solution = rulette(currentPopulation.getSolutions());
        Gene[] genes = new Gene[solution.getGenes().length];
        System.arraycopy(solution.getGenes(), 0, genes, 0, genes.length);
        Chromosome newSolution = new Chromosome();
        newSolution.setGenes(genes);
        return newSolution;

    }

    /**
     * Mutation choose solution using roulette method copy a genes array to new
     * solution (chromosome) randomly choose entry in the gene array of new
     * solution switch this entry to the opposite
     *
     * @return new solution
     */
    private Chromosome mutation() {
        Chromosome solution = rulette(currentPopulation.getSolutions());
        Chromosome newSolution = new Chromosome();
        int max = solution.getGenes().length ;
        Random random = new Random();
        int genePosition1 = random.nextInt(max);
        int genePosition2=genePosition1;
        while(genePosition2==genePosition1) {
            genePosition2 = random.nextInt(max);
        }

        Gene[] gene = new Gene[solution.getGenes().length];
        System.arraycopy(solution.getGenes(), 0, gene, 0,
                solution.getGenes().length);
        int stockLevel1 = gene[genePosition1].getValue();
        int stockLevel2 = gene[genePosition2].getValue();

        gene[genePosition1] = new Gene(stockLevel2, gene[genePosition1].getNode());
        gene[genePosition2]=new Gene(stockLevel1, gene[genePosition2].getNode());
        newSolution.setGenes(gene);

        return newSolution;

    }

    /**
     * Crossing Choose two solutions using roulette method randomly choose entry
     * perform crossing
     *
     * @return array of two new solution, chromosome
     */
    private Chromosome[] crossing() {
        Chromosome firstSolutionToCross = rulette(currentPopulation
                .getSolutions());
        Chromosome secondSolutionToCross = rulette(currentPopulation
                .getSolutions());
        int max = firstSolutionToCross.getGenes().length ;
        Random random = new Random();
        int value = random.nextInt(max);
        Chromosome firstResult = new Chromosome();

        firstResult.setGenes(doCross(firstSolutionToCross.getGenes(),
                secondSolutionToCross.getGenes(), value));
        Chromosome secondResult = new Chromosome();

        secondResult.setGenes(doCross(secondSolutionToCross.getGenes(),
                firstSolutionToCross.getGenes(), value));
        Chromosome[] chromosomesToReturn = new Chromosome[2];
        chromosomesToReturn[0] = firstResult;
        chromosomesToReturn[1] = secondResult;
        return chromosomesToReturn;
    }

    /**
     * @param firstSolution  first array which part is copy
     * @param secondSolution second array which part is copy
     * @param value          index of entry
     * @return array of crossed solutions
     */
    private Gene[] doCross(Gene[] firstSolution, Gene[] secondSolution, int value) {
        Gene[] gene = new Gene[firstSolution.length];
        for (int i = 0; i < value; i++) {
            gene[i] = new Gene(firstSolution[i].getValue(), firstSolution[i].getNode());

        }
        for (int j = value; j < secondSolution.length; j++) {
            gene[j] = new Gene(secondSolution[j].getValue(), secondSolution[j].getNode());

        }
        return gene;

    }



    /**
     * Roulette selection method basing on distribution function of each
     * chromosome
     *
     * @param solutions
     * @return selected solution
     */
    private Chromosome rulette(Chromosome[] solutions) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        if (randomValue < solutions[0].getDistributionFunction()) {
            return solutions[0];
        }
        for (int i = 1; i < solutions.length; i++) {

            if (solutions[i - 1].getDistributionFunction() < randomValue
                    && randomValue < solutions[i].getDistributionFunction()) {
                return solutions[i];
            }
        }

        return solutions[0];

    }

    /**
     * Get the best solution after genetic algorithm terminates search for
     * biggest fitness function and return solution for which it assign
     */
    private void getBestFit() {
        Chromosome best = null;
        double bestFF = Double.NEGATIVE_INFINITY;
        double maximum = Double.NEGATIVE_INFINITY;
        for (Chromosome solution : currentPopulation.getSolutions()) {

            if (maximum < solution.getFitnessFunctionResult()) {
                maximum = solution.getFitnessFunctionResult();
                best = solution;
            }
        }
        for (int i = 0; i < best.getGenes().length; i++) {
            System.out.println("best "+best.getGenes()[i].getValue());
        }
        System.out.println(1.0/best.getFitnessFunctionResult());
    }
}
