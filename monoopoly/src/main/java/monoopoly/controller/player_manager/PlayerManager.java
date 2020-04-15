package monoopoly.controller.player_manager;

import java.util.Set;

import monoopoly.model.item.Purchasable;
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
	public int getPlayerManagerID();

	/**
	 * Returns the {@link Player} associated to the {@link PlayerManager}.
	 *
	 * @return a {@link Player} instance
	 */
	public Player getPlayer();

	/*/**
	 * This method return a manager to manage the {@link Player}'s
	 * {@link Purchasable}
	 *
	 * @return the {@link PlayerPropertyManager}
	 */
	/*public PlayerPropertyManager getPropertyManager();*/

	/**
	 * Moves the {@link Player} forward or backward on the game board.
	 *
	 * @param steps number got from dices or card
	 */
	public void movePlayer(int steps);

	/**
	 * Moves the {@link Player} to a precise position on the game board.
	 *
	 * @param position position on the board
	 * @throws IllegalArgumentException if the position is out of the table bounds
	 */
	public void goToPosition(int position);

	/**
	 * Let a {@link Player} surrender his game TODO all his money and properties
	 * will go to the {@link Bank}.
	 */
	public void giveUp();

	/**
	 * Deduct money to the {@link Player}'s balance.
	 *
	 * @param amount of money to withdraw from the balance
	 */
	public void payMoney(Double amount);

	/**
	 * Add money to the {@link Player}'s balance.
	 *
	 * @param amount of money to add from the balance
	 */
	public void collectMoney(Double amount);

	/**
	 * Creates the offerer {@link Player}'s offer for an exchange of
	 * {@link Purchasable} and/or money.
	 *
	 * @param offererRealEstate {@link Purchasable} offered by the offerer
	 *                          {@link Player}
	 * @param offererMoney      amount of money offered by the offerer
	 *                          {@link Player} in order to balance the offer value
	 */
	public void setOffererOffer(Set<Purchasable> offererRealEstate, Double offererMoney);

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
	public void setContractorRequest(PlayerManager contractor, Set<Purchasable> contractorRealEstate,
			Double contractorMoney);

	/**
	 * Creates an exchange of {@link Purchasable}s and eventually an addition of
	 * money between two {@link Player}s called offerer and contractor.
	 *
	 * @return a {@link Trade} with offerer {@link Player} and contractor
	 *         {@link Player}
	 */
	public Trade createTradeOffer();

	/**
	 * Accepts the trade offer made by the offerer {@link Player}.
	 */
	public void acceptTrade();

	/**
	 * Declines the trade offer made by the offerer {@link Player}.
	 */
	public void declineTrade();

	/**
	 * This method modifies the existing trade setting new fields
	 */
	public void modifyTrade();

	/**
	 * Updates the {@link Player}'s state setting it to "IN GAME"
	 */
	public void leavePrison();

	/**
	 * Checks if the {@link Player} is or not in prison
	 *
	 * @return true if {@link Player} is in prison
	 */
	public boolean isInPrison();
	
	/**
	 * 
	 * @return the set of {@link Purchasable}s owned by the player.
	 */
	public Set<Purchasable> getProperties();

}
