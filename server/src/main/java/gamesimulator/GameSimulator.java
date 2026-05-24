package gamesimulator;

import gamesimulator.actions.FaceoffAction;

import java.util.*;

public class GameSimulator {
    private static final Random rand = new Random();
    private static final int[] ROUNDS = new int[]{60,50,40};
    private static final int LINES = 3;

    private final GameTeam homeTeam;
    private final GameTeam awayTeam;

    private final boolean simulateOvertime;

    public GameSimulator(GameTeam homeTeam, GameTeam awayTeam, boolean simulateOvertime) {
        if (homeTeam.getNumLines() != LINES || awayTeam.getNumLines() != LINES) {
            throw new IllegalArgumentException("Invalid line size for Teams, expected 3");
        }

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.simulateOvertime = simulateOvertime;
    }

    public GameRecord simulateGame() {
        int homeScore = 0;
        int awayScore = 0;
        boolean isOvertime = false;

        List<PlayerRecord> records = new ArrayList<>();

        List<List<MovablePlayer>> homePlayers = new ArrayList<>();
        for (int i = 0; i < LINES; i++) {
            List<MovablePlayer> players = new ArrayList<>();
            for (GamePlayer player : homeTeam.getLine(i)) {
                players.add(new MovablePlayer(player));
            }
            homePlayers.add(players);
        }

        List<List<MovablePlayer>> awayPlayers = new ArrayList<>();
        for (int i = 0; i < LINES; i++) {
            List<MovablePlayer> players = new ArrayList<>();
            for (GamePlayer player : awayTeam.getLine(i)) {
                players.add(new MovablePlayer(player));
            }
            awayPlayers.add(players);
        }

        for (int lineNumber = 0; lineNumber < LINES; lineNumber++) {
            System.out.println("\nBegin Line " + (lineNumber+1));

            //Initialize lines
            List<MovablePlayer> homeLine = homePlayers.get(lineNumber);
            List<MovablePlayer> awayLine = awayPlayers.get(lineNumber);

            Pool pool = new Pool(homeLine, awayLine);

            // Starting Faceoff
            System.out.println("\nFaceoff!");
            while (new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst()).execute()) {
                pool.setDefaultState();
                System.out.println("\nFaceoff!");
            }
            System.out.println(pool);

            // Simulate game rounds
            for (int round = 0; round < ROUNDS[lineNumber]; round++) {
                System.out.println("\nRound " + (round+1));

                List<PlayerPriorityMarker> priorityList = new ArrayList<>();

                // Get Home Action Priorities
                for (MovablePlayer player : homeLine) {
                    priorityList.add(new PlayerPriorityMarker(player, player.getActionPriority(pool, player)));
                }

                // Get Away Action Priorities
                for (MovablePlayer player : awayLine) {
                    priorityList.add(new PlayerPriorityMarker(player, player.getActionPriority(pool, player)));
                }

                // Shuffle priority list to randomize tie-breakers
                Collections.shuffle(priorityList);
                // Sort priority list (lesser priority values go earlier)
                priorityList.sort(Comparator.comparingInt(PlayerPriorityMarker::getPriority));

                // In priority order, get and execute actions
                for (PlayerPriorityMarker playerMarker : priorityList) {
                    if (playerMarker.getAction(pool).execute()) {
                        pool.setDefaultState();

                        if (round != ROUNDS[lineNumber] - 1) {
                            // Pool reset, perform a faceoff
                            System.out.println("\nFaceoff!");
                            while (new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst()).execute()) {
                                pool.setDefaultState();
                                System.out.println("\nFaceoff!");
                            }
                            System.out.println(pool);
                        }

                        break; // round completes on goal (no action "surplus")
                    }

                    System.out.println(pool);
                }
            }

            System.out.println("Line Over!!\n");

            // Opposing Injury Factors
            double homeFactor = 0;
            for (MovablePlayer opponent : homeLine) {
                homeFactor += opponent.getOpponentInjury();
            }
            double awayFactor = 0;
            for (MovablePlayer opponent : awayLine) {
                awayFactor += opponent.getOpponentInjury();
            }

