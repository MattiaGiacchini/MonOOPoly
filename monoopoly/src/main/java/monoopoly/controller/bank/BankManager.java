package monoopoly.controller.bank;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.Bank;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;

/**
 *	This interface allows the game to manage a {@link Player}
 */
public interface BankManager {
	
	/**
	 * Gives money to a {@link Player}.
	 * @param toGive the money given.
	 * @param player the player receiving money.
	 */
	void giveMoney(double toGive, PlayerManager player);
	
	/**
	 * Assign a house to a {@link Property}.
	 * An eventual hotel is intended as the fifth house in the property.
	 * @param property the property in which is building.
	 */
	void assignHouse(Tile property, PlayerManager player);
	
	/**
	 * This mortgages a {@link Purchasable}.
	 * @param property the purchasable.
	 * @param player the player.
	 */
	void mortgageProperty(Tile property, PlayerManager player);
	
	/**
	 * this unmortgages a {@link Purchasable}, and gives back the money.
	 * @param property the purchasable
	 * @param player the player.
	 */
	void unmortgageProperty(Tile property, PlayerManager player);
	
	/**
	 *  This buys a {@link Purchasable}.
	 * @param property	the purchasable to be bought.
	 * @param player the player buying.	
	 *
	 */
	void buyProperty(Tile property, PlayerManager player);
	
	/**
	 * This sells an house in a {@link Property}.
	 * @param property the property.
	 * @param player the player.
	 */
	void sellHouse(Tile property, PlayerManager player);
	
	
	/**
	 * 
	 * @return the bank.
	 */
	Bank getBank();
	
	/**
	 * This method removes from all the assigned or mortgaged properties from a {@link Player}.
	 * @param player {@link Player}'s {@link PlayerManager}.
	 */
	void removeAssignmentsFromPlayer(PlayerManager player);
}
