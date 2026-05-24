package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

import java.util.List;
import java.util.Random;

public class DumpAction implements GameAction {
    private static final Random rand = new Random();

    private final Pool pool;
    private final MovablePlayer player;
    private final int targetX;
    private final int targetY;
    private final int dumpRandEffect;
    private final int[] blockRandEffects;

    public DumpAction(Pool pool, MovablePlayer player, int x, int y) {
        this.pool = pool;
        this.player = player;
        this.targetX = x + rand.nextInt(-3,4);
        this.targetY = y + rand.nextInt(-3,4);
        this.dumpRandEffect = rand.nextInt(0,31);
        this.blockRandEffects = new int[]{rand.nextInt(-40,41),rand.nextInt(-40,41),rand.nextInt(-40,41)};
    }

    public DumpAction(Pool pool, MovablePlayer player, int targetX, int targetY, int dumpRandEffect, int block1, int block2, int block3) {
        this.pool = pool;
        this.player = player;
        this.targetX = targetX;
        this.targetY = targetY;
        this.dumpRandEffect = dumpRandEffect;
        this.blockRandEffects = new int[]{block1, block2, block3};
    }

    @Override
    public boolean execute() {
        // Check possession
        if (pool.hasBall(player)) {
            int dumpFactor = player.getPassFactor() + dumpRandEffect;

            //Check interceptions
            List<MovablePlayer> opponents = pool.getOpponentsAlongLine(player, targetX, targetY);

            for (int i = 0; i < opponents.size(); i++) {
                MovablePlayer opponent = opponents.get(i);

                int distanceEffect = (int) ((pool.getDistance(player, opponent) - 3) * 3);
                int interceptFactor = (opponent.getBlockFactor()+opponent.getSwimFactor())/2 + blockRandEffects[i] + distanceEffect;

                // Dump Intercepted
                if (interceptFactor > dumpFactor) {
                    pool.setBallHolder(opponent);
                    System.out.println(player + " dumps the ball, knocked down by " + opponent);
                    return false;
                }
            }

            // Successful Dump
            pool.removeBallHolder();
            pool.moveBall(targetX, targetY);
            System.out.println(player + " dumps the ball to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
        }

        // Player doesn't have ball, abort dump
        else {
            System.out.println(player + " doesn't have the ball");
        }

        return false;
    }
}
