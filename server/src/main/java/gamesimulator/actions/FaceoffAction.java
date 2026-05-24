package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

import java.util.Random;

public class FaceoffAction implements GameAction {
    private static final Random rand = new Random();

    private final Pool pool;
    private final MovablePlayer homePlayer;
    private final MovablePlayer awayPlayer;
    private final int homeRandEffect;
    private final int awayRandEffect;
    private final boolean freeAction;

    public FaceoffAction(Pool pool, MovablePlayer homePlayer, MovablePlayer awayPlayer) {
        this.pool = pool;
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.homeRandEffect = rand.nextInt(-50,51);
        this.awayRandEffect = rand.nextInt(-50,51);
        this.freeAction = true;
    }

    public FaceoffAction(Pool pool, MovablePlayer homePlayer, MovablePlayer awayPlayer, int homeRandEffect, int awayRandEffect, boolean freeAction) {
        this.pool = pool;
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.homeRandEffect = homeRandEffect;
        this.awayRandEffect = awayRandEffect;
        this.freeAction = freeAction;
    }

    @Override
    public boolean execute() {
        int homeDistanceSquare = (int) Math.pow(pool.getDistance(homePlayer, pool.getBallCoordinates()[0], pool.getBallCoordinates()[1]), 2);
        int awayDistanceSquare = (int) Math.pow(pool.getDistance(awayPlayer, pool.getBallCoordinates()[0], pool.getBallCoordinates()[1]), 2);

        int homeFactor = homePlayer.getFaceoffFactor() + homeRandEffect - (5 * homeDistanceSquare);
        int awayFactor = awayPlayer.getFaceoffFactor() + awayRandEffect - (5 * awayDistanceSquare);

        // Clean win by home player
        if (homeFactor > awayFactor + 20) {
            pool.setBallHolder(homePlayer);
            System.out.println(homePlayer + " cleanly wins the faceoff");
            if (freeAction) {
                return homePlayer.getAction(pool, homePlayer).execute();
            }
        }

        // Clean win by away player
        else if (awayFactor > homeFactor + 20) {
            pool.setBallHolder(awayPlayer);
            System.out.println(awayPlayer + " cleanly wins the faceoff");
            if (freeAction) {
                return awayPlayer.getAction(pool, awayPlayer).execute();
            }
        }

        // Win to home side (home wins on tie)
        else if (homeFactor >= awayFactor) {
            int newX = homePlayer.getX() + rand.nextInt(-2,3);
            int newY = pool.getOffensiveOffset(homePlayer, pool.getBallCoordinates()[1], rand.nextInt(-3, 0));
            pool.moveBall(newX, newY);
            System.out.println(homePlayer + " wins the faceoff to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
        }

        // Win to away side
        else {
            int newX = awayPlayer.getX() + rand.nextInt(-2,3);
            int newY = pool.getOffensiveOffset(awayPlayer, pool.getBallCoordinates()[1], rand.nextInt(-3, 0));
            pool.moveBall(newX, newY);
            System.out.println(awayPlayer + " wins the faceoff to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
        }

        return false;
    }
}
