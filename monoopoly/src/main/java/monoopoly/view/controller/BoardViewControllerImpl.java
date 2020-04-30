package monoopoly.view.controller;

import java.net.URL;
import java.util.HashMap;
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
import monoopoly.utilities.Pair;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class BoardViewControllerImpl implements BoardViewController, Initializable {

	@FXML
	private GridPane gridPane;

	// This is the map with players positions initialized to 0
	private Map<Integer, Integer> playerPositions = IntStream.range(0, 6).boxed()
			.collect(Collectors.toMap(Function.identity(), i -> Integer.valueOf(0)));
	private Map<Integer, Pair<Integer, Integer>> pawns = new HashMap<Integer, Pair<Integer, Integer>>();
	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.utilities.initializeBoard(gridPane);
		System.out.println(playerPositions.toString());
		this.updatePawn();
		this.updatePlayerPositions(
				IntStream.range(0, 6).boxed().collect(Collectors.toMap(Function.identity(), i -> Integer.valueOf(15))));
	}

	@FXML
	public void cellButtonPressed(ActionEvent event) {
		Button myButton = (Button) event.getSource();
		int index = this.utilities.getBoardPosition(GridPane.getColumnIndex(myButton), GridPane.getRowIndex(myButton),
				(this.gridPane.getRowCount() - 1 + this.gridPane.getColumnCount() - 1) * 2);
		System.out.println("\t " + index);
		System.out.println(this.utilities.getCoords(index, gridPane));
	}

	@Override
	public void updatePlayerPositions(Map<Integer, Integer> positions) {
		this.playerPositions.putAll(positions);
		this.updatePawn();
	}

	/**
	 * This method updates the pawn position on the board
	 */
	private void updatePawn() {
		this.playerPositions.forEach((K, V) -> {
			this.pawns.put(K, this.utilities.getCoords(V, gridPane));
		});
		System.out.println(pawns);
	}

}
