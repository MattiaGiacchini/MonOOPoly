package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;

/**
 * This interface builds a Trade, with different fields.
 */
public interface TradeBuilder {
	/**
	 * This sets the player proposing the trade. This is obligatory.
	 * @param player the player.
	 */
	TradeBuilder setPlayerOne(PlayerManager player);
	
	/**
	 * This sets the player that has to accept the trade. This is obligatory.
	 * @param player
	 */
	TradeBuilder setPlayerTwo(PlayerManager player);
	
	/**
	 * This sets what player one offers, in properties.
	 * @param properties the list of properties
	 */
	TradeBuilder setPlayerOneProperties(Set<Purchasable> properties);
	
	/**
	 * This sets what is wanted back, in properties.
	 * @param properties the list of properties.
	 */
	TradeBuilder setPlayerTwoProperties(Set<Purchasable> properties);
	
	/**
	 * This sets what player one offers, in money.
	 * @param money the money offered.
	 */
	TradeBuilder setPlayerOneMoney(double money);
	
	/**
	 * This sets what is wanted back, in money.
	 * @param money the money wanted back.
	 */
	TradeBuilder setPlayerTwoMoney(double money);
	
	/**
	 * Builds the trade.
	 * @return the trade.
	 */
	TradeImpl build();
}
