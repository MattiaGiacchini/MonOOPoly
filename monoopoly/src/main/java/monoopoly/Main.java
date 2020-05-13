package monoopoly;

import javafx.application.Application;
import javafx.stage.Stage;
import monoopoly.view.scene.controller.SceneManager;
import monoopoly.view.scene.controller.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

/**
 * This class represents the Main class of the application.
 */
public class Main extends Application {

    /**
     * This is a static field with the application stage in order to have access to
     * it at any time.
     */
    private static Stage stage;
    private final SceneManager manager = new SceneManagerImpl();

    /**
     * The main method.
     * 
     * @param args args
     */
    public static void main(final String[] args) {
        launch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.setPrimaryStage(primaryStage);
        // this.manager.setup(stage);
        this.manager.loadScene(ScenePath.START_PAGE, primaryStage);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
    }

    private void setPrimaryStage(final Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * This method is used to get the stage of the main application.
     * 
     * @return {@link Stage}
     */
    public static Stage getPrimaryStage() {
        return stage;
    }

}
