package monoopoly.model;

import java.util.List;

import monoopoly.model.item.Property;

/**
 *	This interface represents a trade between two players
 */
public interface Trade {
	/**
	 * Returns the player proposing the trade.
	 * @return
	 */
	Player getPlayerOne();
	
	/**
	 *  Returns the player considering the trade.
	 * @return
	 */
	Player getPlayerTwo();
	
	/**
	 * Returns what the first player proposes, in properties.
	 * @return the list of offered properties.
	 */
	List<Property> getPlayerOneTradeProperty();
	
	/**
	 * Returns what the first player wants, in properties.
	 * @return the list of wanted properties.
	 */
	List<Property> getPlayerTwoTradeProperty();
	
	/**
	 * Returns what the first player offers, in money.
	 * @return the offered money.
	 */
	Integer getPlayerOneTradeMoney();
	
	/**
	 * Return the expected money in return.
	 * @return the expected money.
	 */
	Integer getPlayerTwoTradeMoney();
}
