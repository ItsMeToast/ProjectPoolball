package playertypes;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class PlayerTester {
    public static final Random rand = new Random();

    public static void main(String[] args) {
        testCareerContract(3, Playstyle.FINISHER, Trait.HIGHLIGHT_REEL);
//        testContractProbabilities(rand.nextInt(50, 80), rand.nextInt(20,24), rand.nextInt(1,11));
    }

    public static void genPlayerCareer(Player p1) {
        while (p1.getAge() < 29) {
            System.out.println();
            p1.printFullInformation();
            p1.agePlayer();
        }
        System.out.println();
        p1.printFullInformation();
    }

    public static void getAveragePlayers(int num) {
        for (int potential = 1; potential <= 10; potential++) {
            System.out.println("\nPotential " + potential);
            double[] stats = new double[9];
            int[] max = new int[9];
            int[] min = new int[9];
            Arrays.fill(min, 200);

            for (int i = 0; i < num; i++) {
                int[] arr = StatManager.getStartingStats(potential, new int[]{0,0,0,0,0,0,0,0,0});
                for (int index = 0; index < stats.length; index++) {
                    stats[index] += arr[index];
                    max[index] = Math.max(max[index], arr[index]);
                    min[index] = Math.min(min[index], arr[index]);
                }
            }

            for (int index = 0; index < stats.length; index++) {
                stats[index] = stats[index]/num;
            }

            System.out.println("Average: " + Arrays.toString(stats));
            System.out.println("Max: " + Arrays.toString(max));
            System.out.println("Min: " + Arrays.toString(min));
        }
    }

    public static void getAverageHeights(int num) {
        for (int mod = 0; mod <= 5; mod++) {
            System.out.println("\nHeight Modifier: " + mod);

            double[] stats = new double[9];
            int[] max = new int[9];
            int[] min = new int[9];
            Arrays.fill(min, 200);

            for (int i = 0; i < num; i++) {
                int[] arr = StatManager.getStartingStats(10, new int[]{0, 0, 0, 0, 0, 0, mod, 0, 0});
                for (int index = 0; index < stats.length; index++) {
                    stats[index] += arr[index];
                    max[index] = Math.max(max[index], arr[index]);
                    min[index] = Math.min(min[index], arr[index]);
                }
            }

            for (int index = 0; index < stats.length; index++) {
                stats[index] = stats[index] / num;
            }

            System.out.println("Average: " + Arrays.toString(stats));
            System.out.println("Max: " + Arrays.toString(max));
            System.out.println("Min: " + Arrays.toString(min));
        }
    }

    public static void getAverageGrowth(int num) {
        double[] expectedAverage = new double[10];
        int[] expectedMax = new int[10];
        int[] expectedMin = new int[10];

        for (int age = 18; age <= 28; age++) {
            System.out.println("\nAge " + age);
            double[] stats = new double[10];
            int[] max = new int[10];
            int[] min = new int[10];
            Arrays.fill(max, -200);
            Arrays.fill(min, 200);

            for (int potential = 1; potential <= 10; potential++) {
                for (int i = 0; i < num; i++) {
                    int[] arr = StatManager.getStatGrowth(age, potential, new int[]{0,0,0,0,0,0,0,0,0}, 0);
                    stats[potential-1] += arr[0];
                    max[potential-1] = Math.max(max[potential-1], arr[0]);
                    min[potential-1] = Math.min(min[potential-1], arr[0]);
                }

            }

            for (int index = 0; index < stats.length; index++) {
                stats[index] = stats[index] / num;
            }

            System.out.println("Average: " + Arrays.toString(stats));
            System.out.println("Max: " + Arrays.toString(max));
            System.out.println("Min: " + Arrays.toString(min));


            for (int i = 0; i < stats.length; i++) {
                expectedAverage[i] += Math.max(stats[i],0);
                expectedMax[i] += Math.max(max[i],0);
                expectedMin[i] += Math.max(min[i],0);
            }
        }

        for (int i = 0; i < expectedAverage.length; i++) {
            expectedAverage[i] = (int) expectedAverage[i];
        }

        System.out.println("\n\nAverage Total: " + Arrays.toString(expectedAverage));
        System.out.println("Max Total: " + Arrays.toString(expectedMax));
        System.out.println("Min Total: " + Arrays.toString(expectedMin));
    }

    public static void getAverageInjury(int num) {
        for (int age = 18; age <= 28; age++) {
            double injury = 0;
            for (int i = 0; i < num; i++) {
                injury += StatManager.getStatGrowth(age,10,new int[]{0,0,0,0,0,0,0,0,0}, 0)[8];
            }
            injury = injury / num;
            System.out.println("Age " + age + ", " + (injury/10.0));
        }
    }

    public static void getOSSContractRequests(int OSS) {
        Statline stats = new Statline(OSS, OSS, OSS, OSS, OSS, OSS, OSS, OSS, 1);
        System.out.println("OSS: " + OSS);
        for (int age = 18; age < 29; age++) {
            System.out.print("Age " + age);
            for (int potential = 1; potential <= 10; potential++) {
                Player p = Player.getNewPlayer("test", "test", age, Playstyle.ATTACKER, Trait.CLUTCH, stats, potential);

                System.out.print(" " + p.getContractRequest()[0]);
            }
            System.out.println();
        }
    }

    public static void testContractProbabilities(int OSS, int age, int potential) {
        Statline stats = new Statline(OSS, OSS, OSS, OSS, OSS, OSS, OSS, OSS, 1);
        Player p = Player.getNewPlayer("Contract Man", "TEST", age, Playstyle.ATTACKER, Trait.CLUTCH, stats, potential);

        NumberFormat fmt = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        fmt.setMaximumFractionDigits(2);
        Scanner in = new Scanner(System.in);

        p.printFullInformation();
        int[] contract = p.getContractRequest();
        System.out.println(p.getFirstName() + " requests $" + fmt.format(contract[0]) + " X " + contract[1]);
        System.out.println(contract[0]);

        while (true) {
            System.out.println("\nValue Offer: ");
            int value = in.nextInt();
            System.out.println("Year Offer: ");
            int year = in.nextInt();

            System.out.println("Probability: " + p.getContractProbability(value, year) + "%");
        }
    }

    public static void testCareerContract(int potential, Playstyle style, Trait trait) {
        Player p = Player.getNewPlayer(potential, style, trait);

        p.printFullInformation();
        System.out.println(p.getOSS());
        int[] contract = p.getContractRequest();
        System.out.println(Arrays.toString(contract) + "\n");

        for (int j = 0; j < 11; j++) {
            p.agePlayer();
            p.printFullInformation();
            System.out.println(p.getOSS());
            contract = p.getContractRequest();
            System.out.println(Arrays.toString(contract));

            System.out.println("\n");
        }
    }
}
