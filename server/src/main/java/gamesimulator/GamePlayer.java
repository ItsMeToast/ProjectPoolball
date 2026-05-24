package gamesimulator;

import gamesimulator.actions.GameAction;

public interface GamePlayer {
    String getFirstName();
    String getLastName();
    int getActionPriority(Pool pool, MovablePlayer player);
    GameAction getAction(Pool pool, MovablePlayer player);
    int getSwimFactor();
    int getStealFactor();
    int getPassFactor();
    int getShotFactor();
    int getBlockFactor();
    int getFaceoffFactor();
    double getSelfInjury();
    double getOpponentInjury();
}
