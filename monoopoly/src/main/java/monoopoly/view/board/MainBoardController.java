package monoopoly.view.board;

import java.io.Closeable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class MainBoardController implements Initializable {

	@FXML
	private GridPane gridPane;

	@FXML
	private Button rollDices;

	@FXML
	private Button nextTurn;

	@FXML
	private Button surrender;

	@FXML
	private Label timer;

	@FXML
	private Label currentPlayer;

	@FXML
	private Label currentPlayerBalance;

	/*
	 * Player's data fields (name and balance)
	 */

	@FXML
	private Label playerName0;

	@FXML
	private Label playerName1;

	@FXML
	private Label playerName2;

	@FXML
	private Label playerName3;

	@FXML
	private Label playerName4;

	@FXML
	private Label playerName5;

	@FXML
	private Label playerBalance0;

	@FXML
	private Label playerBalance1;

	@FXML
	private Label playerBalance2;

	@FXML
	private Label playerBalance3;

	@FXML
	private Label playerBalance4;

	@FXML
	private Label playerBalance5;

	private List<Label> playerNames;
	private List<Label> playerBalances;

	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.utilities.initializeBoard(gridPane);
		this.playerNames = new ArrayList<Label>(
				Arrays.asList(playerName0, playerName1, playerName2, playerName3, playerName4, playerName5));
		this.playerBalances = new ArrayList<Label>(Arrays.asList(playerBalance0, playerBalance1, playerBalance2,
				playerBalance3, playerBalance4, playerBalance5));
		// TODO remove those things, used as test
		Map<Integer, String> name = new HashMap<Integer, String>();
		name.put(0, "Mattia");
		name.put(1, "Aiman");
		name.put(2, "Daniele");
		name.put(3, "Cristian");
		name.put(4, "Viroli");
		name.put(5, "");

		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		balance.put(0, 500.00);
		balance.put(1, 1500.00);
		balance.put(2, 2500.00);
		balance.put(3, 3500.00);
		balance.put(4, 4500.00);
		balance.put(5, 0.00);
		this.updatePlayerBalances(balance);
		this.setPlayerNames(name);
	}

	@FXML
	public void quitButtonPressed(ActionEvent event) {

		this.utilities.closeApp(event);
	}

	@FXML
	public void cellButtonPressed(ActionEvent event) {
		Button myButton = (Button) event.getSource();
		int index = this.utilities.getBoardPosition(GridPane.getColumnIndex(myButton), GridPane.getRowIndex(myButton),
				this.gridPane.getRowCount() + this.gridPane.getColumnCount() - 2);
		System.out.println("\t " + index);
	}

	@FXML
	public void rollDicesButtonPressed() {
		this.nextTurn.setDisable(false);
		this.rollDices.setDisable(true);
	}

	@FXML
	public void nextTurnButtonPressed() {
		this.nextTurn.setDisable(true);
		this.rollDices.setDisable(false);
	}

	@FXML
	public void surrenderButtonPressed() {

	}

	@FXML
	public void displayPlayerPropertiesButtonClicked(MouseEvent event) {
		HBox box = (HBox) event.getSource();
		this.currentPlayer.setText(box.getId());
	}

	public void updatePlayerBalances(final Map<Integer, Double> balances) {
		balances.forEach((K, V) -> {
			if (V.equals(0.0)) {
				this.playerBalances.get(K).setText("");
			} else {
				this.playerBalances.get(K).setText(String.format("%.0f", V));
			}
		});
	}

	public void setPlayerNames(final Map<Integer, String> names) {
		names.forEach((K, V) -> {
			this.playerNames.get(K).setText(V);
		});
	}

	public void showDices(final int dice1, final int dice2, final int dice3) {

	}

}
