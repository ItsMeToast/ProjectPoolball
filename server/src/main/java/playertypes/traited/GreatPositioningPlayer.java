package playertypes.traited;

import playertypes.*;

public class GreatPositioningPlayer extends Player{
    public GreatPositioningPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }

    public GreatPositioningPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }

    public GreatPositioningPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GREAT_POSITIONING)) {throw new IllegalArgumentException("GreatPositioningPlayer must have trait GREAT_POSITIONING");}
    }
}
