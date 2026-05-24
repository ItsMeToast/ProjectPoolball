package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class EagerPlayer extends Player{
    public EagerPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    public EagerPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    public EagerPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.EAGER)) {throw new IllegalArgumentException("EagerPlayer must have trait EAGER");}
    }

    @Override
    public int getFaceoffFactor() {
        return super.getFaceoffFactor() + 10;
    }
}
