package monoopoly.view.controller.player.properties;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * This class implements the {@link PlayerPropertiesController} to display a
 * popUp with the properties of a {@link Player}.
 */
public class PlayerPropertiesControllerImpl implements PlayerPropertiesController {

    private Stage stage;
    private final ObservableList<String> properties = FXCollections.observableArrayList();

    @FXML
    private final ListView<String> playerProperties = new ListView<>();

    /**
     * This method closes the popup.
     */
    @FXML
    public void closePopUp() {
        this.stage.close();
    }

    /**
     * This method starts a trade with the chosen player.
     */
    @FXML
    public void startTrade() {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show(final Set<String> properties) {
        this.setProperties(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    /**
     * This method sets the properties in the {@link ListView} to display.
     * 
     * @param properties to display.
     */
    private void setProperties(final Set<String> properties) {
        this.properties.clear();
        for (final String tile : properties) {
            this.properties.add(tile);
        }

        this.playerProperties.setItems(this.properties);
    }

}
