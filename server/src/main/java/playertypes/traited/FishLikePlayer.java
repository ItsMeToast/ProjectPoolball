package playertypes.traited;

import playertypes.*;

public class FishLikePlayer extends Player{
    public FishLikePlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.FISH_LIKE)) {throw new IllegalArgumentException("FishLikePlayer must have trait FISH_LIKE");}
    }

    public FishLikePlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.FISH_LIKE)) {throw new IllegalArgumentException("FishLikePlayer must have trait FISH_LIKE");}
    }

    public FishLikePlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.FISH_LIKE)) {throw new IllegalArgumentException("FishLikePlayer must have trait FISH_LIKE");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[3] += 1; //Higher EXP Growth
        stats[7] += 1; //Higher SPD Growth
        return stats;
    }
}
