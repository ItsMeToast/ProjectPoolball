package playertypes.traited;

import playertypes.*;

public class DumbPlayer extends Player{
    public DumbPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.DUMB)) {throw new IllegalArgumentException("DumbPlayer must have trait DUMB");}
    }

    public DumbPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.DUMB)) {throw new IllegalArgumentException("DumbPlayer must have trait DUMB");}
    }

    public DumbPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.DUMB)) {throw new IllegalArgumentException("DumbPlayer must have trait DUMB");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[4] -= 2; //Lower INT
        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[4] -= 2; //Lower INT Growth
        return stats;
    }
}
