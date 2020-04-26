package monoopoly.controller.dices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

public class DicesImpl implements Dices{

	private PlayerManager currentPlayer;
	private final Table gameTable;
	private Map<Integer, Integer> dices;
	private final Random random;
	private static final int numberOfDices = 2;
	private static final int RANDOM_DICE_BOUND = 5;
	private Set<DicesObserver> observers;
	/**
	 * constructor.
	 * @param number the number of dices.
	 * @param table the table.
	 */
	public DicesImpl(int number, Table table) {
		this.gameTable = table;
		this.dices = new HashMap<Integer, Integer>();
		this.random = new Random();
		this.observers = new HashSet<DicesObserver>();
	}
	
	@Override
	public void roll(PlayerManager playerManager, Table table) {
		for (int i = 0; i < DicesImpl.numberOfDices; i++) {
			this.dices.put(i, random.nextInt(RANDOM_DICE_BOUND) + 1);
		}
		//this.currentPlayer.setDices(dices);
		final int diceSum = this.dices.values().stream().reduce(0, Integer::sum);
		//TODO logica prigione
		/*this.currentPlayer.movePlayer(diceSum);
		this.gameTable.notifyDices(diceSum);*/
		this.notifyObservers(diceSum);
	}

	@Override
	public void setCurrentPlayer(PlayerManager playerManager) {
		this.currentPlayer = playerManager;
	}
	
	@Override
	public Map<Integer, Integer> getDices(){
		return Collections.unmodifiableMap(this.dices);
	}

	@Override
	public void resetDices() {
		this.dices.clear();
		
	}

	@Override
	public boolean areEquals() {
		if (this.dices.get(0).equals(this.dices.get(1))) {
			return true;
		}
		return false;
	}

	@Override
	public void attachObserver(DicesObserver obs) {
		this.observers.add(obs);
	}

	@Override
	public void removeObserver(DicesObserver obs) {
		this.observers.remove(obs);
	}
	
	private void notifyObservers(final int sum) {
		for (DicesObserver obs : this.observers) {
			obs.notifyNormalDices(sum);
		}
	}

	@Override
	public Set<DicesObserver> getObserverSet() {
		return Collections.unmodifiableSet(this.observers);
	}

}
