package monoopoly.model.item;


/**
 *	This interface represents a generic
 * 	Table's tile.
 */
public interface Tile {
	/**
	 *  This is method is used to understand if the 
	 *  Tile is purchasable
	 *  @return true if tile is purchasable, false if not
	 */
	public boolean isPurchasable();
	
	/**
	 *  this method is used to know the name of Tile
	 * 
	 * @return name of tile
	 */
	public String getName();
}
