package monoopoly.controller.player_manager;

import monoopoly.model.player.Player;

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
	 * Returns the player associated to the {@link PlayerManager}
	 * 
	 * @return a {@link Player} instance
	 */
	public Player getPlayer();

	/**
	 * Moves the {@link Player} forward or backward on the game board
	 * 
	 * @param steps number got from dices or card
	 */
	public void movePlayer(int steps);

	/**
	 * Moves the {@link Player} to a precise position on the game board
	 * 
	 * @param position position on the board
	 */
	public void goToPosition(int position);

	/**
	 * Let a {@link Player} surrender his game TODO all his money and properties
	 * will go to the {@link Bank}
	 */
	public void giveUp();

	/**
	 * Allows the player to build a house or a hotel on the chosen property
	 * 
	 * @param property where to build on a house or a hotel
	 */
	public void buildHouse(Property property);

	/**
	 * Allows the player to sell a house or a hotel from the chosen property
	 * 
	 * @param property where to sell a house or a hotel
	 */
	public void sellHouse(Property property);

	/**
	 * Allows the {@link Player} to buy a specific {@link Purchasable}
	 * 
	 * @param purchasableTile table tile marketable to buy
	 */
	public void buyPurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to sell a specific {@link Purchasable} if has no
	 * houses built on it
	 * 
	 * @param purchasableTile table tile marketable to sell
	 */
	public void sellPurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to mortgage a specific {@link Purchasable} if has
	 * no houses built on it
	 * 
	 * @param purchasableTile table tile marketable to mortgage
	 */
	public void mortgagePurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to remove the mortgage put on one of his specific
	 * {@link Purchasable}
	 * 
	 * @param purchasableTile table tile marketable to be released from mortgage
	 */
	public void unMortgagePurchasable(Purchasable purchasableTile);

	/**
	 * Add money to the {@link Player}'s balance
	 * 
	 * @param amount of money to add to the balance
	 */
	public void payMoney(Double amount);

	/**
	 * Add money to the {@link Player}'s balance
	 * 
	 * @param amount of money to deduct from the balance
	 */
	public void collectMoney(Double amount);

}
