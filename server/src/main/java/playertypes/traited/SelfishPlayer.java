package playertypes.traited;

import playertypes.*;

public class SelfishPlayer extends Player{
    public SelfishPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SELFISH)) {throw new IllegalArgumentException("SelfishPlayer must have trait SELFISH");}
    }

    public SelfishPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SELFISH)) {throw new IllegalArgumentException("SelfishPlayer must have trait SELFISH");}
    }

    public SelfishPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SELFISH)) {throw new IllegalArgumentException("SelfishPlayer must have trait SELFISH");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[1] -= 1; //Lower BLC growth
        stats[4] -= 1; //Lower INT growth
        stats[5] += 2; //Extreme POW growth
        return stats;
    }
}
