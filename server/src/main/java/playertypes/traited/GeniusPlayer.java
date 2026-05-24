package playertypes.traited;

import playertypes.*;

public class GeniusPlayer extends Player{
    public GeniusPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GENIUS)) {throw new IllegalArgumentException("GeniusPlayer must have trait GENIUS");}
    }

    public GeniusPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GENIUS)) {throw new IllegalArgumentException("GeniusPlayer must have trait GENIUS");}
    }

    public GeniusPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GENIUS)) {throw new IllegalArgumentException("GeniusPlayer must have trait GENIUS");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[4] += 5; //Extreme INT
        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[0] += 1; //Higher ACC Growth
        stats[4] += 2; //Extreme INT Growth
        return stats;
    }
}
