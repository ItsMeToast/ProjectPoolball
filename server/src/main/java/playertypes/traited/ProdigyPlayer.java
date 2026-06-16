package playertypes.traited;

import playertypes.*;

public class ProdigyPlayer extends Player{
    public ProdigyPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.PRODIGY)) {throw new IllegalArgumentException("ProdigyPlayer must have trait PRODIGY");}
    }

    public ProdigyPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.PRODIGY)) {throw new IllegalArgumentException("ProdigyPlayer must have trait PRODIGY");}
    }

    public ProdigyPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.PRODIGY)) {throw new IllegalArgumentException("ProdigyPlayer must have trait PRODIGY");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] stats = this.getStyle().getModifiers();

        for (int i = 0; i < stats.length-1; i++) {
            if (i != 6) { //No effect on SZE or Injury, otherwise incredible initial stats
                stats[i] = stats[i] * 5;
                stats[i] += 1;
            }
        }

        return stats;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();

        if (this.getAge() < 20) {
            for (int i = 0; i < stats.length-1; i++) {
                if (i != 6) { //No effect on SZE or Injury, otherwise Higher Growth
                    stats[i] += 1;
                }
            }
        }

        return stats;
    }
}
