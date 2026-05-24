package playertypes.traited;

import playertypes.*;

public class GiantPlayer extends Player{
    public GiantPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.GIANT)) {throw new IllegalArgumentException("GiantPlayer must have trait GIANT");}
    }

    public GiantPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.GIANT)) {throw new IllegalArgumentException("GiantPlayer must have trait GIANT");}
    }

    public GiantPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.GIANT)) {throw new IllegalArgumentException("GiantPlayer must have trait GIANT");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[6] += 2; //Extreme SZE
        return stats;
    }
}
