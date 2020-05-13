package monoopoly.model.player;

import monoopoly.utilities.States;

/**
 * This is an interface that models a player.
 */
public interface Player {

    /**
     * This method returns the ID of the {@link Player}.
     * 
     * @return the unique player ID
     */
    int getID();

    /**
     * This method returns the balance of the {@link Player}.
     * 
     * @return the actual balance of the player
     */
    Double getBalance();

    /**
     * This method is used to set the balance on the beginning of the game.
     * 
     * @param balance to set
     */
    void setBalance(Double balance);

    /**
     * This method is used to update the balance during the game.
     * 
     * @param value the value to add or to withdraw from the balance
     */
    void updateBalance(Double value);

    /**
     * This method returns the actual position of the player.
     * 
     * @return the actual position of the player
     */
    int getPosition();

    /**
     * This method is used to set the player's position on the beginning of the
     * game.
     * 
     * @param position the position in which the player is located
     */
    void setPosition(int position);

    /**
     * This method is used to know the game status of the player.
     * 
     * @return the player's state
     */
    States getState();

    /**
     * This method is used to update the player's game status.
     * 
     * @param state actual player's state
     */
    void setState(States state);

    /**
     * This method returns the player's name.
     * 
     * @return player's name
     */
    String getName();

    /**
     * This method is used to set true or false if the player has got the "leave for
     * free from prison" card.
     * 
     * @param leaveForFree boolean: true if has card, false otherwise
     */
    void setPrisonCard(boolean leaveForFree);

    /**
     * This method returns true if the player has got the card "leave for free from
     * prison", false otherwise.
     * 
     * @return {@link Boolean}
     */
    boolean hasPrisonCard();

}
