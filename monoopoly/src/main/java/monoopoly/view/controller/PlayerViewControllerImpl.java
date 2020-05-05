package monoopoly.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.stage.Stage;
import monoopoly.view.utilities.ScenePath;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class PlayerViewControllerImpl implements PlayerViewController, Initializable {

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

	private ViewUtilities utilities = new ViewUtilitiesImpl();
	private List<Label> playerNames = new ArrayList<Label>(
			Arrays.asList(playerName0, playerName1, playerName2, playerName3, playerName4, playerName5));
	private List<Label> playerBalances = new ArrayList<Label>(Arrays.asList(playerBalance0, playerBalance1,
			playerBalance2, playerBalance3, playerBalance4, playerBalance5));

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void displayPlayerPropertiesButtonClicked(MouseEvent event) {
		// TODO
	}

	public void setPlayerNames(final Map<Integer, String> names) {
		names.forEach((K, V) -> {
			this.playerNames.get(K).setText(V);
		});
	}

	@Override
	public void updateBalances(final Map<Integer, Double> balances) {
		balances.forEach((K, V) -> {
			this.playerBalances.get(K).setText(this.utilities.toMoneyString(V));
			// TODO try to change color
//			if (V <= 0) {
//				ColorAdjust colorAdjust = new ColorAdjust();
//				colorAdjust.setSaturation(0.5);
//				this.playerBalances.get(K).getParent().setEffect(colorAdjust);
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

}
