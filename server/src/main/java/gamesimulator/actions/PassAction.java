package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

import java.util.List;
import java.util.Random;

public class PassAction implements GameAction{
    private static final Random rand = new Random();

    private final Pool pool;
    private final MovablePlayer passer;
    private final MovablePlayer receiver;
    private final int passRandEffect;
    private final int fumbleRandEffect;
    private final int[] blockRandEffects;

    public PassAction(Pool pool, MovablePlayer passer, MovablePlayer receiver) {
        this.pool = pool;
        this.passer = passer;
        this.receiver = receiver;
        this.passRandEffect = rand.nextInt(-50,51);
        this.fumbleRandEffect = rand.nextInt(0, (passer.getPassFactor() + receiver.getPassFactor() + passRandEffect)/2);
        blockRandEffects = new int[]{rand.nextInt(-50,51), rand.nextInt(-50,51), rand.nextInt(-50,51)};
    }

    public PassAction(Pool pool, MovablePlayer passer, MovablePlayer receiver, int passRandEffect, int fumbleEffect, int block1, int block2, int block3) {
        this.pool = pool;
        this.passer = passer;
        this.receiver = receiver;
        this.passRandEffect = passRandEffect;
        this.fumbleRandEffect = fumbleEffect;
        blockRandEffects = new int[]{block1, block2, block3};
    }

    @Override
    public boolean execute() {
        System.out.println(passer + " attempts a pass to " + receiver);

        // Check possession
        if (pool.hasBall(passer)) {
            int passFactor = passer.getPassFactor() + passRandEffect;

            //Check interceptions
            List<MovablePlayer> opponents = pool.getOpponentsAlongLine(passer, receiver.getX(), receiver.getY());

            for (int i = 0; i < opponents.size(); i++) {
                MovablePlayer opponent = opponents.get(i);

                int distanceEffect = (int) ((pool.getDistance(passer, opponent) - 3) * 3);
                int interceptFactor = (opponent.getBlockFactor()+opponent.getSwimFactor())/2 + blockRandEffects[i] + distanceEffect;

                // Clean Interception
                if (interceptFactor > passFactor + 20) {
                    pool.setBallHolder(opponent);
                    opponent.incrementSteals();
                    System.out.println("Pass intercepted by " + opponent);
                    return false;
                }
                // Pass Blocked, but not intercepted
                else if (interceptFactor > passFactor) {
                    pool.removeBallHolder();

                    int newX = opponent.getX() + rand.nextInt(-3,4);
                    int newY = opponent.getY() + rand.nextInt(-3,4);
                    pool.moveBall(newX, newY);

                    System.out.println("Pass blocked by " + opponent + " to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
                    return false;
                }
            }

            // No interceptions, check fumble
            int fumbleFactor = fumbleRandEffect;

            // Pass fumbled, set ball to location near receiver
            if (fumbleFactor < 10) {
                pool.removeBallHolder();

                int newX = receiver.getX() + rand.nextInt(-1,2);
                int newY = receiver.getY() + rand.nextInt(-1,2);
                pool.moveBall(newX, newY);

                System.out.println(receiver + " fumbles the pass to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
            }

            // Clean Pass Reception
            else {
                pool.setBallHolder(receiver);
                System.out.println(receiver + " gains possession");
            }
        }

        // Passer no longer has the ball, abort pass
        else {
            System.out.println(passer + " doesn't have the ball");
        }

        return false;
    }

}
