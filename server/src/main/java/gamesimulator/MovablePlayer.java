package gamesimulator;

import gamesimulator.actions.GameAction;

public class MovablePlayer implements GamePlayer{
    private int x;
    private int y;
    private int shots;
    private int goals;
    private int assists;
    private int blocks;
    private int steals;
    private int injuredGames;
    private final GamePlayer player;

    public String getFirstName() {
        return player.getFirstName();
    }

    public String getLastName() {
        return player.getLastName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MovablePlayer(GamePlayer player) {
        this.x = 0;
        this.y = 0;
        this.player = player;
    }

    @Override
    public int getActionPriority(Pool pool, MovablePlayer player) {
        if (player != this) {
            throw new IllegalArgumentException("Incorrect player passed to getAction");
        }

        return this.player.getActionPriority(pool, player);
    }

    @Override
    public GameAction getAction(Pool pool, MovablePlayer player) {
        if (player != this) {
            throw new IllegalArgumentException("Incorrect player passed to getAction");
        }

        return this.player.getAction(pool, player);
    }

    @Override
    public int getSwimFactor() {
        return player.getSwimFactor();
    }

    @Override
    public int getStealFactor() {
        return player.getStealFactor();
    }

    @Override
    public int getPassFactor() {
        return player.getPassFactor();
    }

    @Override
    public int getShotFactor() {
        return player.getShotFactor();
    }

    @Override
    public int getBlockFactor() {
        return player.getBlockFactor();
    }

    @Override
    public int getFaceoffFactor() {
        return player.getFaceoffFactor();
    }

    @Override
    public double getSelfInjury() {
        return player.getSelfInjury();
    }

    @Override
    public double getOpponentInjury() {
        return player.getOpponentInjury();
    }

    public int getShots() {
        return shots;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getSteals() {
        return steals;
    }

    public int getInjuredGames() {
        return injuredGames;
    }

    public void incrementShots() {
        shots++;
    }

    public void incrementGoals() {
        goals++;
    }

    public void incrementAssists() {
        assists++;
    }

    public void incrementBlocks() {
        blocks++;
    }

    public void incrementSteals() {
        steals++;
    }

    public void setInjuredGames(int games) {
        this.injuredGames = games;
    }

    @Override
    public String toString() {
        return player.getFirstName() + " " + player.getLastName();
    }
}
