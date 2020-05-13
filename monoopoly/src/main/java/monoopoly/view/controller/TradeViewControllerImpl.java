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
import monoopoly.game_engine.GameEngine;

/**
 * This class implements the method to manager trades.
 */
/**
 * 
 */
public class TradeViewControllerImpl implements Initializable {

    private final ObservableList<String> available = FXCollections.observableArrayList();
    private final ObservableList<String> selected = FXCollections.observableArrayList();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.available.addAll("LARGO COLOMBO", "PARCO DELLA VITTORIA", "STAZIONE EST", "NON FUNZIONA!!", "OK ORA VA");
        this.availableProperties.setItems(this.available);
        this.selectedProperties.setItems(this.selected);
    }

    /**
     * This method removes the selected property from the list.
     */
    @FXML
    public void removeProperty() {
        final String tile = selectedProperties.getSelectionModel().getSelectedItem();
        if (tile != null) {
            this.selectedProperties.getSelectionModel().clearSelection();
            this.selected.remove(tile);
            this.available.add(tile);
        }
    }

    /**
     * This method adds the selected property from the list.
     */
    @FXML
    public void addProperty() {
        final String tile = this.availableProperties.getSelectionModel().getSelectedItem();
        if (tile != null) {
            this.availableProperties.getSelectionModel().clearSelection();
            this.available.remove(tile);
            this.selected.add(tile);
        }
    }

    /**
     * This method rejects the offer.
     */
    @FXML
    public void close() {
        this.stage.close();
    }

    /**
     * This method sets the offer.
     */
    @FXML
    public void setTradeOffer() {
        // TODO
        this.gameEngine.getClass();
    }

    /**
     * This method updates the monetary fields and checks its content.
     */
    @FXML
    public void updatedMoneyField() {
        if (!this.monetaryField.getText().matches("\\d*")) {
            this.monetaryField.setText(this.monetaryField.getText().replaceAll("[^\\d]", ""));
        }
    }

    /**
     * This method sets the stage for the offerer.
     * 
     * @param gameEngine game engine
     * @param properties of the offerer.
     */
    public void showTileOffer(final GameEngine gameEngine, final Set<String> properties) {
        this.confirm.setText("NEXT");
        this.headerLabel.setText("Place your offer");
        this.setProperties(properties);
        this.gameEngine = gameEngine;
    }

    /**
     * This method sets the stage for the request.
     * 
     * @param gameEngine game engine.
     * @param properties of the contractor.
     */
    public void showTileRequest(final GameEngine gameEngine, final Set<String> properties) {
        this.confirm.setText("SEND");
        this.headerLabel.setText("Place your request");
        this.setProperties(properties);
        this.gameEngine = gameEngine;
    }

    /**
     * This method sets the stage for the trade view components.
     * 
     * @param stage to set.
     */
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    /**
     * This method displays the properties in the {@link ListView}.
     * 
     * @param properties to display.
     */
    private void setProperties(final Set<String> properties) {
        for (final String tile : properties) {
            this.available.add(tile);
        }

        this.availableProperties.setItems(available);
        this.selectedProperties.setItems(selected);
    }

}
