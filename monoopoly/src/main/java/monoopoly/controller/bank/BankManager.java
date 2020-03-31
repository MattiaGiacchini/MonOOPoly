package monoopoly.controller.bank;

import monoopoly.model.Bank;

/**
 *	This interface allows the game to manage a Player
 */
public interface BankManager {
	
	/**
	 * Gives money to a player.
	 * @param toGive the money given.
	 * @param player the player receiving money.
	 */
	void giveMoney(int toGive, Player player);
	
	/**
	 * Assign a house to a Property.
	 * An eventual hotel is intended as the fifth house in the property.
	 * @param property the property in which is building.
	 */
	void assignHouse(Property property);
	
	/**
	 * This mortgages a property.
	 * @param property the property.
	 */
	void mortgageProperty(Property property);
	
	/**
	 * this unmortgages a property, and gives back the money.
	 * @param property the property
	 */
	void unmortgageProperty(Property property);
	
	/**
	 * 
	 * @return the bank.
	 */
	Bank getBank();
}
