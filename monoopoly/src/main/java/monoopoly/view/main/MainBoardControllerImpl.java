package monoopoly.view.main;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import org.davidmoten.text.utils.WordWrap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import monoopoly.game_engine.GameEngine;
import monoopoly.model.item.Tile.Category;
import monoopoly.view.controller.BoardViewControllerImpl;
import monoopoly.view.controller.DiceViewControllerImpl;
import monoopoly.view.controller.PlayerViewControllerImpl;
import monoopoly.view.controller.StockMarketViewControllerImpl;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.controller.TileInfoControllerImpl;
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
	private GameEngine gameEngine;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void endGameButtonPressed() {
		/*
		 * TODO this.gameEngine.endGame();
		 */
	}

	@FXML
	public void quitButtonPressed(ActionEvent event) {
		this.utilities.closeApp(event);
	}

	@FXML
	public void rollDicesButtonPressed() {
		this.updateDices(this.gameEngine.rollDices());
		this.rollDices.setDisable(true);
		this.nextTurn.setDisable(false);
	}

	@FXML
	public void nextTurnButtonPressed() {
		this.gameEngine.passPlayer();
		this.nextTurn.setDisable(true);
		this.rollDices.setDisable(false);
	}

	@FXML
	public void surrenderButtonPressed() {
		// TODO
	}

	@Override
	public void showDeckCard(String cardCategory, String message) {
		Alert deckCard = new Alert(Alert.AlertType.WARNING, cardCategory.toUpperCase());
		deckCard.setHeaderText(cardCategory.toUpperCase());
		deckCard.setContentText(WordWrap.from(message).maxWidth(60).wrap());
		deckCard.initModality(Modality.APPLICATION_MODAL);
		deckCard.setTitle(cardCategory);
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
	public void showPlayerProperties(Set<String> properties) {
		this.playerInfoController.showPlayerProperties(properties);
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

}
