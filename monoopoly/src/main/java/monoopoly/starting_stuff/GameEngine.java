package monoopoly.starting_stuff;

import java.util.*;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.player.Player;

public interface GameEngine {
	
	Table createTable();  //(I need Daniele to create Table interface)
	
	/**
	 * Creating a single player by passing ID
	 * 
	 * @param number as playerID
	 * @return a {@link PlayerManager}  
	 */
	PlayerManager createPlayer(final int number); 
	
	/**
	 * Creating all the players using all the IDs stored in maps
	 */
	void createPlayers();
	
	PlayerManager currentPlayer();
	
	/**
	 * @return a list of {@link PlayerManager} 
	 */
	List<PlayerManager> playerList();
	
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

}
