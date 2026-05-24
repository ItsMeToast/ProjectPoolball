package playertypes;

import java.util.Random;

/**
 * Utility class containing methods to get randomized statlines (by generation or aging)
 */
public class StatManager {
    public static Random rand = new Random();

    /**
     * Returns a random starting statline based on potential and some startingModifiers
     * @param potential the player potential, higher is more likely to be good
     * @param startingModifiers array of exactly 9 elements (8 stats and injury) representing modifiers
     * @return a 9 element array containing the starting stats for the player (int[8] should be divided by 10 for injury%)
     */
    public static int[] getStartingStats(int potential, int[] startingModifiers) {
        if (startingModifiers.length != 9) {
            throw new IllegalArgumentException("startingModifiers must have length 9");
        }

        int[] stats = new int[9];
        for (int i = 0; i < stats.length; i++) {
            stats[i] = 40 + rand.nextInt(2 + potential/2) + startingModifiers[i];
        }

        //Size Override
        int upperBound = Math.min(86 + (5 * startingModifiers[6]), 99);
        int lowerBound = 50 + (10 * startingModifiers[6]);
        lowerBound += (int) ((upperBound - lowerBound - 1) * (potential/15.0));


        stats[6] = rand.nextInt(lowerBound, upperBound);


        //Injury Override (needs to be divided by 10!! Wacky implementation but allows being put in the returned array)
        stats[8] = 50 + rand.nextInt(50) + (10 * startingModifiers[8]);

        return stats;
    }

    /**
     * Returns an array of randomized stat growth based on the given parameters
     * @param age the previous age of the player
     * @param potential the potential of the player
     * @param skillModifiers array of length 9 with modifiers to the 8 stats and injury
     * @param varianceModifier variable representing a modifier to the variance of randomness
     * @return array of length 9 indicating the stats (same order as skillModifiers)
     */
    public static int[] getStatGrowth(int age, int potential, int[] skillModifiers, int varianceModifier) {
        if (skillModifiers.length != 9) {
            throw new IllegalArgumentException("skillModifiers must have length 9");
        }

        int[] growth = new int[9];

        // Parameters for the growth baseline function (vary to change general growth of all players)
        double power = 3;
        double max = 6;
        double offset = 18;
        double normalizer = 80;

        // Double representing the general trend of growth for a given age.
        double statBaseline = getStatBaseline(age, power, max, offset, normalizer);

        for (int i = 0; i < growth.length; i++) {
            double upperBound = statBaseline + 3 + (potential/5) + (2 * varianceModifier);
            double lowerBound = statBaseline - (3.5 - potential/3.0) - (2 * varianceModifier);

            if ((int)upperBound > (int)lowerBound) {
                growth[i] = rand.nextInt((int) lowerBound, (int) upperBound) + skillModifiers[i];
            } else {
                growth[i] = (int) (statBaseline + skillModifiers[i]);
            }
        }

        //Intelligence Override (never goes down)
        if (growth[4] < 0) {
            growth[4] = 0;
        }

        //Size Override
        growth[6] = 0;

        // Injury Override
        growth[8] = (int) (10 * getInjuryBaseline(age) * (rand.nextDouble(0.5, 2.5)) * ((2+skillModifiers[8])/2.0));

        return growth;
    }

    //Helper method for getStatGrowth, returns the baseline stat growth for given age
    private static double getStatBaseline(int age, double power, double max, double offset, double normalizer) {
        return max - (Math.pow((age - offset), power) / normalizer);
    }

    //Helper method for getStatGrowth, returns average injury change per given age
    private static double getInjuryBaseline(int age) {
        return ((age-24)/5.0);
    }
}
