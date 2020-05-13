package monoopoly.model.item.card;

import java.util.Objects;

/**
 * The class StatusEffect is a decorator of card.
 */
public final class StatusEffect extends AbstractCardDecorator {

    private boolean goToJail;
    private boolean goToJailSetted;
    private boolean exitFromJail;
    private boolean exitFromJailSetted;
    private boolean maintainable;
    private boolean maintainableSetted;

    /**
     * This nested static class is used
     * to create a new instance of {@link StatusEffect}.
     */
    public static class Builder {
        private Card    cardToDecore;
        private boolean goToJail;
        private boolean goToJailAdded;
        private boolean exitFromJail;
        private boolean exitFromJailAdded;
        private boolean maintainable;
        private boolean maintainableAdded;

        /**
         * {@link Builder}'s Constructor.
         */
        public Builder() {
            this.exitFromJail       = false;
            this.exitFromJailAdded  = false;
            this.goToJail           = false;
            this.goToJailAdded      = false;
            this.maintainable       = false;
            this.maintainableAdded  = false;
            this.cardToDecore       = null;
        }

        /**
         * this method is used to set the card to decore.
         *
         * @param card to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder cardToDecore(final Card card) {
            Objects.requireNonNull(card, "The decorator cannot decor a null pointer");
            this.cardToDecore = card;
            return this;
        }

        /**
         * this method is used to set if the player who has drawn can exit
         * from jail or not.
         *
         * @param value true if he can, false if not.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exitFromJail(final boolean value) {
            this.exitFromJail = value;
            this.exitFromJailAdded = true;
            return this;
        }

        /**
         * this method is used to set if the player who has drawn must
         * go to jail or not.
         *
         * @param value true if he must go to jail, false if not.
         * @return {@link Builder} for a fluent programming
         */
        public Builder goToJail(final boolean value) {
            this.goToJail = value;
            this.goToJailAdded = true;
            return this;
        }

        /**
         * this method is used to set if the card is maintainable or not.
         *
         * @param value true if the card is maintainable, false if not
         * @return {@link Builder} for a fluent programming
         */
        public Builder maintainable(final boolean value) {
            this.maintainable = value;
            this.maintainableAdded = true;
            return this;
        }

        /**
         * this method is used to create a new instance of
         * {@link StatusEffect} using all parameters you already
         * pass in the {@link Builder}.
         *
         * @return {@link Builder} for a fluent programming
         * @throws IllegalStateException if the parameters aren't formatted
         *                               correctly
         *
         */
        public StatusEffect build() {
            if (nothingSetted()
                    || isThereNothingToMaintain()
                    || isThereDoubleEffect()
                    || isThereGoToJailMaintainable()) {
                throw new IllegalStateException();
            }
            return new StatusEffect(this);
        }

        private boolean nothingSetted() {
            return !this.maintainableAdded
                    && !this.exitFromJailAdded
                    && !this.goToJailAdded;
        }

        private boolean isThereGoToJailMaintainable() {
            return this.goToJail && this.maintainable;
        }

        private boolean isThereDoubleEffect() {
            return this.exitFromJail && this.goToJail;
        }

        private boolean isThereNothingToMaintain() {
            return this.maintainable && !this.exitFromJail && !this.goToJail;
        }

    }

    private StatusEffect(final Builder builder) {
        super(builder.cardToDecore);

        if (builder.exitFromJailAdded) {
            this.exitFromJailSetted = true;
            this.exitFromJail = builder.exitFromJail;
        }

        if (builder.goToJailAdded) {
            this.goToJailSetted = true;
            this.goToJail = builder.goToJail;
        }

        if (builder.maintainableAdded) {
            this.maintainableSetted = true;
            this.maintainable = builder.maintainable;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mustThePlayerGoToJail() {
        if (this.goToJailSetted) {
            return this.goToJail;
        } else {
            return super.mustThePlayerGoToJail();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canThePlayerExitFromJail() {
        if (this.exitFromJailSetted) {
            return this.exitFromJail;
        } else {
            return super.canThePlayerExitFromJail();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isThisCardMaintainable() {
        if (this.maintainableSetted) {
            return this.maintainable;
        } else {
            return super.isThisCardMaintainable();
        }
    }
}
