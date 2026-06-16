package playertypes.traited;

import playertypes.*;

public class ConsistentPlayer extends Player{
    public ConsistentPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.CONSISTENT)) {throw new IllegalArgumentException("ConsistentPlayer must have trait CONSISTENT");}
    }

    public ConsistentPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.CONSISTENT)) {throw new IllegalArgumentException("ConsistentPlayer must have trait CONSISTENT");}
    }

    public ConsistentPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.CONSISTENT)) {throw new IllegalArgumentException("ConsistentPlayer must have trait CONSISTENT");}
    }

    @Override
    protected int getVarianceModifier() {
        return super.getVarianceModifier() - 1;
    }
}
