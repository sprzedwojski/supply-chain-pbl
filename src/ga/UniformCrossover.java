package ga;

import java.util.Random;

/**
 * Created by Marta on 2015-01-20.
 */
public class UniformCrossover {
    private static final double RATIO = 0.5;
    public static Chromosome[] crossing( Chromosome[] solutionsToCross ) {

//
        Chromosome[] solutionsAfterCrossing = new Chromosome[2];
        //init solution after crossing
        for(int i=0; i<solutionsToCross.length; i++){
            solutionsAfterCrossing[i] = new Chromosome();
            Gene[] gene = new Gene[solutionsToCross[i].getGenes().length];
            for(int j=0; j<gene.length; j++){
                gene[j] = new Gene(new byte[ solutionsToCross[i].getGenes()[j].getGene().length],solutionsToCross[i].getGenes()[j].getNode() );

            }
            solutionsAfterCrossing[i].setGenes(gene);
        }


        Random random = new Random();
        for(int i=0; i<solutionsToCross[0].getGenes().length; i++){
            for(int j=0; j<solutionsToCross[0].getGenes()[i].getGene().length; j++){
                double rand = random.nextDouble();
                if(rand<RATIO){

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

}
