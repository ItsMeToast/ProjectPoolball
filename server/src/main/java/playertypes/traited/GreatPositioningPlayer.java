package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class GreatPositioningPlayer extends Player{
    public GreatPositioningPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }

    public GreatPositioningPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }

    public GreatPositioningPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }
}
