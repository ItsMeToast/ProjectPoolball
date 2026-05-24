package gamesimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameRecord implements Iterable<PlayerRecord>{
    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final boolean overtimeGame;
    private final List<PlayerRecord> playerRecords;

    public GameRecord(String homeTeam, String awayTeam, int homeScore, int awayScore, boolean overtimeGame, List<PlayerRecord> playerRecords) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.overtimeGame = overtimeGame;
        this.playerRecords = playerRecords;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public boolean getOvertimeGame() {
        return overtimeGame;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(homeTeam + " " + homeScore + ":" + awayScore + " " + awayTeam);
        if (overtimeGame) {
            result.append(" (OT)");
        }

        result.append("\n");

        for (PlayerRecord record : playerRecords) {
            result.append(record.toString()).append("\n");
        }
        return result.toString();
    }

    public List<PlayerRecord> getPlayerRecords() {
        return new ArrayList<>(playerRecords);
    }

    @Override
    public Iterator<PlayerRecord> iterator() {
        return playerRecords.iterator();
    }
}
