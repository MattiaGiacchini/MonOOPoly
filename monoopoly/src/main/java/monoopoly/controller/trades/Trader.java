package monoopoly.controller.trades;

import java.util.Optional;

import monoopoly.model.trade.Trade;

/**
 * This interfaces presents a {@link trade}, and allows the trade to be accepted.
 *
 */
public interface Trader {

    /**
     * This return the {@link trade} in consideration.
     * @return the trade.
     */
    Trade getTrade();

    /**
     * This accepts the {@link trade}, and makes the opportune swaps.
     */
    void acceptTrade();

    /**
     * This changes the {@link trade} inside the trader.
     * @param trade    The trade.
     */
    void changeTrade(Optional<Trade> trade);
}
