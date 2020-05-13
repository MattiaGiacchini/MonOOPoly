package monoopoly.engine;

import java.util.Map;

public interface StartGame {

    void createEngine();

    GameEngine openRecentGame(int game);

    void setName(Map<Integer, String> name);

    void setBalance(Map<Integer, Double> balance);

}
