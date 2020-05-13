package monoopoly.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import monoopoly.view.controller.ScoreboardViewController;
import monoopoly.view.controller.ScoreboardViewControllerImpl;

/**
 * This class tests the scoreboard visualization.
 */
public class TestScoreboardViewController {

    private static final double THIRD_BALANCE = 900.00;
    private static final double SECOND_BALANCE = 1800.00;
    private static final double FIRST_BALANCE = 3500.00;
    private static final double FOURTH_BALANCE = 500.00;

    private final ScoreboardViewController leaderboardController = new ScoreboardViewControllerImpl();

    /**
     * Testing the ordered leaderboard.
     */
    @Test
    public void testOrderedLeaderboard() {
        final Map<Integer, String> names = new HashMap<>();
        names.put(0, "Mattia");
        names.put(1, "Aiman");
        names.put(2, "Daniele");
        names.put(3, "Cristian");

        final Map<Integer, Double> points = new HashMap<>();
        points.put(0, FOURTH_BALANCE);
        points.put(1, FIRST_BALANCE);
        points.put(2, SECOND_BALANCE);
        points.put(3, THIRD_BALANCE);

        this.leaderboardController.showLeaderboard(names, points);
        final List<Entry<Integer, Double>> rank = this.leaderboardController.getRank();

        assertEquals(1, rank.get(0).getKey());
        assertEquals(2, rank.get(1).getKey());
        assertEquals(3, rank.get(2).getKey());
        assertEquals(0, rank.get(3).getKey());

    }

}
