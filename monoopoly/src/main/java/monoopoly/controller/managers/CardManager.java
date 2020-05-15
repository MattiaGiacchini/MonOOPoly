package monoopoly.controller.managers;

import monoopoly.model.table.card.Card;

/**
 * This class is a gestion of tipe of card effects.
 */
public final class CardManager {

    /**
     * Useful to understend the specific type of card effect.
     * @param card representing the specific card
     * @return {@link monoopoly.utilities.CardEffect}
     */
    public monoopoly.utilities.CardEffect knowCard(final Card card) {
        if (!card.getValueToApplyOnPlayersBalance().isEmpty()) {
            return monoopoly.utilities.CardEffect.MONEY_EXCHANGE;
        } else if (card.mustThePlayerGoToJail()) {
            return monoopoly.utilities.CardEffect.JAIL_IN;
        } else if (card.canThePlayerExitFromJail()) {
            return monoopoly.utilities.CardEffect.JAIL_OUT;
        } else if (!card.getRelativeMoveToPosition().isEmpty()) {
            return monoopoly.utilities.CardEffect.RELATIVE_MOVE;
        } else if (!card.getAbsoluteMoveToPosition().isEmpty()) {
            return monoopoly.utilities.CardEffect.ABSOLUTE_MOVE;
        } else if (!card.getNumberOfBuildingsToRemove().isEmpty()) {
            return monoopoly.utilities.CardEffect.REMOVE_BUILDINGS;
        }
        return null;
    }

}
