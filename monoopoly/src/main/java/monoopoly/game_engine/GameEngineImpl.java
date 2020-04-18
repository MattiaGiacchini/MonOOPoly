package monoopoly.game_engine;

import java.util.*;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.controller.player_manager.TurnManager;
import monoopoly.controller.player_manager.TurnManagerImpl;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;

public class GameEngineImpl implements GameEngine {
	
	/**
	 * creating a map for each credential you need, reachable by player's ID
	 */
	private static final int FIRST_PLAYER = 0;
	
	private Map<Integer, String> name = new HashMap<>(); 

	private Map<Integer, Double> balance = new HashMap<>(); 

	private Map<Integer, Integer> position = new HashMap<>(); 

	private Map<Integer, monoopoly.utilities.States> state = new HashMap<>();
			
	private TurnManager turnManager = new TurnManagerImpl(this.FIRST_PLAYER);

	private Table table;
	
	private Map<Integer, Integer> dices;
	
	//private CardManager cardManager = new CardManagerImpl();


	/**
	 * constructor, so that when StartGame creates GameEngine, it passes
	 * every player's credentials 
	 * 
	 * @param name
	 * @param balance
	 * @param position
	 * @param state
	 */
	public GameEngineImpl(final Map<Integer, String> name, 
						  final Map<Integer, Double> balance,
						  final Map<Integer, Integer> position,
						  final Map<Integer, monoopoly.utilities.States> state) {
		this.name = name;
		this.balance = balance;
		this.position = position;
		this.state = state;
	}
	
	public Table createTable() {
		this.table = new TableImpl();
		return this.getTable();
	}
	
	public PlayerManager createPlayer(final int ID) {
		return new PlayerManagerImpl(ID,this);
	}
	
	public void createPlayers() {
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while(it.hasNext()) {
			//this.createPlayer(it.next().getKey());
			this.turnManager.getPlayersList().add(this.createPlayer(it.next().getKey()));
		}
	}
	
	public PlayerManager currentPlayer() {
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			if (pM.getPlayerManagerID() == this.turnManager.getCurrentPlayer()) { 
				return pM;
			}
		}
		return null;
	}
	
	public List<PlayerManager> playersList(){
		return this.turnManager.getPlayersList();
	}
	
	/*
	 * Getters reachable by PlayerManager by just putting ID
	 */
	public String getName(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.name.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found"); 
		}
	}
	
	public Double getBalance(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.balance.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found"); 
		}
	}

	public int getPosition(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.position.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found"); 
		}
	}

	public monoopoly.utilities.States getState(final int ID){
		if (this.name.keySet().contains(ID)) {
			return this.state.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found"); 
		}
	}

	public PlayerManager passPlayer() {
		return this.turnManager.nextTurn();
	}
	
	public Table getTable() {
		return this.table;
	}

	@Override
	public void updateDices(Map<Integer, Integer> dices) {
		this.dices = dices;
	}

	@Override
	public Map<Integer, Integer> getDices() {
		return this.dices;
	}

}
