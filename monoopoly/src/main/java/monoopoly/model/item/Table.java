package monoopoly.model.item;

import java.util.List;
import java.util.Optional;
import monoopoly.model.item.Purchasable;
import monoopoly.utilities.PurchasableCategory;

/**
 *	This interface represents the monopoly's board-game, 
 *  through it is possible interact with the different 
 *  tiles of the map.
 */
public interface Table {

	/**
	 * This method returns the current diceSum
	 * @return the sum.
	 */
	public int currentDices();
	/**
	 * This method is used to notify table to the last 
	 * dices throws
	 * 
	 * @param sum The sum of the dices.
	 */
	public void notifyDices(final int sum);	 
	
	/**
	 *  this method sets a new quotation for a specific
	 *  category of purchasable tiles of the table
	 *  
	 *  @param 	category of purchasable tile to implement 
	 *  		the quotation
	 *   
	 *  @param	quotation the new value of percentage 
	 *  
	 *  @throws IllegalArgumentException if the parameters aren't
	 *  		as request
	 */
	public void setNewQuotationToSpecificPurchasableCategory(final PurchasableCategory category, 
															 final double quotation);

	/**
	 *  this method return the tile on position required
	 *  
	 *  @param 	position of tile required 
	 *  
	 *  @throws IndexOutOfBoundsException if the parameters is 
	 *  		an index out of the table's bounds
	 *  
	 *  @throws IllegalArgumentException if the parameters isn't
	 *  		an Integer Value
	 */
	public Tile getTile(Integer position);
	
	
	/**
	 *  this method return a list of purchasable tile for a specific
	 *  player Identify
	 *   
	 * @param idPlayer the number which you use to identify the player
	 * 
	 * @return a list of specific Purchasable tile owned by the player
	 * 
	 * @throws IllegalArgumentException if the parameter isn't an
	 * 		   Integer
	 */
	public List<Purchasable> getPurchasablesTilesforSpecificPlayer(final Integer idPlayer);
	
}
