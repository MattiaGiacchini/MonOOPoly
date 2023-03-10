package monoopoly.view.scene.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import monoopoly.view.controller.main.MainBoardControllerImpl;
import monoopoly.view.controller.scoreboard.ScoreboardViewControllerImpl;
import monoopoly.view.utilities.ScenePath;

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
    void setup(Stage stage);

    /**
     * This method loads a scene from the res folder over a stage, passed as
     * argument.
     * 
     * @param scene to load
     * @param stage on which load the scene
     */
    void loadScene(ScenePath scene, Stage stage);

    /**
     * This method swaps the existing scene with the scene given by argument.
     * 
     * @param scene to set.
     */
    void swapScene(ScenePath scene);

    /**
     * This method returns the current scene.
     * 
     * @return the current scene.
     */
    Scene getScene();

    /**
     * This method returns the main view controller {@link MainBoardControllerImpl}.
     * 
     * @return {@link MainBoardControllerImpl}
     */
    MainBoardControllerImpl getMainController();

    /**
     * This methos returns the controller of the final leaderboard.
     * 
     * @return {@link ScoreboardViewControllerImpl}
     */
    ScoreboardViewControllerImpl getLeaderboardController();

}
