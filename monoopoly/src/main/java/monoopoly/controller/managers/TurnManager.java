package monoopoly.controller.managers;

import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;

/**
 * Interface used to handle turns
 */
public interface TurnManager {

    /**
	 * Used to handle turn passing.
	 * 
	 *  @return {@link PlayerManager} successive 
	 */
	PlayerManager nextTurn();
	
	/**
	 * Lets you know if are there still players in game besides the current player
	 * 
	 * @return Boolean
	 */
	Boolean areThereOtherPlayersInGame();
	
	/**
	 * You can obtain the round number
	 * 
	 * @return Inter
	 */
	public Integer getRound();
	
	/**
	 * increments the round by one
	 */
	public void setRound();
	
	/**
	 * @return id of the current player
	 */
	public Integer getCurrentPlayer();
	
	/**
	 * @return the {@link List} of players
	 */
	public List<PlayerManager> getPlayersList();
	
	/**
	 * method used by GameEngine to set the first player (in the beginning)
	 * @param ID
	 */
	void setCurrentID(final Integer ID);
}
