package playertypes.traited;

import playertypes.*;

import java.util.Random;

public class WildcardPlayer extends Player{
    public WildcardPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.WILDCARD)) {throw new IllegalArgumentException("WildcardPlayer must have trait WILDCARD");}
    }

    public WildcardPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.WILDCARD)) {throw new IllegalArgumentException("WildcardPlayer must have trait WILDCARD");}
    }

    public WildcardPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.WILDCARD)) {throw new IllegalArgumentException("WildcardPlayer must have trait WILDCARD");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        Random rand = new Random();

        for (int i = 0; i < stats.length; i++) {
            stats[i] += rand.nextInt(-3,3);
        }

        return stats;
    }

    @Override
    protected int getVarianceModifier() {
        return super.getVarianceModifier() + 1;
    }
}
