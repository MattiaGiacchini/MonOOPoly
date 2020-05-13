package monoopoly.controller.player.manager;

import java.util.Optional;
import java.util.Set;

import monoopoly.controller.trades.Trader;
import monoopoly.controller.trades.TraderImpl;
import monoopoly.model.item.Table;
import monoopoly.model.item.tile.Tile;
import monoopoly.model.item.tile.purchasable.Purchasable;
import monoopoly.model.player.Player;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.utilities.States;

/**
 * This class represents one of the playerManagers which will manage a single
 * {@link Player}.
 */
public class PlayerManagerImpl implements PlayerManager {

    private static final int TURN_IN_PRISON = 3;

    private final int playerManagerID;
    private Player player;
    private final PlayerBalanceManager balanceManager = new PlayerBalanceManagerImpl();

    private final TradeBuilder tradeBuilder = new TradeBuilderImpl();
    private final Trader trader = new TraderImpl();
    private Table table;

    private int prisonTurnCounter;

    /**
     * This constructor creates an instance of {@link PlayerManager}.
     *
     * @param playerManagerID manager ID
     * @param player          's ID
     * @throws Exception if IDs are different
     */
    public PlayerManagerImpl(final int playerManagerID, final Player player) {
        if (playerManagerID == player.getID()) {
            this.player = player;
            this.playerManagerID = playerManagerID;
        } else {
            throw new IllegalStateException("Player and manager's IDs are different");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerManagerID() {
        return this.playerManagerID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrisonTurnCounter() {
        return this.prisonTurnCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTable(final Table table) {
        this.table = table;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePlayer(final int steps) {
        if (!this.isInPrison()) {
            this.leavePrison();
            this.goToPosition(this.nextPosition(steps));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToPosition(final int position) {
        this.player.setState(States.IN_GAME);
        if (position < this.table.getTableSize() && position >= 0) {
            if (!this.isInPrison()) {
                if (this.checkGoToJail(position)) {
                    this.goToPrison();
                } else {
                    this.player.setPosition(position);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveUp() {
        this.player.setState(States.BROKE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void payMoney(final Double amount) {
        this.balanceManager.updateBalance(this.player, -amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collectMoney(final Double amount) {
        this.balanceManager.updateBalance(this.player, amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trade createTradeOffer() {
        return this.tradeBuilder.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptTrade() {
        this.trader.acceptTrade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void declineTrade() {
        this.trader.changeTrade(Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffererOffer(final Set<Purchasable> offererRealEstate, final Double offererMoney) {
        this.tradeBuilder.playerOne(this);
        this.tradeBuilder.playerOneProperties(offererRealEstate);
        this.tradeBuilder.playerOneMoney(offererMoney);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContractorRequest(final PlayerManager contractor, final Set<Purchasable> contractorRealEstate,
            final Double contractorMoney) {
        this.tradeBuilder.playerTwo(contractor);
        this.tradeBuilder.playerTwoProperties(contractorRealEstate);
        this.tradeBuilder.playerTwoMoney(contractorMoney);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leavePrison() {
        this.player.setState(States.IN_GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInPrison() {
        return this.player.getState().equals(States.PRISONED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBroken() {
        return this.player.getState().equals(States.BROKE);
    }

    /**
     * This private method return the right position where the {@link Player} is
     * going to move to.
     *
     * @param steps number of steps the {@link Player} has to do on the board
     * @return the position where the {@link Player} is going to go
     */
    private int nextPosition(final int steps) {
        return this.checkOutOfBoard(this.player.getPosition() + steps);
    }

    /**
     * This private method checks if the {@link Player} is going to exit from the
     * Table bounds. In this case the method adjusts the position.
     *
     * @param position where the {@link Player} should be
     * @return the right {@link Player}'s position
     */
    private int checkOutOfBoard(final int position) {
        if (position >= this.table.getTableSize()) {
            return position - this.table.getTableSize();

        } else if (position < 0) {
            return position + this.table.getTableSize();
        } else {
            return position;
        }
    }

    /**
     * This private method checks if the {@link Player} has to go to the jail.
     *
     * @param position to check
     * @return true if i need to go to the jail
     */
    private boolean checkGoToJail(final int position) {
        return this.table.getTile(position).getCategory().equals(Tile.Category.GO_TO_JAIL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToPrison() {
        if (this.player.hasPrisonCard()) {
            this.player.setPrisonCard(false);
        } else {
            this.resetPrisonCounter();
            this.player.setState(States.PRISONED);
        }
        this.player.setPosition(this.table.getJailPosition());
    }

    @Override
    public void modifyTrade() {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Purchasable> getProperties() {
        return this.table.getPurchasableTilesforSpecificPlayer(this.playerManagerID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newTurn() {
        if (this.isInPrison()) {
            this.prisonTurnCounter = this.prisonTurnCounter + 1;
            if (this.prisonTurnCounter >= TURN_IN_PRISON) {
                this.leavePrison();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPrisonCounter() {
        this.prisonTurnCounter = 0;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void hasPayedRent() {
        this.player.setState(States.HAS_PAYED_RENT);
    }

}
