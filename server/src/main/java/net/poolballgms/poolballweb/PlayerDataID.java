package net.poolballgms.poolballweb;

import java.io.Serializable;
import java.util.Objects;

public class PlayerDataID implements Serializable {
    private long playerID;
    private int season;

    public PlayerDataID(){}

    public PlayerDataID(long playerID, int season) {
        this.playerID = playerID;
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDataID that = (PlayerDataID) o;
        return season == that.season && Objects.equals(playerID, that.playerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, season);
    }
}
