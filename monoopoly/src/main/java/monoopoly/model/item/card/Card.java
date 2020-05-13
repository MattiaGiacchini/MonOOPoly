package monoopoly.model.item.card;

import java.util.Map;
import java.util.Optional;

import monoopoly.model.item.Tile;

/**
 *  this interface represents the effect of every
 *  possible card present in the various decks.
 *  just read it.
 */
public interface Card {

    /**
     * this method is used to get the
     * number of specific card inside
     * the deck.
     *
     * @return the number of card
     */
    Integer getCardNumber();

    /**
     * this method is used to get the
     * description of the card effect.
     *
     * @return a {@link String}
     * of description
     */
    String getDescription();

    /**
     * this method is used to get the
     * type of deck.
     *
     * @return {@link Tile.Category}
     */
    Tile.Category getOriginDeck();

    /**
     * this method is used to get the
     * variation to apply on every
     * player's Balance.
     *
     * @return if the card hasn't effect on
     *  the various balances it will return an
     *  {@link Optional.Empty()}, otherwise it
     *  will return an {@link Optional}<{@link Map}>.
     *  where the key indicates the ID of
     *  each player on which to apply the value
     *  corresponding to the respective balance
     */
    Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance();

    /**
     * This method is used to know if this
     * card forces the player to go to
     * jail.
     *
     * @return true if the player must go
     * to jail, false if not.
     */
    boolean mustThePlayerGoToJail();

    /**
     * This method is used to know if this
     * card allows the player to exit from
     * the jail.
     *
     * @return true if the player can exit
     * from the jail, false if not
     */
    boolean canThePlayerExitFromJail();

    /**
     * this method is used to know if this
     * card allows the player to keep it in
     * game and to use it when necessary.
     *
     * @return true if the card is maintainable
     */
    boolean isThisCardMaintainable();

    /**
     * this method is used to get the change
     * of position to apply on each player in
     * game.
     *
     * @return if the card hasn't effect on
     *  the players move it will return an
     *  {@link Optional.Empty()}, otherwise it
     *  will return an {@link Optional}<{@link Map}>
     *  where the key indicates the ID of
     *  each player on which to apply the move
     *  and the value indicates the number of steps
     *  to do forward or backward to the players position
     */
    Optional<Map<Integer, Integer>> getRelativeMoveToPosition();

    /**
     * this method is used to get the change
     * of position to apply on each player in
     * game.
     *
     * @return if the card hasn't effect on
     *  the players move it will return an
     *  {@link Optional.Empty()}, otherwise it
     *  will return an {@link Optional}<{@link Map}>
     *  where the key indicates the ID of
     *  each player on which to apply the absolute move,
     *  the value indicates the number of tile where to go.
     */
    Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition();

    /**
     * this method is used to get the number of
     * buildings to remove from each property of
     * table.
     *
     * @return if the card hasn't effect on
     *  the property's buildings it will return an
     *  {@link Optional.Empty()}, otherwise it
     *  will return an {@link Optional}<{@link Map}>
     *  where the key indicates the position of
     *  each Property on which to remove the number
     *  of buildings corresponding to the respective
     *  value
     */
    Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove();

}
