package monoopoly.view.utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
			Parent root = FXMLLoader.load(getClass().getResource(scene.getPath()));// resources)
																					// (getClass().getResource(scene.getPath()).toExternalForm());
			Scene newScene = new Scene(root);
			this.stage.setScene(newScene);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.stage.centerOnScreen();
		this.stage.show();
	}

	@Override
	public void swapScene(ScenePath scene) {
		try {
			this.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(scene.getPath()))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.stage.centerOnScreen();
		this.stage.show();
	}

	@Override
	public Scene getScene() {
		return this.stage.getScene();
	}

}
