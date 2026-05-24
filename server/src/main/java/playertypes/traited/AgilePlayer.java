package playertypes.traited;

import playertypes.*;

public class AgilePlayer extends Player{
    public AgilePlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.AGILE)) {throw new IllegalArgumentException("AgilePlayer must have trait AGILE");}
    }

    public AgilePlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.AGILE)) {throw new IllegalArgumentException("AgilePlayer must have trait AGILE");}
    }

    public AgilePlayer(String firstName, String lastName, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, age, style, trait, stats, potential);
        if (!trait.equals(Trait.AGILE)) {throw new IllegalArgumentException("AgilePlayer must have trait AGILE");}
    }

    @Override
    protected int[] getStartingModifiers() {
        int[] statModifiers = this.getStyle().getModifiers();
        statModifiers[8] += 2; //Higher starting injury
        return statModifiers;
    }

    @Override
    protected int[] getAgeModifiers() {
        int[] statModifiers = this.getStyle().getModifiers();
        statModifiers[3] += 1; //Higher EXP growth
        statModifiers[7] += 1; //Higher SPD growth
        return statModifiers;
    }
}
