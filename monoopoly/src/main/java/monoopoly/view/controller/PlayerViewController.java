package monoopoly.view.controller;

import java.util.Map;

public interface PlayerViewController {

	/**
	 * This method updates the balances of the players in the board view
	 *
	 * @param balances a map with all the players' balances
	 */
	void updateBalances(final Map<Integer, Double> balances);

	/**
	 * This method updates the current player info displayed in the board view
	 * 
	 * @param name    of the player actually in game
	 * @param balance of the player actually in game
	 */
	void updateCurrentPlayer(final String name, final Double balance);

	/**
	 * This method updates all the player's balances
	 * 
	 * @param positions a Map containing player identifier and its position on the
	 *                  board
	 */
	void updatePlayerPosition(final Map<Integer, Integer> positions);

	/**
	 * This method sets the player's names on the beginning of the game
	 *
	 * @param names a map of all the players' names
	 */
	void setPlayerNames(final Map<Integer, String> names);

}