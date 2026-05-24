package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class SneakyPlayer extends Player{
    public SneakyPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    public SneakyPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    public SneakyPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    @Override
    public int getStealFactor() {
        return super.getStealFactor() + 10;
    }
}
