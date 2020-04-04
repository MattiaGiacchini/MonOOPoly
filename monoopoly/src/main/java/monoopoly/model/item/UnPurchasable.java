package monoopoly.model.item;

import monoopoly.utilities.UnPurchasableCategory;

/**
 *	This interface represents a generic Table's tile not purchasable.
 */
public interface UnPurchasable extends Tile {

	/**
	 * This method is used to know which {@link UnPurchasableCategory}
	 * this Tile belongs to.
	 * 
	 * @return the specific {@link UnPurchasableCategory}
	 */
	public UnPurchasableCategory getCategory();
}
