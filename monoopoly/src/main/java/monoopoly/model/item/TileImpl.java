package monoopoly.model.item;

public class TileImpl implements Tile {

	private final String name;
	private final Category category;
	private final boolean deck;
	private final boolean purchasable;
	private final boolean buildable;

	public static class Builder{
		
		private String name;
		private Category category;
		private boolean deck;
		private boolean deckSetted;
		private boolean purchasable;
		private boolean purchasableSetted;
		private boolean buildable;
		private boolean buildableSetted;
		
		public Builder(){}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder category(Category category) {
			this.category = category;
			return this;
		}

		public Builder purchasable(boolean purchasable) {
			this.purchasable = purchasable;
			this.purchasableSetted = true;
			return this;
		}

		public Builder deck(boolean deck) {
			this.deck = deck;
			this.deckSetted = true;
			return this;
		}

		public Builder buildable(boolean buildable) {
			this.buildable = buildable;
			this.buildableSetted = true;
			return this;
		}
		
		public Tile build() throws IllegalStateException{
			if(this.name == null || 
			   this.category == null ||
			  !this.purchasableSetted || 
			  !this.deckSetted ||
			  !this.buildableSetted) {
				throw new IllegalStateException("Sequence build error");
			}
			return new TileImpl(this);
		}

	}

	private TileImpl(Builder builder){
		super();
		this.name 		 	= builder.name;
		this.category 	 	= builder.category;
		this.deck 			= builder.deck;
		this.purchasable 	= builder.purchasable;
		this.buildable 		= builder.buildable;
	}
	
	@Override
	public boolean isPurchasable() {
		return this.purchasable;
	}

	@Override
	public boolean isDeck() {
		return this.deck;
	}

	@Override
	public boolean isBuildable() {
		return this.buildable;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Category getCategory() {
		return this.category;
	}

}
