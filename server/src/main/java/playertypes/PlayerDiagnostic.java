package playertypes;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Standard Diagnostics use num=10000
 */
public class PlayerDiagnostic {
    public static void main(String[] args) {
        getInjuryDiagnostic(10000);
    }

    public static void getInjuryDiagnostic(int num) {
        ArrayList<Double> minInjury = new ArrayList<>();
        ArrayList<Double> averageInjury = new ArrayList<>();
        ArrayList<Double> maxInjury = new ArrayList<>();

        for (int age = 18; age <= 29; age++) {
            ArrayList<Double> injury = new ArrayList<>();
            double min = 100;
            double max = 0;

            for (int i = 0; i < num; i++) {
                Player player = new Player(5, Playstyle.ATTACKER, Trait.CLUTCH);

                for (int j = 18; j < age; j++) {
                    player.agePlayer();
                }

                injury.add(player.getInjury());
                if (player.getInjury() > max) {
                    max = player.getInjury();
                } if (player.getInjury() < min) {
                    min = player.getInjury();
                }
            }

            double avg = 0;
            for (Double d : injury) {
                avg += d;
            }
            avg = avg/injury.size();

            minInjury.add(min);
            averageInjury.add(avg);
            maxInjury.add(max);
        }

        for (Double d : minInjury) {
            System.out.println(d);
        }
        System.out.println("\n\n");

        for (Double d : averageInjury) {
            System.out.println(d);
        }
        System.out.println("\n\n");

        for (Double d : maxInjury) {
            System.out.println(d);
        }
    }


    /**
     * Prints diagnostics for modified player stat growth for each potential
     * @param num number of players to simulate
     */
    public static void getPotentialGrowthDiagnostic(int mod, int num) {
        for (int potential = 1; potential <= 10; potential++) {
            System.out.println("\nSimming Potential " + potential);

            try {
                for (int age = 18; age <= 29; age++) {
                    ArrayList<Integer> stats = new ArrayList<>();

                    for (int i = 0; i < num; i++) {
                        Player player = new Player(potential, Playstyle.DISTRIBUTOR, Trait.DIVA);

                        for (int j = 18; j < age; j++) {
                            player.agePlayer();
                        }

                        Field statlineField = Player.class.getDeclaredField("stats");
                        statlineField.setAccessible(true);

                        if (mod == 0) {
                            Field acc = Statline.class.getDeclaredField("accuracy");
                            Field end = Statline.class.getDeclaredField("endurance");
                            Field exp = Statline.class.getDeclaredField("explosiveness");
                            acc.setAccessible(true);
                            end.setAccessible(true);
                            exp.setAccessible(true);

                            stats.add((Integer) acc.get(statlineField.get(player)));
                            stats.add((Integer) end.get(statlineField.get(player)));
                            stats.add((Integer) exp.get(statlineField.get(player)));
                        } else if (mod == 1) {
                            Field blc = Statline.class.getDeclaredField("blocking");
                            blc.setAccessible(true);
                            stats.add((Integer) blc.get(statlineField.get(player)));
                        } else if (mod == 2) {
                            player = new Player(potential, Playstyle.TWOWAY, Trait.DIVA);

                            for (int j = 18; j < age; j++) {
                                player.agePlayer();
                            }

                            statlineField = Player.class.getDeclaredField("stats");
                            statlineField.setAccessible(true);


                            Field pow = Statline.class.getDeclaredField("power");
                            pow.setAccessible(true);
                            stats.add((Integer) pow.get(statlineField.get(player)));
                        } else if (mod == -1) {
                            player = Player.getNewPlayer(potential, Playstyle.ATTACKER, Trait.DEAD_WEIGHT);

                            for (int j = 18; j < age; j++) {
                                player.agePlayer();
                            }

                            statlineField = Player.class.getDeclaredField("stats");
                            statlineField.setAccessible(true);

                            Field end = Statline.class.getDeclaredField("endurance");
                            Field spd = Statline.class.getDeclaredField("speed");
                            end.setAccessible(true);
                            spd.setAccessible(true);

                            stats.add((Integer) end.get(statlineField.get(player)));
                            stats.add((Integer) spd.get(statlineField.get(player)));
                        }
                    }

                    double avg = 0;
                    for (Integer i : stats) {
                        avg += i;
                    }
                    avg = avg/stats.size();

                    System.out.println(avg);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
