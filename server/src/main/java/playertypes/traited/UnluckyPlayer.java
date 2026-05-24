package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class UnluckyPlayer extends Player{
    public UnluckyPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.UNLUCKY)) {throw new IllegalArgumentException("UnluckyPlayer must have trait UNLUCKY");}
    }

    public UnluckyPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.UNLUCKY)) {throw new IllegalArgumentException("UnluckyPlayer must have trait UNLUCKY");}
    }

    public UnluckyPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.UNLUCKY)) {throw new IllegalArgumentException("UnluckyPlayer must have trait UNLUCKY");}
    }

    @Override
    public int getShotFactor() {
        return super.getShotFactor() - 10;
    }
}
