package playertypes.traited;

import playertypes.*;

public class SniperPlayer extends Player{
    public SniperPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SNIPER)) {throw new IllegalArgumentException("SniperPlayer must have trait SNIPER");}
    }

    public SniperPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SNIPER)) {throw new IllegalArgumentException("SniperPlayer must have trait SNIPER");}
    }

    public SniperPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SNIPER)) {throw new IllegalArgumentException("SniperPlayer must have trait SNIPER");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[0] += 2; //Extreme ACC Growth
        return stats;
    }
}
