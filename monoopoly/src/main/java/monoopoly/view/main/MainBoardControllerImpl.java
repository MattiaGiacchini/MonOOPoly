package monoopoly.view.main;

import java.net.URL;
import java.util.List;
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
import monoopoly.game_engine.GameEngine;
import monoopoly.model.item.Tile.Category;
import monoopoly.view.controller.BoardViewControllerImpl;
import monoopoly.view.controller.DiceViewControllerImpl;
import monoopoly.view.controller.PlayerViewControllerImpl;
import monoopoly.view.controller.StockMarketViewControllerImpl;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.controller.TileInfoControllerImpl;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

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

    private ViewUtilities utilities = new ViewUtilitiesImpl();
    private SceneManager manager = new SceneManagerImpl();
    private GameEngine gameEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nextTurn.setDisable(true);
        this.surrender.setDisable(true);
    }

    @FXML
    public void endGameButtonPressed() {
        this.gameEngine.endGame();
    }

    @FXML
    public void quitButtonPressed(ActionEvent event) {
        this.utilities.closeApp(event);
    }

    @FXML
    public void rollDicesButtonPressed(ActionEvent event) {
        this.updateDices(this.gameEngine.rollDices());
        this.rollDices.setDisable(true);
        this.nextTurn.setDisable(false);
        this.surrender.setDisable(false);
        if (this.tileInfoController.playerPayedRent()) {
            this.tileInfoController.resetButtons();
        }
        event.consume();
    }

    @FXML
    public void nextTurnButtonPressed(ActionEvent event) {
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

    @FXML
    public void surrenderButtonPressed() {
        this.surrender.setDisable(true);
        this.gameEngine.lose();
    }

    @Override
    public void showDeckCard(String cardCategory, String message) {
        Alert deckCard = new Alert(Alert.AlertType.WARNING, cardCategory.toUpperCase());
        deckCard.setHeaderText(cardCategory.toUpperCase());
        deckCard.getDialogPane().setContent(this.createDeckCardMessage(message));
        deckCard.initModality(Modality.APPLICATION_MODAL);
        deckCard.setTitle(cardCategory);
        deckCard.initOwner(Main.getPrimaryStage().getScene().getWindow());
        deckCard.showAndWait();
    }

    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.boardController.setGameEngine(gameEngine);
        this.tileInfoController.setGameEngine(gameEngine);
        this.playerInfoController.setGameEngine(gameEngine);
    }

    @Override
    public void setTileNames(List<String> tileNames) {
        this.boardController.initializeBoard(tileNames);
    }

    @Override
    public void updatePlayers(Map<Integer, Integer> positions, Map<Integer, Double> balances) {
        this.playerInfoController.updateBalances(balances);
        this.boardController.updatePlayerPositions(positions);
    }

    @Override
    public void updateCurrentPlayer(String name, Double balance) {
        this.currentPlayerController.updateCurrentPlayer(name, balance);
    }

    @Override
    public void setPlayerNames(Map<Integer, String> names) {
        this.playerInfoController.setPlayerNames(names);
    }

    @Override
    public void showPlayerProperties(Set<String> properties, String playerName) {
        this.playerInfoController.showPlayerProperties(properties, playerName);
    }

    @Override
    public void updateDices(int dice1, int dice2, Optional<Integer> dice3) {
        this.dicesImageController.updateDices(dice1, dice2, dice3);
    }

    @Override
    public void updateDices(Map<Integer, Integer> dices) {
        this.updateDices(dices.get(0), dices.get(1), Optional.empty());
    }

    @Override
    public void showPropertyPane(TileInfo info) {
        this.tileInfoController.showPropertyPane(info);
    }

    @Override
    public void updateStockMarket(Map<Category, Double> actualStockMarket, List<Map<Category, Double>> stockHistory) {
        this.stockMarketController.updateStockMarket(actualStockMarket);
        this.stockMarketController.updateStockMarketHistory(stockHistory);
    }

    @Override
    public void deletePlayer(final int playerID) {
        this.boardController.removePawn(playerID);
    }

    /**
     * This method transform a string in a textArea
     * 
     * @param message to display
     * @return a textArea with the message
     */
    private TextArea createDeckCardMessage(final String message) {
        TextArea messageBox = new TextArea(message);
        messageBox.setWrapText(true);
        messageBox.setEditable(false);
        return messageBox;
    }

}
