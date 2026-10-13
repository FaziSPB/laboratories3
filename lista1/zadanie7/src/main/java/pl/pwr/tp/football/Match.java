package pl.pwr.tp.football;

public class Match {

    private static final int WIN_POINTS = 3;
    private static final int DRAW_POINTS = 1;

    private final Team home;
    private final Team away;
    private final int homeGoals;
    private final int awayGoals;

    public Match(Team home, Team away, int homeGoals, int awayGoals) {
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public void play() {
        if (homeGoals > awayGoals) {
            home.addPoints(WIN_POINTS);
        } else if (homeGoals < awayGoals) {
            away.addPoints(WIN_POINTS);
        } else {
            home.addPoints(DRAW_POINTS);
            away.addPoints(DRAW_POINTS);
        }
    }

    @Override
    public String toString() {
        return home.getName() + " " + homeGoals + ":" + awayGoals + " " + away.getName();
    }
}
