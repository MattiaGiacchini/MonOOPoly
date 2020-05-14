package monoopoly.model.table.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The class MoneyEffect is a decorator of card.
 */
public final class MoneyEffect extends AbstractCardDecorator {

    private static final int EXPONENT = -6;
    private static final int BASE = 10;
    private static final int ONE = 1;
    private static final Double ZERO_VALUE = 0.0;

    private Optional<Map<Integer, Double>> valueToApplyOnPlayersBalance;

    /**
     * This nested static class is used
     * to create a new instance of {@link MoneyEffect}.
     */
    public static class Builder {
        private Card cardToDecore;
        private Integer idDrawer;
        private Map<Integer, Double>    actualPlayersBalance;
        private Optional<Double>        playerToOthers;
        private Optional<Double>        playerToBank;
        private Optional<Double>        playerValueHouseToBank;
        private Optional<Double>        playerValueHotelToBank;
        private Optional<Integer>       playerNumberHouse;
        private Optional<Integer>       playerNumberHotel;
        private Optional<Double>        allToBankPercentage;
        private Optional<Double>        allToBank;
        private boolean                 makeTheAvarage;

        /**
         * {@link Builder}'s Constructor.
         */
        public Builder() {
            super();
            this.cardToDecore           = null;
            this.idDrawer               = null;
            this.actualPlayersBalance   = null;
            this.playerToOthers         = Optional.empty();
            this.playerToBank           = Optional.empty();
            this.playerValueHouseToBank = Optional.empty();
            this.playerValueHotelToBank = Optional.empty();
            this.playerNumberHouse      = Optional.empty();
            this.playerNumberHotel      = Optional.empty();
            this.allToBankPercentage    = Optional.empty();
            this.allToBank              = Optional.empty();
            this.makeTheAvarage         = false;
        }

        /**
         * this method is used to set the Card to decore.
         *
         * @param card to decore
         * @return  {@link Builder} for a fluent programming
         */
        public Builder cardToDecore(final Card card) {
            Objects.requireNonNull(card,
                    "The decorator cannot decor a null pointer");
            this.cardToDecore = card;
            return this;
        }

        /**
         * this method is used to set the ID of player
         * who has drawn.
         *
         * @param value of id player
         * @return {@link Builder} for a fluent programming
         */
        public Builder idDrawer(final Integer value) {
            Objects.requireNonNull(value,
                    "the IdDrawer cannot has a null value");
            this.idDrawer = value;
            return this;
        }

        /**
         * this method is used to pass the actual balance of all
         * players in game.
         *
         * @param map of balance where the keys are the id of players
         *        and the values are the balances
         * @return {@link Builder} for a fluent programming
         */
        public Builder actualPlayersBalance(final Map<Integer, Double> map) {
            Objects.requireNonNull(map,
                    "the IdDrawer cannot has a null value");
            for (final Double value : map.values()) {
                this.doubleChecker(value);
            }
            this.actualPlayersBalance = map;
            return this;
        }

