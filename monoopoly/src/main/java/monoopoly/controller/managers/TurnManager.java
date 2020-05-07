package monoopoly.controller.managers;

import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.player.Player;

/**
 * Interface used to handle turns
 */
public interface TurnManager {

	/**
	 * Used to handle turn passing
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
	
	public void setRound();
	
	public Integer getCurrentPlayer();
	
	public List<PlayerManager> getPlayersList();
	
	void setCurrentID(final Integer ID);
}
