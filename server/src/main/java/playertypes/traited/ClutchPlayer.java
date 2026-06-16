package playertypes.traited;

import playertypes.*;

public class ClutchPlayer extends Player{
    public ClutchPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }

    public ClutchPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }

    public ClutchPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }
}
