package monoopoly.controller.trades;

import monoopoly.model.trade.Trade;

/**
 * This interfaces presents a trade, and allows the trade to be accepted.
 *
 */
public interface Trader {

	/**
	 * This return the trade in consideration.
	 * @return the trade.
	 */
	Trade getTrade();
	
	/**
	 * This accepts the trade, and makes the opportune swaps.
	 */
	void acceptTrade();
	
	/**
	 * This changes the trade inside the trader.
	 * @param trade	The trade.
	 */
	void changeTrade(Trade trade);
}
