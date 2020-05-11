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
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

public class StartingPageController implements Initializable {

    private SceneManager manager = new SceneManagerImpl();

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
    public void initialize(URL location, ResourceBundle resources) {
        this.logo.setImage(new Image(getClass().getResourceAsStream("/logoMonoopoly500.png")));
    }

    @FXML
    public void startNewGame(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnNewGame.getScene().getWindow();
        manager.loadScene(ScenePath.SET_PLAYERS, stage);
    }

    @FXML
    public void loadGame() {

    }

}
