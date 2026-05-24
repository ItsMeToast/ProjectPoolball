package playertypes.traited;

import playertypes.*;

public class ScaredPlayer extends Player{
    public ScaredPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SCARED)) {throw new IllegalArgumentException("ScaredPlayer must have trait SCARED");}
    }

    public ScaredPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SCARED)) {throw new IllegalArgumentException("ScaredPlayer must have trait SCARED");}
    }

    public ScaredPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SCARED)) {throw new IllegalArgumentException("ScaredPlayer must have trait SCARED");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[1] -= 1; //Lower BLC growth
        return stats;
    }
}
