package playertypes.traited;

import playertypes.*;

public class HardenedPlayer extends Player{
    public HardenedPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.HARDENED)) {throw new IllegalArgumentException("HardenedPlayer must have trait HARDENED");}
    }

    public HardenedPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.HARDENED)) {throw new IllegalArgumentException("HardenedPlayer must have trait HARDENED");}
    }

    public HardenedPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.HARDENED)) {throw new IllegalArgumentException("HardenedPlayer must have trait HARDENED");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[8] -= 3; //Lower Injury
        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[2] += 1; //Higher END Growth
        stats[4] += 1; //Higher INT Growth
        return stats;
    }
}
