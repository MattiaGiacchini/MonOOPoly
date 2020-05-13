package monoopoly.view.utilities;

/**
 * This enum represents the different tile categories for the view.
 */
public enum TileViewCategory {
    /**
     * The tile is a {@link Purchasable} property.
     */
    PROPERTY,

    /**
     * The tile is a {@link Station}.
     */
    STATION,

    /**
     * The tile is a {@link Society}.
     */
    SOCIETY,

    /**
     * The tile is a deck (probability, unexpected, calamity).
     */
    DECK,

    /**
     * The tile is a "special" one (start, prison, free parking, go to prison).
     */
    OTHER;
}
