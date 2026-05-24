package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class UnpredictablePlayer extends Player{
    public UnpredictablePlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.UNPREDICTABLE)) {throw new IllegalArgumentException("UnpredictablePlayer must have trait UNPREDICTABLE");}
    }

    public UnpredictablePlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.UNPREDICTABLE)) {throw new IllegalArgumentException("UnpredictablePlayer must have trait UNPREDICTABLE");}
    }

    public UnpredictablePlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.UNPREDICTABLE)) {throw new IllegalArgumentException("UnpredictablePlayer must have trait UNPREDICTABLE");}
    }

    @Override
    public int getBlockFactor() {
        return super.getBlockFactor() - 10;
    }
}
