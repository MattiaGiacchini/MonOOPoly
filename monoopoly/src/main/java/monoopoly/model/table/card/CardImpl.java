package monoopoly.model.table.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.table.tile.Tile.Category;

/**
 *  CardImpl is the base of every single Card.
 */
public final class CardImpl implements Card {

    // values necessary to create any Card
    private final Integer cardNumber;
    private final String description;
    private final Category originDeck;

    /**
     * This nested static class is used
     * to create a new instance of {@link CardImpl}.
     */
    public static final class Builder {

        private Integer cardNumber;
        private String description;
        private Category originDeck;

        /**
         * this is the {@link Builder}'s Constructor.
         */
        public Builder() {
            super();
            this.cardNumber = null;
            this.description = null;
            this.originDeck = null;
        }

        /**
         * this method is used to set the card's number.
         *
         * @param number of card
         * @return {@link Builder} for a fluent programming
         */
        public Builder cardNumber(final Integer number) {
            Objects.requireNonNull(number, "the Card number has null value");
            if (number < 0) {
                throw new IllegalArgumentException("number Card must be a positive value");
            }
            this.cardNumber = number;
            return this;
        }

        /**
         * this method is used to set the Origin deck from this card
         * has been drawn.
         *
         * @param category where the card has been drawn
         * @return {@link Builder} for a fluent programming
         */
        public Builder originDeck(final Category category) {
            Objects.requireNonNull(category, "the Card origin-deck has null value");
            this.originDeck = category;
            return this;
        }

        /**
         * this method is used to set the card's description.
         *
         * @param description of card
         * @return {@link Builder} for a fluent programming
         */
        public Builder description(final String description) {
            Objects.requireNonNull(description, "The card description has NULL Value");
            this.description = description;
            return this;
        }
        /**
         * This method is used to create the instance of
         * {@link CardImpl} using all parameters you already
         * pass to the {@link Builder}.
         *
         * @return {@link CardImpl} new Instance
         * @throws IllegalStateException if the parameters are
         *         not all setted
         */
        public CardImpl build() {
            if (Objects.isNull(this.cardNumber)
                    || Objects.isNull(this.description)
                    || Objects.isNull(this.originDeck)) {
                throw new IllegalStateException("missing construction elements");
            }
            return new CardImpl(this);
        }
    }

    private CardImpl(final Builder builder) {
        super();
        this.cardNumber     = builder.cardNumber;
        this.description    = builder.description;
        this.originDeck     = builder.originDeck;
    }

    @Override
    public Integer getCardNumber() {
        return this.cardNumber;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Category getOriginDeck() {
        return this.originDeck;
    }

    @Override
    public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
        return Optional.empty();
    }

    @Override
    public boolean mustThePlayerGoToJail() {
        return false;
    }

    @Override
    public boolean canThePlayerExitFromJail() {
        return false;
    }

    @Override
    public boolean isThisCardMaintainable() {
        return false;
    }

    @Override
    public Optional<Map<Integer, Integer>> getRelativeMoveToPosition() {
        return  Optional.empty();
    }

    @Override
    public Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition() {
        return  Optional.empty();
    }

    @Override
    public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
        return  Optional.empty();
    }

}
