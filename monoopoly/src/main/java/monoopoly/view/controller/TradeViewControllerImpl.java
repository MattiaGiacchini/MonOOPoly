package monoopoly.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import monoopoly.Main;
import monoopoly.game_engine.GameEngine;

public class TradeViewControllerImpl implements Initializable {

	ObservableList<String> available = FXCollections.observableArrayList();
	ObservableList<String> selected = FXCollections.observableArrayList();
	private GameEngine gameEngine;

	@FXML
	private Label headerLabel;

	@FXML
	private ListView<String> availableProperties;

	@FXML
	private ListView<String> selectedProperties;

	@FXML
	private TextField monetaryField;

	@FXML
	private Button confirm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void removeProperty() {
		String tile = selectedProperties.getSelectionModel().getSelectedItem();
		if (tile != null) {
			selectedProperties.getSelectionModel().clearSelection();
			selected.remove(tile);
			available.add(tile);
		}
	}

	@FXML
	public void addProperty() {
		String tile = availableProperties.getSelectionModel().getSelectedItem();
		if (tile != null) {
			availableProperties.getSelectionModel().clearSelection();
			available.remove(tile);
			selected.add(tile);
		}
	}

	@FXML
	public void close() {
		Main.getPrimaryStage().close();
	}

	@FXML
	public void setTradeOffer() {
		this.gameEngine.setProposal(this.selected, Double.valueOf(this.monetaryField.getText().trim()));
	}

	@FXML
	public void updatedMoneyField() {
		if (!this.monetaryField.getText().matches("\\d*")) {
			this.monetaryField.setText(this.monetaryField.getText().replaceAll("[^\\d]", ""));
		}
	}

	/**
	 * This method sets the stage for the offerer
	 * 
	 * @param gameEngine
	 * @param properties
	 */
	public void showTileOffer(GameEngine gameEngine, Set<String> properties) {
		this.confirm.setText("NEXT");
		this.headerLabel.setText("Place your offer");
		this.setProperties(properties);
		this.gameEngine = gameEngine;
	}

	/**
	 * This method sets the stage for the request
	 * 
	 * @param gameEngine
	 * @param properties
	 */
	public void showTileRequest(GameEngine gameEngine, Set<String> properties) {
		this.confirm.setText("SEND");
		this.headerLabel.setText("Place your request");
		this.setProperties(properties);
		this.gameEngine = gameEngine;
	}

	/**
	 * This method displays the properties in the {@link ListView}
	 * 
	 * @param properties
	 */
	private void setProperties(Set<String> properties) {
		for (String tile : properties) {
			this.available.add(tile);
		}

		this.availableProperties.setItems(available);
		this.selectedProperties.setItems(selected);
	}
}
