package playertypes.traited;

import playertypes.*;

public class FumblerPlayer extends Player{
    public FumblerPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.FUMBLER)) {throw new IllegalArgumentException("FumblerPlayer must have trait FUMBLER");}
    }

    public FumblerPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.FUMBLER)) {throw new IllegalArgumentException("FumblerPlayer must have trait FUMBLER");}
    }

    public FumblerPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.FUMBLER)) {throw new IllegalArgumentException("FumblerPlayer must have trait FUMBLER");}
    }
}
