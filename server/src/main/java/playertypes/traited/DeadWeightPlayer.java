package playertypes.traited;

import playertypes.*;

public class DeadWeightPlayer extends Player{
    public DeadWeightPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.DEAD_WEIGHT)) {throw new IllegalArgumentException("DeadWeightPlayer must have trait DEAD_WEIGHT");}
    }

    public DeadWeightPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.DEAD_WEIGHT)) {throw new IllegalArgumentException("DeadWeightPlayer must have trait DEAD_WEIGHT");}
    }

    public DeadWeightPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.DEAD_WEIGHT)) {throw new IllegalArgumentException("DeadWeightPlayer must have trait DEAD_WEIGHT");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[2] -= 1; //Lower END growth
        stats[7] -= 1; //Lower SPD growth
        return stats;
    }
}
