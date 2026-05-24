package playertypes.traited;

import playertypes.*;

public class WimpPlayer extends Player{
    public WimpPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.WIMP)) {throw new IllegalArgumentException("WimpPlayer must have trait WIMP");}
    }

    public WimpPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.WIMP)) {throw new IllegalArgumentException("WimpPlayer must have trait WIMP");}
    }

    public WimpPlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.WIMP)) {throw new IllegalArgumentException("WimpPlayer must have trait WIMP");}
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] stats = this.getStyle().getModifiers();
        stats[5] -= 1; //Lower POW Growth
        return stats;
    }
}
