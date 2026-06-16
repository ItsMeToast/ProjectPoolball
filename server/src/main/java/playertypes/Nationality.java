package playertypes;

import java.util.Random;

public enum Nationality {
    CAN("Canada"),
    USA("United States"),
    MEX("Mexico"),
    ENG("England"),
    GER("Germany"),
    ESP("Spain"),
    ITA("Italy"),
    FRA("France"),
    NED("Netherlands"),
    SUI("Switzerland"),
    SWE("Sweden"),
    CHN("China"),
    JPN("Japan"),
    AUS("Australia"),
    BRA("Brazil"),
    NGA("Nigeria");

    private static final Random rand = new Random();
    private final String country;

    Nationality(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public static Nationality random() {
        return Nationality.values()[rand.nextInt(Nationality.values().length)];
    }
}
