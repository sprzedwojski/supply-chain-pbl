package ga;

import java.util.Random;

/**
 * Created by Marta on 2015-01-20.
 */
public class GeneticOperator {
    private double mutationProbability = 0.3;
    private double crossingProbability = 0.4;

    public GeneticOperator() {

    }

    public GeneticOperator(double mutationProbability, double crossingProbability) {
        this.mutationProbability = mutationProbability;
        this.crossingProbability = crossingProbability;
    }

    public Chromosome[] performGeneticOperation(int iteration, Population currentPopulation) {
        Random random = new Random();

        double randomValue = random.nextDouble();
        Chromosome[] chromosomeToInsert;
        if (randomValue < crossingProbability
                && currentPopulation.getSolutions().length - iteration >= 2) {
            chromosomeToInsert = crossing(currentPopulation);

        } else if (randomValue < (crossingProbability + mutationProbability)) {
            chromosomeToInsert = mutation(currentPopulation);

        } else {

            chromosomeToInsert = reproduction(currentPopulation);

        }
        return chromosomeToInsert;
    }


    /**
     * Crossing Choose two solutions using roulette method randomly choose entry
     * perform crossing
     *
     * @return array of two new solution, chromosome
     */
    private Chromosome[] crossing(Population currentPopulation) {
        Chromosome[] solutionsToCross = new Chromosome[2];
        for (int i = 0; i < solutionsToCross.length; i++) {
            solutionsToCross[i] = RouletteSelection.selectChromosomes(currentPopulation
                    .getSolutions());


        }
        Chromosome[] solutionsAfterCrossing = UniformCrossover.crossing(solutionsToCross);

        return solutionsAfterCrossing;
    }

    /**
     * Mutation choose solution using roulette method copy a genes array to new
     * solution (chromosome) randomly choose entry in the gene array of new
     * solution switch this entry to the opposite
     *
     * @return new solution
     */
    private Chromosome[] mutation(Population currentPopulation) {
        Chromosome solution = RouletteSelection.selectChromosomes(currentPopulation.getSolutions());


        Chromosome[] newSolution = new Chromosome[]{
                new Chromosome()
        };

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
        newSolution[0].setGenes(genes);


        return newSolution;

    }

    /**
     * Reproduction Choose solution using roulette method Copy current solution
     * to the new one
     *
     * @return new solution
     */
    private Chromosome[] reproduction(Population currentPopulation) {

        Chromosome solution = RouletteSelection.selectChromosomes(currentPopulation.getSolutions());
        Gene[] genes = new Gene[solution.getGenes().length];
        System.arraycopy(solution.getGenes(), 0, genes, 0, genes.length);
        Chromosome[] newSolution = new Chromosome[]{
                new Chromosome()
        };
        newSolution[0].setGenes(genes);

        return newSolution;

    }
}
