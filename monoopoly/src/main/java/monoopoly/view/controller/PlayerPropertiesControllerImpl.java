package monoopoly.view.controller;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PlayerPropertiesControllerImpl {

	private Stage stage;
	ObservableList<String> properties = FXCollections.observableArrayList();

	@FXML
	private ListView<String> playerProperties;

	@FXML
	void close() {
		this.stage.close();
	}

	@FXML
	void startTrade() {

	}

	public void show(Set<String> properties) {
		this.setProperties(properties);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * This method displays the properties in the {@link ListView}
	 * 
	 * @param properties
	 */
	private void setProperties(Set<String> properties) {
		for (String tile : properties) {
			this.properties.add(tile);
		}

		this.playerProperties.setItems(this.properties);
	}

}
