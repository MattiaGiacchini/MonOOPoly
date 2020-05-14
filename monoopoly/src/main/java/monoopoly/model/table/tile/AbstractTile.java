package monoopoly.model.table.tile;

import java.util.Objects;

/**
 * this Abstract Class is used to implement the pattern
 * decorator for the Tiles.
 */
public abstract class AbstractTile implements Tile {

    private final Tile decorated;

    /**
     * Constructor of class {@link AbstractTile}.
     *
     * @param decorated Tile to decore
     * @throws NullPointerException if the decorated has null value
     */
    public AbstractTile(final Tile decorated) {
        super();
        Objects.requireNonNull(decorated,
                "Tile to decore cannot has null value");
        this.decorated = decorated;
    }

    @Override
    public final boolean isPurchasable() {
        return this.decorated.isPurchasable();
    }

    @Override
    public final boolean isDeck() {
        return this.decorated.isDeck();
    }

    @Override
    public final boolean isBuildable() {
        return this.decorated.isBuildable();
    }

    @Override
    public final String getName() {
        return this.decorated.getName();
    }

    @Override
    public final Category getCategory() {
        return this.decorated.getCategory();
    }

}
