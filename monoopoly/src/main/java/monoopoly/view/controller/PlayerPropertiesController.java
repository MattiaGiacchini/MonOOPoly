package monoopoly.view.controller;

import java.util.Set;

import javafx.stage.Stage;

/**
 * This interface represents the controller for the popup with the
 * {@link Player} properties.
 */
public interface PlayerPropertiesController {

    /**
     * This method displays the properties of the chosen player.
     * 
     * @param properties to display.
     */
    void show(Set<String> properties);

    /**
     * This method sets the stage of the popup.
     * 
     * @param stage to set.
     */
    void setStage(Stage stage);

}
