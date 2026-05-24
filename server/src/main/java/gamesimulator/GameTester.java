package gamesimulator;

import gamesimulator.actions.*;
import playertypes.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class GameTester {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        GameRecord rec = testAgedGame(3);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void testPoolMethods() {
        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        int stat = 80;
        Statline stats = new Statline(stat, stat, stat, stat, stat, stat, stat, stat, 0);
        int stat2 = 80;
        Statline stats2 = new Statline(stat2, stat2, stat2, stat2, stat2, stat2, stat2, stat2, 0);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                homeLines.get(i).add(new Player("Home", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.AGILE, stats, 10));
                awayLines.get(i).add(new Player("Away", String.valueOf(j+4), 20, Playstyle.ATTACKER, Trait.AGILE, stats2, 10));
            }
        }

        List<MovablePlayer> homeLine = new ArrayList<>();
        homeLine.add(new MovablePlayer(homeLines.getFirst().get(0)));
        homeLine.add(new MovablePlayer(homeLines.getFirst().get(1)));
        homeLine.add(new MovablePlayer(homeLines.getFirst().get(2)));

        List<MovablePlayer> awayLine = new ArrayList<>();
        awayLine.add(new MovablePlayer(awayLines.getFirst().get(0)));
        awayLine.add(new MovablePlayer(awayLines.getFirst().get(1)));
        awayLine.add(new MovablePlayer(awayLines.getFirst().get(2)));

        Pool pool = new Pool(homeLine, awayLine);
        pool.setDefaultState();

        System.out.println(pool.getOpponentsOnCone(homeLine.getFirst(), 0, 11));
    }

    public static void testPresetGame() {
        double homeScore = 0;
        double awayScore = 0;
        double homeWin = 0;
        double awayWin = 0;

        int num = 5000;

        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        ArrayList<Player> homePlayers = new ArrayList<>();
        ArrayList<Player> awayPlayers = new ArrayList<>();

        homePlayers.add(Player.getNewPlayer("James", "Nefeli", 24, Playstyle.FINISHER, Trait.SNIPER, new Statline(95, 85, 87, 90, 95, 92, 86, 93, 3.9), 8));
        homePlayers.add(Player.getNewPlayer("Stephen", "Greywood", 26, Playstyle.PLAYMAKER, Trait.GENIUS, new Statline(94, 86, 84, 82, 98, 86, 82, 85, 5.4), 6));
        homePlayers.add(Player.getNewPlayer("Yerushalayim", "Levi", 23, Playstyle.DEFENDER, Trait.HARDENED, new Statline(82, 95, 88, 89, 79, 82, 92, 84, 4.0), 4));

        awayPlayers.add(Player.getNewPlayer("Elisha", "Lych", 24, Playstyle.ATTACKER, Trait.SUPERSTAR, new Statline(95, 85, 87, 90, 95, 92, 86, 93, 3.9), 9));
        awayPlayers.add(Player.getNewPlayer("Jonas", "Matterson", 24, Playstyle.DISTRIBUTOR, Trait.SHORT_LIVED, new Statline(94, 86, 84, 82, 98, 86, 82, 85, 5.4), 4));
        awayPlayers.add(Player.getNewPlayer("Oli", "Batu", 24, Playstyle.DEFENDER, Trait.GIANT, new Statline(82, 95, 88, 89, 79, 82, 92, 84, 4.0), 5));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                homeLines.get(i).add(homePlayers.get(j));
                awayLines.get(i).add(awayPlayers.get(j));
            }
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

        System.out.println(homeTeam);
        System.out.println();
        System.out.println(awayTeam);


        PrintStream stdout = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        for (int i = 0; i < num; i++) {
            stdout.println("Simming game " + (i+1));

            GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, true);
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

        for (Player player : homeTeam.getLine(0)) {
            System.out.println();
            player.printFullInformation();
        }

        for (Player player : awayTeam.getLine(0)) {
            System.out.println();
            player.printFullInformation();
        }

        System.out.println("\n");

        System.out.println("Home " + homeScore + " vs. Away " + awayScore);
        System.out.println("Home " + 100*(homeWin/num) + "% vs. Away " + 100*(awayWin/num) + "% (Tie " + 100*(1 - (homeWin+awayWin)/num)+ "%)");

    }

    public static void testFullGame() {
        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                homeLines.get(i).add(new Player());
                awayLines.get(i).add(new Player());
            }
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

        System.out.println(homeTeam);
        System.out.println();
        System.out.println(awayTeam);

        for (Player player : homeTeam.getLine(0)) {
            System.out.println();
            player.printFullInformation();
        }

        for (Player player : awayTeam.getLine(0)) {
            System.out.println();
            player.printFullInformation();
        }

        System.out.println("\n\nBegin GameLog:");
        GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, true);
        GameRecord record = simulator.simulateGame();
        System.out.println("\n\nGame Record:\n" + record);
    }

    public static void testFullBalancedGame() {
        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        int stat = 80;
        Statline stats = new Statline(stat, stat, stat, stat, stat, stat, stat, stat, 5);
        int stat2 = 80;
        Statline stats2 = new Statline(stat2, stat2, stat2, stat2, stat2, stat2, stat2, stat2, 5);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j == 2) {
                    homeLines.get(i).add(Player.getNewPlayer("Home", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.VISIONARY, stats, 10));
                } else {
                    homeLines.get(i).add(new Player("Home", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.AGILE, stats, 10));
                }
                awayLines.get(i).add(new Player("Away", String.valueOf(j+4), 20, Playstyle.ATTACKER, Trait.AGILE, stats2, 10));
            }
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

        System.out.println(homeTeam);
        System.out.println();
        System.out.println(awayTeam);

        System.out.println("\n\nBegin GameLog:");
        GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, true);
        GameRecord record = simulator.simulateGame();

        System.out.println("\n\nGame Record:\n" + record);
    }

    public static void testPositionGame() {
        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        int goodStat = 85;
        int badStat = 50;
        Statline gs = new Statline(goodStat, goodStat, badStat, goodStat, goodStat, goodStat, badStat, badStat, 0);
        Statline pm = new Statline(goodStat, goodStat, goodStat, goodStat, goodStat, badStat, badStat, goodStat, 0);
        Statline df = new Statline(badStat, goodStat, goodStat, goodStat, badStat, badStat, goodStat, goodStat, 0);

        for (int i = 0; i < 3; i++) {
            homeLines.get(i).add(new Player("Home", "1", 20, Playstyle.ATTACKER, Trait.CLUTCH, gs, 10));
            homeLines.get(i).add(new Player("Home", "2", 20, Playstyle.PLAYMAKER, Trait.CLUTCH, pm, 10));
            homeLines.get(i).add(new Player("Home", "3", 20, Playstyle.DEFENDER, Trait.CLUTCH, df, 10));

            awayLines.get(i).add(new Player("Away", "1", 20, Playstyle.ATTACKER, Trait.CLUTCH, gs, 10));
            awayLines.get(i).add(new Player("Away", "2", 20, Playstyle.PLAYMAKER, Trait.CLUTCH, pm, 10));
            awayLines.get(i).add(new Player("Away", "3", 20, Playstyle.DEFENDER, Trait.CLUTCH, df, 10));
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

        System.out.println(homeTeam);
        System.out.println();
        System.out.println(awayTeam);

        System.out.println("\n\nBegin GameLog:");
        GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, true);
        GameRecord record = simulator.simulateGame();

        System.out.println("\n\nGame Record:\n" + record);
    }

    public static GameRecord testAgedGame(int ageNum) {
        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        for (int i = 0; i < 3; i++) {
            homeLines.get(i).add(new Player(8-i, Playstyle.ATTACKER, Trait.SNIPER));
            homeLines.get(i).add(new Player(8-i, Playstyle.PLAYMAKER, Trait.GENIUS));
            homeLines.get(i).add(new Player(8-i, Playstyle.DEFENDER, Trait.BRICK_WALL));

            awayLines.get(i).add(new Player(8-i, Playstyle.ATTACKER, Trait.SNIPER));
            awayLines.get(i).add(new Player(8-i, Playstyle.PLAYMAKER, Trait.GENIUS));
            awayLines.get(i).add(new Player(8-i, Playstyle.DEFENDER, Trait.BRICK_WALL));

            for (int k = 0; k < ageNum; k++) {
                homeLines.get(i).get(0).agePlayer();
                homeLines.get(i).get(1).agePlayer();
                homeLines.get(i).get(2).agePlayer();
                awayLines.get(i).get(0).agePlayer();
                awayLines.get(i).get(1).agePlayer();
                awayLines.get(i).get(2).agePlayer();
            }
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

        System.out.println(homeTeam);
        System.out.println();
        System.out.println(awayTeam);

        System.out.println("\n\nBegin GameLog:");
        GameSimulator simulator = new GameSimulator(homeTeam, awayTeam, true);
        return simulator.simulateGame();
    }

    public static void testShotAction() {
        ArrayList<MovablePlayer> team1 = new ArrayList<>();
        ArrayList<MovablePlayer> team2 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Player p1 = new Player();
            Player p2 = new Player();
            MovablePlayer player1 = new MovablePlayer(p1);
            MovablePlayer player2 = new MovablePlayer(p2);
            team1.add(player1);
            team2.add(player2);
        }

        Pool pool = new Pool(team1, team2);

        MovablePlayer shooter = team1.getFirst();
        MovablePlayer passer = team1.get(1);
        MovablePlayer player2 = team2.getFirst();
        MovablePlayer player3 = team2.get(1);
        MovablePlayer player4 = team2.get(2);

        pool.movePlayer(shooter, 0 ,8);
        pool.movePlayer(passer, 4,8);
        pool.movePlayer(player2, 4,8);
        pool.movePlayer(player3, 4,10);
        pool.movePlayer(player4, 0,10);

        pool.setBallHolder(shooter);
        pool.setBallHolder(shooter);

        ShotAction action = new ShotAction(pool, shooter, 0, 11);
        action.execute();

        System.out.println("Blocks: " + player4.getBlocks());
        System.out.println("Shots: " + shooter.getShots());
        System.out.println("Goals: " + shooter.getGoals());
        System.out.println("Assists: " + passer.getAssists());

        System.out.println(pool.getBallHolder());
        System.out.println(Arrays.toString(pool.getBallCoordinates()));
    }

    public static void getProbabilities(int num, int stat, int stat2) {
        double homeScore = 0;
        double awayScore = 0;
        double homeWin = 0;
        double awayWin = 0;

        ArrayList<ArrayList<Player>> homeLines = new ArrayList<>();
        ArrayList<ArrayList<Player>> awayLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            homeLines.add(new ArrayList<>());
            awayLines.add(new ArrayList<>());
        }

        Statline stats = new Statline(stat, stat, stat, stat, stat, stat, stat, stat, 0);
        Statline stats2 = new Statline(stat2, stat2, stat2, stat2, stat2, stat2, stat2, stat2, 0);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                homeLines.get(i).add(new Player("Home", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.AGILE, stats, 10));
                awayLines.get(i).add(new Player("Away", String.valueOf(j+1), 20, Playstyle.ATTACKER, Trait.AGILE, stats2, 10));
            }
        }

        GameTeam homeTeam = new GameTeam("HSN", homeLines.get(0), homeLines.get(1), homeLines.get(2));
        GameTeam awayTeam = new GameTeam("CHI", awayLines.get(0), awayLines.get(1), awayLines.get(2));

//        System.out.println(homeTeam);
//        System.out.println();
//        System.out.println(awayTeam);

        PrintStream stdout = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        for (int i = 0; i < num; i++) {
            //stdout.println("Simming game " + (i+1));

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

        System.out.println("(" + stat + " vs " + stat2 + ") Home " + homeScore + " vs. Away " + awayScore);
        System.out.println("Home " + 100*(homeWin/num) + "% vs. Away " + 100*(awayWin/num) + "% (Tie " + 100*(1 - (homeWin+awayWin)/num)+ "%)");
        //System.out.println(awayScore);
    }
}
