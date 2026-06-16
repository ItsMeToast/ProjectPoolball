package playertypes.traited;

import playertypes.*;

public class GenerousPlayer extends Player{
    public GenerousPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GENEROUS)) {throw new IllegalArgumentException("GenerousPlayer must have trait GENEROUS");}
    }

    public GenerousPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GENEROUS)) {throw new IllegalArgumentException("GenerousPlayer must have trait GENEROUS");}
    }

    public GenerousPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GENEROUS)) {throw new IllegalArgumentException("GenerousPlayer must have trait GENEROUS");}
    }
}
