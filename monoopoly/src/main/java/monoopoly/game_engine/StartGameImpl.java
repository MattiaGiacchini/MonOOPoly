package monoopoly.game_engine;

import java.util.*;

import monoopoly.Main;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;

public class StartGameImpl implements StartGame {
	
	private Map<Integer, String> name = new HashMap<>(); 
	
	private Map<Integer, Double> balance = new HashMap<>(); 

	private Map<Integer, Integer> position = new HashMap<>(); 

	private Map<Integer, monoopoly.utilities.States> state = new HashMap<>(); 

	
	public StartGameImpl() {
	}

	@Override
	public void createEngine() {
		final GameEngine newGM = new GameEngineImpl(this.name, this.balance/*, this.position, this.state*/);
		SceneManager manager = new SceneManagerImpl();
		manager.loadScene(ScenePath.BOARD, Main.getPrimaryStage());
		newGM.setMainBoardController(manager.getMainController());
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
	
	/*public void setPosition(Map<Integer, Integer> position) {
		this.position = position;
	}
	
	public void setState(Map<Integer, monoopoly.utilities.States> state) {
		this.state = state;
	}*/

}
