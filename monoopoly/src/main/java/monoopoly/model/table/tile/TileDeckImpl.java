package monoopoly.model.table.tile;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import monoopoly.model.table.card.Card;
import monoopoly.model.table.card.CardImpl;
import monoopoly.model.table.card.MoneyEffect;
import monoopoly.model.table.card.MoveEffect;
import monoopoly.model.table.card.PropertyEffect;
import monoopoly.model.table.card.StatusEffect;
import monoopoly.model.table.deck.Deck;

/**
 * TileDeckImpl is a decoration of Tile and it's represents the one
 * decks inside the map. TileDeckImpl is used to draw new cards!
 */
public final class TileDeckImpl extends AbstractTileDecorator
implements TileDeck {

    private static final Integer ZERO = 0;

    private final Function<Category, Set<Integer>> retriverSetOfCategoryPosition;
    private final Function<Integer, Integer> retriverNumberHouseOfPlayer;
    private final Function<Integer, Integer> retriverNumberHotelOfPlayer;
    private final Supplier<Map<Integer, Integer>> supplierMapOfProperty;
    private final Deck deck;
    private final Integer tableSize;
    private Integer idDrawer;
    private Map<Integer, Double> playersBalance;
    private Map<Integer, Integer> playersPosition;

    /**
     * This nested static class is used
     * to create a new instance of TileDeckImpl.
     */
    public static class Builder {
        private Tile tileToDecore;
        private Deck deck;
        private Integer tableSize;
        private Function<Integer, Integer> retriverNumberHouseOfPlayer;
        private Function<Integer, Integer> retriverNumberHotelOfPlayer;
        private Supplier<Map<Integer, Integer>> supplierMapOfProperty;
        private Function<Category, Set<Integer>> retriverSetOfCategoryPosition;

        /**
         *
         */
        public Builder() {
            this.deck                            = null;
            this.tileToDecore                    = null;
            this.tableSize                       = null;
            this.retriverNumberHouseOfPlayer     = null;
            this.retriverNumberHotelOfPlayer     = null;
            this.retriverSetOfCategoryPosition   = null;
        }

        /**
         * this method is used to set the tile to decore.
         *
         * @param tile to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder tileToDecore(final Tile tile) {
            Objects.requireNonNull(tile,
                    "the tile to decore cannot has null value");
            if (!tile.isDeck()) {
                throw new IllegalArgumentException(
                        "the tile hasn't a deck Category");
            }
            this.tileToDecore = tile;
            return this;
        }

        /**
         * this method is used to set the dimension of table.
         *
         * @param tableSize dimension
         * @return {@link Builder} for a fluent programming
         */
        public Builder tableSize(final Integer tableSize) {
            Objects.requireNonNull(tableSize,
                    "The Table size cannot has null value!");
            if (tableSize <= ZERO) {
                throw new IllegalArgumentException(
                        "The Table size has a wrong format!");
            }
            this.tableSize = tableSize;
            return this;
        }

        /**
         * this method is used to set the supplier that will be used to
         * recover a map of number of property on the table.
         *
         * @param supplierMapOfPositionBuildings to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder supplierNumOfPropertyOnTheTable(
                final Supplier<Map<Integer, Integer>>
                supplierMapOfPositionBuildings) {
            Objects.requireNonNull(supplierMapOfPositionBuildings,
                    "the supplier to retrive the map where are located the "
                            + "number of all buildings buildings on the table cannot "
                            + "has null value");
            this.supplierMapOfProperty = supplierMapOfPositionBuildings;
            return this;
        }

        /**
         * this method is used to set the function that will be used to
         * recover the number of hotel owned by a specific player.
         *
         * @param funNumOfHotelOwnedBy to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder retriverNumberHotelOfPlayer(
                final Function<Integer, Integer> funNumOfHotelOwnedBy) {
            Objects.requireNonNull(funNumOfHotelOwnedBy,
                    "the function to retrive the number "
                            + "of hotel cannot has null value");
            this.retriverNumberHotelOfPlayer = funNumOfHotelOwnedBy;
            return this;
        }

        /**
         * this method is used to set the function that will be used to
         * recover the number of houses owned by a specific player.
         *
         * @param funNumOfHouseOwnedBy to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder retriverNumberHouseOfPlayer(
                final Function<Integer, Integer> funNumOfHouseOwnedBy) {
            Objects.requireNonNull(funNumOfHouseOwnedBy,
                    "the function to retrive the number "
                            + "of house cannot has null value");
            this.retriverNumberHouseOfPlayer = funNumOfHouseOwnedBy;
            return this;
        }

        /**
         * this method is used to set the function that will be used to
         * recover the positions of the tiles of a specific category.
         *
         * @param funPosOfCategoryOnTable to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder retriverSetOfPositionforSpecificCategory(
                final Function<Category, Set<Integer>>
                funPosOfCategoryOnTable) {
            Objects.requireNonNull(funPosOfCategoryOnTable,
                    "the function to retrive the number "
                            + "of house cannot has null value");
            this.retriverSetOfCategoryPosition = funPosOfCategoryOnTable;
            return this;
        }

        /**
         * this method is used to set the Deck from where draw when is needed.
         *
         * @param deck where is possible draw
         * @return {@link Builder} for a fluent programming
         */
        public Builder deck(final Deck deck) {
            Objects.requireNonNull(deck, "the deck cannot has null value");
            this.deck = deck;
            return this;
        }

        /**
         * This method is used to create a new instance of
         * {@link TileDeckImpl} using ALL parameters you already pass
         * to the {@link Builder}.
         *
         * @return the Instance of Society
         * @throws NullPointerException if parameters are
         *         not all setted
         */
        public TileDeckImpl build() {
            Objects.requireNonNull(this.tileToDecore,
                    "there is no tile to decore setted!");
            Objects.requireNonNull(this.deck, "there is no deck setted!");
            Objects.requireNonNull(this.tableSize, "Table size isn't Setted!");
            Objects.requireNonNull(this.retriverNumberHouseOfPlayer,
                    "The function to retrive the number of house "
                            + "of specific player isn't setted!");
            Objects.requireNonNull(this.retriverNumberHotelOfPlayer,
                    "The function to retrive the number of hotel"
                            + "of specific player isn't setted!");
            Objects.requireNonNull(this.supplierMapOfProperty,
                    "The supplier to retrive the map of property"
                            + " isn't setted!");
            Objects.requireNonNull(this.retriverSetOfCategoryPosition,
                    "The function to retrive the Set of Specific"
                            + "category tile's position isn't setted!");
            return new TileDeckImpl(this);
        }

    }

    private TileDeckImpl(final Builder builder) {
        super(builder.tileToDecore);
        this.tableSize = builder.tableSize;
        this.deck = builder.deck;
        this.retriverNumberHouseOfPlayer = builder.retriverNumberHouseOfPlayer;
        this.retriverNumberHotelOfPlayer = builder.retriverNumberHotelOfPlayer;
        this.supplierMapOfProperty = builder.supplierMapOfProperty;
        this.retriverSetOfCategoryPosition =
                builder.retriverSetOfCategoryPosition;
    }

    @Override
    public TileDeck idPlayerWhoHasDraw(final Integer idDrawer) {
        Objects.requireNonNull(idDrawer, "the IdDrawer cannot has null value");
        this.idDrawer = idDrawer;
        return this;
    }

    @Override
    public TileDeck actualPlayersBalance(
            final Map<Integer, Double> playersBalance) {
        Objects.requireNonNull(playersBalance,
                "The Players Balance cannot has null value");
        playersBalance.entrySet().forEach(entry -> {
            if (entry.getValue().isNaN() || entry.getValue().isInfinite()) {
                throw new IllegalArgumentException(
                        "the values of playersBalance aren't correct");
            }
        });
        this.playersBalance = Collections.unmodifiableMap(playersBalance);
        return this;
    }

    @Override
    public TileDeck actualPlayersPosition(
            final Map<Integer, Integer> playersPosition) {
        Objects.requireNonNull(playersPosition,
                "The Players Position cannot has null value");
        this.playersPosition = Collections.unmodifiableMap(playersPosition);
        return this;
    }

    @Override
    public Card draw() {
        Objects.requireNonNull(this.idDrawer,
                "the identifier of the player who is to draw has not been set");
        Objects.requireNonNull(this.playersBalance,
                "players balance unsetted before draw");
        Objects.requireNonNull(this.playersPosition,
                "players position unsetted before draw");
        if (!this.playersBalance.containsKey(this.idDrawer)) {
            throw new IllegalArgumentException(
                    "the Player's identifier who is to draw isn't "
                            + "inside the playersBalance");
        }
        if (!this.playersPosition.containsKey(this.idDrawer)) {
            throw new IllegalArgumentException(
                    "the Player's identifier who is to draw isn't "
                            + "inside the playersPosition");
        }

        final Card card = this.generateNewCard();

        this.idDrawer = null;
        this.playersBalance = null;
        this.playersPosition = null;

        return card;

    }

    private Card generateNewCard() {
        this.deck.draw(super.getCategory());
        Card card = this.generateBaseCard();

        if (this.deck.hasMoneyEffect()) {
            card = this.decoreWithMoneyEffect(card);
        }

        if (this.deck.hasStatusEffect()) {
            card = this.decoreWithStatusEffect(card);
        }

        if (this.deck.hasPropertyEffect()) {
            card = this.decoreWithPropertyEffect(card);
        }

        if (this.deck.hasMovementEffect()) {
            card = this.decoreWithMovementEffect(card);
        }

        return card;
    }

    private Card generateBaseCard() {
        return new CardImpl.Builder()
                .originDeck(super.getCategory())
                .cardNumber(this.deck.getNumberCard())
                .description(this.deck.getDescription())
                .build();
    }

    private Card decoreWithMoneyEffect(final Card card) {
        return new MoneyEffect.Builder()
                .actualPlayersBalance(this.playersBalance)
                .cardToDecore(card)
                .idDrawer(this.idDrawer)
                .exchangePlayerToOthers(
                        this.deck.getPlayersToOthers())
                .exchangePlayerToBank(
                        this.deck.getPlayerToBank())
                .playerNumberOfHotel(
                        this.retriverNumberHotelOfPlayer
                        .apply(this.idDrawer))
                .playerNumberOfHouse(
                        this.retriverNumberHouseOfPlayer
                        .apply(this.idDrawer))
                .exchangeValueHouseToBank(
                        this.deck.getValueHouseToBank())
                .exchangeValueHotelToBank(
                        this.deck.getValueHotelToBank())
                .exchangeAllToBank(this.deck.getAllToBank())
                .exchangeAllToBankPercentage(
                        this.deck.getAllToBankPercentage())
                .makeTheAvaragePlayersBalance(
                        this.deck
                        .isMakeTheAveragePlayersBalance())
                .build();
    }

    private Card decoreWithStatusEffect(final Card card) {
        return new StatusEffect.Builder()
                .cardToDecore(card)
                .goToJail(this.deck.goToJail())
                .exitFromJail(this.deck.exitFromJail())
                .maintainable(this.deck.isMaintainable())
                .build();
    }

    private Card decoreWithPropertyEffect(final Card card) {
        return new PropertyEffect.Builder()
                .cardToDecore(card)
                .buildingsToModify(this.supplierMapOfProperty
                        .get())
                .build();
    }

    private Card decoreWithMovementEffect(final Card card) {
        return new MoveEffect.Builder()
                .cardToDecore(card)
                .idDrawers(this.idDrawer)
                .playersPosition(this.playersPosition)
                .tableSize(this.tableSize)
                .stepToDo(this.deck.getStepsToDo())
                .tilePositionToGo(this.deck.getTilePositionToGo())
                .nextTileCategoryToReach(
                        this.deck.getTileCategoryToReach())
                .tileRetriverFromCategory(
                        Objects.isNull(this.deck.getTileCategoryToReach())
                        ? null : this.getCategoryTileRetriver())
                .generateRandomStep(this.deck
                        .isGenerateRandomSteps())
                .applyToPlayer(this.deck.isMoveToApplyToPlayer())
                .applyToOthers(this.deck.isMoveToApplyToOthers())
                .build();
    }

    private BiFunction<Integer, Category, Integer> getCategoryTileRetriver() {
        return (actualPosition, category) -> {
            final Set<Integer> possiblePosition =
                    retriverSetOfCategoryPosition.apply(category);

            if (possiblePosition.stream().anyMatch(el -> el > actualPosition)) {
                return possiblePosition.stream()
                        .filter(el -> el > actualPosition)
                        .min(Comparator.comparing(
                                Integer::valueOf))
                        .get() - actualPosition;
            }
            return possiblePosition.stream()
                    .min(Comparator.comparing(Integer::valueOf))
                    .get() + this.tableSize
                    - actualPosition;
        };
    }
}
