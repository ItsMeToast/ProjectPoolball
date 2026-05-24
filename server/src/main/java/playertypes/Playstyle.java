package playertypes;

import java.util.Arrays;

public enum Playstyle {
    ATTACKER(new int[]{1,0,0,0,1,1,0,0,0}),
    FINISHER(new int[]{0,0,0,1,0,1,0,1,0}),
    TWOWAY(new int[]{0,0,0,0,0,2,2,0,0}),
    PLAYMAKER(new int[]{0,0,0,0,2,0,0,2,0}),
    DISTRIBUTOR(new int[]{0,1,0,0,1,0,2,0,0}),
    DEFENDER(new int[]{0,0,1,0,0,0,2,1,0});

    private final int[] modifiers;

    Playstyle(int[] modifiers) {
        this.modifiers = modifiers;
    }

    public int[] getModifiers() {
        return Arrays.copyOf(modifiers, modifiers.length);
    }
}
