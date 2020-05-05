package monoopoly;

import javafx.application.Application;
import javafx.stage.Stage;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

/**
 * This class represents the Main class of the application
 */
public class Main extends Application {

	/**
	 * This is a static field with the application stage in order to have access to
	 * it at any time
	 */
	private static Stage stage;
	SceneManager manager = new SceneManagerImpl();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setPrimaryStage(primaryStage);
		// this.manager.setup(stage);
		this.manager.loadScene(ScenePath.BOARD, primaryStage);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
	}

	private void setPrimaryStage(Stage primaryStage) {
		stage = primaryStage;
	}

	public static Stage getPrimaryStage() {
		return stage;
	}

}
