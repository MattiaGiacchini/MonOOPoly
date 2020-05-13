package monoopoly.view.start;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import monoopoly.view.scene.controller.SceneManager;
import monoopoly.view.scene.controller.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

/**
 * 
 */
public class StartingPageController implements Initializable {

    private final SceneManager manager = new SceneManagerImpl();

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnLoadGame;

    @FXML
    private ImageView logo;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.logo.setImage(new Image(getClass().getResourceAsStream("/logoMonoopoly500.png")));
    }

    /**
     * This method make the game begin, swaps the scene and loads the player
     * creation form.
     * 
     * @param event to consume.
     */
    @FXML
    public void startNewGame(final ActionEvent event) {
        final Stage stage = (Stage) btnNewGame.getScene().getWindow();
        manager.loadScene(ScenePath.SET_PLAYERS, stage);
    }

}
