package playertypes.traited;

import playertypes.*;

public class ShortLivedPlayer extends Player{
    public ShortLivedPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SHORT_LIVED)) {throw new IllegalArgumentException("ShortLivedPlayer must have trait SHORT_LIVED");}
    }

    public ShortLivedPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SHORT_LIVED)) {throw new IllegalArgumentException("ShortLivedPlayer must have trait SHORT_LIVED");}
    }

    public ShortLivedPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SHORT_LIVED)) {throw new IllegalArgumentException("ShortLivedPlayer must have trait SHORT_LIVED");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        if (this.getAge() > 26) {
            for (int i = 0; i < stats.length; i++) {
                stats[i] -= 5; //Fast decay after prime
            }
        }
        return stats;
    }

    @Override
    protected int getVarianceModifier() {
        int prev = super.getVarianceModifier();
        if (this.getAge() > 26) {
            return prev-1; //Fast decay after prime
        } else {
            return prev;
        }
    }
}
