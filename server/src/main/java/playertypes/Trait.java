package playertypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Trait {
    AGILE(Playstyle.ATTACKER, Playstyle.FINISHER),
    BRICK_WALL(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    CONSISTENT(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    DEAD_WEIGHT(Playstyle.ATTACKER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    DEDICATED(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    DIVA(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    DUMB(Playstyle.DEFENDER, Playstyle.FINISHER, Playstyle.TWOWAY),
    FISH_LIKE(Playstyle.DEFENDER, Playstyle.FINISHER, Playstyle.PLAYMAKER),
    GENEROUS(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    GENIUS(Playstyle.ATTACKER, Playstyle.DISTRIBUTOR, Playstyle.PLAYMAKER),
    GIANT(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    HARD_WORKER(Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    HARDENED(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR),
    PASS_FIRST(Playstyle.ATTACKER, Playstyle.DISTRIBUTOR, Playstyle.PLAYMAKER),
    PRODIGY(6,10,Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    SCARED(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.PLAYMAKER),
    SELFISH(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.TWOWAY),
    SHORT_LIVED(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    SNIPER(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.TWOWAY),
    SPEEDSTER(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.PLAYMAKER),
    STUNTED(1, 5, Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    SUPERSTAR(6, 10, Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    WIMP(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.PLAYMAKER),
    WILDCARD(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    CLUTCH(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    EAGER(Playstyle.DEFENDER, Playstyle.FINISHER, Playstyle.PLAYMAKER),
    EFFICIENT(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    ELITE_SHOT(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.TWOWAY),
    FRAIL(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    FUMBLER(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    GOON(1,5,Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    GREAT_POSITIONING(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    HIGHLIGHT_REEL(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    LAZY(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.PLAYMAKER),
    LEADER(Playstyle.ATTACKER, Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.FINISHER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    QUICK_REFLEXES(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    SNEAKY(Playstyle.DEFENDER, Playstyle.DISTRIBUTOR, Playstyle.TWOWAY),
    UNLUCKY(Playstyle.DEFENDER, Playstyle.PLAYMAKER, Playstyle.TWOWAY),
    UNPREDICTABLE(Playstyle.ATTACKER, Playstyle.FINISHER, Playstyle.TWOWAY),
    VISIONARY(Playstyle.ATTACKER, Playstyle.DISTRIBUTOR, Playstyle.PLAYMAKER);

    private final List<Playstyle> styles; //List of styles that are allowed to obtain the trait
    private final int minPotential; //minimum potential (inclusive) to obtain this trait (default is 1)
    private final int maxPotential; //maximum potential (inclusive) to obtain this trait (default is 10)

    Trait(Playstyle... validStyles) {
        minPotential = 1;
        maxPotential = 10;
        styles = new ArrayList<>();
        styles.addAll(Arrays.asList(validStyles));
    }

    Trait(int minPotential, int maxPotential, Playstyle... validStyles) {
        this.minPotential = minPotential;
        this.maxPotential = maxPotential;
        styles = new ArrayList<>();
        styles.addAll(Arrays.asList(validStyles));
    }

    public boolean isValidStyle(Playstyle style) {
        return styles.contains(style);
    }
    public boolean isValidPotential(int potential) {
        return (potential >= minPotential && potential <= maxPotential);
    }
}
