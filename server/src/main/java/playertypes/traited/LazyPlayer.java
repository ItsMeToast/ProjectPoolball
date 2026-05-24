package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class LazyPlayer extends Player{
    public LazyPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.LAZY)) {throw new IllegalArgumentException("LazyPlayer must have trait LAZY");}
    }

    public LazyPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.LAZY)) {throw new IllegalArgumentException("LazyPlayer must have trait LAZY");}
    }

    public LazyPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.LAZY)) {throw new IllegalArgumentException("LazyPlayer must have trait LAZY");}
    }

    @Override
    public int getFaceoffFactor() {
        return super.getFaceoffFactor() - 10;
    }
}
