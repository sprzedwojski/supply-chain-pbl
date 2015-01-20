package ga;

import structure.Node;

import java.util.Random;

/**
 * Created by Marta on 2015-01-10.
 */
public class GA {

    private double mutationProbability = 0.3;
    private double crossingProbability = 0.2;
    private Chromosome[] newSolutions;
    private int populationSize;
    private Population currentPopulation, nextPopulation;

    public Node[] nodes;
    public int demandPeriodLength;


    public GA(int populationSize, Node[] nodes, int demandPeriodLength, int intervalEnd) {
        this.populationSize = populationSize;
        this.nodes = nodes;
        this.demandPeriodLength = demandPeriodLength;
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
           iteration=chooseGeneticOperation(iteration);
        }
        nextPopulation = new Population(this.populationSize);
        nextPopulation.setSolutions(newSolutions);

    }

    /**
     * Choose genetic operation from mutation, crossing and reproduction basing
     * on predefined probabilities increase i, if crossing by 2, else by 1 add
     * new solutions to new population
     */
    private int chooseGeneticOperation(int iteration) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        if (randomValue < crossingProbability
                && populationSize - iteration >= 2) {
            Chromosome[] chromosomeToInsert = crossing();
            newSolutions[iteration] = chromosomeToInsert[0];
            newSolutions[iteration + 1] = chromosomeToInsert[1];
            iteration += 2;

        } else if (randomValue < (crossingProbability + mutationProbability)) {
            newSolutions[iteration] = mutation();

            iteration++;
        } else {

            newSolutions[iteration] = reproduction();
            iteration++;
        }
        return iteration;
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

        Gene[] genes = new Gene[solution.getGenes().length];
        for (int i = 0; i < solution.getGenes().length; i++) {
            int max = solution.getGenes()[i].getGene().length;
            Random random = new Random();
            int genePosition = random.nextInt(max);
            byte[] gene = new byte[solution.getGenes()[i].getGene().length];
            System.arraycopy(solution.getGenes()[i].getGene(), 0, gene, 0,
                    solution.getGenes()[i].getGene().length);
            gene[genePosition] = (byte) (gene[genePosition] == 0 ? 1 : 0);
            genes[i] = new Gene(gene, solution.getGenes()[i].getNode());
        }
        newSolution.setGenes(genes);


        //   int stockLevel1 = gene[genePosition1].getValue();
        //  int stockLevel2 = gene[genePosition2].getValue();

        //   gene[genePosition1] = new Gene(stockLevel2, gene[genePosition1].getNode());
        //  gene[genePosition2]=new Gene(stockLevel1, gene[genePosition2].getNode());


        return newSolution;

    }

    /**
     * Crossing Choose two solutions using roulette method randomly choose entry
     * perform crossing
     *
     * @return array of two new solution, chromosome
     */
    private Chromosome[] crossing() {
        System.out.println("crossing");
        Chromosome[] solutionsToCross = new Chromosome[2];
        Chromosome[] solutionsAfterCrossing = new Chromosome[2];
        for(int i=0; i<solutionsToCross.length; i++){
            solutionsToCross[i] =  rulette(currentPopulation
                    .getSolutions());
            solutionsAfterCrossing[i] = new Chromosome();
            Gene[] gene = new Gene[solutionsToCross[i].getGenes().length];
            for(int j=0; j<gene.length; j++){
                gene[j] = new Gene(new byte[ solutionsToCross[i].getGenes()[j].getGene().length],solutionsToCross[i].getGenes()[j].getNode() );

            }
            solutionsAfterCrossing[i].setGenes(gene);
        }

        double ratio=0.5;
        Random random = new Random();
        for(int i=0; i<solutionsToCross[0].getGenes().length; i++){
            for(int j=0; j<solutionsToCross[0].getGenes()[i].getGene().length; j++){
                double rand = random.nextDouble();


                if(rand<ratio){

                    solutionsAfterCrossing[0].getGenes()[i].getGene()[j]= solutionsToCross[1].getGenes()[i].getGene()[j];
                    solutionsAfterCrossing[1].getGenes()[i].getGene()[j]= solutionsToCross[0].getGenes()[i].getGene()[j];
                }
                else{
                    solutionsAfterCrossing[0].getGenes()[i].getGene()[j]= solutionsToCross[0].getGenes()[i].getGene()[j];
                    solutionsAfterCrossing[1].getGenes()[i].getGene()[j]= solutionsToCross[1].getGenes()[i].getGene()[j];
                }
            }
        }


        return solutionsAfterCrossing;
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
            //   gene[i] = new Gene(firstSolution[i].getValue(), firstSolution[i].getNode());

        }
        for (int j = value; j < secondSolution.length; j++) {
            // gene[j] = new Gene(secondSolution[j].getValue(), secondSolution[j].getNode());

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
            System.out.println(1.0 / solution.getFitnessFunctionResult());
            for (Gene gene : solution.getGenes()) {
                for (int i = 0; i < gene.getGene().length; i++) {
                    System.out.print(gene.getGene()[i]);
                }
                System.out.print("\n");
            }
            if (maximum < solution.getFitnessFunctionResult()) {
                maximum = solution.getFitnessFunctionResult();
                best = solution;
            }
        }
        for (int i = 0; i < best.getGenes().length; i++) {
             System.out.println("best "+best.getGenes()[i].getGeneIntValue());
        }
        System.out.println(1.0 / best.getFitnessFunctionResult());
    }
}
