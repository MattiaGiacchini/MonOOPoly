package monoopoly.model.trade;


import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;

/**
 *    This interface represents a trade between two players.
 */
public interface Trade {
    /**
     * Returns the {@link PlayerManager} proposing the trade.
     * @return the player.
     */
    PlayerManager getPlayerOne();

    /**
     * Returns the {@link PlayerManager} considering the trade.
     * @return the player.
     */
    PlayerManager getPlayerTwo();

    /**
     * Returns what the first {@link PlayerManager} proposes, in {@link Purchasable}.
     * @return the list of offered purchasables.
     */
    Set<Purchasable> getPlayerOneTradeProperty();

    /**
     * Returns what the first {@link PlayerManager} wants, in {@link Purchasable}.
     * @return the list of wanted purchasables.
     */
    Set<Purchasable> getPlayerTwoTradeProperty();

    /**
     * Returns what the first {@link PlayerManager} offers, in money.
     * @return the offered money.
     */
    double getPlayerOneTradeMoney();

    /**
     * Return the expected money in return.
     * @return the expected money.
     */
    double getPlayerTwoTradeMoney();
}
