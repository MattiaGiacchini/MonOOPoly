package monoopoly.model.item;

import monoopoly.model.item.card.Card;

public interface Deck extends UnPurchasable {

	/**
	 * it's used to know which deck is this.
	 */
	enum Type{
		CALAMITY,
		UNEXPECTED,
		PROBABILITY;
	}
	
	/**
	 *  this method is used to draw a card from deck
	 *  
	 * @return a new {@link Card}
	 */
	public Card draw();

	/**
	 * this method is used to know which Deck is this
	 *  
	 * @return the {@link Type} of this Deck
	 */
	public Deck.Type getDeckType();
}
