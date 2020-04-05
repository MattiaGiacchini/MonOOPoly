package monoopoly.model.item;

/**
 *	This interface represents a generic Table's tile not purchasable.
 */
public interface UnPurchasable extends Tile {

	/**
	 *	This enum lists the possible Category of UnPurchasable Tile
	 */
	public enum Category {
		START,
		DECK,	
		JAIL,
		GO_TO_JAIL,
		FREE_PARKING;
	}
	
	/**
	 * This method is used to know which {@link UnPurchasable.Category}
	 * this Tile belongs to.
	 * 
	 * @return the specific {@link UnPurchasableCategory}
	 */
	public UnPurchasable.Category getCategory();
}
