package net.poolballgms.poolballweb;

import jakarta.persistence.Embeddable;
import playertypes.Statline;

/***
Class is needed for JPA shenanigans (requiring no-argument constructor)
 ***/
@Embeddable
public class StatlineJPA {
    private int accuracy;
    private int blocking;
    private int endurance;
    private int explosiveness;
    private int intelligence;
    private int power;
    private int size;
    private int speed;
    private double injury;

    public StatlineJPA() {}

    public StatlineJPA(int accuracy, int blocking, int endurance, int explosiveness, int intelligence, int power, int size, int speed, double injury) {
        this.accuracy = Math.clamp(accuracy, 1, 99);
        this.blocking = Math.clamp(blocking, 1, 99);
        this.endurance = Math.clamp(endurance, 1, 99);
        this.explosiveness = Math.clamp(explosiveness, 1, 99);
        this.intelligence = Math.clamp(intelligence, 1, 99);
        this.power = Math.clamp(power, 1, 99);
        this.size = Math.clamp(size, 1, 99);
        this.speed = Math.clamp(speed, 1, 99);
        this.injury = Math.clamp(Math.round(injury * 10) / 10.0, 0.1, 10);
    }

    // Build StatlineJPA from Statline
    public StatlineJPA(Statline statline) {
        this.accuracy = statline.getAccuracy();
        this.blocking = statline.getBlocking();
        this.endurance = statline.getEndurance();
        this.explosiveness = statline.getExplosiveness();
        this.intelligence = statline.getIntelligence();
        this.power = statline.getPower();
        this.size = statline.getSize();
        this.speed = statline.getSpeed();
        this.injury = statline.getInjury();
    }

    @Override
    public String toString() {
        return "ACC: " + accuracy + ", BLC: " + blocking + ", END: " + endurance + ", EXP: " + explosiveness + ", INT: " + intelligence + ", POW: " + power + ", SZE: " + size + ", SPD: " + speed + ", Injury: " + injury;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getBlocking() {
        return blocking;
    }

    public void setBlocking(int blocking) {
        this.blocking = blocking;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getExplosiveness() {
        return explosiveness;
    }

    public void setExplosiveness(int explosiveness) {
        this.explosiveness = explosiveness;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getInjury() {
        return injury;
    }

    public void setInjury(double injury) {
        this.injury = injury;
    }
}

