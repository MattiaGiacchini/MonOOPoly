package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.table.tile.purchasable.Purchasable;

/**
 * This interface builds a {@link Trade}, with different fields.
 */
public interface TradeBuilder {
    /**
     * This sets the {@link PlayerManager} proposing the {@link Trade}. This is obligatory.
     * @param player the player.
     * @return the builder.
     */
    TradeBuilder playerOne(PlayerManager player);

    /**
     * This sets the {@link PlayerManager} that has to accept the {@link Trade}. This is obligatory.
     * @param player the player.
     * @return the builder.
     */
    TradeBuilder playerTwo(PlayerManager player);

    /**
     * This sets what player one offers, in {@link Purchasable}.
     * @param properties the list of purchasables.
     * @return the builder.
     */
    TradeBuilder playerOneProperties(Set<Purchasable> properties);

    /**
     * This sets what is wanted back, in {@link Purchasable}.
     * @param properties the list of purchasables.
     * @return the builder.
     */
    TradeBuilder playerTwoProperties(Set<Purchasable> properties);

    /**
     * This sets what player one offers, in money.
     * @param money the money offered.
     * @return the builder.
     */
    TradeBuilder playerOneMoney(double money);

    /**
     * This sets what is wanted back, in money.
     * @param money the money wanted back.
     * @return the builder.
     */
    TradeBuilder playerTwoMoney(double money);

    /**
     * Builds the {@link Trade}.
     * @return the trade.
     */
    TradeImpl build();
}
