package gamesimulator;

import gamesimulator.actions.GameAction;

public class PlayerPriorityMarker {
    private final MovablePlayer player;
    private final int priority;

    public PlayerPriorityMarker(MovablePlayer player, int priority) {
        this.player = player;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public GameAction getAction(Pool pool) {
        return player.getAction(pool, player);
    }
}
