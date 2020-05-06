package monoopoly;

import javafx.application.Application;
import javafx.stage.Stage;
import monoopoly.view.start.StartingPageController;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

/**
 * This class represents the Main class of the application
 */
public class Main extends Application {

	SceneManager manager = new SceneManagerImpl();
	StartingPageController firstPage = new StartingPageController();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.manager.setup(primaryStage);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		this.manager.swapScene(ScenePath.START_PAGE);

	}

}
