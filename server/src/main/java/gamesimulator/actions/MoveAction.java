package gamesimulator.actions;

import gamesimulator.MovablePlayer;
import gamesimulator.Pool;

public class MoveAction implements GameAction{
    private final Pool pool;
    private final MovablePlayer player;
    private final int x;
    private final int y;

    public MoveAction(Pool pool, MovablePlayer player, int x, int y) {
        this.pool = pool;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean execute() {
        int oldX = player.getX();
        int oldY = player.getY();
        pool.movePlayer(player, x, y);
        System.out.println(player + " moved from (" + oldX + "," + oldY + ") to (" + player.getX() + "," + player.getY() + ")");

        // If moved to the ball and no one else has the ball, pick it up
        if (pool.getBallHolder().isEmpty()) {
            int[] ballCoordinates = pool.getBallCoordinates();
            if (player.getX() == ballCoordinates[0] && player.getY() == ballCoordinates[1]) {
                pool.setBallHolder(player);
                System.out.println(player + " picked up the ball");
            }
        }

        return false;
    }
}
