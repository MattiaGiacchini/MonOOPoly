package monoopoly.controller.dices;

import java.util.Map;

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
	 * @param playerManager the manager of the player.
	 * @param table the table.
	 */
	void roll(PlayerManager playerManager, Table table);
	
	/**
	 * This method changes the player who's currently playing
	 * @param playerManager the player manager.
	 */
	void setCurrentPlayer(PlayerManager playerManager);
	
	/**
	 * 
	 * @return the dices.
	 */
	public Map<Integer, Integer> getDices();
	
	/**
	 * This method resets the dices (aka, empties the map).
	 */
	void resetDices();
}
