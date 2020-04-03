package monoopoly.controller.player_manager;

import java.util.List;
import java.util.Set;

import monoopoly.model.Trade;
import monoopoly.model.player.Player;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;

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
	 */
	public void goToPosition(int position);

	/**
	 * Let a {@link Player} surrender his game TODO all his money and properties
	 * will go to the {@link Bank}.
	 */
	public void giveUp();

	/**
	 * Allows the player to build a house or a hotel on the chosen property.
	 * 
	 * @param property where to build on a house or a hotel
	 */
	public void buildHouse(Property property);

	/**
	 * Allows the player to sell a house or a hotel from the chosen property.
	 * 
	 * @param property where to sell a house or a hotel
	 */
	public void sellHouse(Property property);

	/**
	 * Allows the {@link Player} to buy a specific {@link Purchasable}.
	 * 
	 * @param purchasableTile table tile marketable to buy
	 */
	public void buyPurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to sell a specific {@link Purchasable} if has no
	 * houses built on it.
	 * 
	 * @param purchasableTile table tile marketable to sell
	 */
	public void sellPurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to mortgage a specific {@link Purchasable} if has
	 * no houses built on it.
	 * 
	 * @param purchasableTile table tile marketable to mortgage
	 */
	public void mortgagePurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to remove the mortgage put on one of his specific
	 * {@link Purchasable}.
	 * 
	 * @param purchasableTile table tile marketable to be released from mortgage
	 */
	public void unMortgagePurchasable(Purchasable purchasableTile);

	/**
	 * Add money to the {@link Player}'s balance.
	 * 
	 * @param amount of money to add to the balance
	 */
	public void payMoney(Double amount);

	/**
	 * Add money to the {@link Player}'s balance.
	 * 
	 * @param amount of money to deduct from the balance
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
	public void setContractorRequest(Player contractor, Set<Purchasable> contractorRealEstate, Double contractorMoney);

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

}
