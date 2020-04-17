package monoopoly.model.item;

import java.util.Set;
import java.util.function.Predicate;

import monoopoly.model.item.Purchasable;

/**
 *	This interface represents the monopoly's board-game,
 *  through it is possible interact with the different
 *  tiles of the map.
 */
public interface Table {
	
	/**
	 * This method is used to notify table to the last
	 * dices throws
	 *
	 * @param sum The sum of the dices.
	 */
	public void notifyDices(final Integer sum);

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
	public void setNewQuotationToSpecificPurchasableCategory(final Tile.Category category,
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
	public Tile getTile(final Integer position);


	/**
	 *  this method return a list of purchasable tile for a specific
	 *  player Identify
	 *
	 * @param idPlayer the number which you use to identify the player
	 *
	 * @return a set of specific Purchasable tile owned by the player
	 * 		   (the set can be Empty)
	 *
	 * @throws IllegalArgumentException if the parameter isn't an
	 * 		   Integer
	 */
	public Set<Purchasable> getPurchasableTilesforSpecificPlayer(final Integer idPlayer);
	
	/**
	 * this method is used to get the number Tile inside the Table 
	 * 
	 * @return number of tile
	 */
	public Integer getTableSize();

	/**
	 * This method returns the current diceSum
	 * 
	 * @return the sum.
	 */
	public int getNotifiedDices();
	
	/**
	 * this method is used to know the value that should  
	 * retrieve a player when pass over the start Tile
	 * 
	 * @return the value to retrieve
	 */
	public double getValueToRetrieveFromStart();
	
	/**
	 * this method is used to know the Jail's Position
	 * 
	 * @return the Jail's Position
	 */
	public Integer getJailPosition();
	

	/**
	 * This method is used to receive back a Set of Specific tiles
	 * or an extension of them. The Set received will be of type 
	 * inferred as the elements inside of him   
	 * 
	 * @param <T> this generic is used to define the Type of Set 
	 * and the elements inside of his
	 * @param type is used to apply the cast on the filtered tiles
	 * @param filter this predicate is used to select a specific 
	 * set of tile which the same property
	 * @return	the {@link Set} of Tile Casted on Type Inferred.
	 * @throws ClassCastException when the type inferred isn't a
	 * superclass of the all tiles filtered
	 */
	public <T extends Tile> Set<T> getFilteredTiles(Class<T> type, Predicate<Tile> filter);
}
