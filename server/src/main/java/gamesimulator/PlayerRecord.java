package gamesimulator;

public record PlayerRecord(String firstName, String lastName, int shots, int goals, int assists, int blocks, int steals, int injuredGames) {
    @Override
    public String toString() {
        String content = firstName + " " + lastName + ": " + shots + " shots, " + goals + " goals, " + assists + " assists, " + blocks + " blocks, " + steals + " steals";
        String injury = "";

        if (injuredGames > 1) {
            injury = "(Injury: " + injuredGames + " games)";
        } else if (injuredGames == 1) {
            injury = "(Injury: " + injuredGames + " game)";
        }

        return content + " " + injury;
    }
}
