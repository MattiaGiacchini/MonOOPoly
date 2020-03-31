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
}
