package gamesimulator;

import playertypes.Player;

import java.util.List;

public class GameTeam {
    private final String name;
    private final List<Player> firstLine;
    private final List<Player> secondLine;
    private final List<Player> thirdLine;

    public GameTeam(String name, List<Player> firstLine, List<Player> secondLine, List<Player> thirdLine) {
        this.name = name;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
    }

    public String getName() {
        return this.name;
    }

    public int getNumLines() {
        return 3;
    }

    public int getLineSize() {
        return firstLine.size();
    }

    //Bad design but oh well, change later?
    public List<Player> getLine(int lineNumber) {
        if (lineNumber == 0) {return firstLine;}
        else if (lineNumber == 1) {return secondLine;}
        else if (lineNumber == 2) {return thirdLine;}
        else {
            throw new IllegalArgumentException("Line number " + lineNumber + " does not exist");
        }
    }

    @Override
    public String toString() {
        return name + "\nFirst Line: " + firstLine + "\nSecond Line: " + secondLine + "\nThird Line: " + thirdLine;
    }
}
