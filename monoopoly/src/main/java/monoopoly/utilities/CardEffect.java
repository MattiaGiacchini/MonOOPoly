package monoopoly.utilities;

/**
 * Enum representing the effect type of cards.
 */
public enum CardEffect {
    /**
     * Effect dealing with money exchange.
     */
    MONEY_EXCHANGE,
    /**
     * Effect that takes you to prison.
     */
    JAIL_IN,
    /**
     * Effect useful to go out from prison.
     */
    JAIL_OUT,
    /**
     * Effect dealing with relative moves.
     */
    RELATIVE_MOVE,
    /**
     * Effect dealing with absolute moves.
     */
    ABSOLUTE_MOVE,
    /**
     * Effect dealing with removing buildings.
     */
    REMOVE_BUILDINGS;
}
