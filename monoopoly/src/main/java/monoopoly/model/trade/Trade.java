package monoopoly.model.trade;


import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;
import monoopoly.model.player.Player;

/**
 *	This interface represents a trade between two players
 */
public interface Trade {
	/**
	 * Returns the {@link Player} proposing the trade.
	 * @return
	 */
	PlayerManager getPlayerOne();
	
	/**
	 * Returns the {@link Player} considering the trade.
	 * @return
	 */
	PlayerManager getPlayerTwo();
	
	/**
	 * Returns what the first {@link Player} proposes, in {@link Purchasable}.
	 * @return the list of offered purchasables.
	 */
	Set<Purchasable> getPlayerOneTradeProperty();
	
	/**
	 * Returns what the first {@link Player} wants, in {@link Purchasable}.
	 * @return the list of wanted purchasables.
	 */
	Set<Purchasable> getPlayerTwoTradeProperty();
	
	/**
	 * Returns what the first {@link Player} offers, in money.
	 * @return the offered money.
	 */
	double getPlayerOneTradeMoney();
	
	/**
	 * Return the expected money in return.
	 * @return the expected money.
	 */
	double getPlayerTwoTradeMoney();
}
