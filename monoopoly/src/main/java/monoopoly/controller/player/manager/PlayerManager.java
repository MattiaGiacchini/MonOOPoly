package monoopoly.controller.player.manager;

import java.util.Set;

import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;
import monoopoly.model.trade.Trade;

/**
 * This interface is used to manage the {@link Player} associated to the
 * {@link PlayerManager}.
 *
 * Commands are discernible into sub-groups: game management, finance management
 * and estates management.
 *
 */
public interface PlayerManager {

    /**
     * Return the {@link PlayerManager} identifier.
     *
     * @return the {@link PlayerManager} identifier
     */
    int getPlayerManagerID();

    /**
     * Returns the {@link Player} associated to the {@link PlayerManager}.
     *
     * @return a {@link Player} instance
     */
    Player getPlayer();

    /**
     * This method sets the {@link Table} inside of the PlayerManager.
     * 
     * @param table Game table instance
     */
    void setTable(final Table table);

    /*
     * /** This method return a manager to manage the {@link Player}'s {@link
     * Purchasable}
     *
     * @return the {@link PlayerPropertyManager}
     */
    /* PlayerPropertyManager getPropertyManager(); */

    /**
     * Moves the {@link Player} forward or backward on the game board.
     *
     * @param steps number got from dices or card
     */
    void movePlayer(int steps);

    /**
     * Moves the {@link Player} to a precise position on the game board.
     *
     * @param position position on the board
     * @throws IllegalArgumentException if the position is out of the table bounds
     */
    void goToPosition(int position);

    /**
     * Let a {@link Player} surrender.All his properties are will be set as free
     * {@link Purchasable}.
     */
    void giveUp();

    /**
     * Deduct money to the {@link Player}'s balance.
     *
     * @param amount of money to withdraw from the balance
     */
    void payMoney(Double amount);

    /**
     * Add money to the {@link Player}'s balance.
     *
     * @param amount of money to add from the balance
     */
    void collectMoney(Double amount);

    /**
     * Creates the offerer {@link Player}'s offer for an exchange of
     * {@link Purchasable} and/or money.
     *
     * @param offererRealEstate {@link Purchasable} offered by the offerer
     *                          {@link Player}
     * @param offererMoney      amount of money offered by the offerer
     *                          {@link Player} in order to balance the offer value
     */
    void setOffererOffer(Set<Purchasable> offererRealEstate, Double offererMoney);

    /**
     * Creates the contractor {@link Player}'s request made by the offerer
     * {@link Player} for an exchange of {@link Purchasable} and/or money.
     *
     * @param contractor           the {@link Player} we want to exchange with
     * @param contractorRealEstate the contractor {@link Player}'s
     *                             {@link Purchasable}s to exchange and/or money
     * @param contractorMoney      additional money to give in order to balance the
     *                             offer value
     */
    void setContractorRequest(PlayerManager contractor, Set<Purchasable> contractorRealEstate, Double contractorMoney);

    /**
     * Creates an exchange of {@link Purchasable}s and eventually an addition of
     * money between two {@link Player}s called offerer and contractor.
     *
     * @return a {@link Trade} with offerer {@link Player} and contractor
     *         {@link Player}
     */
    Trade createTradeOffer();

    /**
     * Accepts the trade offer made by the offerer {@link Player}.
     */
    void acceptTrade();

    /**
     * Declines the trade offer made by the offerer {@link Player}.
     */
    void declineTrade();

    /**
     * This method modifies the existing trade setting new fields
     */
    void modifyTrade();

    /**
     * Updates the {@link Player}'s state setting it to "IN GAME"
     */
    void leavePrison();

    /**
     * Checks if the {@link Player} is or not in prison
     *
     * @return true if {@link Player} is in prison
     */
    boolean isInPrison();

    /**
     * This method returns the set of {@link Purchasable}s owned by the
     * {@link Player}
     *
     * @return the set of {@link Purchasable}s owned by the {@link Player}
     */
    Set<Purchasable> getProperties();

    /**
     * This method notifies to the {@link PlayerManager} a new turn.
     */
    void newTurn();

    /**
     * This method return the number of turns the {@link Player} is in prison.
     * 
     * @return the number of turn the {@link Player} stayed in prison.
     */
    int getPrisonTurnCounter();

    /**
     * This methods checks if the {@link Player} is eliminated or if he gave up.
     * 
     * @return true if the {@link Player} have been eliminated.
     */
    boolean isBroken();

    /**
     * This method resets the counter of the turn remained in prison;
     */
    void resetPrisonCounter();

    /**
     * This method updates the state of the {@link Player} to "PRISONED" and moves
     * the {@link Player} to the prison tile. If the {@link Player} has got the
     * "leave prison for free" card, it will be applied.
     */
    void goToPrison();

    /**
     * This method is used to know if the {@link Player} has payed the rent on the
     * current {@link Tile}.
     */
    void hasPayedRent();

}
