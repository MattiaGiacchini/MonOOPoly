package monoopoly.view.start;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import monoopoly.game_engine.StartGame;
import monoopoly.game_engine.StartGameImpl;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * This class checks the parameters set in the setPlayers JavaFX scene and
 * starts the game
 */
public class SetPlayerController implements Initializable {

    /**
     * These constants defines the balance bounds, the balance increase step for the
     * "spinner" and the minimum player number
     */

    private static final Double STARTING_BALANCE = 3000.00;
    private static final Double MIN_BALANCE = 1500.00;
    private static final Double MAX_BALANCE = 15000.00;
    private static final Double BALANCE_INCREASE_VALUE = 500.00;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_NAME_LENGHT = 10;

    private StartGame start;

    private Map<Integer, String> playerMap = new HashMap<Integer, String>();
    private Double balance = STARTING_BALANCE;

    /*
     * Text field with the balance chosen by the user
     */
    @FXML
    private TextField startingBalance;

    /*
     * Displayed logo
     */
    @FXML
    private ImageView logo;

    /*
     * List of player names fields
     */
    @FXML
    private ArrayList<TextField> namesList;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logo.setImage(new Image(this.getClass().getResourceAsStream("/logoMonoopoly500.png")));
        this.startingBalance.setText(STARTING_BALANCE.toString());
    }

    /**
     * This method starts the game when the "Start Game" button is pressed
     */
    @FXML
    public void btnStartGameClicked(ActionEvent event) {
        if (this.checkFields()) {
            this.removeEmptyPlayer();
            this.start = new StartGameImpl();
            this.start.setName(this.playerMap);
            this.start.setBalance(this.setBalanceMap());
            this.start.createEngine();
        } else {
            new Alert(AlertType.ERROR, "You have to set at least two players").show();
        }
    }

    /**
     * This method updates the player names map to pass to the {@link StartGame}
     */
    @FXML
    public void updatedText() {

        this.namesList.forEach(x -> {
            if (x.getLength() > MAX_NAME_LENGHT) {
                x.setText(x.getText().substring(0, MAX_NAME_LENGHT));
            }
            this.playerMap.put(this.namesList.indexOf(x), x.getText().trim());
        });
    }

    /**
     * This method updates the balance variable and checks the balance value set by
     * the player, and eventually corrects it
     */
    @FXML
    public void updatedBalance() {
        if (this.startingBalance.getText().isEmpty()) {
            this.startingBalance.setText(this.balance.toString());
        } else if (!this.startingBalance.getText().matches("\\d+(.\\d+)?")) {
            this.balance = Double.valueOf(
                    this.startingBalance.toString().replaceAll("[^0-9.]", "").isEmpty() ? STARTING_BALANCE.toString()
                            : this.startingBalance.toString());
            this.startingBalance
                    .setText(this.balance.toString().isBlank() ? STARTING_BALANCE.toString() : this.balance.toString());
        }

        this.balance = Double.valueOf(this.startingBalance.getText().trim());
        this.checkBalanceUpperBound();
        this.checkBalanceLowerBound();
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

    /**
     * This method sets the initial balance map to set in {@link StartGame}
     *
     * @return the map of balances
     */
    private Map<Integer, Double> setBalanceMap() {
        Map<Integer, Double> balanceMap = new HashMap<Integer, Double>();
        this.playerMap.forEach((K, V) -> {
            balanceMap.put(K, this.balance);
        });

        return balanceMap;
    }

    /**
     * This method removes the empty strings from player names map
     */
    private void removeEmptyPlayer() {
        this.playerMap.forEach((K, V) -> {
            if (V.isBlank()) {
                this.playerMap.remove(K);
            }
        });

        List<String> tmpPlayers = new ArrayList<>(playerMap.values());
        playerMap.clear();
        int i = 0;
        for (String x : tmpPlayers) {
            playerMap.put(i, x);
            i++;
        }
    }

}
