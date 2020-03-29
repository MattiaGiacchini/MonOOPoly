package model;

public interface Player {

	/**
	 * This method returns the balance of the {@link Player}
	 * 
	 * @return the actual balance of the player
	 */
	public Double getBalance();

	/**
	 * This method is used to set the balance on the beginning of the game
	 * 
	 * @param balance
	 */
	public void setBalance(Double balance);

	/**
	 * This method is used to update the balance during the game
	 * 
	 * @param value the value to add or to withdraw from the balance
	 */
	public void updateBalance(Double value);

	/**
	 * This method returns the actual position of the player
	 * 
	 * @return the actual position of the player
	 */
	public int getPosition();

	/**
	 * This method is used to set the player's position on the beginning of the game
	 * 
	 * @param position the position in which the player is located
	 */
	public void setPosition(int position);

	/**
	 * @param distance the value to apply on the position to move the player
	 */
	public void updatePosition(int distance);

	/**
	 * This method is used to know the game staus of the player
	 * 
	 * @return the player's state
	 */
//	public States getState();
//
//	/**
//	 * This method is used to update the player's game status
//	 * 
//	 * @param state actual player's state
//	 */
//	public void setState(States state);

	/**
	 * This method returns the player's name
	 * 
	 * @return player's name
	 */
	public String getName();

}
