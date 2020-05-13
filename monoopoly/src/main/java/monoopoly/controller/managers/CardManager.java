package monoopoly.controller.managers;

import monoopoly.model.table.card.Card;

public interface CardManager {

    /**
     * this method lets you know the effect type possessed by this card.
     * @param card
     * @return {@link monoopoly.utilities.CardEffect}
     */
    monoopoly.utilities.CardEffect knowCard(Card card);

}
