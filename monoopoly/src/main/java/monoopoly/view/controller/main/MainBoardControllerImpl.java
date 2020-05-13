package monoopoly.view.controller.main;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import monoopoly.Main;
import monoopoly.engine.GameEngine;
import monoopoly.model.item.tile.Tile.Category;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.controller.board.BoardViewControllerImpl;
import monoopoly.view.controller.dices.DiceViewControllerImpl;
import monoopoly.view.controller.player.info.PlayerViewControllerImpl;
import monoopoly.view.controller.stockmarket.StockMarketViewControllerImpl;
import monoopoly.view.controller.tile.TileInfoControllerImpl;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * This class is used to send messages between view and controller
 * ({@link GameEngine}).
 */
public class MainBoardControllerImpl implements Initializable, MainBoardController {

    @FXML
    private Button rollDices;

    @FXML
    private Button nextTurn;

    @FXML
    private Button surrender;

    @FXML
    private Label timer;

    @FXML
    private PlayerViewControllerImpl currentPlayerController;

    @FXML
    private PlayerViewControllerImpl playerInfoController;

    @FXML
    private BoardViewControllerImpl boardController;

    @FXML
    private TileInfoControllerImpl tileInfoController;

    @FXML
    private DiceViewControllerImpl dicesImageController;

    @FXML
    private StockMarketViewControllerImpl stockMarketController;

    private final ViewUtilities utilities = new ViewUtilitiesImpl();
    private GameEngine gameEngine;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.nextTurn.setDisable(true);
        this.surrender.setDisable(true);
    }

    /**
     * This method ends the game and displays a leaderboard.
     */
    @FXML
    public void endGameButtonPressed() {
        this.gameEngine.endGame();
    }

    /**
     * This method closes the application.
     * 
     * @param event for closing the app.
     */
    @FXML
    public void quitButtonPressed(final ActionEvent event) {
        this.utilities.closeApp(event);
    }

    /**
     * This method is used to roll the dices and move the {@link Player}.
     * 
     * @param event to consume.
     */
    @FXML
    public void rollDicesButtonPressed(final ActionEvent event) {
        this.updateDices(this.gameEngine.rollDices());
        this.rollDices.setDisable(true);
        this.nextTurn.setDisable(false);
        this.surrender.setDisable(false);
        if (this.tileInfoController.playerPayedRent()) {
            this.tileInfoController.resetButtons();
        }
        event.consume();
    }

    /**
     * This method is used to pass the turn to the next {@link Player} when the
     * current player has finished its actions. If the {@link Player} hasn't still
     * payed the rent the button will consume the event.
     * 
     * @param event to consume.
     */
    @FXML
    public void nextTurnButtonPressed(final ActionEvent event) {
        if (this.tileInfoController.playerPayedRent()) {
            this.nextTurn.setDisable(true);
            this.surrender.setDisable(true);
            this.gameEngine.passPlayer();
            this.tileInfoController.lockButtons();
            this.rollDices.setDisable(false);
        } else {
            event.consume();
        }
    }

    /**
     * This method allows the current {@link Player} to surrender itself.
     */
    @FXML
    public void surrenderButtonPressed() {
        this.surrender.setDisable(true);
        this.tileInfoController.lockButtons();
        this.tileInfoController.hasPayed();
        this.gameEngine.lose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDeckCard(final String cardCategory, final String message) {
        final Alert deckCard = new Alert(Alert.AlertType.WARNING, cardCategory.toUpperCase(new Locale("it")));
        deckCard.setHeaderText(cardCategory.toUpperCase(new Locale("it")));
        deckCard.getDialogPane().setContent(this.createDeckCardMessage(message));
        deckCard.initModality(Modality.APPLICATION_MODAL);
        deckCard.setTitle(cardCategory);
        deckCard.initOwner(Main.getPrimaryStage().getScene().getWindow());
        deckCard.showAndWait();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.boardController.setGameEngine(gameEngine);
        this.tileInfoController.setGameEngine(gameEngine);
        this.playerInfoController.setGameEngine(gameEngine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileNames(final List<String> tileNames) {
        this.boardController.initializeBoard(tileNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayers(final Map<Integer, Integer> positions, final Map<Integer, Double> balances) {
        this.playerInfoController.updateBalances(balances);
        this.boardController.updatePlayerPositions(positions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCurrentPlayer(final String name, final Double balance) {
        this.currentPlayerController.updateCurrentPlayer(name, balance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerNames(final Map<Integer, String> names) {
        this.playerInfoController.setPlayerNames(names);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPlayerProperties(final Set<String> properties, final String playerName) {
        this.playerInfoController.showPlayerProperties(properties, playerName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDices(final int dice1, final int dice2, final Optional<Integer> dice3) {
        this.dicesImageController.updateDices(dice1, dice2, dice3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDices(final Map<Integer, Integer> dices) {
        this.updateDices(dices.get(0), dices.get(1), Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPropertyPane(final TileInfo info) {
        this.tileInfoController.showPropertyPane(info);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStockMarket(final Map<Category, Double> actualStockMarket,
            final List<Map<Category, Double>> stockHistory) {
        this.stockMarketController.updateStockMarket(actualStockMarket);
        this.stockMarketController.updateStockMarketHistory(stockHistory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlayer(final int playerID) {
        this.boardController.removePawn(playerID);
    }

    /**
     * This method transform a string in a textArea.
     * 
     * @param message to display
     * @return a textArea with the message
     */
    private TextArea createDeckCardMessage(final String message) {
        final TextArea messageBox = new TextArea(message);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        return messageBox;
    }

}
