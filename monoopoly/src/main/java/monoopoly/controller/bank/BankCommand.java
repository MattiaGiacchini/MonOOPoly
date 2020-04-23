package monoopoly.controller.bank;

import monoopoly.model.Bank;
import monoopoly.model.item.Purchasable;

/**
 *	This interface represents a generic command that can be executed by the bank.
 */
public interface BankCommand {
	/**
	 *  This method allows the bank to execute the specific command.
	 * 	@param bank the bank.
	 */
	void execute();
	
	/*
	/**
	 * This method assigns an house to the tile.
	 */
	/*void assignHouse();
	
	/**
	 * This method mortages the property. Launches an {@link IllegalArgumentException} if the property
	 * isn't a {@link Purchasable}.
	 */
	/*void mortgageProperty();
	
	/**
	 * This method unmortages the property. Launches an {@link IllegalArgumentException} if the property
	 * isn't a {@link Purchasable}.
	 */
	/*void umortgageProperty();
	
	/**
	 * This method buys the property. Launches an {@link IllegalArgumentException} if the property
	 * isn't a {@link Purchasable}.
	 */
	/*void buyProperty();
	
	/**
	 * This method sells an house in the property.
	 */
	/*void sellHouse();*/
}
