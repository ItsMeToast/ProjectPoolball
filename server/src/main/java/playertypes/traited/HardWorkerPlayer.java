package playertypes.traited;

import playertypes.*;

public class HardWorkerPlayer extends Player{
    public HardWorkerPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.HARD_WORKER)) {throw new IllegalArgumentException("HardWorkerPlayer must have trait HARD_WORKER");}
    }

    public HardWorkerPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.HARD_WORKER)) {throw new IllegalArgumentException("HardWorkerPlayer must have trait HARD_WORKER");}
    }

    public HardWorkerPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.HARD_WORKER)) {throw new IllegalArgumentException("HardWorkerPlayer must have trait HARD_WORKER");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[0] += 1; //Higher ACC Growth
        stats[1] += 1; //Higher BLC Growth
        return stats;
    }
}
