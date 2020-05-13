package monoopoly.view.scene.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import monoopoly.view.controller.ScoreboardViewControllerImpl;
import monoopoly.view.main.MainBoardControllerImpl;
import monoopoly.view.utilities.ScenePath;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * This class implements the method to change scenes or to create new stages.
 */
public class SceneManagerImpl implements SceneManager {

    private Stage stage;
    private final FXMLLoader loader = new FXMLLoader();
    private final ViewUtilities utilities = new ViewUtilitiesImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(final Stage stage) {
        this.stage = stage;
        stage.setTitle("Monoopoly");
        stage.setOnCloseRequest(event -> utilities.closeApp(event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadScene(final ScenePath scene, final Stage stage) {
        this.setup(stage);
        this.swapScene(scene);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swapScene(final ScenePath scene) {
        this.stage.setResizable(true);
        this.loader.setLocation(this.getClass().getResource(scene.getPath()));
        try {
            this.stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stage.centerOnScreen();
        this.checkScene(scene);
        this.stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/logoMonoopoly500.png")));
        this.stage.show();
    }

    /**
     * This method checks the scenes and sets the stage with the right settings.
     * 
     * @param scene to display.
     */
    private void checkScene(final ScenePath scene) {
        if (scene.equals(ScenePath.BOARD)) {
            this.stage.setMaximized(true);
        } else {
            this.stage.setMaximized(false);
            this.stage.setResizable(true);
            this.stage.sizeToScene();
            this.stage.centerOnScreen();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scene getScene() {
        return this.stage.getScene();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainBoardControllerImpl getMainController() {
        return (MainBoardControllerImpl) this.loader.getController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreboardViewControllerImpl getLeaderboardController() {
        return (ScoreboardViewControllerImpl) this.loader.getController();
    }

}
