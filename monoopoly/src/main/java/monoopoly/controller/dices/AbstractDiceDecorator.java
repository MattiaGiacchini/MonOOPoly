package monoopoly.controller.dices;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

public abstract class AbstractDiceDecorator implements Dices {

	private Dices decorated;
	private Set<DicesObserver> observers;
	
	public AbstractDiceDecorator(Dices toDecorate) {
		this.decorated = toDecorate;
		this.observers = new HashSet<DicesObserver>();
	}
	
	public void roll() {
		this.decorated.roll();
	};

	@Override
	public Map<Integer, Integer> getDices() {
		return this.decorated.getDices();
	}

	@Override
	public void resetDices() {
		this.decorated.getDices();
	}

	@Override
	public boolean areEquals() {
		return this.decorated.areEquals();
	}

	@Override
	public void attachObserver(DicesObserver obs) {
		this.decorated.attachObserver(obs);
		this.observers.add(obs);
	}

	@Override
	public void removeObserver(DicesObserver obs) {
		this.decorated.removeObserver(obs);
	}
	
	@Override
	public Set<DicesObserver> getObserverSet(){
		return this.decorated.getObserverSet();
	}

}
