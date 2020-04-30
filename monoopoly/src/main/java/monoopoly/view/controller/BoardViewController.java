package monoopoly.view.controller;

import java.util.Map;

/**
 * This interface represents the view controller for the game board
 */
public interface BoardViewController {

	/**
	 * This method updates the player positions on the board
	 * 
	 * @param positions players positions map
	 */
	public void updatePlayerPositions(final Map<Integer, Integer> positions);

}
