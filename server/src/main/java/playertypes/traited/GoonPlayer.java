package playertypes.traited;

import playertypes.*;

public class GoonPlayer extends Player{
    public GoonPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GOON)) {throw new IllegalArgumentException("GoonPlayer must have trait GOON");}
    }

    public GoonPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GOON)) {throw new IllegalArgumentException("GoonPlayer must have trait GOON");}
    }

    public GoonPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GOON)) {throw new IllegalArgumentException("GoonPlayer must have trait GOON");}
    }

    @Override
    public double getOpponentInjury() {
        return super.getOpponentInjury() + 3;
    }
}
