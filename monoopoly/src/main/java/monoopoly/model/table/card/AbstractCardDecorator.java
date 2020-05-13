package monoopoly.model.table.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.table.tile.Tile.Category;

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

    /**
     *  {@inheritDoc}
     */
    @Override
    public Optional<Map<Integer, Double>>
    getValueToApplyOnPlayersBalance() {
        return this.decoratedCard.getValueToApplyOnPlayersBalance();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public boolean mustThePlayerGoToJail() {
        return this.decoratedCard.mustThePlayerGoToJail();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public boolean canThePlayerExitFromJail() {
        return this.decoratedCard.mustThePlayerGoToJail();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public boolean isThisCardMaintainable() {
        return this.decoratedCard.isThisCardMaintainable();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Optional<Map<Integer, Integer>> getRelativeMoveToPosition() {
        return this.decoratedCard.getRelativeMoveToPosition();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition() {
        return this.decoratedCard.getAbsoluteMoveToPosition();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
        return this.decoratedCard.getNumberOfBuildingsToRemove();
    }

}
