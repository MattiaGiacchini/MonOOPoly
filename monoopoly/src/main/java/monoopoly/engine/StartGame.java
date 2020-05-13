package monoopoly.engine;

import java.util.Map;

public interface StartGame {

    /**
     * Method used to create the GameEngine.
     */
    void createEngine();

    /**
     * Method used to create the beginning list of Players 
     * with their names.
     * @param name
     */
    void setName(Map<Integer, String> name);

    /**
     * Method used to create the beginning list of Players 
     * with their names.
     * @param balance
     */
    void setBalance(Map<Integer, Double> balance);

}
