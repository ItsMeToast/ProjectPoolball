package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

import java.util.List;
import java.util.Random;

public class ShotAction implements GameAction{
    private static final Random rand = new Random();

    private final Pool pool;
    private final MovablePlayer shooter;
    private final int targetX;
    private final int targetY;
    private final int shotRandEffect;
    private final int[] blockRandEffects;

    public ShotAction(Pool pool, MovablePlayer shooter, int targetX, int targetY) {
        this.pool = pool;
        this.shooter = shooter;
        this.targetX = targetX;
        this.targetY = targetY;
        this.shotRandEffect = rand.nextInt(-50,51);
        this.blockRandEffects = new int[]{rand.nextInt(-50,51), rand.nextInt(-50,51), rand.nextInt(-50,51)};
    }

    public ShotAction(Pool pool, MovablePlayer shooter, int targetX, int targetY, int shotRandEffect, int block1, int block2, int block3) {
        this.pool = pool;
        this.shooter = shooter;
        this.targetX = targetX;
        this.targetY = targetY;
        this.shotRandEffect = shotRandEffect;
        this.blockRandEffects = new int[]{block1, block2, block3};
    }

    @Override
    public boolean execute() {
        System.out.println(shooter + " attempts a shot on net at (" + targetX + "," + targetY + ")");

        // Check that shooter still has the ball
        if (pool.hasBall(shooter)) {
            shooter.incrementShots();
            int shotFactor = shooter.getShotFactor() + shotRandEffect;

            // Check blocks
            List<MovablePlayer> blockers = pool.getOpponentsOnCone(shooter, targetX, targetY);

            // getOpponentsOnCone returns in increasing distance, check further opponents first (so third man gets more blocks)
            for (int i = blockers.size() - 1; i >= 0; i--) {
                MovablePlayer blocker = blockers.get(i);

                int distanceEffect = (Math.abs(shooter.getY() - blocker.getY()) - 3) * 5;
                int blockFactor = blocker.getBlockFactor() + blockRandEffects[i] + distanceEffect;

                // Clean Block, grab the ball
                if (blockFactor > shotFactor + 20) {
                    pool.setBallHolder(blocker);
                    blocker.incrementBlocks();
                    System.out.println("Shot grabbed by " + blocker);
                    return false;
                }

                // Block with rebound
                else if (blockFactor > shotFactor) {
                    pool.removeBallHolder();
                    blocker.incrementBlocks();

                    int newX = blocker.getX() + rand.nextInt(-3,4);
                    int newY = blocker.getY() + rand.nextInt(-3,4);
                    pool.moveBall(newX, newY);

                    System.out.println("Shot blocked by " + blocker + " and rebounds to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
                    return false;
                }
            }

            // No blocks, check if shot hits the net
            int distance = (int) Math.sqrt(Math.pow(targetX - shooter.getX(), 2) + Math.pow(targetY - shooter.getY(), 2));
            int distanceEffect = (3 - distance) * 10;

            // Shot misses the net
            if (shotFactor + distanceEffect < 30) {
                pool.removeBallHolder();

                int newX = targetX + rand.nextInt(-2,3);
                int newY = targetY + rand.nextInt(-2,3);
                pool.moveBall(newX, newY);

                System.out.println(shooter + " misses the net, ball lands (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
            }

            // GOOOOOOOOOOOOOOOOAL!!!
            else {
                shooter.incrementGoals();
                String message = "Goal scored by " + shooter;

                if (pool.getPrevBallHolder().isPresent()) {
                    MovablePlayer prevHolder = pool.getPrevBallHolder().get();
                    if (pool.areSameTeam(shooter, prevHolder) && prevHolder != shooter) {
                        prevHolder.incrementAssists();
                        message += ", assisted by " + prevHolder;
                    }
                }

                System.out.println(message + "!");
                return true;
            }

        }

        // Shooter doesn't have ball anymore, abort shot
        else {
            System.out.println(shooter + " no longer has the ball");
        }

        return false;
    }

}
