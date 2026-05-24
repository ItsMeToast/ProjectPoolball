package playertypes;

public class Statline {
    private final int accuracy;
    private final int blocking;
    private final int endurance;
    private final int explosiveness;
    private final int intelligence;
    private final int power;
    private final int size;
    private final int speed;
    private final double injury;

    /**
     * Generates a random statline using the StatManager class
     */
    public Statline(int potential, int[] startingModifiers) {
        int[] startingStats = StatManager.getStartingStats(potential, startingModifiers);
        this.accuracy = startingStats[0];
        this.blocking = startingStats[1];
        this.endurance = startingStats[2];
        this.explosiveness = startingStats[3];
        this.intelligence = startingStats[4];
        this.power = startingStats[5];
        this.size = startingStats[6];
        this.speed = startingStats[7];
        this.injury = startingStats[8]/10.0;
    }

    public Statline(int accuracy, int blocking, int endurance, int explosiveness, int intelligence, int power, int size, int speed, double injury) {
        this.accuracy = Math.max(1, Math.min(accuracy,99));
        this.blocking = Math.max(1, Math.min(blocking,99));
        this.endurance = Math.max(1, Math.min(endurance,99));
        this.explosiveness = Math.max(1, Math.min(explosiveness,99));
        this.intelligence = Math.max(1, Math.min(intelligence,99));
        this.power = Math.max(1, Math.min(power,99));
        this.size = Math.max(1, Math.min(size,99));
        this.speed = Math.max(1, Math.min(speed,99));
        this.injury = Math.max(Math.min(Math.round(injury * 10) / 10.0, 10), 0.1);
    }

    /**
     * @return the average of goalscoring stats, rounded down to the nearest int
     */
    public int getGS() {
        return (power + accuracy + explosiveness)/3;
    }

    /**
     * @return the average of play-making stats, rounded down to the nearest int
     */
    public int getPM() {
        return (intelligence + accuracy + blocking)/3;
    }

    /**
     * @return the average of swimming stats, rounded down to the nearest int
     */
    public int getSW() {
        return (speed + explosiveness + endurance)/3;
    }

    /**
     * @return the average of defensive stats, rounded down to the nearest int
     */
    public int getDF() {
        return (size + blocking + endurance)/3;
    }

    /**
     * @return the injury percentage of the statline
     */
    public double getInjury() {
        return injury;
    }

    /**
     * Generates the one-year aged statline based on this and some modifiers
     * @param age the current age of the player (before update! IE if going 26->27 this should be 26)
     * @param potential the potential of the player
     * @param skillModifiers a size eight array representing modifiers to the 8 main stats
     * @param varianceModifier a modifier affecting the spread of randomness
     * @return a new Statline object with the updated values
     * @throws IllegalArgumentException if skillModifiers.length is not exactly 9 or age is >= 29
     */
    public Statline getAgedStatline(int age, int potential, int[] skillModifiers, int varianceModifier){
        if (skillModifiers.length != 9) {
            throw new IllegalArgumentException("skillModifiers must have length 9");
        } else if (age >= 29) {
            throw new IllegalArgumentException("Cannot age player beyond 29 years old!");
        }

        int[] growth = StatManager.getStatGrowth(age, potential, skillModifiers, varianceModifier);
        Statline newStatline = new Statline(
                this.accuracy + growth[0],
                this.blocking + growth[1],
                this.endurance + growth[2],
                this.explosiveness + growth[3],
                this.intelligence + growth[4],
                this.power + growth[5],
                this.size + growth[6],
                this.speed + growth[7],
                this.injury + (growth[8]/10.0)
        );

        return newStatline;
    }

    @Override
    public String toString() {
        return "ACC: " + accuracy + ", BLC: " + blocking + ", END: " + endurance + ", EXP: " + explosiveness + ", INT: " + intelligence + ", POW: " + power + ", SZE: " + size + ", SPD: " + speed + ", Injury: " + injury;
    }
}
