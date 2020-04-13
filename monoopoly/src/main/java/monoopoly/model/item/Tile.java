package monoopoly.model.item;

/**
 *	This interface represents a generic
 * 	Table's tile.
 */
public interface Tile {

	/**
	 *	This enum lists the possible Category of every Tile
	 */
	public enum Category {  
		START,	
		JAIL,
		GO_TO_JAIL,
		FREE_PARKING,
		BROWN,
		LIGHT_BLUE,
		PINK,
		ORANGE,
		RED,
		YELLOW,
		GREEN,
		BLUE,
		UNEXPECTED,
		PROBABILITY,
		DISASTER,
		SOCIETY,
		STATION;
	}

	/**
	 *  This method is used to know if the 
	 *  Tile is purchasable
	 *  
	 *  @return true if tile is purchasable, false if not
	 */
	public boolean isPurchasable();

	/**
	 *  this method is used to know if the 
	 *  Tile is a deck 
	 * 
	 * @return true if the tile is a deck
	 */
	public boolean isDeck();
	
	/**
	 *  this method is used to know if the
	 *  Category is Buildable 
	 * 
	 * @return true if the tile is a deck
	 */
	public boolean isBuildable();
	
	/**
	 *  this method is used to know the Tile's name
	 * 
	 * @return tile's name
	 */
	public String getName();

	/**
	 * This method is used to know which {@link Tile.Category}
	 * this Tile belongs to.
	 * 
	 * @return the specific {@link Tile.Category}
	 */
	public Tile.Category getCategory();
}