        /**
         * this method is used to set the value which the player (who has
         * drawn the card) must give/receive to/from each others Players.
         *
         * @param value if value is positive player give to the other players
         *              the same quantity of money,
         *              if negative the player receive from the other players
         *              the same quantity of money.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangePlayerToOthers(final Double value) {
            this.doubleChecker(value);
            this.playerToOthers = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the value which the player (who has
         * drawn the card) must give/receive to/from the bank.
         *
         * @param value if value is positive player give to the bank,
         *              if negative the player receive from the bank.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangePlayerToBank(final Double value) {
            this.doubleChecker(value);
            this.playerToBank = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the value of single house. this value
         * will be used to calculate the amount of money which the player
         * (who has drawn the card) must give/receive to/from the bank for
         * each house owned.
         *
         * @param value if value is positive player will give to the bank,
         *              if negative the player will receive from the bank.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangeValueHouseToBank(final Double value) {
            this.doubleChecker(value);
            this.playerValueHouseToBank = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the value of single hotel. this value
         * will be used to calculate the amount of money which the player
         * (who has drawn the card) must give/receive to/from the bank for
         * each hotel owned.
         *
         * @param value if value is positive player will give to the bank,
         *              if negative the player will receive from the bank.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangeValueHotelToBank(final Double value) {
            this.doubleChecker(value);
            this.playerValueHotelToBank = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the number of house owned
         * by the player which has drawn.
         *
         * @param value number of house owned by the player
         * @return {@link Builder} for a fluent programming
         */
        public Builder playerNumberOfHouse(final Integer value) {
            this.playerNumberHouse = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the number of hotel owned
         * by the player which has drawn.
         *
         * @param value number of house owned by the player
         * @return {@link Builder} for a fluent programming
         */
        public Builder playerNumberOfHotel(final Integer value) {
            this.playerNumberHotel = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the value which every player
         * in game must give/receive to/from the bank.
         *
         * @param value if value is positive all players will give to the bank,
         *              if negative all players will receive from the bank.
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangeAllToBank(final Double value) {
            this.doubleChecker(value);
            this.allToBank = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to set the percentage which will be used
         * to calculate the amount each player must give to the bank.
         *
         * @param value percentage to use for calculate the amount
         * @return {@link Builder} for a fluent programming
         */
        public Builder exchangeAllToBankPercentage(final Double value) {
            this.doubleChecker(value);
            this.allToBankPercentage = Optional.ofNullable(value);
            return this;
        }

        /**
         * this method is used to enable the function which will
         * transform each players balance in the average value.
         *
         * @param value true if the function must be enabled, false if not
         * @return {@link Builder} for a fluent programming
         */
        public Builder makeTheAvaragePlayersBalance(final boolean value) {
            this.makeTheAvarage = value;
            return this;
        }

        /**
         * This method is used to create the instance of
         * Society using all parameters you already pass
         * to the {@link Builder}.
         *
         * @return the Instance of {@link MoneyEffect}
         * @throws IllegalStateException if parameters aren't
         *         formatted correctly
         */
        public MoneyEffect build() {
            if ((this.makeTheAvarage
                    || this.allToBank.isPresent()
                    || this.playerToBank.isPresent()
                    || this.playerToOthers.isPresent()
                    || this.allToBankPercentage.isPresent()
                    || isThereEffectToApplyOnBuildings())
                    && !Objects.isNull(this.cardToDecore)
                    && !Objects.isNull(this.idDrawer)
                    && !Objects.isNull(this.actualPlayersBalance)) {
                return new MoneyEffect(this);
            }
            throw new IllegalStateException("Wrong build sequence");
        }

        private boolean isThereEffectToApplyOnBuildings() {
            return this.playerNumberHotel.isPresent()
                    && this.playerNumberHouse.isPresent()
                    && this.playerValueHouseToBank.isPresent()
                    && this.playerValueHotelToBank.isPresent();
        }

        private void doubleChecker(final Double value) {
            if (Objects.nonNull(value)
                    && (value.isInfinite() || value.isNaN())) {
                throw new IllegalArgumentException(
                        "The double value hasn't a correnct format");
            }
        }
    }

    private MoneyEffect(final Builder builder) {
        super(builder.cardToDecore);

        final Map<Integer, Double> copyActPlayersBal = new HashMap<>();
        copyActPlayersBal.putAll(builder.actualPlayersBalance);
        final Integer numPlayers = copyActPlayersBal.size();

        final Map<Integer, Double> result = new HashMap<>();
        copyActPlayersBal.keySet()
        .forEach(x -> result.put(x, MoneyEffect.ZERO_VALUE));

        if (super.getValueToApplyOnPlayersBalance().isPresent()) {
            super.getValueToApplyOnPlayersBalance().get()
            .entrySet()
            .forEach(el -> {
                if (!result.get(el.getKey()).equals(el.getValue())) {
                    result.put(el.getKey(), el.getValue());
                }
            });
        }

        // generate the value
        if (builder.playerToOthers.isPresent()) {
            result.entrySet().forEach(el -> {
                if (el.getKey().equals(builder.idDrawer)) {
                    result.put(el.getKey(),
                            el.getValue() - ((numPlayers - ONE)
                                    * builder.playerToOthers.get()));
                } else {
                    result.put(el.getKey(),
                            el.getValue() + builder.playerToOthers.get());
                }
            });

        } else if (builder.playerToBank.isPresent()) {
            final Double el = result.get(builder.idDrawer);
            result.put(builder.idDrawer, el - builder.playerToBank.get());

        } else if (builder.playerValueHotelToBank.isPresent()
                && builder.playerValueHouseToBank.isPresent()
                && builder.playerNumberHotel.isPresent()
                && builder.playerNumberHouse.isPresent()) {
            final Double moneyToPay = builder.playerValueHotelToBank.get()
                    * builder.playerNumberHotel.get()
                    + builder.playerValueHouseToBank.get()
                    * builder.playerNumberHouse.get();
            final Double el = result.get(builder.idDrawer);
            result.put(builder.idDrawer, el - moneyToPay);

        } else if (builder.allToBank.isPresent()) {
            result.entrySet().forEach(el -> {
                result.put(el.getKey(),
                        el.getValue() - builder.allToBank.get());
            });

        } else if (builder.allToBankPercentage.isPresent()) {
            copyActPlayersBal.entrySet().forEach(el -> {
                copyActPlayersBal.put(el.getKey(),
                        result.get(el.getKey()) + el.getValue());
            });

            result.entrySet().forEach(el -> {
                result.put(el.getKey(),
                        el.getValue() - (copyActPlayersBal.get(el.getKey())
                                * builder.allToBankPercentage.get()));
            });

        } else if (builder.makeTheAvarage) {
            copyActPlayersBal.entrySet().forEach(el -> {
                copyActPlayersBal.put(el.getKey(),
                        result.get(el.getKey()) + el.getValue());
            });

            final Double avarage = makeTheAvarage(copyActPlayersBal);

            result.entrySet().forEach(el -> {
                result.put(el.getKey(),
                        (avarage - copyActPlayersBal.get(el.getKey()))
                        + el.getValue());
            });

        }

        if (noValueToApply(result)) {
            this.valueToApplyOnPlayersBalance = Optional.empty();
        } else {
            this.valueToApplyOnPlayersBalance = Optional.of(result);
        }

    }

    @Override
    public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
        return this.valueToApplyOnPlayersBalance;
    }

    private boolean doubleEqualsWithTollerance(final Double a, final Double b) {
        return Math.abs(a - b) < Math.pow(BASE, EXPONENT);
    }

    private boolean noValueToApply(final Map<Integer, Double> result) {
        return result.entrySet()
                .stream()
                .allMatch(x -> this.doubleEqualsWithTollerance(x.getValue(),
                        MoneyEffect.ZERO_VALUE));
    }

    private double makeTheAvarage(
            final Map<Integer, Double> copyActPlayersBal) {
        return copyActPlayersBal.entrySet()
                .stream()
                .mapToDouble(x -> (Double) x.getValue())
                .average()
                .getAsDouble();
    }
}
