package monoopoly.controller.dices;

import java.util.Map;

import monoopoly.controller.player.manager.PlayerManager;

/**
 *	This interface represents the dices involved in a game of monoopoly.
 *	They can be two, if the game's classic, or three, if the rule "Speedy dice" is present.
 */
public interface Dices {

	/**
	 * This method rolls the dices, and notifies a {@link PlayerManager} and the {@link Table} that the player 
	 * must move.
	 * @param playerManager the manager of the player.
	 */
	void roll(PlayerManager playerManager);
	
	/**
	 * 
	 * @return the dices.
	 */
	Map<Integer, Integer> getDices();
	
	/**
	 * This method resets the dices (aka, empties the map).
	 */
	void resetDices();
	
	/**
	 * This method tells if the dices returned the same number.
	 * @return if the dices are equals.
	 */
	boolean areEquals();
}
