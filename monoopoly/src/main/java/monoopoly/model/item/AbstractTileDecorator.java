package monoopoly.model.item;

public abstract class AbstractTileDecorator implements Tile {

	private final Tile decorated;

	public AbstractTileDecorator(Tile decorated) {
		super();
		this.decorated = decorated;
	}

	@Override
	public boolean isPurchasable() {
		return this.decorated.isPurchasable();
	}

	@Override
	public boolean isDeck() {
		return this.decorated.isDeck();
	}

	@Override
	public boolean isBuildable() {
		return this.decorated.isBuildable();
	}

	@Override
	public String getName() {
		return this.decorated.getName();
	}

	@Override
	public Category getCategory() {
		return this.decorated.getCategory();
	}

}
