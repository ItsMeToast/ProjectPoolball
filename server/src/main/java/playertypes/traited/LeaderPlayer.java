package playertypes.traited;

import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Statline;
import playertypes.Trait;

public class LeaderPlayer extends Player{
    public LeaderPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }

    public LeaderPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }

    public LeaderPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }
}
