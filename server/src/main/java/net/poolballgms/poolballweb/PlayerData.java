package net.poolballgms.poolballweb;

import jakarta.persistence.*;
import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Trait;

@Entity
@Table(name="PLAYER_DATA")
public class PlayerData {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private int season;
    private int age;

    @Enumerated(EnumType.STRING)
    private Playstyle playstyle;

    @Enumerated(EnumType.STRING)
    private Trait trait;

    private int potential;

    @Embedded
    private StatlineJPA stats;

    public PlayerData() {}

    // Update PlayerData using Player
    public void updateFromPlayer(Player player) {
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.age = player.getAge();
        this.playstyle = player.getStyle();
        this.trait = player.getTrait();
        this.potential = player.getPotential();
        this.stats = new StatlineJPA(player.getStats());
    }

    public Long getId() {
        return id;
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
