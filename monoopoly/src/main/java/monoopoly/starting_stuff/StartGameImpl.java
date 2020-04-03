package monoopoly.starting_stuff;

import java.util.*;

public class StartGameImpl implements StartGame {
	
	private Map<Integer, String> name = new HashMap<>(); 
	
	private Map<Integer, Double> balance = new HashMap<>(); 

	private Map<Integer, Integer> position = new HashMap<>(); 

	private Map<Integer, monoopoly.utilities.States> state = new HashMap<>(); 

	
	public StartGameImpl() {
	}

	@Override
	public GameEngine createEngine() {
		final GameEngine newGM = new GameEngineImpl(this.name, this.balance, this.position, this.state);
		return newGM;
	}

	@Override
	public GameEngine openRecentGame(int game) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * setters used to initialize the player list, every player with his own credentials (not finished)
	 * @param name
	 */
	public void setName(Map<Integer, String> name) {
		this.name = name;
	}

	public void setBalance(Map<Integer, Double> balance) {
		this.balance = balance;
	}
	
	public void setPosition(Map<Integer, Integer> position) {
		this.position = position;
	}
	
	public void setState(Map<Integer, monoopoly.utilities.States> state) {
		this.state = state;
	}

}
