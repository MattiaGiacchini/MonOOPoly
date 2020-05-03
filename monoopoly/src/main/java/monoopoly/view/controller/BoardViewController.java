package monoopoly.view.controller;

import java.util.List;
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

	/**
	 * This method initializes the board with the tileNames passed as argument
	 *
	 * @param tileNames the names of all the board tiles
	 */
	void initializeBoard(List<String> tileNames);

}
