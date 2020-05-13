package monoopoly.view.utilities;

/**
 * This enum represents the possible states.
 */
public enum PurchasableState {
    /**
     * The {@link Purchasable} has no owner.
     */
    FREE_PROPERTY,

    /**
     * The {@link Purchasable} is owned by a different {@link Player} then the
     * current.
     */
    OWNED_PROPERTY,

    /**
     * The {@link Purchasable} is owned by the current {@link Player}.
     */
    MY_PROPERTY,

    /**
     * The tile is not a purchasable.
     */
    OTHER;
}
