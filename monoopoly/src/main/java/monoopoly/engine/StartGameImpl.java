package monoopoly.engine;


import java.util.HashMap;
import java.util.Map;
import monoopoly.Main;
import monoopoly.view.scene.controller.SceneManager;
import monoopoly.view.scene.controller.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

public final class StartGameImpl implements StartGame {

    private Map<Integer, String> name = new HashMap<>(); 
    private Map<Integer, Double> balance = new HashMap<>(); 

    @Override
    public void createEngine() {
        final GameEngine engine = new GameEngineImpl(this.name, this.balance);
        final SceneManager manager = new SceneManagerImpl();
        manager.loadScene(ScenePath.BOARD, Main.getPrimaryStage());
        engine.setMainBoardController(manager.getMainController());
        engine.createPlayers();
    }

    @Override
    public void setName(final Map<Integer, String> name) {
        this.name = name;
    }

    @Override
    public void setBalance(final Map<Integer, Double> balance) {
        this.balance = balance;
    }


}
