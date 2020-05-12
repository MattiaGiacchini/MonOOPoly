package monoopoly.view.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * This class implements the methods to display the scoreboard at the end of the
 * game.
 */
public class ScoreboardViewControllerImpl implements ScoreboardViewController, Initializable {

    private final ViewUtilities utilities = new ViewUtilitiesImpl();

    @FXML
    private ImageView logo;

    @FXML
    private Button closeWindow;

    @FXML
    private List<TextField> nameList;

    @FXML
    private List<TextField> pointsList;

    private Map<Integer, String> names = new HashMap<>();
    private List<Entry<Integer, Double>> rank;

    /**
     * This method closes the game when the button is closed.
     * 
     * @param event for closing app.
     */
    @FXML
    public void quitButtonPressed(final ActionEvent event) {
        this.utilities.closeApp(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLeaderboard(final Map<Integer, String> names, final Map<Integer, Double> points) {
        this.names = names;
        this.orderLeaderboard(points);
        this.displayLeaderboard();
    }

    /**
     * This method displays on the screen the leaderboard.
     */
    private void displayLeaderboard() {
        this.rank.forEach(x -> {
            this.nameList.get(rank.indexOf(x)).setText(names.get(x.getKey()));
            this.pointsList.get(rank.indexOf(x)).setText(this.utilities.toMoneyString(x.getValue()));
        });
    }

    /**
     * This method generates the ordered map of names and points of the players.
     * 
     * @param names
     * @param points
     */
    private void orderLeaderboard(final Map<Integer, Double> points) {
        this.rank = points.entrySet().stream().sorted((x, y) -> Double.compare(y.getValue(), x.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.logo.setImage(new Image(getClass().getResourceAsStream("/logoMonoopoly500.png")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry<Integer, Double>> getRank() {
        return rank;
    }
}
