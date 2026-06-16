package playertypes.traited;

import playertypes.*;

public class BrickWallPlayer extends Player{
    public BrickWallPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.BRICK_WALL)) {throw new IllegalArgumentException("BrickWallPlayer must have trait BRICK_WALL");}
    }

    public BrickWallPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.BRICK_WALL)) {throw new IllegalArgumentException("BrickWallPlayer must have trait BRICK_WALL");}
    }

    public BrickWallPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.BRICK_WALL)) {throw new IllegalArgumentException("BrickWallPlayer must have trait BRICK_WALL");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[1] += 2; //Extreme BLC growth
        stats[7] -= 1; //Lower SPD growth
        return stats;
    }
}
