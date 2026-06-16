package playertypes.traited;

import playertypes.*;

public class PassFirstPlayer extends Player{
    public PassFirstPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.PASS_FIRST)) {throw new IllegalArgumentException("PassFirstPlayer must have trait PASS_FIRST");}
    }

    public PassFirstPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.PASS_FIRST)) {throw new IllegalArgumentException("PassFirstPlayer must have trait PASS_FIRST");}
    }

    public PassFirstPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.PASS_FIRST)) {throw new IllegalArgumentException("PassFirstPlayer must have trait PASS_FIRST");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[0] += 1; //Higher ACC growth
        stats[4] += 1; //Higher INT growth
        stats[5] -= 1; //Lower POW growth
        return stats;
    }
}
