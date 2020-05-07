package monoopoly.view.controller;

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

public class BoardViewControllerImpl implements BoardViewController, Initializable {

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
	private Map<Integer, Integer> playerPositions = IntStream.range(0, 6).boxed()
			.collect(Collectors.toMap(Function.identity(), i -> Integer.valueOf(0)));
	private Map<Integer, Pair<Integer, Integer>> pawns = new HashMap<Integer, Pair<Integer, Integer>>();
	private Map<Integer, Circle> circles = new HashMap<Integer, Circle>();
	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.circles.put(0, player0);
		this.circles.put(1, player1);
		this.circles.put(2, player2);
		this.circles.put(3, player3);
		this.circles.put(4, player4);
		this.circles.put(5, player5);

		this.updatePawn();
	}

	@Override
	public void setGameEngine(final GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@FXML
	public void cellButtonPressed(ActionEvent event) {
		Button tileButton = (Button) event.getSource();
		int index = this.utilities.getBoardPosition(GridPane.getColumnIndex(tileButton),
				GridPane.getRowIndex(tileButton),
				(this.gridPane.getRowCount() - 1 + this.gridPane.getColumnCount() - 1) * 2);
		this.gameEngine.giveTileInfo(index);
	}

	@Override
	public void updatePlayerPositions(final Map<Integer, Integer> positions) {
		this.playerPositions.putAll(positions);
		this.playerPositions.keySet().forEach(K -> {
			if (!positions.containsKey(K)) {
				this.circles.get(K).setVisible(false);
			}
		});

		this.updatePawn();
	}

	@Override
	public void initializeBoard(final List<String> tileNames) {
		this.utilities.initializeBoard(gridPane, tileNames);
	}

	/**
	 * This method updates the pawn position on the board
	 */
	private void updatePawn() {
		playerPositions.forEach((K, V) -> {
			this.pawns.put(K, this.utilities.getCoords(V, gridPane));
			GridPane.setColumnIndex(this.circles.get(K), this.pawns.get(K).getX());
			GridPane.setRowIndex(this.circles.get(K), this.pawns.get(K).getY());
		});
	}

}
