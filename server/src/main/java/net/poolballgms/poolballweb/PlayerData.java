package net.poolballgms.poolballweb;

import jakarta.persistence.*;
import playertypes.Nationality;
import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Trait;

@Entity
@Table(name="PLAYER_DATA")
@IdClass(PlayerDataID.class)
public class PlayerData {
    @Id
    private long playerID;

    @Id
    private int season;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    private int age;

    @Enumerated(EnumType.STRING)
    private Playstyle playstyle;

    @Enumerated(EnumType.STRING)
    private Trait trait;

    private int potential;

    @Embedded
    private StatlineJPA stats;

    public PlayerData() {}

    // Build PlayerData from a Player
    public PlayerData(Player player, long playerID, int season) {
        this.playerID = playerID;
        this.season = season;

        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.nationality = player.getNationality();
        this.age = player.getAge();
        this.playstyle = player.getStyle();
        this.trait = player.getTrait();
        this.potential = player.getPotential();
        this.stats = new StatlineJPA(player.getStats());
    }

    // Build Player from this PlayerData
    public Player makePlayer() {
        return Player.getNewPlayer(
                this.firstName,
                this.lastName,
                this.nationality,
                this.age,
                this.playstyle,
                this.trait,
                this.stats.makeStatline(),
                this.potential
        );
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Playstyle getPlaystyle() {
        return playstyle;
    }

    public void setPlaystyle(Playstyle playstyle) {
        this.playstyle = playstyle;
    }

    public Trait getTrait() {
        return trait;
    }

    public void setTrait(Trait trait) {
        this.trait = trait;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public StatlineJPA getStats() {
        return stats;
    }

    public void setStats(StatlineJPA stats) {
        this.stats = stats;
    }
}
