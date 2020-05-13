package monoopoly.controller.managers;

import monoopoly.model.item.card.Card;

public interface CardManager {

    /**
     * this method lets you know the type of effect that this card has.
     * @param card
     * @return {@link monoopoly.utilities.CardEffect}
     */
    monoopoly.utilities.CardEffect knowCard(Card card);

}
