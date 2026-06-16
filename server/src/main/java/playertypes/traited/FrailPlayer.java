package playertypes.traited;

import playertypes.*;

public class FrailPlayer extends Player{
    public FrailPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    public FrailPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    public FrailPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.FRAIL)) {throw new IllegalArgumentException("FrailPlayer must have trait FRAIL");}
    }

    @Override
    public double getSelfInjury() {
        return super.getSelfInjury() + 5;
    }
}
