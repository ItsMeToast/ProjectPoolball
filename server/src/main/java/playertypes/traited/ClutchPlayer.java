package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class ClutchPlayer extends Player{
    public ClutchPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }

    public ClutchPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }

    public ClutchPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.CLUTCH)) {throw new IllegalArgumentException("ClutchPlayer must have trait CLUTCH");}
    }
}
