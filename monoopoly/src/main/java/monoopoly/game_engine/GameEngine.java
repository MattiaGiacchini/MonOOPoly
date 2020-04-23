package monoopoly.game_engine;

import java.util.*;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;
import monoopoly.model.player.Player;

/**
 * Interface representing the brain behind 
 * every action.
 */
public interface GameEngine {
	
	/**
	 * method to create Table, monopoly's board-game
	 * 
	 * @return {@link Table}
	 */
	Table createTable();  
	
	/**
	 * Creating a single player by passing ID
	 * 
	 * @param number as playerID
	 * @return a {@link PlayerManager}  
	 */
	PlayerManager createPlayer(final int ID); 
	
	/**
	 * Creating all the players using all the IDs stored in maps
	 */
	void createPlayers();
	
	/**
	 * Method to track turn by turn the current player by watching it 
	 * into TurnManagerImpl.
	 * 
	 * @return {@link PlayerManager} that is the currently gaming player 
	 */
	PlayerManager currentPlayer();
	
	/**
	 * @return a list of {@link PlayerManager} 
	 */
	List<PlayerManager> playersList();
	
	/**
	 * helpful for getting player's name by putting ID
	 * @param ID
	 * @return String
	 */
	String getName(final int ID);
	
	/**
	 * helpful for getting player's balance by putting ID
	 * @param ID
	 * @return Double
	 */
	Double getBalance(final int ID);

	/**
	 * helpful for getting player's position by putting ID
	 * @param ID
	 * @return int
	 */
	int getPosition(final int ID);

	/**
	 * helpful for getting player's state by putting ID
	 * @param ID
	 * @return monoopoly.utilities.States
	 */
	monoopoly.utilities.States getState(final int ID);
	
	/**
	 * You can call this method to pass your turn
	 * @return successive {@link PlayerManager} 
	 */
	PlayerManager passPlayer();
	
	/**
	 * You can use this method to get the player with the higher balance
	 * @return {@link PlayerManager}
	 */
	PlayerManager getGameWinner();
	
	/**
	 * method to use the effect of a card
	 */
	void useCard();

}
