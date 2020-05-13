package monoopoly.model.item.tile.purchasable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import monoopoly.model.item.tile.Tile;

/**
 * the class Society is a Decorator of Tile.
 */
public final class Society extends AbstractPurchasable {

    private static final Double NO_LEASE = 0.0;
    private static final Integer LEVEL_ONE = 1;
    private static final Integer LEVEL_TWO = 2;
    private final double multiplierLevelOne;
    private final double multiplierLevelTwo;
    private final Supplier<Integer> supplierDiceResult;
    private final Function<Integer, Integer> functionGetNumOfSocietyOwned;

    /**
     * This nested static class is used
     * to create a new instance of Society.
     */
    public static class Builder {

        private Tile decorated;
        private Double mortgageValue;
        private Double salesValue;
        private Double multiplierLevelOne;
        private Double multiplierLevelTwo;
        private Supplier<Integer> supplierDiceResult;
        private Function<Integer, Integer> functionGetNumOfSocietyOwned;

        /**
         * this is the {@link Builder}'s constructor.
         */
        public Builder() {
            super();
            this.decorated              = null;
            this.mortgageValue          = null;
            this.salesValue             = null;
            this.multiplierLevelOne     = null;
            this.multiplierLevelTwo     = null;
            this.supplierDiceResult     = null;
            this.functionGetNumOfSocietyOwned  = null;
        }

        /**
         * this method is used to set the Tile to decore.
         *
         * @param decorated tile to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder tile(final Tile decorated) {
            if (decorated.getCategory() != Category.SOCIETY) {
                throw new IllegalArgumentException("the Tile isn't a Society");
            }
            this.decorated = decorated;
            return this;
        }

        /**
         * this method is used to set the Supplier to use for
         * retrieving the last dices result.
         *
         * @param supplierDiceResult to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder supplierDiceResult(
                final Supplier<Integer> supplierDiceResult) {
            Objects.requireNonNull(supplierDiceResult,
                    "the supplier of dice cannot has null value!");
            this.supplierDiceResult = supplierDiceResult;
            return this;
        }

        /**
         * this method is used to set the function to use for
         * retrieving the number of Society owned by a specific
         * id player.
         *
         * @param function to used
         * @return  {@link Builder} for a fluent programming
         */
        public Builder funNumOfCatOwned(
                final Function<Integer, Integer> function) {
            Objects.requireNonNull(function,
                    "the BiFunction of category Owned cannot has null value");
            this.functionGetNumOfSocietyOwned = function;
            return this;
        }

        /**
         * this method is used to set the MortgageValue of Society.
         *
         * @param mortgageValue of society
         * @return {@link Builder} for a fluent programming
         */
        public Builder mortgage(final Double mortgageValue) {
            Objects.requireNonNull(mortgageValue,
                    "mortgage cannot has null value!");
            this.doubleChecker(mortgageValue, "mortgage value wrong format!");
            this.mortgageValue = mortgageValue;
            return this;
        }

        /**
         * this method is used to set the sales value of Society.
         *
         * @param salesValue of Society
         * @return {@link Builder} for a fluent programming
         */
        public Builder sales(final Double salesValue) {
            Objects.requireNonNull(salesValue,
                    "sales value cannot has null value!");
            this.doubleChecker(salesValue, "sales value wrong format!");
            this.salesValue = salesValue;
            return this;
        }

        /**
         * this method is used to set the multiplier to use
         * on the sum of the last dices thrown when the player
         * has one of specific society.
         *
         * @param multiplier to use on the result of dices
         * @return {@link Builder} for a fluent programming
         */
        public Builder multiplierLevelOne(final Double multiplier) {
            Objects.requireNonNull(multiplier,
                    "the multiplier level one cannot has null value!");
            this.doubleChecker(multiplier, "multiplier level one wrong format");
            this.multiplierLevelOne = multiplier;
            return this;
        }

        /**
         * this method is used to set the multiplier to use
         * on the sum of the last dices thrown when the player
         * has all the society of game.
         *
         * @param multiplier to use on the result of dices
         * @return {@link Builder} for a fluent programming
         */
        public Builder multiplierLevelTwo(final Double multiplier) {
            Objects.requireNonNull(multiplier,
                    "the multiplier level two cannot has null value!");
            this.doubleChecker(multiplier, "multiplier level one wrong format");
            this.multiplierLevelTwo = multiplier;
            return this;
        }

        /**
         * This method is used to create the instance of
         * Society using all parameters you already pass
         * to the {@link Builder}.
         *
         * @return the Instance of Society
         * @throws NullPointerException if parameters are
         *         not all set
         */
        public Society build() {
            Objects.requireNonNull(this.decorated,
                    "SOCIETY: Card to decor is unsetted!");
            Objects.requireNonNull(this.mortgageValue,
                    "SOCIETY: the mortgage value is unsetted!");
            Objects.requireNonNull(this.salesValue,
                    "SOCIETY: The sales Value is unsetted!");
            Objects.requireNonNull(this.multiplierLevelOne,
                    "SOCIETY: the multiplier one is Unsetted!");
            Objects.requireNonNull(this.multiplierLevelTwo,
                    "SOCIETY: the multiplier two is Unsetted!");
            Objects.requireNonNull(this.supplierDiceResult,
                    "SOCIETY: the multiplier two is Unsetted!");
            Objects.requireNonNull(this.functionGetNumOfSocietyOwned,
                    "SOCIETY: the BiFunction to get the "
                            + "number of Society owned is unsetted!");
            return new Society(this);
        }

        private void doubleChecker(final Double value, final String string) {
            if (value.isInfinite() || value.isNaN()) {
                throw new IllegalArgumentException(string);
            }
        }
    }

    private Society(final Builder builder) {
        super(builder.decorated, builder.mortgageValue, builder.salesValue);
        this.multiplierLevelOne    = builder.multiplierLevelOne;
        this.multiplierLevelTwo    = builder.multiplierLevelTwo;
        this.supplierDiceResult    = builder.supplierDiceResult;
        this.functionGetNumOfSocietyOwned =
                builder.functionGetNumOfSocietyOwned;
    }

    @Override
    public Map<Integer, Double> getLeaseList() {
        return Map.of(Society.LEVEL_ONE,
                super.applyQuotationOnValue(this.multiplierLevelOne),
                Society.LEVEL_TWO,
                super.applyQuotationOnValue(this.getQuotation()));
    }

    @Override
    public double getLeaseValue() {
        if (super.getOwner().isPresent()) {
            final int nSociety = this.functionGetNumOfSocietyOwned.apply(
                    super.getOwner().get());

            if (Society.LEVEL_ONE.equals(nSociety)) {
                return super.applyQuotationOnValue(
                        this.multiplierLevelOne
                        * this.supplierDiceResult.get());

            } else if (Society.LEVEL_TWO.equals(nSociety)) {
                return super.applyQuotationOnValue(
                        this.multiplierLevelTwo
                        * this.supplierDiceResult.get());
            }
        }
        return Society.NO_LEASE;
    }

}
