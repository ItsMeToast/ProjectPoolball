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

    public GenerousPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GENEROUS)) {throw new IllegalArgumentException("GenerousPlayer must have trait GENEROUS");}
    }
}
