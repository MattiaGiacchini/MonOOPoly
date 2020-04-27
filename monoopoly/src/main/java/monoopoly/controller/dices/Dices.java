package monoopoly.controller.dices;

import java.util.Map;
import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

/**
 *	This interface represents the dices involved in a game of monoopoly.
 *	They can be two, if the game's classic, or three, if the rule "Speedy dice" is present.
 */
public interface Dices {

	/**
	 * This method rolls the dices, and notifies a playerManager and the table that the player 
	 * must move.
	 */
	void roll();
	
	/**
	 * 
	 * @return the dices.
	 */
	public Map<Integer, Integer> getDices();
	
	/**
	 * This method resets the dices (aka, empties the map).
	 */
	void resetDices();
	
	/**
	 * This method tells if the dices returned the same number
	 * @return
	 */
	boolean areEquals();
	
	/**
	 * This method attaches an observer to the dices.
	 * @param obs The observer.
	 */
	void attachObserver(DicesObserver obs);
	
	/**
	 * This method removes an observer to the dices.
	 * @param obs The observer
	 */
	void removeObserver(DicesObserver obs);
	
	/**
	 * This method returns the set of observers.
	 * @return the set
	 */
	Set<DicesObserver> getObserverSet();
}
