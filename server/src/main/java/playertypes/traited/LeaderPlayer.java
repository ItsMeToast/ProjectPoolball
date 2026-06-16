package playertypes.traited;

import playertypes.*;

public class LeaderPlayer extends Player{
    public LeaderPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }

    public LeaderPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }

    public LeaderPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.LEADER)) {throw new IllegalArgumentException("LeaderPlayer must have trait LEADER");}
    }
}
