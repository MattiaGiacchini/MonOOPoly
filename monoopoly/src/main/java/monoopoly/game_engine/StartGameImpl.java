package monoopoly.game_engine;

import java.util.*;

import monoopoly.Main;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

public class StartGameImpl implements StartGame {
	
	private Map<Integer, String> name = new HashMap<>(); 
	
	private Map<Integer, Double> balance = new HashMap<>(); 

	public StartGameImpl() {
	}

	@Override
	public void createEngine() {
		final GameEngine engine = new GameEngineImpl(this.name, this.balance);
		SceneManager manager = new SceneManagerImpl();
		manager.loadScene(ScenePath.BOARD, Main.getPrimaryStage());
		engine.setMainBoardController(manager.getMainController());
		engine.createPlayers();
	}

	@Override
	public GameEngine openRecentGame(int game) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * setters used to initialize the player list, every player with his own credentials
	 */
	public void setName(Map<Integer, String> name) {
		this.name = name;
	}

	public void setBalance(Map<Integer, Double> balance) {
		this.balance = balance;
	}


}
