package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

import java.util.Random;

public class StealAction implements GameAction{
    private static final Random rand = new Random();

    private final Pool pool;
    private final MovablePlayer stealer;
    private final MovablePlayer victim;
    private final int stealerRandEffect;
    private final int victimRandEffect;

    public StealAction(Pool pool, MovablePlayer stealer, MovablePlayer victim) {
        this.pool = pool;
        this.stealer = stealer;
        this.victim = victim;
        this.stealerRandEffect = rand.nextInt(-50,31);
        this.victimRandEffect = rand.nextInt(-50,51);
    }

    public StealAction(Pool pool, MovablePlayer stealer, MovablePlayer victim, int stealerRandEffect, int victimRandEffect) {
        this.pool = pool;
        this.stealer = stealer;
        this.victim = victim;
        this.stealerRandEffect = stealerRandEffect;
        this.victimRandEffect = victimRandEffect;
    }

    @Override
    public boolean execute() {
        System.out.println(stealer + " tries to steal from " + victim);

        // Check victim still has the ball
        if (pool.hasBall(victim)) {
            int stealerFactor = stealer.getStealFactor() + stealerRandEffect;
            int victimFactor = victim.getStealFactor() + victimRandEffect;
            double distance = pool.getDistance(stealer, victim);

            // Within stealing distance
            if (distance < 2) {
                stealerFactor -= (int) (Math.pow(distance, 2) * 10);

                // Successful Steal
                if (stealerFactor > victimFactor + 20){
                    pool.setBallHolder(stealer);
                    stealer.incrementSteals();
                    System.out.println(stealer + " steals the ball from " + victim);
                }

                // Steal attempt forces fumble
                else if (stealerFactor > victimFactor) {
                    pool.removeBallHolder();

                    int newX = stealer.getX() + rand.nextInt(-2,3);
                    int newY = stealer.getY() + rand.nextInt(-2,3);
                    pool.moveBall(newX, newY);

                    System.out.println(stealer + " knocks the ball from " + victim + " to (" + pool.getBallCoordinates()[0] + "," + pool.getBallCoordinates()[1] + ")");
                }

                // Unsuccessful Steal
                else {
                    System.out.println(victim + " maintains possession");
                }
            }

            //Distance is too far
            else {
                System.out.println(victim + " is too far away");
            }

        }

        // Victim no longer has the ball, abort steal
        else {
            System.out.println(victim + " no longer has the ball");
        }

        return false;
    }
}
