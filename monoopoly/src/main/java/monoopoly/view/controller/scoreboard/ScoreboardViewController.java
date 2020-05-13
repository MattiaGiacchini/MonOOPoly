package monoopoly.view.controller.scoreboard;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This interface represents the controller of the leaderboard view.
 */
public interface ScoreboardViewController {

    /**
     * This method displays the final leaderboard.
     * 
     * @param names  map of the {@link Player} names
     * @param points map of the {@link Player} point gained
     */
    void showLeaderboard(Map<Integer, String> names, Map<Integer, Double> points);

    /**
     * This method returns the list with the {@link Player} id ordered by their
     * points.
     * 
     * @return the rank
     */
    List<Entry<Integer, Double>> getRank();

}
