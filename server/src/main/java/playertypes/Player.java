package playertypes;

import gamesimulator.GamePlayer;
import gamesimulator.MovablePlayer;
import gamesimulator.Pool;
import gamesimulator.actions.*;
import playertypes.traited.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Player implements GamePlayer {
    private static final Random rand = new Random();
    private static final HashMap<Trait, Class<? extends Player>> traitClasses = new HashMap<>();

    // Initialize HashMap with the Traits and their classes
    static {
        traitClasses.put(Trait.AGILE, AgilePlayer.class);
        traitClasses.put(Trait.BRICK_WALL, BrickWallPlayer.class);
        traitClasses.put(Trait.CLUTCH, ClutchPlayer.class);
        traitClasses.put(Trait.CONSISTENT, ConsistentPlayer.class);
        traitClasses.put(Trait.DEAD_WEIGHT, DeadWeightPlayer.class);
        traitClasses.put(Trait.DEDICATED, DedicatedPlayer.class);
        traitClasses.put(Trait.DIVA, DivaPlayer.class);
        traitClasses.put(Trait.DUMB, DumbPlayer.class);
        traitClasses.put(Trait.EAGER, EagerPlayer.class);
        traitClasses.put(Trait.EFFICIENT, EfficientPlayer.class);
        traitClasses.put(Trait.ELITE_SHOT, EliteShotPlayer.class);
        traitClasses.put(Trait.FISH_LIKE, FishLikePlayer.class);
        traitClasses.put(Trait.FRAIL, FrailPlayer.class);
        traitClasses.put(Trait.FUMBLER, FumblerPlayer.class);
        traitClasses.put(Trait.GENEROUS, GenerousPlayer.class);
        traitClasses.put(Trait.GENIUS, GeniusPlayer.class);
        traitClasses.put(Trait.GIANT, GiantPlayer.class);
        traitClasses.put(Trait.GOON, GoonPlayer.class);
        traitClasses.put(Trait.GREAT_POSITIONING, GreatPositioningPlayer.class);
        traitClasses.put(Trait.HARDENED, HardenedPlayer.class);
        traitClasses.put(Trait.HARD_WORKER, HardWorkerPlayer.class);
        traitClasses.put(Trait.HIGHLIGHT_REEL, HighlightReelPlayer.class);
        traitClasses.put(Trait.LAZY, LazyPlayer.class);
        traitClasses.put(Trait.LEADER, LeaderPlayer.class);
        traitClasses.put(Trait.PASS_FIRST, PassFirstPlayer.class);
        traitClasses.put(Trait.PRODIGY, ProdigyPlayer.class);
        traitClasses.put(Trait.QUICK_REFLEXES, QuickReflexesPlayer.class);
        traitClasses.put(Trait.SCARED, ScaredPlayer.class);
        traitClasses.put(Trait.SELFISH, SelfishPlayer.class);
        traitClasses.put(Trait.SHORT_LIVED, ShortLivedPlayer.class);
        traitClasses.put(Trait.SNEAKY, SneakyPlayer.class);
        traitClasses.put(Trait.SNIPER, SniperPlayer.class);
        traitClasses.put(Trait.SPEEDSTER, SpeedsterPlayer.class);
        traitClasses.put(Trait.STUNTED, StuntedPlayer.class);
        traitClasses.put(Trait.SUPERSTAR, SuperstarPlayer.class);
        traitClasses.put(Trait.UNLUCKY, UnluckyPlayer.class);
        traitClasses.put(Trait.UNPREDICTABLE, UnpredictablePlayer.class);
        traitClasses.put(Trait.VISIONARY, VisionaryPlayer.class);
        traitClasses.put(Trait.WILDCARD, WildcardPlayer.class);
        traitClasses.put(Trait.WIMP, WimpPlayer.class);
    }

    private final String firstName;
    private final String lastName;
    private final Nationality nationality;
    private int age;
    private final Playstyle style;
    private final Trait trait;
    private Statline stats;
    private final int potential;

    /**
     * Constructs randomized Traited Player using traited constructor
     */
    public static Player getNewPlayer(Trait trait) {
        try {
            Constructor<? extends Player> cons = traitClasses.get(trait).getDeclaredConstructor(Trait.class);
            return cons.newInstance(trait);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs randomized Traited Player using traited constructor
     */
    public static Player getNewPlayer(int potential, Playstyle style, Trait trait) {
        try {
            Constructor<? extends Player> cons = traitClasses.get(trait).getDeclaredConstructor(int.class, Playstyle.class, Trait.class);
            return cons.newInstance(potential, style, trait);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs randomized Traited Player using traited constructor
     */
    public static Player getNewPlayer(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        try {
            Constructor<? extends Player> cons = traitClasses.get(trait).getDeclaredConstructor(String.class, String.class, Nationality.class, int.class, Playstyle.class, Trait.class, Statline.class, int.class);
            return cons.newInstance(firstName, lastName, nationality, age, style, trait, stats, potential);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a fully randomized Player
     */
    public Player() {
        this.firstName = NameGenerator.getFirstName();
        this.lastName = NameGenerator.getLastName();
        this.nationality = Nationality.random();
        this.age = 18;
        this.potential = rand.nextInt(10)+1;
        this.style = Playstyle.values()[rand.nextInt(Playstyle.values().length)];

        // Ensure a valid trait is chosen based on styles and potential
        Trait traitCandidate = Trait.values()[rand.nextInt(Trait.values().length)];
        while (!traitCandidate.isValidStyle(this.style) || !traitCandidate.isValidPotential(this.potential)) {
            traitCandidate = Trait.values()[rand.nextInt(Trait.values().length)];
        }
        this.trait = traitCandidate;

        this.stats = new Statline(potential, style.getModifiers());
    }

    /**
     * Constructs a Player object with given trait
     * @param trait player trait to use (useful for traited Player classes later)
     */
    public Player(Trait trait) {
        this.firstName = NameGenerator.getFirstName();
        this.lastName = NameGenerator.getLastName();
        this.nationality = Nationality.random();
        this.age = 18;
        this.trait = trait;

        int potentialCandidate = rand.nextInt(10)+1;
        Playstyle styleCandidate = Playstyle.values()[rand.nextInt(Playstyle.values().length)];

        while (!this.trait.isValidStyle(styleCandidate) || !this.trait.isValidPotential(potentialCandidate)) {
            potentialCandidate = rand.nextInt(10)+1;
            styleCandidate = Playstyle.values()[rand.nextInt(Playstyle.values().length)];
        }
        this.potential = potentialCandidate;
        this.style = styleCandidate;

        this.stats = new Statline(potential, getStartingModifiers());
    }

    /**
     * Constructs a Player object with given potential, style, and trait
     * @param style player playstyle, dictates growth
     * @param trait player trait (must be valid given
     * @param potential the potential (measure of expected growth) of the player
     */
    public Player(int potential, Playstyle style, Trait trait) {
        if (!trait.isValidStyle(style) || !trait.isValidPotential(potential)) {
            throw new IllegalArgumentException("Illegal Trait passed for Player (" + trait + ", " + style + ", " + potential + " star)");
        }
        this.potential = potential;
        this.style = style;
        this.trait = trait;

        this.firstName = NameGenerator.getFirstName();
        this.lastName = NameGenerator.getLastName();
        this.nationality = Nationality.random();

        // Weird bug, check if names are null
        if (Objects.equals(this.firstName, "null") || Objects.equals(this.lastName, "null")) {
            throw new RuntimeException("NAME IS NULL BUG");
        }

        this.age = 18;

        this.stats = new Statline(potential, getStartingModifiers());
    }

    /**
     * Constructs a Player object using full parameterization
     * @param firstName the first name of the player
     * @param lastName the last name of the player
     * @param nationality the nationality of the player
     * @param age player age (20-30 for major leagues)
     * @param style player playstyle, dictates growth
     * @param trait player trait (must be valid given
     * @param stats statline object containing the player stats
     * @param potential the potential (measure of expected growth) of the player
     */
    public Player(String firstName, String lastName, Nationality nationality, int age, Playstyle style, Trait trait, Statline stats, int potential) {
        if (!trait.isValidStyle(style) || !trait.isValidPotential(potential)) {
            throw new IllegalArgumentException("Illegal Trait passed for Player (" + trait + ", " + style + ", " + potential + " star)");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.age = age;
        this.style = style;
        this.trait = trait;
        this.stats = stats;
        this.potential = potential;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public int getAge() {
        return age;
    }

    public Playstyle getStyle() {
        return style;
    }

    public Trait getTrait() {
        return trait;
    }

    public int getPotential() {
        return potential;
    }

    public Statline getStats() {
        return stats;
    }

    public int getGS() {
        return stats.getGS();
    }

    public int getPM() {
        return stats.getPM();
    }

    public int getSW() {
        return stats.getSW();
    }

    public int getDF() {
        return stats.getDF();
    }

    public int getOSS() {
        return (stats.getGS() + stats.getPM() + stats.getSW() + stats.getDF())/4;
    }

    public double getInjury() {
        return stats.getInjury();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + age + "," + style + ")";
    }

    /**
     * Prints all information about the player
     */
    public void printFullInformation() {
        System.out.println(firstName + " " + lastName);
        System.out.println(potential + " star " + age + " year old " + trait + " " + style);
        System.out.println("GS: " + getGS() + ", PM: " + getPM() + ", SW: " + getSW() + ", DF: " + getDF());
        System.out.println(stats);
    }

    /**
     * Ages the player by one year (updating age and getting a new Statline)
     */
    public void agePlayer() {
        this.stats = this.stats.getAgedStatline(this.age, this.potential, getAgeModifiers(), getVarianceModifier());
        this.age++;
    }

    /**
     * Helper method to return array of modifiers for creating a new player. Override in traited classes to change starting modifiers
     * @return a 9-element array representing stat growth modifiers
     */
    protected int[] getStartingModifiers() {
        return style.getModifiers();
    }

    /**
     * Helper method to return array of modifiers for aging player. Override in traited classes to change growth modifiers
     * @return a 9-element array representing stat growth modifiers
     */
    protected int[] getAgeModifiers() {
        return style.getModifiers();
    }

    /**
     * Helper method to return the variance modifier. Override to change variation in stat growth from baseline
     * @return the varianceModifier (default is 0)
     */
    protected int getVarianceModifier() {
        return 0;
    }

    /**
     * Determines the requested contract (value, years) for the player
     * @return array containing the value of contract and years requested
     */
    public int[] getContractRequest() {
        int[] contract = new int[2];

        double value = Math.pow((this.getOSS() - 40)/2.0, 1.8) * 5 + ((27 - age) * potential * 5);

        contract[0] = Math.max(250000, 50000 * (int) Math.round(value/50)); // Round to nearest multiple of 50
        contract[1] = 5 - (age - 21)/2;

        // Override for ELC
        if (age < 20) {
            contract[0] = 250000;
            contract[1] = 20-age;
        }

        return contract;
    }

    /**
     * Function to determine the probability that the player will accept the proposed contract
     * @param value of the contract
     * @param years of the contract
     * @return the probability that the player will accept the contract
     */
    public int getContractProbability(int value, int years) {
        if (years > 5 || value < 250000) {
            return 0; // over max term or under min contract, never accept (precaution)
        }

        int[] request = getContractRequest();

        int valueEffect = (value - request[0])/50000;
        int yearEffect = years - request[1];

        return 100 + (valueEffect * 20) + (yearEffect * 10);
    }

    /**
     * Returns a semi-random action priority based on the current pool state.
     * @param pool the Pool object containing game state
     * @param player the player to perform an action
     * @return integer representing the priority of the player for this round
     */
    @Override
    public int getActionPriority(Pool pool, MovablePlayer player) {
        if (pool.hasBall(player)) {
            return 150 - ((getShotFactor() + getPassFactor() + getSwimFactor())/3) + rand.nextInt(-30, 31);
        } else {
            return 150 - getSwimFactor() + rand.nextInt(-30, 31);
        }
    }

    /**
     * Returns an action for the player (and corresponding MovablePlayer) given the pool state
     * @param pool the Pool object containing game state
     * @param player the player to perform the action with
     * @return a GameAction representing the players action
     */
    @Override
    public GameAction getAction(Pool pool, MovablePlayer player) {
        int maxMove = 1 + (int) Math.ceil((getSwimFactor() + rand.nextInt(-50, 51))/80.0);
        // No one has the ball (can only move)
        if (pool.getBallHolder().isEmpty()) {
            return getNoCarrierAction(pool, player, maxMove);
        }

        MovablePlayer carrier = pool.getBallHolder().get();

        // Enemy player has the ball (can move or steal)
        if (!pool.areSameTeam(carrier, player)) {
            return getEnemyCarrierAction(pool, player, carrier, maxMove);
        }

        // I have the ball (can move, shoot, or pass)
        else if (pool.hasBall(player)) {
            return getCarrierAction(pool, player, maxMove);
        }

        // Teammate has the ball (can only move)
        else {
            return getTeammateCarrierAction(pool, player, carrier, maxMove);
        }
    }

    /**
     * Action to be performed if there is no current ball carrier
     * @param pool the Pool containing game state
     * @param player the player performing the action
     * @param maxMove maximum distance for a MoveAction
     * @return a GameAction representing the players action
     */
    protected GameAction getNoCarrierAction(Pool pool, MovablePlayer player, int maxMove) {
        // Get Teammate Positioning
        MovablePlayer firstMan = pool.getFirstMan(player);
        MovablePlayer thirdMan = pool.getThirdMan(player);
        int[] ballCoordinates = pool.getBallCoordinates();

        // Array storing the coordinates to move to
        int[] movement;

        // If I am the closest teammate to the ball, or within 3 tiles of ball, swim towards ball
        if (pool.getClosestTeammateToBall(player) == player || pool.getDistance(player, ballCoordinates[0], ballCoordinates[1]) <= 3) {
            movement = pool.getBestMove(player, maxMove, ballCoordinates[0], ballCoordinates[1]);
        }

        // FIRST MAN
        else if (firstMan == player) {
            int targetY = pool.getOffensiveOffset(player, ballCoordinates[1], 3);
            movement = pool.getBestMove(player, maxMove, pool.getPoolXHalf(), targetY);
        }

        // THIRD MAN
        else if (thirdMan == player) {
            // Far away, cover the side but only in the middle three columns
            if (pool.getDistance(player, ballCoordinates[0], ballCoordinates[1]) > 4) {
                int targetY = pool.getOffensiveOffset(player, pool.getEnemyPlayer(1,player).getY(),-4);
                int targetX = pool.getPoolXHalf();

                if (ballCoordinates[0] < pool.getPoolXHalf()) {
                    targetX -= 1;
                } else if (ballCoordinates[0] > pool.getPoolXHalf()) {
                    targetX += 1;
                }

                movement = pool.getBestMove(player, maxMove, targetX, targetY);
            }

            // Cover straight shot exactly
            else {
                int targetY = pool.getOffensiveOffset(player, pool.getEnemyPlayer(1,player).getY(),-3);
                movement = pool.getBestMove(player, maxMove, ballCoordinates[0], targetY);
            }
        }

        // SECOND MAN
        else {
            movement = pool.getBestMove(player, maxMove, pool.getPoolXHalf(), ballCoordinates[1]);
        }

        // Return new MoveAction based on movement array determined by conditionals
        return new MoveAction(pool, player, movement[0], movement[1]);
    }

    /**
     * Action to be performed if an opponent currently has the ball
     * @param pool the Pool containing game state
     * @param player the player performing the action
     * @param carrier the opponent who has the ball
     * @param maxMove maximum distance for a MoveAction
     * @return a GameAction representing the players action
     */
    protected GameAction getEnemyCarrierAction(Pool pool, MovablePlayer player, MovablePlayer carrier, int maxMove) {
        // Get Teammate Positioning
        MovablePlayer firstMan = pool.getFirstMan(player);
        MovablePlayer thirdMan = pool.getThirdMan(player);

        // If you are not the previous ball holder, and within steal range, chance for a steal (third man always tries).
        if (pool.getPrevBallHolder().isEmpty() || pool.getPrevBallHolder().get() != player) {
            if (pool.getDistance(player, carrier) <= 1) {
                if (pool.getThirdMan(player) == player || rand.nextInt(0, getStealFactor()) > 20) {
                    return new StealAction(pool, player, carrier);
                }
            }
        }

        // Array storing the coordinates to move to
        int[] movement;
        int firstManDistance = pool.getDistanceFromDefensive(player, firstMan.getY());
        boolean firstManBetween = (pool.getDistanceFromDefensive(player, carrier.getY()) >= firstManDistance);

        // FIRST MAN
        if (firstMan == player) {
            // If between carrier and your own net
            if (firstManBetween) {
                movement = pool.getBestMove(player, maxMove, carrier.getX(), carrier.getY());
            }

            // Carrier has gotten past you
            else {
                // Chance for a back-check to the carrier (if closest)
                if (pool.getClosestTeammateToBall(player) == player && rand.nextInt(0, getBlockFactor()) > 50) {
                    movement = pool.getBestMove(player, maxMove, carrier.getX(), carrier.getY());
                }

                else {
                    // Move to block pass to third man
                    int targetY = pool.getOffensiveOffset(player, carrier.getY(), 2);
                    movement = pool.getBestMove(player, maxMove, pool.getEnemyPlayer(1,player).getX(), targetY);
                }
            }
        }

        // THIRD MAN
        else if (thirdMan == player) {
            // Far away, cover the side but only in the middle three columns
            if (pool.getDistanceFromDefensive(player, carrier.getY()) > 2) {
                int targetY = pool.getOffensiveOffset(player, pool.getEnemyPlayer(1,player).getY(),-5);
                int targetX = pool.getPoolXHalf();

                if (carrier.getX() < pool.getPoolXHalf()) {
                    targetX -= 1;
                } else if (carrier.getX() > pool.getPoolXHalf()) {
                    targetX += 1;
                }

                movement = pool.getBestMove(player, maxMove, targetX, targetY);
            }

            // Cover straight shot exactly
            else {
                int targetY = pool.getOffensiveOffset(player, pool.getEnemyPlayer(1,player).getY(),-4);
                movement = pool.getBestMove(player, maxMove, carrier.getX(), targetY);
            }
        }

        // SECOND MAN
        else {
            MovablePlayer firstOpponent = pool.getEnemyPlayer(1, player);

            // First man is between carrier and our net, cover open enemy player
            if (firstManBetween) {
                MovablePlayer secondOpponent = pool.getEnemyPlayer(2, player);
                // Align with enemy second man
                int targetX = secondOpponent.getX();
                int targetY = pool.getOffensiveOffset(player, carrier.getY(), -2);

                // If enemy second man has ball, align with first man instead
                if (secondOpponent == carrier) {
                    targetX = firstOpponent.getX();
                    targetY = firstOpponent.getY();
                }

                movement = pool.getBestMove(player, maxMove, targetX, targetY);
            }

            // First man has been beat, cover the enemy first man
            else {
                int targetY = pool.getOffensiveOffset(player, firstOpponent.getY(), -1);
                movement = pool.getBestMove(player, maxMove, firstOpponent.getX(), targetY);
            }
        }

        return new MoveAction(pool, player, movement[0], movement[1]);
    }

    /**
     * Action to be performed if a teammate has the ball
     * @param pool the Pool containing game state
     * @param player the player performing the action
     * @param carrier the teammate who has the ball
     * @param maxMove maximum distance for a MoveAction
     * @return a GameAction representing the players action
     */
    protected GameAction getTeammateCarrierAction(Pool pool, MovablePlayer player, MovablePlayer carrier, int maxMove) {
        // Get Teammate and Opponent Positioning
        MovablePlayer firstMan = pool.getFirstMan(player);
        MovablePlayer secondMan = pool.getSecondMan(player);
        MovablePlayer thirdMan = pool.getThirdMan(player);

        // Target coordinates for the player to move to
        int targetX;
        int targetY;

        // FIRST MAN
        if (firstMan == player) {
            targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(secondMan), 2);
            targetY = pool.getOffensiveOffset(player, secondMan.getY(), 4);
        }

        // THIRD MAN
        else if (thirdMan == player) {
            // If carrier is in the back half, chance to move up for breakout pass
            if (pool.getDistanceFromDefensive(carrier, carrier.getY()) < pool.getPoolYHalf() && rand.nextInt(0, player.getPassFactor()) > 45) {
                targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(carrier), 1);
                targetY = pool.getOffensiveOffset(player, carrier.getY(), 2);
            }

            // If carrier is in the front half, chance to move to open side for attack
            else if (pool.getDistanceFromDefensive(carrier, carrier.getY()) >= pool.getPoolYHalf() && rand.nextInt(0, player.getShotFactor()) > 50) {
                targetX = pool.getBalanceOffset(pool.getEnemyBalance(carrier), 2);
                targetY = pool.getOffensiveOffset(player, secondMan.getY(), 0);
            }

            // Creep up in the middle
            else {
                targetX = pool.getPoolXHalf();
                targetY = pool.getOffensiveOffset(player, pool.getEnemyPlayer(1, player).getY(), 1);
            }
        }

        // SECOND MAN
        else {
            // If first man has the ball, chance to move past the first man for forward pass
            if (carrier == firstMan && rand.nextInt(0, player.getShotFactor()) > 40) {
                targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(firstMan), 2);
                targetY = pool.getOffensiveOffset(player, firstMan.getY(), 2);
            }

            // If first man has the ball, try to open up back/side pass for first man
            else if (carrier == firstMan) {
                targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 1);
                targetY = pool.getOffensiveOffset(player, carrier.getY(), rand.nextInt(-2, 1));
            }

            // Move to open up a forward pass from third man
            else {
                targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(carrier), 1);
                targetY = pool.getOffensiveOffset(player, carrier.getY(), 2);
            }
        }

        int[] movement = pool.getBestMove(carrier, maxMove, targetX, targetY);
        return new MoveAction(pool, player, movement[0], movement[1]);
    }

    /**
     * Action to be performed when player has the ball
     * @param pool the Pool containing game state
     * @param player the player (ball carrier) performing the action
     * @param maxMove maximum distance for a MoveAction
     * @return a GameAction representing the players action
     */
    protected GameAction getCarrierAction(Pool pool, MovablePlayer player, int maxMove) {
        // Get Teammate Positioning
        MovablePlayer firstMan = pool.getFirstMan(player);
        MovablePlayer secondMan = pool.getSecondMan(player);
        MovablePlayer thirdMan = pool.getThirdMan(player);

        int distanceFromGoal = pool.getPoolY() - pool.getDistanceFromDefensive(player, player.getY());
        int bestShotTarget = pool.getBestShot(player);

        // FIRST MAN
        if (player == firstMan) {
            // If there is open shot lane, chance to take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0) {
                if (distanceFromGoal <= 3 || distanceFromGoal == 4 && rand.nextInt(0, player.getShotFactor()) > 40 || distanceFromGoal <= 5 && rand.nextInt(0, player.getShotFactor()) > 70) {
                    return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
                }
            }

            // If closest forward opponent is more than distance 2 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 2) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // Chance to pass to second man, or guaranteed if second man close with open shot lane
            if (rand.nextInt(0, player.getPassFactor()) > 50 || pool.getShotCongestion(secondMan,pool.getBestShot(secondMan)) == 0 && pool.getDistanceFromDefensive(player, secondMan.getY()) > 6) {
                return new PassAction(pool, player, secondMan);
            }

            return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
        }


        // THIRD MAN
        else if (player == thirdMan) {
            // If there is open shot lane, chance to take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0) {
                if (distanceFromGoal <= 5 || distanceFromGoal == 6 && rand.nextInt(0, player.getShotFactor()) > 30 || distanceFromGoal <= 7 && rand.nextInt(0, player.getShotFactor()) > 50) {
                    return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
                }
            }

            // If closest forward opponent is more than distance 4 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 3) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            //If there is open pass to second man or first man, make the pass
            if (pool.getPassCongestion(player, secondMan) == 0) {
                return new PassAction(pool, player, secondMan);
            } if (pool.getPassCongestion(player, firstMan) == 0) {
                return new PassAction(pool, player, firstMan);
            }

            // Chance to just pass to second man
            if (rand.nextInt(0, player.getPassFactor()) > 40) {
                return new PassAction(pool, player, secondMan);
            }

            // Chance to shoot the ball if reasonable distance
            if (rand.nextInt(0, player.getShotFactor()) > 60 && distanceFromGoal <= 8) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            // Chance to make a move around defender
            if (rand.nextInt(0, player.getSwimFactor()) > 55) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player),2);
                int targetY = pool.getOffensiveOffset(player, player.getY(), 2);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // Otherwise, dump the ball into opponent’s zone ahead of first man
            return new DumpAction(pool, player, firstMan.getX(), pool.getOffensiveOffset(player, pool.getTarget(firstMan), -3));
        }

        // SECOND MAN
        else {
            // If there is open shot lane, chance to take shot based on distance
            if (pool.getShotCongestion(player, bestShotTarget) == 0) {
                if (distanceFromGoal <= 3 || distanceFromGoal == 4 && rand.nextInt(0, player.getShotFactor()) > 40 || distanceFromGoal <= 5 && rand.nextInt(0, player.getShotFactor()) > 70) {
                    return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
                }
            }

            // If closest forward opponent is more than distance 3 away, move into open space
            if (pool.getDistance(player, pool.getNextOpponent(player)) > 3) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player), 2);
                int targetY = pool.getOffensiveOffset(player, pool.getNextOpponent(player).getY(), 1);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            // If there is an open pass to first man, make the pass
            if (pool.getPassCongestion(player, firstMan) == 0) {
                return new PassAction(pool, player, firstMan);
            }

            //If there is open pass to third man, make the pass
            if (pool.getPassCongestion(player, thirdMan) == 0) {
                return new PassAction(pool, player, thirdMan);
            }

            //Chance to shoot the ball if reasonable distance
            if (distanceFromGoal <= 6 && rand.nextInt(0, player.getShotFactor()) > 40) {
                return new ShotAction(pool, player, bestShotTarget, pool.getTarget(player));
            }

            //Chance to just pass to first man
            if (rand.nextInt(0, player.getPassFactor()) > 40) {
                return new PassAction(pool, player, firstMan);
            }

            //Chance to make a move around defender
            if (rand.nextInt(0, player.getSwimFactor()) > 50) {
                int targetX = pool.getBalanceOffset(pool.getEnemyForwardBalance(player),2);
                int targetY = pool.getOffensiveOffset(player, player.getY(), 2);

                int[] movement = pool.getBestMove(player, maxMove, targetX, targetY);
                return new MoveAction(pool, player, movement[0], movement[1]);
            }

            //Otherwise, dump the ball into opponent’s zone ahead of first man or pass it
            if (rand.nextInt(0, 2) == 1) {
                return new PassAction(pool, player, firstMan);
            } else {
                return new DumpAction(pool, player, firstMan.getX(), pool.getOffensiveOffset(player, pool.getTarget(firstMan), -3));
            }
        }
    }

    @Override
    public int getSwimFactor() {
        return this.getSW();
    }

    @Override
    public int getStealFactor() {
        return this.getDF();
    }

    @Override
    public int getPassFactor() {
        return this.getPM();
    }

    @Override
    public int getShotFactor() {
        return this.getGS();
    }

    @Override
    public int getBlockFactor() {
        return this.getDF();
    }

    @Override
    public int getFaceoffFactor() {
        return this.getPM();
    }

    @Override
    public double getSelfInjury() {
        return getInjury();
    }

    @Override
    public double getOpponentInjury() {
        return 0;
    }
}
