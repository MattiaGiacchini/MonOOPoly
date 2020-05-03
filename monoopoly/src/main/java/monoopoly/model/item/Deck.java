package monoopoly.model.item;

import java.util.Map;

import monoopoly.model.item.card.Card;

/**
 * this interface represents the deck tile.
 * with this you can draw a special effect to
 * apply on the
 */
public interface Deck extends Tile {
	
	/**
	 * this method must be used every time
	 * you want to draw a new card! 
	 * it is necessary to specify who should draw a 
	 * card
	 *  
	 * @param idDrawer the player's identifier 
	 * @return the same {@link Deck} on you call this
	 * method. Useful for a fluent programming.
	 */
	public Deck idPlayerWhoHasDraw(Integer idDrawer);
	
	/**
	 * this method must be used every time
	 * you want to draw a new card. 
	 * it is necessary to calculate any variation to be 
	 * applied to the players Balance
	 * 
	 * @param playersBalance it is a {@link Map} where 
	 * the keys are the player's identifiers and the values
	 * are the respective player's balance 
	 * @return the same {@link Deck} on you call this
	 * method. Useful for a fluent programming.
	 */
	public Deck actualPlayersBalance(Map<Integer,Double> playersBalance);
	
	/**
	 * this method must be used every time
	 * you want to draw a new card. 
	 * it is necessary to calculate any movement to
	 * be applied on the actual player's position.
	 * 
	 * @param playersPosition it is a {@link Map} where 
	 * the keys are the player's identifiers and the values
	 * are the respective player's position 
	 * @return the same {@link Deck} on you call this
	 * method. Useful for a fluent programming.
	 */
	public Deck actualPlayersPosition(Map<Integer,Integer> playersPosition);
	
	/**
	 * this method is used to flip a new card from the deck.
	 * 
	 * @return {@link Card} 
	 * @throws NullPointerException this method must be invoked after: <br>
	 * {@link #idPlayerWhoHasDraw(Integer)},<br>
	 * {@link #actualPlayersBalance(Map)},<br>
	 * {@link #actualPlayersPosition(Map)};<br>
	 * or it will throw the exception
	 * @throws IllegalArgumentException if the Player's identifier isn't
	 * inside the map of players balance or players position 
	 */
	public Card draw();
}
