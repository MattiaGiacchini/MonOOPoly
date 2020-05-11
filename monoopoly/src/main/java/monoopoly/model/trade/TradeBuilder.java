package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;
import monoopoly.model.player.Player;

/**
 * This interface builds a {@link Trade}, with different fields.
 */
public interface TradeBuilder {
	/**
	 * This sets the {@link Player} proposing the {@link Trade}. This is obligatory.
	 * @param player the player.
	 */
	TradeBuilder setPlayerOne(PlayerManager player);
	
	/**
	 * This sets the {@link Player} that has to accept the {@link Trade}. This is obligatory.
	 * @param player
	 */
	TradeBuilder setPlayerTwo(PlayerManager player);
	
	/**
	 * This sets what {@link Player} one offers, in {@link Purchasable}.
	 * @param properties the list of purchasables
	 */
	TradeBuilder setPlayerOneProperties(Set<Purchasable> properties);
	
	/**
	 * This sets what is wanted back, in {@link Purchasable}.
	 * @param properties the list of purchasables.
	 */
	TradeBuilder setPlayerTwoProperties(Set<Purchasable> properties);
	
	/**
	 * This sets what {@link Player} one offers, in money.
	 * @param money the money offered.
	 */
	TradeBuilder setPlayerOneMoney(double money);
	
	/**
	 * This sets what is wanted back, in money.
	 * @param money the money wanted back.
	 */
	TradeBuilder setPlayerTwoMoney(double money);
	
	/**
	 * Builds the {@link Trade}.
	 * @return the trade.
	 */
	TradeImpl build();
}
