package monoopoly.controller.player_manager;

import java.util.List;

import monoopoly.model.player.Player;

/**
 * Interface used to handle turns
 */
public interface TurnManager {

	/**
	 * Used to handle turn passing
	 * 
	 *  @param a list of {@link PlayerManager} 
	 *  @return {@link PlayerManager} successive 
	 */
	PlayerManager nextTurn(final List<PlayerManager> playersList );
	
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
	Integer getNumberOfRound();
	
	public Integer getCurrentPlayer();
	
	public List<PlayerManager> getPlayersList();
}
