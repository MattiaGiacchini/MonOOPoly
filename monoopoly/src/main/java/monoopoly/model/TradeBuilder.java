package monoopoly.model;

import java.util.List;

/**
 * This interface builds a Trade, with different fields.
 */
public interface TradeBuilder {
	/**
	 * This sets the player proposing the trade. This is obligatory.
	 * @param player the player.
	 */
	void setPlayerOne(Player player);
	
	/**
	 * This sets the player that has to accept the trade. This is obligatory.
	 * @param player
	 */
	void setPlayerTwo(Player player);
	
	/**
	 * This sets what player one offers, in properties.
	 * @param properties the list of properties
	 */
	void setPlayerOneProperties(List<Property> properties);
	
	/**
	 * This sets what is wanted back, in properties.
	 * @param properties the list of properties.
	 */
	void setPlayerTwoProperties(List<Property> properties);
	
	/**
	 * This sets what player one offers, in money.
	 * @param money the money offered.
	 */
	void setPlayerOneMoney(int money);
	
	/**
	 * This sets what is wanted back, in money.
	 * @param money the money wanted back.
	 */
	void setPlayerTwoMoney(int money);
	
	/**
	 * Builds the trade.
	 * @return the trade.
	 */
	Trade build();
}
