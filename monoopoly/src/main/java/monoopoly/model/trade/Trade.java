package monoopoly.model.trade;


import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Purchasable;

/**
 *	This interface represents a trade between two players
 */
public interface Trade {
	/**
	 * Returns the player proposing the trade.
	 * @return
	 */
	PlayerManager getPlayerOne();
	
	/**
	 *  Returns the player considering the trade.
	 * @return
	 */
	PlayerManager getPlayerTwo();
	
	/**
	 * Returns what the first player proposes, in properties.
	 * @return the list of offered properties.
	 */
	Set<Purchasable> getPlayerOneTradeProperty();
	
	/**
	 * Returns what the first player wants, in properties.
	 * @return the list of wanted properties.
	 */
	Set<Purchasable> getPlayerTwoTradeProperty();
	
	/**
	 * Returns what the first player offers, in money.
	 * @return the offered money.
	 */
	double getPlayerOneTradeMoney();
	
	/**
	 * Return the expected money in return.
	 * @return the expected money.
	 */
	double getPlayerTwoTradeMoney();
}
