package pl.pwr.tp.football;

public class Team {

    private final String name;
    private int points;

    public Team(String name) {
        this.name = name;
    }

    public void addPoints(int value) {
        points += value;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
