package monoopoly.view.board;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import monoopoly.view.ViewUtilities;
import monoopoly.view.ViewUtilitiesImpl;

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

	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.utilities.initializeBoard(gridPane);
		this.playerNames = new ArrayList<Label>(
				Arrays.asList(playerName0, playerName1, playerName2, playerName3, playerName4, playerName5));
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		balance.put(0, 500.00);
		balance.put(1, 1500.00);
		balance.put(2, 2500.00);
		balance.put(3, 3500.00);
		balance.put(4, 4500.00);
		balance.put(5, 6500.00);
		this.updatePlayerBalances(balance);
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

	}

	@FXML
	public void nextTurnButtonPressed() {

	}

	@FXML
	public void surrenderButtonPressed() {

	}

	public void updatePlayerBalances(final Map<Integer, Double> balances) {
		balances.forEach((K, V) -> {
			this.playerNames.get(K).setText(String.format("%.0f", V));
		});
	}

	public void setPlayerNames(final Map<Integer, String> names) {

	}

	public void showDices(final int dice1, final int dice2) {

	}

}
