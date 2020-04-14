package monoopoly.controller.bank;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.Bank;
import monoopoly.model.item.Tile;

/**
 *	This interface allows the game to manage a Player
 */
public interface BankManager {
	
	/**
	 * Gives money to a player.
	 * @param toGive the money given.
	 * @param player the player receiving money.
	 */
	void giveMoney(double toGive, PlayerManager player);
	
	/**
	 * Assign a house to a Property.
	 * An eventual hotel is intended as the fifth house in the property.
	 * @param property the property in which is building.
	 */
	void assignHouse(Tile property, PlayerManager player);
	
	/**
	 * This mortgages a property.
	 * @param property the property.
	 * @param player the player.
	 */
	void mortgageProperty(Tile property, PlayerManager player);
	
	/**
	 * this unmortgages a property, and gives back the money.
	 * @param property the property
	 * @param player the player.
	 */
	void unmortgageProperty(Tile property, PlayerManager player);
	
	/**
	 *  This buys a property.
	 * @param property	the property to be bought.
	 * @param player the player buying.	
	 *
	 */
	void buyProperty(Tile property, PlayerManager player);
	
	/**
	 * This sells an house in a property.
	 * @param property the property.
	 * @param player the player.
	 */
	void sellHouse(Tile property, PlayerManager player);
	
	
	/**
	 * 
	 * @return the bank.
	 */
	Bank getBank();
}
