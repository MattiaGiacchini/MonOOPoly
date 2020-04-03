package monoopoly.starting_stuff;

import java.util.*;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;

public class GameEngineImpl implements GameEngine {
	
	/**
	 * creating a map for each credential you need, reachable by player's ID
	 */
	private Map<Integer, String> name = new HashMap<>(); 
	
	private Map<Integer, Double> balance = new HashMap<>(); 

	private Map<Integer, Integer> position = new HashMap<>(); 

	private Map<Integer, monoopoly.utilities.States> state = new HashMap<>();
	
	private List<PlayerManager> playersList = new ArrayList<>();
	
	/**
	 * constructor, so that when StartGame creates GameEngine, it passes
	 * every player's credentials 
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
		//TODO (Table doesn't exist yet)
	}
	
	public PlayerManager createPlayer(final int number) {
		return new PlayerManagerImpl(number);
	}
	
	public void createPlayers() {
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while(it.hasNext()) {
			this.createPlayer(it.next().getKey());
			this.playersList.add(this.createPlayer(it.next().getKey()));
		}
	}
	
	public PlayerManager currentPlayer() {
		//TODO
		return null;
	}
	
	public List<PlayerManager> playerList(){
		return this.playersList;
	}
	
	/*
	 * Getters reachable by PlayerManager by just putting ID
	 */
	public String getName(final int ID) {
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while(it.hasNext()) {
			if(it.next().getKey() == ID) {
				return it.next().getValue();
			}
		}
		return null;
	}
	
	public Double getBalance(final int ID) {
		Iterator<Map.Entry<Integer, Double>> it = balance.entrySet().iterator();
		while(it.hasNext()) {
			if(it.next().getKey() == ID) {
				return it.next().getValue();
			}
		}
		return null;
	}

	public int getPosition(final int ID) {
		Iterator<Map.Entry<Integer, Integer>> it = position.entrySet().iterator();
		while(it.hasNext()) {
			if(it.next().getKey() == ID) {
				return it.next().getValue();
			}
		}
		return -1;
	}

	public monoopoly.utilities.States getState(final int ID){
		Iterator<Map.Entry<Integer, monoopoly.utilities.States>> it = state.entrySet().iterator();
		while(it.hasNext()) {
			if(it.next().getKey() == ID) {
				return it.next().getValue();
			}
		}
		return null;
	}
}
