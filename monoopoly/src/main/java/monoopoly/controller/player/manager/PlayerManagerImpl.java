package monoopoly.controller.player.manager;

import java.util.Optional;
import java.util.Set;

import monoopoly.controller.trades.Trader;
import monoopoly.model.trade.*;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;
import monoopoly.utilities.States;

/**
 * This class represents one of the playerManagers which will manage a single
 * {@link Player}
 */
public class PlayerManagerImpl implements PlayerManager {

    private static final int TURN_IN_PRISON = 3;

    private final int playerManagerID;
    private Player player;
    private PlayerBalanceManager balanceManager = new PlayerBalanceManagerImpl();

    private TradeBuilder tradeBuilder;
    private Trader trader;
    private Table table;

    private int prisonTurnCounter = 0;

    /**
     * This constructor creates an instance of {@link PlayerManager}
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

    @Override
    public int getPlayerManagerID() {
        return this.playerManagerID;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public int getPrisonTurnCounter() {
        return this.prisonTurnCounter;
    }

    @Override
    public void setTable(final Table table) {
        this.table = table;
    }

    @Override
    public void movePlayer(int steps) {
        if (!this.isInPrison()) {
            this.leavePrison();
            this.goToPosition(this.nextPosition(steps));
        }
    }

    @Override
    public void goToPosition(int position) {
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

    @Override
    public void giveUp() {
        this.player.setState(States.BROKE);
    }

    @Override
    public void payMoney(Double amount) {
        this.balanceManager.updateBalance(this.player, -amount);
    }

    @Override
    public void collectMoney(Double amount) {
        this.balanceManager.updateBalance(this.player, amount);
    }

    @Override
    public Trade createTradeOffer() {
        return this.tradeBuilder.build();
    }

    @Override
    public void acceptTrade() {
        this.trader.acceptTrade();
    }

    @Override
    public void declineTrade() {
        this.trader.changeTrade(Optional.empty());
    }

    @Override
    public void setOffererOffer(Set<Purchasable> offererRealEstate, Double offererMoney) {
        this.tradeBuilder.setPlayerOne(this);
        this.tradeBuilder.setPlayerOneProperties(offererRealEstate);
        this.tradeBuilder.setPlayerOneMoney(offererMoney);
    }

    @Override
    public void setContractorRequest(PlayerManager contractor, Set<Purchasable> contractorRealEstate,
            Double contractorMoney) {
        this.tradeBuilder.setPlayerTwo(contractor);
        this.tradeBuilder.setPlayerTwoProperties(contractorRealEstate);
        this.tradeBuilder.setPlayerTwoMoney(contractorMoney);
    }

    @Override
    public void leavePrison() {
        this.player.setState(States.IN_GAME);
    }

    @Override
    public boolean isInPrison() {
        return this.player.getState().equals(States.PRISONED);
    }

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
    private int nextPosition(int steps) {
        return this.checkOutOfBoard(this.player.getPosition() + steps);
    }

    /**
     * This private method checks if the {@link Player} is going to exit from the
     * Table bounds. In this case the method adjusts the position.
     *
     * @param position where the {@link Player} should be
     * @return the right {@link Player}'s position
     */
    private int checkOutOfBoard(int position) {
        if (position >= this.table.getTableSize()) {
            return position = position - this.table.getTableSize();
        } else if (position < 0) {
            return position + this.table.getTableSize();
        } else {
            return position;
        }
    }

    /**
     * This private method checks if the {@link Player} has to go to the jail
     *
     * @param position to check
     * @return true if i need to go to the jail
     */
    private boolean checkGoToJail(int position) {
        return this.table.getTile(position).getCategory().equals(Tile.Category.GO_TO_JAIL);
    }

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

    @Override
    public Set<Purchasable> getProperties() {
        return this.table.getPurchasableTilesforSpecificPlayer(this.playerManagerID);
    }

    @Override
    public void newTurn() {
        if (this.isInPrison()) {
            this.prisonTurnCounter = this.prisonTurnCounter + 1;
            if (this.prisonTurnCounter >= TURN_IN_PRISON) {
                this.leavePrison();
            }
        }
    }

    @Override
    public void resetPrisonCounter() {
        this.prisonTurnCounter = 0;
    }

}
