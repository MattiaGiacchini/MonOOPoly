package monoopoly.view.controller.board;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import monoopoly.game_engine.GameEngine;
import monoopoly.utilities.Pair;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * this class represents the controller for the board: -pawns on the game board;
 * -tiles buttons pressed on the board game.
 */
public class BoardViewControllerImpl implements BoardViewController, Initializable {

    private static final int MAX_PLAYER = 6;

    @FXML
    private GridPane gridPane;

    @FXML
    private Circle player0;

    @FXML
    private Circle player1;

    @FXML
    private Circle player2;

    @FXML
    private Circle player3;

    @FXML
    private Circle player4;

    @FXML
    private Circle player5;

    private GameEngine gameEngine;

    // This is the map with players positions initialized to 0
    private final Map<Integer, Integer> playerPositions = IntStream.range(0, MAX_PLAYER).boxed()
            .collect(Collectors.toMap(Function.identity(), i -> Integer.valueOf(0)));
    private final Map<Integer, Pair<Integer, Integer>> pawns = new HashMap<>();
    private final Map<Integer, Circle> circles = new HashMap<>();
    private final ViewUtilities utilities = new ViewUtilitiesImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.circles.put(0, player0);
        this.circles.put(1, player1);
        this.circles.put(2, player2);
        this.circles.put(3, player3);
        this.circles.put(4, player4);
        this.circles.put(MAX_PLAYER - 1, player5);

        this.updatePawn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * This method notifies the {@link GameEngine} that a tile have been pressed.
     * 
     * @param event to get the button source.
     */
    @FXML
    public void cellButtonPressed(final ActionEvent event) {
        final Button tileButton = (Button) event.getSource();
        final int index = this.utilities.getBoardPosition(GridPane.getColumnIndex(tileButton),
                GridPane.getRowIndex(tileButton),
                (this.gridPane.getRowCount() - 1 + this.gridPane.getColumnCount() - 1) * 2);
        this.gameEngine.giveTileInfo(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayerPositions(final Map<Integer, Integer> positions) {
        this.playerPositions.putAll(positions);
        this.playerPositions.keySet().forEach(k -> {
            if (!positions.containsKey(k)) {
                this.circles.get(k).setVisible(false);
            }
        });

        this.updatePawn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeBoard(final List<String> tileNames) {
        this.utilities.initializeBoard(gridPane, tileNames);
    }

    /**
     * This method updates the pawn position on the board.
     */
    private void updatePawn() {
        playerPositions.forEach((k, v) -> {
            this.pawns.put(k, this.utilities.getCoords(v, gridPane));
            GridPane.setColumnIndex(this.circles.get(k), this.pawns.get(k).getX());
            GridPane.setRowIndex(this.circles.get(k), this.pawns.get(k).getY());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePawn(final int playerID) {
        this.circles.get(playerID).setVisible(false);
    }

}
