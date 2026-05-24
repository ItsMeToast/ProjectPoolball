package playertypes.traited;

import playertypes.*;

public class StuntedPlayer extends Player{
    public StuntedPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.STUNTED)) {throw new IllegalArgumentException("StuntedPlayer must have trait STUNTED");}
    }

    public StuntedPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.STUNTED)) {throw new IllegalArgumentException("StuntedPlayer must have trait STUNTED");}
    }

    public StuntedPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.STUNTED)) {throw new IllegalArgumentException("StuntedPlayer must have trait STUNTED");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();

        for (int i = 0; i < stats.length-1; i++) {
            if (i != 6) { //No effect on SZE or Injury, otherwise low initial stats
                stats[i] -= 3;
            }
        }

        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();

        if (this.getAge() < 20) {
            for (int i = 0; i < stats.length-1; i++) {
                if (i != 6) { //No effect on SZE or Injury, otherwise incredible initial stats
                    stats[i] -= (21-this.getAge());
                }
            }
        }

        return stats;
    }
}
