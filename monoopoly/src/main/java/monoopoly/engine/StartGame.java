package monoopoly.engine;

import java.util.Map;

/**
 * This interface is engaged to create the game. 
 */
public interface StartGame {

    /**
     * Method used to create the GameEngine.
     */
    void createEngine();

    /**
     * Method used to create the beginning list of Players 
     * with their names.
     * @param name representing every player with his own name.
     */
    void setName(Map<Integer, String> name);

    /**
     * Method used to create the beginning list of Players 
     * with their names.
     * @param balance representing every player with his own balance.
     */
    void setBalance(Map<Integer, Double> balance);

}
