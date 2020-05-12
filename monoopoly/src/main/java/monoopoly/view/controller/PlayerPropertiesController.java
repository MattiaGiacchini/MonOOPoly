package monoopoly.view.controller;

import java.util.Set;

import javafx.stage.Stage;
import monoopoly.model.item.Purchasable;
import monoopoly.model.player.Player;

public interface PlayerPropertiesController {

    /**
     * This method displays the properties the {@link Purchasable} of the selected {@link Player}
     * 
     * @param properties
     */
    void show(Set<String> properties);

    /**
     * This method sets the stage of the {@link PlayerPropertiesControllerImpl}
     * 
     * @param stage
     */
    void setStage(Stage stage);

}
