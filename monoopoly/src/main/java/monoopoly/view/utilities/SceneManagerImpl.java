package monoopoly.view.utilities;

import java.io.IOException;

import com.sun.tools.javac.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import monoopoly.view.main.MainBoardControllerImpl;

public class SceneManagerImpl implements SceneManager {

    private Stage stage;
    private FXMLLoader loader = new FXMLLoader();
    private ViewUtilities utilities = new ViewUtilitiesImpl();

    @Override
    public void setup(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Monoopoly");
        stage.setOnCloseRequest(event -> utilities.closeApp(event));
    }

    @Override
    public void loadScene(ScenePath scene, Stage stage) {
        try {
            this.setup(stage);
            this.swapScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void swapScene(ScenePath scene) {
        this.stage.setResizable(true);
        this.loader.setLocation(getClass().getResource(scene.getPath()));
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

    private void checkScene(ScenePath scene) {
        if (scene.equals(ScenePath.BOARD)) {
            this.stage.setMaximized(true);
        }

    }

    @Override
    public Scene getScene() {
        return this.stage.getScene();
    }

    @Override
    public MainBoardControllerImpl getMainController() {
        return (MainBoardControllerImpl) this.loader.getController();
    }

}
