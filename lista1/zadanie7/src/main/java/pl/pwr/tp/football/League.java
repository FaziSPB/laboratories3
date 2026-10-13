package pl.pwr.tp.football;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class League {

    private final List<Team> teams = new ArrayList<>();

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void play(Match match) {
        match.play();
        System.out.println(match);
    }

    public List<Team> getTable() {
        List<Team> table = new ArrayList<>(teams);
        table.sort(Comparator.comparingInt(Team::getPoints).reversed());
        return table;
    }

    public static void main(String[] args) {
        Team legia = new Team("Legia");
        Team lech = new Team("Lech");
        Team rakow = new Team("Raków");

        League league = new League();
        league.addTeam(legia);
        league.addTeam(lech);
        league.addTeam(rakow);

        league.play(new Match(legia, lech, 2, 1));
        league.play(new Match(lech, rakow, 3, 0));
        league.play(new Match(rakow, legia, 1, 1));

        System.out.println();
        for (Team team : league.getTable()) {
            System.out.println(team.getName() + " " + team.getPoints() + " pts");
        }
    }
}
