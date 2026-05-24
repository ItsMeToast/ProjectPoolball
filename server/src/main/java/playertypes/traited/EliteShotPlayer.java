package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class EliteShotPlayer extends Player{
    public EliteShotPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.ELITE_SHOT)) {throw new IllegalArgumentException("EliteShotPlayer must have trait ELITE_SHOT");}
    }

    public EliteShotPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.ELITE_SHOT)) {throw new IllegalArgumentException("EliteShotPlayer must have trait ELITE_SHOT");}
    }

    public EliteShotPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.ELITE_SHOT)) {throw new IllegalArgumentException("EliteShotPlayer must have trait ELITE_SHOT");}
    }

    @Override
    public int getShotFactor() {
        return super.getShotFactor() + 10;
    }
}
