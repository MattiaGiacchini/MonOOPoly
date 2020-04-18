package monoopoly.model.item;

import java.util.Set;

import monoopoly.model.item.Tile.Category;

/**
 * this Interface represent the Observer for Purchasable Tile 
 *
 */
public interface ObserverPurchasable {

	/**
	 * this method is used to retrieve a Set
	 * of Tile with a specific category  
	 * 
	 * @param category the specific category to retrieve
	 * @return the {@link Set} 
	 */
	public Set<Tile> getTilesforSpecificCategoty(Category category);
	
	/**
	 * This method returns the current diceSum
	 * 
	 * @return the sum.
	 */
	public int getNotifiedDices();
}
