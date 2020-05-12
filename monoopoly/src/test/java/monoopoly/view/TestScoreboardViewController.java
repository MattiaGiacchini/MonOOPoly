package monoopoly.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import monoopoly.view.controller.ScoreboardViewController;
import monoopoly.view.controller.ScoreboardViewControllerImpl;

public class TestScoreboardViewController {

    private ScoreboardViewController leaderboardController = new ScoreboardViewControllerImpl();

    @Test
    public void testOrderedLeaderboard() {
        Map<Integer, String> names = new HashMap<Integer, String>();
        names.put(0, "Mattia");
        names.put(1, "Aiman");
        names.put(2, "Daniele");
        names.put(3, "Cristian");

        Map<Integer, Double> points = new HashMap<Integer, Double>();
        points.put(0, 500.00);
        points.put(1, 3500.00);
        points.put(2, 1800.00);
        points.put(3, 900.00);

        this.leaderboardController.showLeaderboard(names, points);
        List<Entry<Integer, Double>> rank = this.leaderboardController.getRank();

        assertEquals(1, rank.get(0).getKey());
        assertEquals(2, rank.get(1).getKey());
        assertEquals(3, rank.get(2).getKey());
        assertEquals(0, rank.get(3).getKey());

    }

}
