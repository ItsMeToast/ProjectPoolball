package playertypes.traited;

import playertypes.*;

public class SuperstarPlayer extends Player{
    public SuperstarPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.SUPERSTAR)) {throw new IllegalArgumentException("SuperstarPlayer must have trait SUPERSTAR");}
    }

    public SuperstarPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.SUPERSTAR)) {throw new IllegalArgumentException("SuperstarPlayer must have trait SUPERSTAR");}
    }

    public SuperstarPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.SUPERSTAR)) {throw new IllegalArgumentException("SuperstarPlayer must have trait SUPERSTAR");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();

        for (int i = 0; i < stats.length-1; i++) {
            if (i != 6) { //No effect on SZE or Injury, otherwise incredible growth
                stats[i] = stats[i] * 3;
            }
        }

        return stats;
    }
}
