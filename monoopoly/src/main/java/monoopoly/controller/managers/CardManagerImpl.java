package monoopoly.controller.managers;

import monoopoly.model.table.card.Card;

public final class CardManagerImpl implements CardManager {

    @Override
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
