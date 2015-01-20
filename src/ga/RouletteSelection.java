package ga;

import java.util.Random;

/**
 * Created by Marta on 2015-01-20.
 */
public class RouletteSelection {

    /**
     * Roulette selection method basing on distribution function of each
     * chromosome
     *
     * @param solutions
     * @return selected solution
     */
    public static Chromosome selectChromosomes(Chromosome[] solutions) {
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
}
