package net.poolballgms.poolballweb;

import jakarta.persistence.*;
import playertypes.Nationality;

@Entity
@Table(name="PLAYERS")
public class PlayerJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long playerID;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    public PlayerJPA(String firstName, String lastName, Nationality nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    public PlayerJPA() {} // Needed for JPA purposes

    public long getPlayerID() {
        return playerID;
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
}
