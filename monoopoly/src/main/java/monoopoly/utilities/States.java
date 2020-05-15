package monoopoly.utilities;

/**
 * This enum lists the possible states in which a player can be during a game.
 */
public enum States {
    /**
     * This state means that the {@link Player} is in game.
     */
    IN_GAME,

    /**
     * This state means that the {@link Player} is in prison.
     */
    PRISONED,

    /**
     * This state means that the {@link Player} has payed the rent for the current
     * turn.
     */
    HAS_PAYED_RENT,

    /**
     * This state means that the {@link Player} have been eliminated.
     */
    BROKE;
}
