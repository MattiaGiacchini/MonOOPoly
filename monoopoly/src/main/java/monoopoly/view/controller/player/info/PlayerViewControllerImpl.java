package monoopoly.view.controller.player.info;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import monoopoly.Main;
import monoopoly.engine.GameEngine;
import monoopoly.view.controller.player.properties.PlayerPropertiesController;
import monoopoly.view.controller.player.properties.PlayerPropertiesControllerImpl;
import monoopoly.view.utilities.ScenePath;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * this class implements the methods to display {@link Player} info.
 */
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

    private final ViewUtilities utilities = new ViewUtilitiesImpl();
    private GameEngine gameEngine;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    /**
     * This method displays the {@link Purchasable} of a {@link Player}.
     *
     * @param event to get the parent button.
     */
    @FXML
    public void displayPlayerPropertiesButtonClicked(final MouseEvent event) {
        final HBox playerBox = (HBox) event.getSource();
        this.gameEngine.giveProperties(Integer.valueOf(playerBox.getId().replaceAll("[^\\d]", "")));
    }

    /**
     * This method sets the {@link Player} names in the view.
     *
     * @param names to display.
     */
    @Override
    public void setPlayerNames(final Map<Integer, String> names) {
        names.forEach((k, v) -> {
            this.nameList.get(k).setText(v);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBalances(final Map<Integer, Double> balances) {
        balances.forEach((k, v) -> {
            this.balanceList.get(k).setText(this.utilities.toMoneyString(v));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCurrentPlayer(final String name, final Double balance) {
        this.currentPlayer.setText(name);
        this.currentPlayerBalance.setText(this.utilities.toMoneyString(balance));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPlayerProperties(final Set<String> properties, final String playerName) {
        final Stage propertiesStage = new Stage();
        final FXMLLoader loader = new FXMLLoader();
        final PlayerPropertiesController propertiesController = new PlayerPropertiesControllerImpl();
        loader.setLocation(getClass().getResource(ScenePath.PLAYER_PROPERTIES.getPath()));
        loader.setController(propertiesController);
        try {
            propertiesStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertiesController.setStage(propertiesStage);
        propertiesStage.initModality(Modality.APPLICATION_MODAL);
        propertiesController.show(properties);
        propertiesStage.setTitle(playerName.toUpperCase(new Locale("it")));
        propertiesStage.initOwner(Main.getPrimaryStage());
        propertiesStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/logoMonoopoly.png")));
        propertiesStage.setResizable(false);
        propertiesStage.showAndWait();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

}
