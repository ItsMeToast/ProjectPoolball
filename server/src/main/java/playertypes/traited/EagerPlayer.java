package playertypes.traited;

import playertypes.*;

public class EagerPlayer extends Player{
    public EagerPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    public EagerPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    public EagerPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    @Override
    public int getFaceoffFactor() {
        return super.getFaceoffFactor() + 10;
    }
}
