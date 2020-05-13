package monoopoly.model.item.deck;

import monoopoly.model.item.tile.Tile.Category;

/**
 *  this Interface represents the container of all
 *  effect of one card can have. every time you call
 *  the method {@link #draw(Category)}, the object
 *  will prepare another set of properties which you'll
 *  can use to generate a new card.
 */
public interface Deck {

    /**
     * this method is used to receive another card's
     * properties.
     *
     * @param category to specify from which deck
     * to draw
     */
    void draw(Category category);

    /**
     * this method is used to get the number
     * of card properties you already draw with
     * {@link #draw(Category)}.
     *
     * @return number of card
     */
    Integer getNumberCard();

    /**
     * this method is used to get the description
     * of card properties you already draw with
     * {@link #draw(Category)}.
     *
     * @return card description
     */
    String getDescription();

    /**
     * this method is used to know if the card's
     * properties has effects to apply on the
     * balance of players.
     *
     * @return true if there is a money effect
     */
    boolean hasMoneyEffect();

    /**
     * this method is used to know if the card's
     * properties has effects to apply on the
     * status players.
     *
     * @return true if there is a status effect
     */
    boolean hasStatusEffect();

    /**
     * this method is used to know if the card's
     * properties has effects to apply on the
     * player's position.
     *
     * @return true if there is a movement effect
     */
    boolean hasMovementEffect();

    /**
     * this method is used to know if the card's
     * properties has effects to apply on the
     * properties of players.
     *
     * @return true if there is a movement effect
     */
    boolean hasPropertyEffect();

    /**
     * this method will retrieve the value
     * which the player must give to every
     * other players.
     *
     * @return the value to exchange
     */
    Double getPlayersToOthers();

    /**
     * this method will retrieve the value
     * which the player must give to bank.
     *
     * @return the value to exchange
     */
    Double getPlayerToBank();

    /**
     * this method returns the value to be
     * multiplied to the number of houses owned
     * by the player. the result is the value
     * that the player must give to the bank.
     *
     * @return the value of single house
     */
    Double getValueHouseToBank();

    /**
     * this method returns the value to be
     * multiplied to the number of hotel owned
     * by the player. the result is the value
     * that the player must give to the bank.
     *
     * @return the value of single hotel
     */
    Double getValueHotelToBank();

    /**
     * this method returns the value
     * which every player must pay to
     * the bank.
     *
     * @return the value to pay
     */
    Double getAllToBank();

    /**
     * this method returns the percentage
     * to be deducted from the balance of
     * each player and then deposits the
     * sum in the bank.
     *
     * @return the percentage
     */
    Double getAllToBankPercentage();

    /**
     * this method is used to know if all
     * player accounts should be balanced.
     *
     * @return true if the average must be apply
     */
    boolean isMakeTheAveragePlayersBalance();

    /**
     * this method is used to know if the
     * player must go to the jail.
     *
     * @return true if the player must go to
     *         the jail
     */
    boolean goToJail();

    /**
     * this method is used to know if the
     * player can exit from jail.
     *
     * @return true if the player is can exit
     */
    boolean exitFromJail();

    /**
     * this method is used to know if the
     * card generated can be maintainable
     * by the player.
     *
     * @return true if the player can handle
     *         the card during the game
     */
    boolean isMaintainable();

    /**
     * this method is used to get the number
     * of steps of some target must do.
     *
     * @return number of steps to do forward
     *         if the number is positive,
     *         backward if the number is
     *         negative
     */
    Integer getStepsToDo();

    /**
     * this method returns the position of tile
     * to go.
     *
     * @return the position of table game
     */
    Integer getTilePositionToGo();

    /**
     * this method is used to get which is the
     * {@link Category} to reach forward
     * in the table.
     *
     * @return {@link Category}
     */
    Category getTileCategoryToReach();

    /**
     * this method request to generate random
     * steps to do.
     *
     * @return the steps to do
     */
    boolean isGenerateRandomSteps();

    /**
     * this method is used to know if the movement
     * should also be applied to the drawer player.
     *
     * @return true if the drawer is considered
     */
    boolean isMoveToApplyToPlayer();

    /**
     *  this method is used to know if the movement
     *  should also be applied to all except the
     *  player who drew.
     *
     * @return true if all players except the one
     *         who drew the card are considered
     */
    boolean isMoveToApplyToOthers();

}
