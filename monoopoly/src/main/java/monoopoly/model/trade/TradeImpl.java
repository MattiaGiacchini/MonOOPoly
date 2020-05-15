package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.table.tile.purchasable.Purchasable;
/**
 * Class that implements {@link Trade}.
 */
public class TradeImpl implements Trade {

    private final PlayerManager playerOne;
    private final PlayerManager playerTwo;
    private final Set<Purchasable> propertiesOne;
    private final Set<Purchasable> propertiesTwo;
    private final double moneyOne;
    private final double moneyTwo;
    /**
     * constructor.
     * @param playerOne player one.
     * @param playerTwo player two.
     * @param propertiesOne one's properties.
     * @param propertiesTwo two's properties.
     * @param moneyOne one's money.
     * @param moneyTwo two's money.
     */
    public TradeImpl(final PlayerManager playerOne, final PlayerManager playerTwo,
                final Set<Purchasable> propertiesOne, final Set<Purchasable> propertiesTwo,
                final double moneyOne, final double moneyTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo; 
        this.propertiesOne = propertiesOne;
        this.propertiesTwo = propertiesTwo;
        this.moneyOne = moneyOne;
        this.moneyTwo = moneyTwo;
    }

    @Override
    public final PlayerManager getPlayerOne() {
        return this.playerOne;
    }

    @Override
    public final PlayerManager getPlayerTwo() {
        return this.playerTwo;
    }

    @Override
    public final Set<Purchasable> getPlayerOneTradeProperty() {
        return this.propertiesOne;
    }

    @Override
    public final Set<Purchasable> getPlayerTwoTradeProperty() {
        return this.propertiesTwo;
    }

    @Override
    public final double getPlayerOneTradeMoney() {
        return this.moneyOne;
    }

    @Override
    public final double getPlayerTwoTradeMoney() {
        return this.moneyTwo;
    }

}
