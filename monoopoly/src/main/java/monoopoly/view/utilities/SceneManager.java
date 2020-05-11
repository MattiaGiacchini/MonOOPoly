package monoopoly.view.utilities;

import javafx.scene.Scene;
import javafx.stage.Stage;
import monoopoly.view.controller.ScoreboardViewControllerImpl;
import monoopoly.view.main.MainBoardControllerImpl;

/**
 * This interface is used to manage scenes swap and to set a stage.
 */
public interface SceneManager {

	/**
	 * This method is used to set the scene's stage.
	 * 
	 * @param stage to use
	 * @throws Exception
	 */
	void setup(Stage stage) throws Exception;

	/**
	 * This method loads a scene from the res folder over a stage, passed as
	 * argument.
	 * 
	 * @param scene to load
	 * @param stage on which load the scene
	 */
	void loadScene(ScenePath scene, Stage stage);

	/**
	 * This method swaps the existing scene with the scene given by argument
	 * 
	 * @param scene
	 */
	void swapScene(ScenePath scene);

	/**
	 * This method returns the current scene
	 * 
	 * @return the current scene
	 */
	Scene getScene();

	/**
	 * This method returns the main view controller {@link MainBoardControllerImpl}
	 * 
	 * @return
	 */
	MainBoardControllerImpl getMainController();

    /**
     * This methos returns the controller of the final leaderboard
     * 
     * @return
     */
    ScoreboardViewControllerImpl getLeaderboardController();

}