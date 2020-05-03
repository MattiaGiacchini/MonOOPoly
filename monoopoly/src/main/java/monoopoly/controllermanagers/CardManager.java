package monoopoly.controllermanagers;

public interface CardManager {

	/**
	 * this method lets you know the type of effect that this card has
	 * @param {@link Card}
	 * @return monoopoly.utilities.CardEffect
	 */
	monoopoly.utilities.CardEffect knowCard (final Card card);
	
	/**
	 * a method to know if the card is maintainable
	 * @return boolean
	 */
	boolean isThisCardMaintainable();
	
	/**
	 * this method sets the card maintainable
	 * @param boolean
	 */
	void setThisCardMaintainable(boolean isThisCardMaintainable);
}
