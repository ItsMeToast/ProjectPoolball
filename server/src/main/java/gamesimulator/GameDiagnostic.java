package gamesimulator;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameDiagnostic {
    public static void main(String[] args) {
        runAllDiagnostics();
    }

    /**
     * Creates all Game Diagnostics with shortened 1000 simulations
     * Average Time: 20m
     */
    public static void runShortDiagnostics() {
        getBalancedGameDiagnostic(1000);
        getBaseDiffDiagnostic(55, 1000);
        getBaseDiffDiagnostic(70, 1000);
        getBaseDiffDiagnostic(85, 1000);
        getPositionStatDiagnostics(1000);
        getPositionImpactDiagnostic(1000);
    }

    /**
     * Creates all Game Diagnostics with standard 10000 simulations
     * Average Time: 3h37m
     */
    public static void runAllDiagnostics() {
        getBalancedGameDiagnostic(10000);
        getBaseDiffDiagnostic(55, 10000);
        getBaseDiffDiagnostic(70, 10000);
        getBaseDiffDiagnostic(85, 10000);
        getPositionStatDiagnostics(10000);
        getPositionImpactDiagnostic(10000);
    }

    /**
     * Prints out the Impact of each position's skill
     * @param num number of games to simulate
     */
    public static void getPositionImpactDiagnostic(int num) {
        //Offense
        ArrayList<Double> homeWin = new ArrayList<>();
        ArrayList<Double> awayWin = new ArrayList<>();
        ArrayList<Double> homeScore = new ArrayList<>();
        ArrayList<Double> awayScore = new ArrayList<>();

        for (int i = 40; i <= 99; i++) {
            System.out.println("Simming Offense " + i);

            GameTeam homeTeam = getBalancedTeam("Home", i, 75 ,75);
            GameTeam awayTeam = getBalancedTeam("Away", i, 75,75);

            double[] results = getProbabilities(homeTeam, awayTeam, num);
            homeWin.add(results[0]);
            awayWin.add(results[1]);
            homeScore.add(results[2]);
            awayScore.add(results[3]);
        }

        List<List<Double>> data = compileGameData(homeWin, awayWin, homeScore, awayScore);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hhmm a");
        writeCSV("Offense Impact (" + LocalDateTime.now().format(formatter) + ")", data);

        //Middle
        homeWin.clear();
        awayWin.clear();
        homeScore.clear();
        awayScore.clear();

        for (int i = 40; i <= 99; i++) {
            System.out.println("Simming Middle " + i);

            GameTeam homeTeam = getBalancedTeam("Home", 75, i,75);
            GameTeam awayTeam = getBalancedTeam("Away", 75, i,75);

            double[] results = getProbabilities(homeTeam, awayTeam, num);
            homeWin.add(results[0]);
            awayWin.add(results[1]);
            homeScore.add(results[2]);
            awayScore.add(results[3]);
        }

        data = compileGameData(homeWin, awayWin, homeScore, awayScore);
        writeCSV("Middle Impact (" + LocalDateTime.now().format(formatter) + ")", data);

        homeWin.clear();
        awayWin.clear();
        homeScore.clear();
        awayScore.clear();

        for (int i = 40; i <= 99; i++) {
            System.out.println("Simming Defense " + i);

            GameTeam homeTeam = getBalancedTeam("Home", 75, 75 ,i);
            GameTeam awayTeam = getBalancedTeam("Away", 75, 75,i);

            double[] results = getProbabilities(homeTeam, awayTeam, num);
            homeWin.add(results[0]);
            awayWin.add(results[1]);
            homeScore.add(results[2]);
            awayScore.add(results[3]);
        }

        data = compileGameData(homeWin, awayWin, homeScore, awayScore);
        writeCSV("Defense Impact (" + LocalDateTime.now().format(formatter) + ")", data);
    }

    /**
     * Prints diagnostics for the average stats of each position for every stat
     * @param num number of games to simulate
     */
    public static void getPositionStatDiagnostics(int num) {
        ArrayList<double[]> firstStats = new ArrayList<>();
        ArrayList<double[]> secondStats = new ArrayList<>();
        ArrayList<double[]> thirdStats = new ArrayList<>();

        for (int i = 40; i <= 99; i++) {
            System.out.println("Simming Stat " + i);

            GameTeam homeTeam = getBalancedTeam("Home", i, i ,i);
            GameTeam awayTeam = getBalancedTeam("Away", i, i ,i);

            double[][] results = getStatProbabilities(homeTeam, awayTeam, num);

            firstStats.add(results[0]);
            secondStats.add(results[1]);
            thirdStats.add(results[2]);
        }

        // Format Data Manually for Position Stats
        List<List<Double>> data = new ArrayList<>();
        for (int i = 0; i < (3 * firstStats.getFirst().length); i++) {
            data.add(new ArrayList<>());
        }

        for (double[] first : firstStats) {
            for (int i = 0; i < firstStats.getFirst().length; i++) {
                data.get(i).add(first[i]);
            }
        }

        for (double[] second : secondStats) {
            for (int i = 0; i < secondStats.getFirst().length; i++) {
                data.get(firstStats.getFirst().length + i).add(second[i]);
            }
        }

        for (double[] third : thirdStats) {
            for (int i = 0; i < thirdStats.getFirst().length; i++) {
                data.get(firstStats.getFirst().length + secondStats.getFirst().length + i).add(third[i]);
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hhmm a");
        writeCSV("Position Stats (" + LocalDateTime.now().format(formatter) + ")", data);
    }

    /**
     * Prints Diagnostics for Base Difference (+-20 stat from the base)
     * @param base the base stat to compare
     * @param num number of games to simulate for each difference
     */
    public static void getBaseDiffDiagnostic(int base, int num) {
        ArrayList<Double> homeWin = new ArrayList<>();
        ArrayList<Double> awayWin = new ArrayList<>();
        ArrayList<Double> homeScore = new ArrayList<>();
        ArrayList<Double> awayScore = new ArrayList<>();

        for (int i = Math.max(40, base-20); i <= Math.min(99, base+20); i++) {
            System.out.println("Simming Stat " + i);

            GameTeam homeTeam = getBalancedTeam("Home", base, base, base);
            GameTeam awayTeam = getBalancedTeam("Away", i, i, i);

            double[] results = getProbabilities(homeTeam, awayTeam, num);
            homeWin.add(results[0]);
            awayWin.add(results[1]);
            homeScore.add(results[2]);
            awayScore.add(results[3]);
        }

        List<List<Double>> data = compileGameData(homeWin, awayWin, homeScore, awayScore);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hhmm a");
        writeCSV("Base Diff " + base + " (" + LocalDateTime.now().format(formatter) + ")", data);
    }

    /**
     * Prints Diagnostics for Balanced Games across all stats
     * @param num number of games to simulate per stat
     */
    public static void getBalancedGameDiagnostic(int num) {
        ArrayList<Double> homeWin = new ArrayList<>();
        ArrayList<Double> awayWin = new ArrayList<>();
        ArrayList<Double> homeScore = new ArrayList<>();
        ArrayList<Double> awayScore = new ArrayList<>();

        for (int i = 40; i <= 99; i++) {
            System.out.println("Simming Stat " + i);

            GameTeam homeTeam = getBalancedTeam("Home", i, i ,i);
            GameTeam awayTeam = getBalancedTeam("Away", i, i ,i);

            double[] results = getProbabilities(homeTeam, awayTeam, num);
            homeWin.add(results[0]);
            awayWin.add(results[1]);
            homeScore.add(results[2]);
            awayScore.add(results[3]);
        }

        List<List<Double>> data = compileGameData(homeWin, awayWin, homeScore, awayScore);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hhmm a");
        writeCSV("Balanced Game (" + LocalDateTime.now().format(formatter) + ")", data);
    }

    public static List<List<Double>> compileGameData(ArrayList<Double> homeWin, ArrayList<Double> awayWin, ArrayList<Double> homeScore, ArrayList<Double> awayScore) {
        List<List<Double>> data = new ArrayList<>();

        data.add(homeWin);
        data.add(awayWin);

        ArrayList<Double> tie = new ArrayList<>();
        for (int i = 0; i < homeWin.size(); i++) {
            tie.add(100 - homeWin.get(i) - awayWin.get(i));
        }
        data.add(tie);

        data.add(homeScore);
        data.add(awayScore);

        return data;
    }

    /**
     * Writes a 2D double array to a CSV file with specified name
     * @param name Name of the csv file to save to
     * @param data 2D array of the data to print to CSV
     */
    public static void writeCSV(String name, List<List<Double>> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("out\\diagnostics\\" + name + ".csv"));

            for (int row = 0; row < data.getFirst().size(); row++) {
                for (List<Double> dataSet : data) {
                    writer.write(dataSet.get(row) + ",");
                }
                writer.write("\n");
            }
            writer.close();

            System.out.println("Wrote to file " + name + ".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return an array of double arrays of the average stats per game, by position (home/away are counted the same)
     * @param homeTeam home team to simulate
     * @param awayTeam away team to simulate
     * @param num number of games to simulate
     * @return double[][] array with stats for each position
     */
    public static double[][] getStatProbabilities(GameTeam homeTeam, GameTeam awayTeam, int num) {
        double[] firstStats = new double[5];
        double[] secondStats = new double[5];
        double[] thirdStats = new double[5];

        PrintStream stdout = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        for (int i = 0; i < num; i++) {
            GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, false);
            GameRecord record = simulator.simulateGame();

            System.out.println(record);

            List<PlayerRecord> playerRecords = record.getPlayerRecords();

            for (int j = 0; j < playerRecords.size(); j++) {
                firstStats[0] += playerRecords.get(j).shots();
                firstStats[1] += playerRecords.get(j).goals();
                firstStats[2] += playerRecords.get(j).assists();
                firstStats[3] += playerRecords.get(j).blocks();
                firstStats[4] += playerRecords.get(j).steals();

                j++;
                secondStats[0] += playerRecords.get(j).shots();
                secondStats[1] += playerRecords.get(j).goals();
                secondStats[2] += playerRecords.get(j).assists();
                secondStats[3] += playerRecords.get(j).blocks();
                secondStats[4] += playerRecords.get(j).steals();

                j++;
                thirdStats[0] += playerRecords.get(j).shots();
                thirdStats[1] += playerRecords.get(j).goals();
                thirdStats[2] += playerRecords.get(j).assists();
                thirdStats[3] += playerRecords.get(j).blocks();
                thirdStats[4] += playerRecords.get(j).steals();
            }
        }
        System.setOut(stdout);

        for (int i = 0; i < firstStats.length; i++) {
            firstStats[i] = firstStats[i] / (6*num);
            secondStats[i] = secondStats[i] / (6*num);
            thirdStats[i] = thirdStats[i] / (6*num);
        }

        return new double[][]{firstStats, secondStats, thirdStats};
    }

    /**
     * Returns a double array with the home/away win percent and average goals scored
     * @param homeTeam the home team used in game simulations
     * @param awayTeam the away team used in game simulations
     * @param num The number of games to simulate
     */
    public static double[] getProbabilities(GameTeam homeTeam, GameTeam awayTeam, int num) {
        double homeScore = 0, awayScore = 0, homeWin = 0, awayWin = 0;

        PrintStream stdout = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        for (int i = 0; i < num; i++) {
            GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, false);
            GameRecord record = simulator.simulateGame();
            homeScore += record.getHomeScore();
            awayScore += record.getAwayScore();

            if (record.getHomeScore() > record.getAwayScore()) {
                homeWin++;
            } else if (record.getAwayScore() > record.getHomeScore()) {
                awayWin++;
            }
        }
        System.setOut(stdout);

        homeScore = homeScore / num;
        awayScore = awayScore / num;

        return new double[]{100*(homeWin/num), 100*(awayWin/num), homeScore, awayScore};
    }

    /**
     * Creates a team with three identical lines
     * @param name Team name (and player) to use
     * @param offense Stat to give all offense players
     * @param playmaker Stat to give all playmaker players
     * @param defense Stat to give all defense players
     * @return The new Team
     */
    public static GameTeam getBalancedTeam(String name, int offense, int playmaker, int defense) {
        ArrayList<Player> line = new ArrayList<>();

        Statline offenseStat = new Statline(offense, offense, offense, offense, offense, offense, offense, offense, 5);
        Statline playmakerStat = new Statline(playmaker, playmaker, playmaker, playmaker, playmaker, playmaker, playmaker, playmaker, 5);
        Statline defenseStat = new Statline(defense, defense, defense, defense, defense, defense, defense, defense, 5);

        line.add(Player.getNewPlayer(name, "Offense", 25, Playstyle.ATTACKER, Trait.CONSISTENT, offenseStat, 5));
        line.add(Player.getNewPlayer(name, "Playmaker", 25, Playstyle.PLAYMAKER, Trait.CONSISTENT, playmakerStat, 5));
        line.add(Player.getNewPlayer(name, "Defense", 25, Playstyle.DEFENDER, Trait.CONSISTENT, defenseStat, 5));

        return new GameTeam(name, line, line, line);
    }
}
