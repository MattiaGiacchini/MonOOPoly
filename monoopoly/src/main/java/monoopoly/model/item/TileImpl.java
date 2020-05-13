package monoopoly.model.item;

import java.util.Objects;

/**
 * this class represents the Tile base which can be
 * decorated.
 */
public final class TileImpl implements Tile {

    private final String name;
    private final Category category;
    private final boolean deck;
    private final boolean purchasable;
    private final boolean buildable;

    /**
     * This nested static class is used
     * to create a new instance of TileImpl.
     */
    public static class Builder {

        private String name;
        private Category category;
        private boolean deck;
        private boolean deckSetted;
        private boolean purchasable;
        private boolean purchasableSetted;
        private boolean buildable;
        private boolean buildableSetted;

        /**
         * this is the constructor of TileImpl.Builder.
         */
        public Builder() {
            super();
            this.name = null;
            this.category = null;
            this.deck = false;
            this.deckSetted = false;
            this.purchasable = false;
            this.purchasableSetted = false;
            this.buildable = false;
            this.buildableSetted = false;
        }

        /**
         * this method is used to insert the Tile's name.
         *
         * @param name of Tile
         * @return {@link Builder} for a fluent programming
         */
        public Builder name(final String name) {
            this.objRequired(name, "Tile's name");
            this.name = name;
            return this;
        }

        /**
         * this method is used to insert the Tile's Category.
         *
         * @param category of Tile
         * @return {@link Builder} for a fluent programming
         */
        public Builder category(final Category category) {
            this.objRequired(category, "Tile's Category");
            this.category = category;
            return this;
        }

        /**
         * this method is used to insert the flag that indicate if the Tile is
         * purchasable.
         *
         * @param purchasable true if it's purchasable, false if not!
         * @return {@link Builder} for a fluent programming
         */
        public Builder purchasable(final boolean purchasable) {
            this.purchasable = purchasable;
            this.purchasableSetted = true;
            return this;
        }

        /**
         * this method is used to insert the flag that indicate if the Tile is
         * a deck.
         *
         * @param deck true if it's a deck, false if not!
         * @return {@link Builder} for a fluent programming
         */
        public Builder deck(final boolean deck) {
            this.deck = deck;
            this.deckSetted = true;
            return this;
        }

        /**
         * this method is used to insert the flag that indicate if the Tile is
         * buildable.
         *
         * @param buildable true if it's buildable, false if not!
         * @return {@link Builder} for a fluent programming
         */
        public Builder buildable(final boolean buildable) {
            this.buildable = buildable;
            this.buildableSetted = true;
            return this;
        }

        /**
         * this Method is used to create a new instance of
         * TileImpl.
         *
         * @return TileImpl new instance
         * @throws IllegalStateException if the parameters aren't all
         *         setted!
         */
        public TileImpl build() {
            if (Objects.isNull(this.name)
                    || Objects.isNull(this.category)
                    || !this.purchasableSetted
                    || !this.deckSetted
                    || !this.buildableSetted) {
                throw new IllegalStateException("Sequence build error");
            }
            return new TileImpl(this);
        }

        private void objRequired(final Object obj, final String str) {
            Objects.requireNonNull(obj, "TileImpl: the " + str
                    + " cannot has Null value!");
        }

    }

    private TileImpl(final Builder builder) {
        super();
        this.name           = builder.name;
        this.category       = builder.category;
        this.deck           = builder.deck;
        this.purchasable    = builder.purchasable;
        this.buildable      = builder.buildable;
    }

    @Override
    public boolean isPurchasable() {
        return this.purchasable;
    }

    @Override
    public boolean isDeck() {
        return this.deck;
    }

    @Override
    public boolean isBuildable() {
        return this.buildable;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

}
