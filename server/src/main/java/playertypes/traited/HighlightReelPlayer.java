package playertypes.traited;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;
import gamesimulator.actions.*;
import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

import java.util.Random;

public class HighlightReelPlayer extends Player{
    private static final Random rand = new Random();

    public HighlightReelPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.HIGHLIGHT_REEL)) {throw new IllegalArgumentException("HighlightReelPlayer must have trait HIGHLIGHT_REEL");}
    }

    public HighlightReelPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.HIGHLIGHT_REEL)) {throw new IllegalArgumentException("HighlightReelPlayer must have trait HIGHLIGHT_REEL");}
    }

    public HighlightReelPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.HIGHLIGHT_REEL)) {throw new IllegalArgumentException("HighlightReelPlayer must have trait HIGHLIGHT_REEL");}
    }

    /**
     * Action to be performed when player has the ball (Highlight reel takes more shots from far out)
     * @param pool the Pool containing game state
     * @param player the player (ball carrier) performing the action
     * @param maxMove maximum distance for a MoveAction
     * @return a GameAction representing the players action
     */
    @Override
    protected GameAction getCarrierAction(Pool pool, MovablePlayer player, int maxMove) {
        // Get Teammate Positioning
        MovablePlayer firstMan = pool.getFirstMan(player);
        MovablePlayer secondMan = pool.getSecondMan(player);
        MovablePlayer thirdMan = pool.getThirdMan(player);

        int distanceFromGoal = pool.getPoolY() - pool.getDistanceFromDefensive(player, player.getY());
        int bestShotTarget = pool.getBestShot(player);

        // FIRST MAN
        if (player == firstMan) {
            // If there is open shot lane, take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0 && distanceFromGoal <= 7) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            // If closest forward opponent is more than distance 2 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 2) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // Chance to pass to second man, or guaranteed if second man close with open shot lane
            if (rand.nextInt(0, player.getPassFactor()) > 60 || pool.getShotCongestion(secondMan,pool.getBestShot(secondMan)) == 0 && pool.getDistanceFromDefensive(player, secondMan.getY()) > 6) {
                return new PassAction(pool, player, secondMan);
            }

            return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
        }


        // THIRD MAN
        else if (player == thirdMan) {
            // If there is open shot lane, chance to take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0 && distanceFromGoal <= 8) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            // If closest forward opponent is more than distance 3 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 3) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            //If there is open pass to second man or first man, make the pass
            if (pool.getPassCongestion(player, secondMan) == 0) {
                return new PassAction(pool, player, secondMan);
            } if (pool.getPassCongestion(player, firstMan) == 0) {
                return new PassAction(pool, player, firstMan);
            }

            // Chance to just pass to second man
            if (rand.nextInt(0, player.getPassFactor()) > 40) {
                return new PassAction(pool, player, secondMan);
            }

            // Chance to shoot the ball if reasonable distance
            if (rand.nextInt(0, player.getShotFactor()) > 50 && distanceFromGoal <= 8) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            // Chance to make a move around defender
            if (rand.nextInt(0, player.getSwimFactor()) > 55) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player),2);
                int targetY = pool.getOffensiveOffset(player, player.getY(), 2);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // Otherwise, dump the ball into opponent’s zone ahead of first man
            return new DumpAction(pool, player, firstMan.getX(), pool.getOffensiveOffset(player, pool.getTarget(firstMan), -3));
        }

        // SECOND MAN
        else {
            // If there is open shot lane, chance to take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0 && distanceFromGoal <= 7) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            // If closest forward opponent is more than distance 3 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 3) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // If there is an open pass to first man, make the pass
            if (pool.getPassCongestion(player, firstMan) == 0) {
                return new PassAction(pool, player, firstMan);
            }

            //If there is open pass to third man, make the pass
            if (pool.getPassCongestion(player, thirdMan) == 0) {
                return new PassAction(pool, player, thirdMan);
            }

            //Chance to shoot the ball if reasonable distance
            if (distanceFromGoal <= 8 && rand.nextInt(0, player.getShotFactor()) > 40) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            //Chance to just pass to first man
            if (rand.nextInt(0, player.getPassFactor()) > 40) {
                return new PassAction(pool, player, firstMan);
            }

            //Chance to make a move around defender
            if (rand.nextInt(0, player.getSwimFactor()) > 50) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player),2);
                int targetY = pool.getOffensiveOffset(player, player.getY(), 2);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            //Otherwise, dump the ball into opponent’s zone ahead of first man or pass it
            if (rand.nextInt(0, 2) == 1) {
                return new PassAction(pool, player, firstMan);
            } else {
                return new DumpAction(pool, player, firstMan.getX(), pool.getOffensiveOffset(player, pool.getTarget(firstMan), -3));
            }
        }
    }
}
