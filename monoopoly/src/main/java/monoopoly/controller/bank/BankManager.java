package monoopoly.controller.bank;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.Bank;
import monoopoly.model.item.Tile;

/**
 *	This interface allows the game to manage the bank.
 */
public interface BankManager {
	
	/**
	 * Gives money to a {@link PlayerManager}.
	 * @param toGive the money given.
	 * @param player the player receiving money.
	 */
	void giveMoney(double toGive, PlayerManager player);
	
	/**
	 * Assign a house to a {@link Property}.
	 * An eventual hotel is intended as the fifth house in the property.
	 * @param property the property in which is building.
	 * @param player the player.
	 */
	void assignHouse(Tile property, PlayerManager player);
	
	/**
	 * This mortgages a {@link Property}, society or a station.
	 * @param property the purchasable.
	 * @param player the player.
	 */
	void mortgageProperty(Tile property, PlayerManager player);
	
	/**
	 * this unmortgages a {@link Property}, society or a station, and gives back the money.
	 * @param property the purchasable
	 * @param player the player.
	 */
	void unmortgageProperty(Tile property, PlayerManager player);
	
	/**
	 *  This buys a {@link Property}, society or station.
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
	 * This method removes from all the assigned or mortgaged properties from a {@link PlayerManager}.
	 * @param player Player's {@link PlayerManager}.
	 */
	void removeAssignmentsFromPlayer(PlayerManager player);
}
