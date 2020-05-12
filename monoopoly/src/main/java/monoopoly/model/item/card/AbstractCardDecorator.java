package monoopoly.model.item.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.item.Tile.Category;

/**
 * this abstract Class has been created to
 * implement the Pattern Decorator for the Card.
 */
public abstract class AbstractCardDecorator implements Card {

    private final Card decoratedCard;

    /**
     * Constructor for the class abstractCardDecorator.
     *
     * @param decoratedCard this is the card to decore
     */
    public AbstractCardDecorator(final Card decoratedCard) {
        super();
        Objects.requireNonNull(decoratedCard,
                "the cart to decore has null value");
        this.decoratedCard = decoratedCard;
    }

    @Override
    public final Integer getCardNumber() {
        return this.decoratedCard.getCardNumber();
    }

    @Override
    public final String getDescription() {
        return this.decoratedCard.getDescription();
    }

    @Override
    public final Category getOriginDeck() {
        return this.decoratedCard.getOriginDeck();
    }

    @Override
    public final Optional<Map<Integer, Double>>
    getValueToApplyOnPlayersBalance() {
        return this.decoratedCard.getValueToApplyOnPlayersBalance();
    }

    @Override
    public final boolean mustThePlayerGoToJail() {
        return this.decoratedCard.mustThePlayerGoToJail();
    }

    @Override
    public final boolean canThePlayerExitFromJail() {
        return this.decoratedCard.mustThePlayerGoToJail();
    }

    @Override
    public final boolean isThisCardMaintainable() {
        return this.decoratedCard.isThisCardMaintainable();
    }

    @Override
    public final Optional<Map<Integer, Integer>> getRelativeMoveToPosition() {
        return this.decoratedCard.getRelativeMoveToPosition();
    }

    @Override
    public final Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition() {
        return this.decoratedCard.getAbsoluteMoveToPosition();
    }

    @Override
    public final Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
        return this.decoratedCard.getNumberOfBuildingsToRemove();
    }

}
