package playertypes.traited;

import playertypes.*;

public class SpeedsterPlayer extends Player{
    public SpeedsterPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SPEEDSTER)) {throw new IllegalArgumentException("SpeedsterPlayer must have trait SPEEDSTER");}
    }

    public SpeedsterPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SPEEDSTER)) {throw new IllegalArgumentException("SpeedsterPlayer must have trait SPEEDSTER");}
    }

    public SpeedsterPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SPEEDSTER)) {throw new IllegalArgumentException("SpeedsterPlayer must have trait SPEEDSTER");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[6] -= 1; //Lower SZE
        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[1] -= 1; //Lower BLC Growth
        stats[2] += 1; //Higher END Growth
        stats[7] += 2; //Extreme SPD Growth
        return stats;
    }
}
