package monoopoly.view.start;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import monoopoly.game_engine.StartGame;
import monoopoly.game_engine.StartGameImpl;
import monoopoly.view.ViewController;

/**
 * This class checks the parameters set in the setPlayers JavaFX scene and
 * starts the game
 */
public class SetPlayersFormChecker implements ViewController {

	/**
	 * These constants defines the balance bounds, the balance increase step for the
	 * "spinner" and the minimum player number
	 */
	private static final Double MIN_BALANCE = 200.00;
	private static final Double MAX_BALANCE = 20000.00;
	private static final Double BALANCE_INCREASE_VALUE = 500.00;
	private static final int MIN_PLAYERS = 2;

	private StartGame start = new StartGameImpl();

	private Map<Integer, String> playerMap = new HashMap<Integer, String>();
	private Double balance = 0.0;

	
	/**
	 * Fields and buttons in .fxml file reference
	 */
	@FXML
	private TextField player1;

	@FXML
	private TextField player2;

	@FXML
	private TextField player3;

	@FXML
	private TextField player4;

	@FXML
	private TextField player5;

	@FXML
	private TextField player6;

	@FXML
	private TextField startingBalance;

	@FXML
	private Button decreaseBalance;

	@FXML
	private Button increaseBalance;

	@FXML
	private Button startGameBtn;

	@Override
	public void onDisplay() {

	}

	/**
	 * This method starts the game when the "Start Game" button is pressed
	 */
	@FXML
	public void btnStartGameClicked() {
		if (this.checkFields()) {
			new Alert(AlertType.ERROR, "Game starts in 3... 2... 1.. \n GO").show();
			// this.start.createEngine();
		} else {
			new Alert(AlertType.ERROR, "Too few players").show();
		}
	}

	/**
	 * This method updates the player names map to pass to the {@link StartGame}
	 */
	@FXML
	public void updatedText() {
		this.playerMap.put(1, player1.getText().trim());
		this.playerMap.put(2, player2.getText().trim());
		this.playerMap.put(3, player3.getText().trim());
		this.playerMap.put(4, player4.getText().trim());
		this.playerMap.put(5, player5.getText().trim());
		this.playerMap.put(6, player6.getText().trim());
	}

	/**
	 * This method updates the balance variable and checks the balance value set by
	 * the player, and eventually corrects it
	 */
	@FXML
	public void updatedBalance() {
		if (this.startingBalance.getText().isEmpty()) {
			this.startingBalance.setText("0.0");
		}
		this.balance = Double.valueOf(this.startingBalance.getText().trim());
		this.checkBalanceUpperBound();
		this.checkBalanceLowerBound();
	}

	/**
	 * This method checks if the balance is lower then the minimum balance value
	 */
	private void checkBalanceLowerBound() {
		if (this.balance < MIN_BALANCE) {
			this.balance = MIN_BALANCE;
			this.setBalanceField();
		}
	}

	/**
	 * This method checks if the balance is greater then the maximum balance value
	 */
	private void checkBalanceUpperBound() {
		if (this.balance > MAX_BALANCE) {
			this.balance = MAX_BALANCE;
			this.setBalanceField();
		}
	}

	/**
	 * This method increases the balance by a set value
	 */
	@FXML
	public void btnIncreaseBalanceClicked() {
		this.updatedBalance();
		if (this.balance + BALANCE_INCREASE_VALUE <= MAX_BALANCE) {
			this.balance = this.balance + BALANCE_INCREASE_VALUE;
		} else {
			new Alert(AlertType.ERROR, "Balance should be less or equal to " + MAX_BALANCE.toString()).show();
			this.balance = MAX_BALANCE;
		}
		this.setBalanceField();
	}

	/**
	 * This method decreases the balance by a set value
	 */
	@FXML
	public void btnDecreaseBalanceClicked() {
		this.updatedBalance();
		if (this.balance - BALANCE_INCREASE_VALUE >= MIN_BALANCE) {
			this.balance = this.balance - BALANCE_INCREASE_VALUE;
		} else {
			new Alert(AlertType.ERROR, "Balance should be at least " + MIN_BALANCE.toString()).show();
			this.balance = MIN_BALANCE;
		}
		this.setBalanceField();
	}

	/**
	 * This method checks if there are at least MIN_PLAYERS in game and if the
	 * balance is between the bounds
	 * 
	 * @return true if the game is ready to start
	 */
	private boolean checkFields() {
		this.checkBalanceUpperBound();
		this.checkBalanceLowerBound();
		this.playerMap.values().removeIf(String::isBlank);
		return (this.playerMap.values().stream().count() >= MIN_PLAYERS);
	}

	/**
	 * This method updates the displayed balance field
	 */
	private void setBalanceField() {
		this.startingBalance.setText(String.valueOf(this.balance));
	}

}
