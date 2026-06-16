package playertypes.traited;

import playertypes.*;

public class SneakyPlayer extends Player{
    public SneakyPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    public SneakyPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    public SneakyPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SNEAKY)) {throw new IllegalArgumentException("SneakyPlayer must have trait SNEAKY");}
    }

    @Override
    public int getStealFactor() {
        return super.getStealFactor() + 10;
    }
}
