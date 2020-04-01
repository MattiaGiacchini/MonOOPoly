package monoopoly.model.item;
import java.util.List;
import monoopoly.utilities.PurchasableCategory;

/**
 *	This interface represents the monopoly's board-game, 
 *  through it is possible interact with the different 
 *  tiles of the map.
 */
public interface Table {

	/**
	 *  this method sets a new quotation for a specific
	 *  category of purchasable tiles of the table
	 *  
	 *  @param 	category of purchasable tile to implement 
	 *  		the quotation
	 *   
	 *  @param	quotation the new value of percentage 
	 *  
	 *  @throws java.io.IOException
	 */
	public void setNewQuotationToSpecificPurchasableCategory
			   (final PurchasableCategory category, final double quotation);

	/**
	 *  this method return the tile on position required
	 *  
	 *  @param 	position of tile required 
	 *  
	 *  @throws java.io.IOException
	 */
	public Tile getTile(Integer position);
	
}
