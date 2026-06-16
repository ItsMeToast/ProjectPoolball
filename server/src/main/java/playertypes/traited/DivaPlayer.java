package playertypes.traited;

import playertypes.*;

public class DivaPlayer extends Player{
    public DivaPlayer(Trait trait) {
        super(trait);
        if (!trait.equals(Trait.DIVA)) {throw new IllegalArgumentException("DivaPlayer must have trait DIVA");}
    }

    public DivaPlayer(int potential, Playstyle style, Trait trait) {
        super(potential, style, trait);
        if (!trait.equals(Trait.DIVA)) {throw new IllegalArgumentException("DivaPlayer must have trait DIVA");}
    }

    public DivaPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        super(firstName, lastName, nationality, age, style, trait, stats, potential);
        if (!trait.equals(Trait.DIVA)) {throw new IllegalArgumentException("DivaPlayer must have trait DIVA");}
    }

    @Override
    public int[] getContractRequest() {
        int[] contract = super.getContractRequest();
        contract[0] = (int) (contract[0] * 1.25);
        return contract;
    }
}
