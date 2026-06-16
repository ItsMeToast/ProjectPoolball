package playertypes.traited;

import playertypes.*;

public class QuickReflexesPlayer extends Player{
    public QuickReflexesPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.QUICK_REFLEXES)) {throw new IllegalArgumentException("QuickReflexesPlayer must have trait QUICK_REFLEXES");}
    }

    public QuickReflexesPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.QUICK_REFLEXES)) {throw new IllegalArgumentException("QuickReflexesPlayer must have trait QUICK_REFLEXES");}
    }

    public QuickReflexesPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.QUICK_REFLEXES)) {throw new IllegalArgumentException("QuickReflexesPlayer must have trait QUICK_REFLEXES");}
    }

    @Override
    public int getBlockFactor() {
        return super.getBlockFactor() + 10;
    }
}
