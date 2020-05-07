package monoopoly.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import monoopoly.game_engine.GameEngine;
import monoopoly.view.utilities.ScenePath;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class PlayerViewControllerImpl implements PlayerViewController, Initializable {

	@FXML
	private Label currentPlayer;

	@FXML
	private Label currentPlayerBalance;

	/*
	 * Player's data fields list (name and balance)
	 */
	@FXML
	private List<Label> nameList;
	
	@FXML
	private List<Label> balanceList;

	private ViewUtilities utilities = new ViewUtilitiesImpl();
	private GameEngine gameEngine;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void displayPlayerPropertiesButtonClicked(MouseEvent event) {
		HBox playerBox = (HBox) event.getSource();
		this.showPlayerProperties(
				this.gameEngine.giveProperties(Integer.valueOf(playerBox.getId().replaceAll("[^\\d]", ""))));
	}

	public void setPlayerNames(final Map<Integer, String> names) {
		names.forEach((K, V) -> {
			this.nameList.get(K).setText(V);
		});
	}

	@Override
	public void updateBalances(final Map<Integer, Double> balances) {
		balances.forEach((K, V) -> {
			this.balanceList.get(K).setText(this.utilities.toMoneyString(V));
			// TODO try to change color
//			if (V <= 0) {
//				ColorAdjust colorAdjust = new ColorAdjust();
//				colorAdjust.setSaturation(0.5);
//				this.balanceList.get(K).getParent().setEffect(colorAdjust);
//			}
		});
	}

	@Override
	public void updateCurrentPlayer(final String name, final Double balance) {
		this.currentPlayer.setText(name);
		this.currentPlayerBalance.setText(this.utilities.toMoneyString(balance));
	}

	@Override
	public void showPlayerProperties(Set<String> properties) {
		Stage propertiesStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(ScenePath.PLAYER_PROPERTIES.getPath()));
		PlayerPropertiesControllerImpl propertiesController = new PlayerPropertiesControllerImpl();
		loader.setController(propertiesController);
		try {
			propertiesStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		propertiesController.setStage(propertiesStage);
		propertiesController.show(properties);
	}

	@Override
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

}
