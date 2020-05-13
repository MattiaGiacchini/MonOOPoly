/**
 * 
 */
package monoopoly.view.utilities;

/**
 * This enumeration contains all the scenes with their path.
 */
public enum ScenePath {

    /**
     * First scene where you can choose if load an existing game or if you want to
     * play a new game.
     */
    START_PAGE("001_startGame.fxml"),

    /**
     * Scene to set starting balance and player's names.
     */
    SET_PLAYERS("002_createPlayers.fxml"),

    /**
     * Scene that let you load an existing game.
     */
    LOAD_GAME("003_loadGame.fxml"),

    /**
     * Scene with the game board and main controls on the player.
     */
    BOARD("004_mainBoard.fxml"),

    /**
     * Scene that shows all the properties of a chosen player.
     */
    PLAYER_PROPERTIES("005_playerProperties.fxml"),

    /**
     * Scene that makes you compile a trade with a specific player.
     */
    TRADE("006_trade.fxml"),

    /**
     * Final scene with the score board.
     */
    SCOREBOARD("007_scoreboard.fxml");

    private static final String PATH = "/scenes/";
    private final String name;

    ScenePath(final String sceneName) {
        this.name = sceneName;
    }

    /**
     * This method returns the scene's path to load.
     * 
     * @return the PATH of the scene to load
     */
    public String getPath() {
        return PATH + this.name;
    }
}
