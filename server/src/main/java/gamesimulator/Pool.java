package gamesimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Pool {
    private static final Random rand = new Random();
    private static final int POOLX = 5;
    private static final int POOLY = 11;

    private final List<MovablePlayer> homePlayers;
    private final List<MovablePlayer> awayPlayers;

    private Optional<MovablePlayer> ballHolder;
    private Optional<MovablePlayer> prevBallHolder;
    private final int[] ballCoordinates = new int[2];

    public Pool(List<MovablePlayer> homePlayers, List<MovablePlayer> awayPlayers) {
        this.homePlayers = homePlayers;
        this.awayPlayers = awayPlayers;
        setDefaultState();
    }

    /**
     * Resets the pool to default state (Faceoff)
     */
    public void setDefaultState() {
        movePlayer(homePlayers.get(0), 2,4);
        movePlayer(homePlayers.get(1), 2,3);
        movePlayer(homePlayers.get(2), 2,2);

        movePlayer(awayPlayers.get(0), 2,6);
        movePlayer(awayPlayers.get(1), 2,7);
        movePlayer(awayPlayers.get(2), 2,8);

        prevBallHolder = Optional.empty();
        ballHolder = Optional.empty();
        moveBall(2,5);
    }

    @Override
    public String toString() {
        StringBuilder pool = new StringBuilder();

        for (int y = POOLY - 1; y >= 0; y--) {
            for (int x = 0; x < POOLX; x++) {
                boolean hasObject = false;

                for (MovablePlayer player : homePlayers) {
                    if (player.getX() == x && player.getY() == y) {
                        pool.append(homePlayers.indexOf(player)+1);
                        hasObject = true;
                    }
                }

                if (!hasObject) {
                    pool.append("-");
                }
                hasObject = false;

                for (MovablePlayer player : awayPlayers) {
                    if (player.getX() == x && player.getY() == y) {
                        pool.append(awayPlayers.indexOf(player) + 4);
                        hasObject = true;
                    }
                }

                if (!hasObject) {
                    pool.append("-");
                }
                hasObject = false;

                if (getBallCoordinates()[0] == x && getBallCoordinates()[1] == y) {
                    pool.append("B");
                    hasObject = true;
                }

                if (!hasObject) {
                    pool.append("-");
                }
                pool.append("|");
            }
            pool.append("\n");
        }

        return pool.toString();
    }

    /**
     * Moves the given player to the specified coordinates in the pool
     * @param player MovablePlayer to move, must be in pool
     * @param x the x coordinate to move to, must be within pool
     * @param y the y coordinate to move to, must be within pool
     */
    public void movePlayer(MovablePlayer player, int x, int y) {
        if (x < 0 || POOLX <= x) {
            throw new IllegalArgumentException("Invalid x-coordinate: " + x);
        } else if (y < 0 || POOLY <= y) {
            throw new IllegalArgumentException("Invalid y-coordinate: " + y);
        }

        if (containsPlayer(player)) {
          player.move(x, y);
        }
    }

    /**
     * Moves the ball to the specified coordinates
     * @param x the x coordinate to move to, must be within pool
     * @param y the y coordinate to move to, must be within pool
     */
    public void moveBall(int x, int y) {
        x = Math.min(Math.max(x, 0), POOLX - 1);
        y = Math.min(Math.max(y, 0), POOLY - 1);

        ballCoordinates[0] = x;
        ballCoordinates[1] = y;
    }

    /**
     * Sets the current ball holder to the specified player
     * @param player MovablePlayer to gain possession of the ball, must be in pool
     */
    public void setBallHolder(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Pool does not contain player " + player);
        }

        if (ballHolder.isPresent()) {
            prevBallHolder = ballHolder;
        }

        ballHolder = Optional.of(player);
    }

    /**
     * Removes the current ball holder
     */
    public void removeBallHolder() {
        if (ballHolder.isPresent()) {
            moveBall(ballHolder.get().getX(), ballHolder.get().getY());
            prevBallHolder = ballHolder;
        }

        ballHolder = Optional.empty();
    }

    /**
     * Returns the full Optional for the ball holder
     * @return Optional object containing the current ball holder (or lack of)
     */
    public Optional<MovablePlayer> getBallHolder() {
        return ballHolder;
    }

    /**
     * Returns the full Optional for the previous ball holder
     * @return Optional object containing the previous ball holder (or lack of)
     */
    public Optional<MovablePlayer> getPrevBallHolder() {
        return prevBallHolder;
    }

    /**
     * Returns the length of the pool in the x direction
     * @return The size of the pool x-wise (will be out of bounds as index)
     */
    public int getPoolX() {
        return POOLX;
    }

    /**
     * Returns the length of the pool in the y direction
     * @return The size of the pool y-wise (will be out of bounds as index)
     */
    public int getPoolY() {
        return POOLY;
    }

    /**
     * Provides the midway x-index for the pool
     * @return the integer corresponding to the middle tile of the pool in the x direction
     */
    public int getPoolXHalf() {return POOLX/2;}

    /**
     * Provides the midway y-index for the pool
     * @return the integer corresponding to the middle tile of the pool in the y direction
     */
    public int getPoolYHalf() {return POOLY/2;}

    /**
     * Provides the current coordinates of the ball
     * @return array formatted as [x, y] of the coordinates
     */
    public int[] getBallCoordinates() {
        if (ballHolder.isEmpty()) {
            return ballCoordinates;
        } else {
            return new int[]{ballHolder.get().getX(), ballHolder.get().getY()};
        }
    }

    /**
     * Returns true if the given player is in the Pool
     * @param player MovablePlayer to check
     * @return boolean representing if the player is in either homePlayers or awayPlayers
     */
    public boolean containsPlayer(MovablePlayer player) {
        return (homePlayers.contains(player) || awayPlayers.contains(player));
    }

    /**
     * Checks if a player has the ball
     * @param player MovablePlayer to check
     * @return if the current ball holder is the given player
     */
    public boolean hasBall(MovablePlayer player) {
        return (ballHolder.isPresent() && ballHolder.get() == player);
    }

    /**
     * Checks if two players are on the same team
     * @param player1 first MovablePlayer to check
     * @param player2 second MovablePlayer to check
     * @return true if both players are on the same team, false otherwise
     */
    public boolean areSameTeam(MovablePlayer player1, MovablePlayer player2) {
        if (homePlayers.contains(player1) && homePlayers.contains(player2)) {
            return true;
        } else {
            return awayPlayers.contains(player1) && awayPlayers.contains(player2);
        }
    }

    /**
     * Calculates the Euclidean distance between two players
     * @param source first MovablePlayer (must be in pool)
     * @param destination second MovablePlayer (must be in pool)
     * @return the distance between the source and destination players (order is irrelevant)
     */
    public double getDistance(MovablePlayer source, MovablePlayer destination) {
        if (containsPlayer(source) && containsPlayer(destination)) {
            return Math.sqrt(Math.pow(destination.getX() - source.getX(), 2) + Math.pow(destination.getY() - source.getY(), 2));
        }

        if (containsPlayer(source)) {
            throw new IllegalArgumentException("Pool does not contain player " + destination);
        }
        throw new IllegalArgumentException("Pool does not contain player " + source);
    }

    /**
     * Calculates the Euclidean distance between a player and a coordinate
     * @param source MovablePlayer
     * @param destinationX the x coordinate to use. Can be outside pool
     * @param destinationY the y coordinate to use. Can be outside pool
     * @return the distance between player and the specified point (x,y)
     */
    public double getDistance(MovablePlayer source, int destinationX, int destinationY) {
        if (!containsPlayer(source)) {
            throw new IllegalArgumentException("Pool does not contain player " + source);
        }

        return Math.sqrt(Math.pow(source.getX() - destinationX, 2) + Math.pow(source.getY() - destinationY, 2));
    }

    /**
     * Returns a list of the opponents on a one tile wide line from the player to a point
     * @param player MovablePlayer to start at
     * @param xEnd the x coordinate to end at
     * @param yEnd the y coordinate to end at
     * @return ArrayList containing the opponents found
     */
    public List<MovablePlayer> getOpponentsAlongLine(MovablePlayer player, int xEnd, int yEnd) {
        ArrayList<MovablePlayer> players = new ArrayList<>();

        int vecX = xEnd - player.getX();
        int vecY = yEnd - player.getY();
        double length = Math.sqrt(Math.pow(vecX, 2) + Math.pow(vecY, 2));

        double deltaX = vecX / length;
        double deltaY = vecY / length;

        ArrayList<MovablePlayer> opponentList;
        if (homePlayers.contains(player)) {
            opponentList = new ArrayList<>(awayPlayers);
        } else {
            opponentList = new ArrayList<>(homePlayers);
        }

        for (int i = 0; i < Math.ceil(length); i++) {
            double x = player.getX() + (i * deltaX);
            double y = player.getY() + (i * deltaY);

            for (MovablePlayer opponent : opponentList) {
                if ((opponent.getX() == Math.ceil(x) || opponent.getX() == Math.floor(x)) && (opponent.getY() == Math.ceil(y) || opponent.getY() == Math.floor(y))) {
                    if (!players.contains(opponent)) {
                        players.add(opponent);
                    }
                }
            }
        }

        return players;
    }

    /**
     * Returns a list of the opponents on a cone originating from a player and spreading to a point
     * @param player MovablePlayer to start at
     * @param xEnd the x coordinate to end at
     * @param yEnd the y coordinate to end at
     * @return ArrayList containing the opponents found
     */
    public List<MovablePlayer> getOpponentsOnCone(MovablePlayer player, int xEnd, int yEnd) {
        ArrayList<MovablePlayer> players = new ArrayList<>();

        int xSlope = xEnd - player.getX();
        int ySlope = yEnd - player.getY();
        double length = Math.sqrt(Math.pow(xSlope, 2) + Math.pow(ySlope, 2));

        double deltaX = xSlope/length;
        double deltaY = ySlope/length;

        ArrayList<MovablePlayer> opponentList;
        if (homePlayers.contains(player)) {
            opponentList = new ArrayList<>(awayPlayers);
        } else {
            opponentList = new ArrayList<>(homePlayers);
        }

        double x1 = xEnd + (length/2) * deltaY;
        double x2 = xEnd - (length/2) * deltaY;
        double y1 = yEnd - (length/2) * deltaX;
        double y2 = yEnd + (length/2) * deltaX;

        for (MovablePlayer opp : opponentList) {
            if (pointWithinTriangle(opp.getX(), opp.getY(), player.getX(), player.getY(), x1, y1, x2, y2)) {
                players.add(opp);
            } else if (opp.getX() == player.getX() && opp.getY() == getOffensiveOffset(player, player.getY(), 1)) {
                players.add(opp);
            }
        }

        return players;
    }

    /**
     * Helper method for pointWithinTriangle, using barycentric coordinates
     */
    private double triangleSign(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
    }

    /**
     * Calculates whether a point is within a given triangle
     * @param x the x coordinate to verify
     * @param y the y coordinate to verify
     * @return whether the given point is within the triangle bounded by the three given vertices
     */
    private boolean pointWithinTriangle(double x, double y, double x1, double y1, double x2, double y2, double x3, double y3) {
        double ts1 = triangleSign(x, y, x1, y1, x2, y2);
        double ts2 = triangleSign(x, y, x2, y2, x3, y3);
        double ts3 = triangleSign(x, y, x3, y3, x1, y1);

        boolean negative = (ts1 < 0) || (ts2 < 0) || (ts3 < 0);
        boolean positive = (ts1 > 0) || (ts2 > 0) || (ts3 > 0);

        return !(negative && positive);
    }

    /**
     * Method used for testing only, to find all points in grid within a triangle
     */
    public void allWithinTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 11; y++) {
                if (pointWithinTriangle(x, y, x1, y1, x2, y2, x3, y3)) {
                    System.out.println("(" + x + "," + y + ")");
                }
            }
        }
    }

    /**
     * Returns the target that the player must shoot at to score
     * @param player MovablePlayer to check target
     * @return the y coordinate of the player's target
     */
    public int getTarget(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        if (homePlayers.contains(player)) {
            return POOLY;
        } else {
            return -1;
        }
    }

    /**
     * Calculates the distance from a player's defensive wall
     * @param player MovablePlayer to use as team reference
     * @param targetY y coordinate to check distance
     * @return integer representing distance, lowest possible is 1 when in furthest tile back
     */
    public int getDistanceFromDefensive(MovablePlayer player, int targetY) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        if (homePlayers.contains(player)) {
            return targetY + 1;
        } else {
            return POOLY - targetY;
        }
    }

    /**
     * Returns a new y coordinate that is offset from a starting position towards the players target
     * @param player MovablePlayer to use as team reference
     * @param sourceY the starting y coordinate
     * @param distance the difference from y to use (negative values will go towards defensive zone)
     * @return the y coordinate that has been offset
     */
    public int getOffensiveOffset(MovablePlayer player, int sourceY, int distance) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        if (homePlayers.contains(player)) {
            return Math.max(Math.min(POOLY - 1, sourceY + distance), 0);
        } else {
            return Math.min(Math.max(0, sourceY - distance), POOLY-1);
        }
    }

    /**
     * Calculates the general x spread of opponents relative to a player
     * @param player MovablePlayer to use as source
     * @return int representing the balance, negative is towards left, positive is towards right
     */
    public int getEnemyBalance(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        List<MovablePlayer> opponents;

        if (homePlayers.contains(player)) {
            opponents = awayPlayers;
        } else {
            opponents = homePlayers;
        }

        int balance = 0;

        for (MovablePlayer opp : opponents) {
            balance += (opp.getX() - (POOLX/2)) * (POOLY - Math.abs(opp.getY() - player.getY()));
        }

        return balance;
    }

    /**
     * Calculates the general x spread of opponents relative to a player in the forward direction only
     * @param player MovablePlayer to use as source
     * @return int representing the balance, negative is towards left, positive is towards right
     */
    public int getEnemyForwardBalance(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        List<MovablePlayer> opponents;

        if (homePlayers.contains(player)) {
            opponents = awayPlayers;
        } else {
            opponents = homePlayers;
        }

        int balance = 0;

        for (MovablePlayer opp : opponents) {
            if (getDistanceFromDefensive(player, opp.getY()) >= getDistanceFromDefensive(player, player.getY())) {
                balance += (opp.getX() - (POOLX/2)) * (POOLY - Math.abs(opp.getY() - player.getY()));
            }
        }

        return balance;
    }

    /**
     * Uses a balance value and strength to determine an open x coordinate
     * @param balance balance value, likely from EnemyBalance or EnemyForwardBalance
     * @param strength The strength to tend towards the edge of the pool (0, 1, or 2 only)
     * @return corresponding x coordinate best opposing the balance value
     */
    public int getBalanceOffset(int balance, int strength) {
        if (strength > 2 || strength < 0) {
            throw new IllegalArgumentException("Strength should only be 0, 1, or 2");
        }

        int xHalf = getPoolXHalf();

        if (balance < 0) {
            return xHalf + strength;
        }

        else if (balance > 0) {
            return xHalf - strength;
        }

        else {
            if (rand.nextInt(2) == 1) {
                return xHalf + strength;
            } else {
                return xHalf - strength;
            }
        }
    }

    /**
     * Returns the First Man on the same team as the player provided
     * @param player MovablePlayer to use as team reference
     * @return the first man on the given team
     */
    public MovablePlayer getFirstMan(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        MovablePlayer firstMan = null;

        if (homePlayers.contains(player)) {
            int best = -1;

            for (MovablePlayer teammate : homePlayers) {
                if (teammate.getY() > best) {
                    best = teammate.getY();
                    firstMan = teammate;
                }
            }
        } else {
            int best = POOLY;

            for (MovablePlayer teammate : awayPlayers) {
                if (teammate.getY() < best) {
                    best = teammate.getY();
                    firstMan = teammate;
                }
            }
        }

        return firstMan;
    }

    /**
     * Returns the Second Man on the same team as the player provided
     * @param player MovablePlayer to use as team reference
     * @return the second man on the given team
     */
    public MovablePlayer getSecondMan(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        MovablePlayer firstMan = getFirstMan(player);
        MovablePlayer secondMan = null;

        if (homePlayers.contains(player)) {
            int best = -1;

            for (MovablePlayer teammate : homePlayers) {
                if (teammate.getY() > best && teammate != firstMan) {
                    best = teammate.getY();
                    secondMan = teammate;
                }
            }
        } else {
            int best = POOLY;

            for (MovablePlayer teammate : awayPlayers) {
                if (teammate.getY() < best && teammate != firstMan) {
                    best = teammate.getY();
                    secondMan = teammate;
                }
            }
        }

        return secondMan;
    }

    /**
     * Returns the Third Man on the same team as the player provided
     * @param player MovablePlayer to use as team reference
     * @return the third man on the given team
     */
    public MovablePlayer getThirdMan(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        MovablePlayer thirdMan = null;
        if (homePlayers.contains(player)) {
            int best = POOLY;

            for (MovablePlayer teammate : homePlayers) {
                if (teammate.getY() <= best) {
                    best = teammate.getY();
                    thirdMan = teammate;
                }
            }
        } else {
            int best = -1;

            for (MovablePlayer teammate : awayPlayers) {
                if (teammate.getY() >= best) {
                    best = teammate.getY();
                    thirdMan = teammate;
                }
            }
        }

        return thirdMan;
    }

    /**
     * Returns the desired position on the enemy team of the player provided
     * @param position the position of enemy to get (1, 2, or 3)
     * @param player MovablePlayer to use as team reference
     * @return the specified enemy player
     */
    public MovablePlayer getEnemyPlayer(int position, MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        List<MovablePlayer> opponents;
        if (homePlayers.contains(player)) {
            opponents = awayPlayers;
        } else {
            opponents = homePlayers;
        }

        // 1: Enemy First Man, 2: Enemy Second Man, 3: Enemy Third Man
        if (position == 1) {
            return getFirstMan(opponents.getFirst());
        } else if (position == 2) {
            return getSecondMan(opponents.getFirst());
        } else {
            return getThirdMan(opponents.getFirst());
        }
    }

    /**
     * Returns the closest opponent, favouring opponents that are ahead of given player
     * @param player MovablePlayer to use as source
     * @return the next player on opposing team (favours closer and in front)
     */
    public MovablePlayer getNextOpponent(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        List<MovablePlayer> opponents;

        if (homePlayers.contains(player)) {
            opponents = awayPlayers;
        } else {
            opponents = homePlayers;
        }

        MovablePlayer closest = opponents.getFirst();

        for (MovablePlayer opponent : opponents) {
            int offensiveDistance = getDistanceFromDefensive(player, opponent.getY()) - getDistanceFromDefensive(player, player.getY());
            int closestDistance = getDistanceFromDefensive(player, closest.getY()) - getDistanceFromDefensive(player, player.getY());

            if (offensiveDistance >= 0 && (closestDistance < 0 || offensiveDistance < closestDistance)) {
                closest = opponent;
            } else if (offensiveDistance < 0 && closestDistance < 0 && offensiveDistance > closestDistance) {
                closest = opponent;
            } else if (offensiveDistance == closestDistance && getDistance(player, opponent) < getDistance(player, closest)) {
                closest = opponent;
            }
        }

        return closest;
    }

    /**
     * Calculates the number of opponents that could intercept a pass from passer to receiver
     * @param passer MovablePlayer to pass the ball
     * @param receiver MovablePlayer to receive the pass
     * @return the number of opponents along the pass trajectory
     */
    public int getPassCongestion(MovablePlayer passer, MovablePlayer receiver) {
        if (!containsPlayer(passer) || !containsPlayer(receiver)) {
            throw new IllegalArgumentException("Pool does not contain players passed");
        }

        return getOpponentsAlongLine(passer, receiver.getX(), receiver.getY()).size();
    }

    /**
     * Calculates the number of opponents that could block a shot from player to a specific x target
     * @param shooter MovablePlayer to shoot the ball
     * @param xTarget x coordinate that the player will aim at
     * @return number of opponents along the shot trajectory
     */
    public int getShotCongestion(MovablePlayer shooter, int xTarget) {
        if (!containsPlayer(shooter)) {
            throw new IllegalArgumentException("Player " + shooter + " is not in the pool");
        }

        return getOpponentsOnCone(shooter, xTarget, getTarget(shooter)).size();
    }

    /**
     * Determines the best x coordinate for a given player to shoot at
     * @param shooter MovablePlayer to shoot the ball
     * @return the least contested shot target, favouring closer targets in the case of a tie
     */
    public int getBestShot(MovablePlayer shooter) {
        int bestXTarget = 0;
        int bestShotCongestion = getShotCongestion(shooter, bestXTarget);

        for (int x = 1; x < POOLX; x++) {
            int candidate = getShotCongestion(shooter, x);
            int xDiff = Math.abs(shooter.getX()-x);

            // If shot is less contested, or equally contested but a more straight shot, it is best candidate
            if (candidate < bestShotCongestion || (candidate == bestShotCongestion && xDiff < Math.abs(shooter.getX()-bestXTarget))) {
                bestShotCongestion = candidate;
                bestXTarget = x;
            }
        }

        return bestXTarget;
    }

    /**
     * Determines the teammate on a player's team that is closest to the ball
     * @param player MovablePlayer to use as team reference
     * @return the closest teammate to the ball
     */
    public MovablePlayer getClosestTeammateToBall(MovablePlayer player) {
        if (!containsPlayer(player)) {
            throw new IllegalArgumentException("Player " + player + " is not in the pool");
        }

        List<MovablePlayer> teammates;

        if (homePlayers.contains(player)) {
            teammates = homePlayers;
        } else {
            teammates = awayPlayers;
        }

        double min = Double.MAX_VALUE;
        MovablePlayer closest = teammates.getFirst();
        int[] ballCoordinates = getBallCoordinates();

        for (MovablePlayer teammate : teammates) {
            double distance = getDistance(teammate, ballCoordinates[0], ballCoordinates[1]);

            if (distance < min || (distance == min && teammate == player)) {
                min = distance;
                closest = teammate;
            }
        }

        return closest;
    }

    /**
     * Calculates the best tile for a player to move to given a distance restriction and desired end location
     * @param player MovablePlayer to move and use as source point
     * @param maxMove the largest Euclidean distance that can be covered by the player in a move
     * @param xTarget the desired x coordinate, not always reachable
     * @param yTarget the desired y coordinate, not always reachable
     * @return the best location for the player to move to, always within the pool even if target is not
     */
    public int[] getBestMove(MovablePlayer player, int maxMove, int xTarget, int yTarget) {
        int xSlope = xTarget - player.getX();
        int ySlope = yTarget - player.getY();
        double length = getDistance(player, xTarget, yTarget);

        int bestX;
        int bestY;

        // Can make target
        if (maxMove >= length) {
            bestX = xTarget;
            bestY = yTarget;
        }

        // Find best reachable point
        else {
            double deltaX = xSlope/length;
            double deltaY = ySlope/length;

            bestX = player.getX() + (int) Math.round(deltaX * maxMove);
            bestY = player.getY() + (int) Math.round(deltaY * maxMove);
        }

        bestX = Math.min(POOLX-1, Math.max(0, bestX));
        bestY = Math.min(POOLY-1, Math.max(0, bestY));

        return new int[]{bestX, bestY};
    }
}
