package pl.pwr.tp.football;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeagueTest {

    @Test
    void winGivesThreePoints() {
        Team a = new Team("A");
        Team b = new Team("B");

        new Match(a, b, 2, 0).play();

        assertEquals(3, a.getPoints());
        assertEquals(0, b.getPoints());
    }

    @Test
    void drawGivesOnePointToEachTeam() {
        Team a = new Team("A");
        Team b = new Team("B");

        new Match(a, b, 1, 1).play();

        assertEquals(1, a.getPoints());
        assertEquals(1, b.getPoints());
    }

    @Test
    void tableIsSortedByPoints() {
        Team a = new Team("A");
        Team b = new Team("B");
        League league = new League();
        league.addTeam(a);
        league.addTeam(b);

        league.play(new Match(a, b, 0, 1));

        assertEquals(b, league.getTable().get(0));
    }
}