            // Home Player Injuries + Stats
            for (MovablePlayer player : homeLine) {
                double injureEffect = rand.nextDouble(0, 100);

                if (injureEffect < (player.getSelfInjury() + awayFactor)/5) {
                    player.setInjuredGames(3);
                } else if (injureEffect < (player.getSelfInjury() + awayFactor)/2) {
                    player.setInjuredGames(2);
                } else if (injureEffect < player.getSelfInjury() + awayFactor) {
                    player.setInjuredGames(1);
                }

                System.out.println(new PlayerRecord(player.getFirstName(),player.getLastName(),player.getShots(), player.getGoals(), player.getAssists(), player.getBlocks(), player.getSteals(), player.getInjuredGames()));
            }
            // Away Player Injuries + Stats
            for (MovablePlayer player : awayLine) {
                double injureEffect = rand.nextDouble(0, 100);

                if (injureEffect < (player.getSelfInjury() + awayFactor)/5) {
                    player.setInjuredGames(3);
                } else if (injureEffect < (player.getSelfInjury() + awayFactor)/2) {
                    player.setInjuredGames(2);
                } else if (injureEffect < player.getSelfInjury() + awayFactor) {
                    player.setInjuredGames(1);
                }

                System.out.println(new PlayerRecord(player.getFirstName(),player.getLastName(),player.getShots(), player.getGoals(), player.getAssists(), player.getBlocks(), player.getSteals(), player.getInjuredGames()));
            }
        }

        // Compute relevant stats
        for (List<MovablePlayer> players : homePlayers) {
            for (MovablePlayer player : players) {
                homeScore += player.getGoals();
            }
        }

        for (List<MovablePlayer> players : awayPlayers) {
            for (MovablePlayer player : players) {
                awayScore += player.getGoals();
            }
        }

        // Game Tied, Sudden Death Overtime
        if (homeScore == awayScore && simulateOvertime) {
            isOvertime = true;

            System.out.println("\nBegin Overtime!");

            //Initialize lines
            List<MovablePlayer> homeLine = homePlayers.getFirst();
            List<MovablePlayer> awayLine = awayPlayers.getFirst();

            Pool pool = new Pool(homeLine, awayLine);

            // Simulate unlimited rounds of overtime
            boolean suddenDeath = true;

            // Starting Faceoff
            if (new FaceoffAction(pool, homeLine.getFirst(), awayLine.getFirst()).execute()) {
                suddenDeath = false;
            }
            System.out.println(pool);

            while (suddenDeath) {
                System.out.println("\nNew Round");

                List<PlayerPriorityMarker> priorityList = new ArrayList<>();

                // Get Home Action Priorities
                for (MovablePlayer player : homeLine) {
                    priorityList.add(new PlayerPriorityMarker(player, player.getActionPriority(pool, player)));
                }

                // Get Away Action Priorities
                for (MovablePlayer player : awayLine) {
                    priorityList.add(new PlayerPriorityMarker(player, player.getActionPriority(pool, player)));
                }

                // Shuffle priority list to randomize tie-breakers
                Collections.shuffle(priorityList);
                // Sort priority list (lesser priority values go earlier)
                priorityList.sort(Comparator.comparingInt(PlayerPriorityMarker::getPriority));

                // In priority order, get and execute actions
                for (PlayerPriorityMarker playerMarker : priorityList) {
                    if (playerMarker.getAction(pool).execute()) {
                        suddenDeath = false;
                        break;
                    }

                    System.out.println(pool);
                }
            }

            System.out.println("Game Over!!\n");
        }

        homeScore = 0;
        awayScore = 0;

        // Create Player Records and calculate game score
        for (List<MovablePlayer> players : homePlayers) {
            for (MovablePlayer player : players) {
                homeScore += player.getGoals();
                records.add(new PlayerRecord(player.getFirstName(),player.getLastName(),player.getShots(), player.getGoals(), player.getAssists(), player.getBlocks(), player.getSteals(), player.getInjuredGames()));
            }
        }

        for (List<MovablePlayer> players : awayPlayers) {
            for (MovablePlayer player : players) {
                awayScore += player.getGoals();
                records.add(new PlayerRecord(player.getFirstName(),player.getLastName(),player.getShots(), player.getGoals(), player.getAssists(), player.getBlocks(), player.getSteals(), player.getInjuredGames()));
            }
        }

        return new GameRecord(homeTeam.getName(), awayTeam.getName(), homeScore, awayScore, isOvertime, records);
    }
}
