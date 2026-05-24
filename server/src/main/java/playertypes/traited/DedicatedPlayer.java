package playertypes.traited;

import playertypes.*;

public class DedicatedPlayer extends Player{
    public DedicatedPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.DEDICATED)) {throw new IllegalArgumentException("DedicatedPlayer must have trait DEDICATED");}
    }

    public DedicatedPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.DEDICATED)) {throw new IllegalArgumentException("DedicatedPlayer must have trait DEDICATED");}
    }

    public DedicatedPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.DEDICATED)) {throw new IllegalArgumentException("DedicatedPlayer must have trait DEDICATED");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        if (this.getAge() > 26) {
            for (int i = 0; i < stats.length; i++) {
                stats[i] += 3; //Slower decay after prime
            }
        }
        return stats;
    }

    @Override
    protected int getVarianceModifier() {
        int prev = super.getVarianceModifier();
        if (this.getAge() > 26) {
            return prev-1; //Slower decay after prime
        } else {
            return prev;
        }
    }
}
