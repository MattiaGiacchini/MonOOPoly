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
		//			 purch   deck  build  
		START		(false, false, false),	
		JAIL		(false, false, false),
		GO_TO_JAIL	(false, false, false),
		FREE_PARKING(false, false, false),
		BROWN		(true,  false, true ),
		LIGHT_BLUE	(true,  false, true ),
		PINK		(true,  false, true ),
		ORANGE		(true,  false, true ),
		RED			(true,  false, true ),
		YELLOW		(true,  false, true ),
		GREEN		(true,  false, true ),
		BLUE		(true,  false, true ),
		UNEXPECTED	(false, true,  false),
		PROBABILITY	(false, true,  false),
		DISASTER	(false, true,  false),
		SOCIETY		(true,  false, false),
		STATION		(true,  false, false);
		
		private final boolean purchasable;
		private final boolean deck;
		private final boolean buildable;
		
		private Category(final boolean purchasable, 
						 final boolean deck,
						 final boolean buildable) {
			this.purchasable = purchasable;
			this.deck = deck;
			this.buildable = buildable;
		}
		
		/**
		 *  This method is used to know if the 
		 *  Category represents a purchasable Tile
		 *  
		 *  @return true if tile is purchasable, false if not
		 */
		protected boolean isPurchasable() {
			return this.purchasable;
		}
		
		/**
		 *  this method is used to know if the 
		 *  Category represents a deck 
		 * 
		 * @return true if the tile is a deck
		 */
		protected boolean isDeck() {
			return this.deck;
		}
		
		/**
		 *  this method is used to know if the
		 *  Category represents a Property
		 * 
		 * @return true if the tile is a deck
		 */
		protected boolean isBuildable() {
			return this.buildable;
		}
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
