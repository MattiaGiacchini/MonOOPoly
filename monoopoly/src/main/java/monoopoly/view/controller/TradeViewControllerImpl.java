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
import javafx.stage.Stage;
import monoopoly.Main;
import monoopoly.game_engine.GameEngine;

public class TradeViewControllerImpl implements Initializable {

	ObservableList<String> available = FXCollections.observableArrayList();
	ObservableList<String> selected = FXCollections.observableArrayList();
	private GameEngine gameEngine;
	private Stage stage;

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

		this.available.addAll("LARGO COLOMBO", "PARCO DELLA VITTORIA", "STAZIONE EST", "NON FUNZIONA!!",
				"OK ORA VA");
		this.availableProperties.setItems(this.available);
		this.selectedProperties.setItems(this.selected);
	}

	@FXML
	public void removeProperty() {
		String tile = selectedProperties.getSelectionModel().getSelectedItem();
		if (tile != null) {
			this.selectedProperties.getSelectionModel().clearSelection();
			this.selected.remove(tile);
			this.available.add(tile);
		}
	}

	@FXML
	public void addProperty() {
		String tile = this.availableProperties.getSelectionModel().getSelectedItem();
		if (tile != null) {
			this.availableProperties.getSelectionModel().clearSelection();
			this.available.remove(tile);
			this.selected.add(tile);
		}
	}

	@FXML
	public void close() {
		this.stage.close();
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

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
