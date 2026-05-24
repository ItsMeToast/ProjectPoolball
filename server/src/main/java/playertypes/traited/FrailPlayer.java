package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class FrailPlayer extends Player{
    public FrailPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    public FrailPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    public FrailPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    @Override
    public double getSelfInjury() {
        return super.getSelfInjury() + 5;
    }
}
