package monoopoly.model.table.tile.purchasable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import monoopoly.model.table.tile.Tile;

/**
 * the class Station is a Decorator of Tile.
 */
public final class Station extends AbstractPurchasable {

    private static final Integer CORRECTION = 1;
    private static final Integer ZERO = 0;
    private static final Double  BASE = 2.0;
    private final Double leaseBase;
    private final Integer numOfStations;
    private final Function<Integer, Integer> funToRetriveNumOfStationOwned;

    /**
     * This nested static class is used
     * to create a new instance of Society.
     */
    public static class Builder {

        private Tile decorated;
        private Double mortgageValue;
        private Double salesValue;
        private Double lease;
        private Integer numOfStations;
        private Function<Integer, Integer> funToRetriveNumOfStationOwned;

        /**
         * This nested static class is used
         * to create a new instance of Station.
         */
        public Builder() {
            this.decorated     = null;
            this.mortgageValue = null;
            this.salesValue    = null;
            this.lease         = null;
            this.numOfStations = null;
            this.funToRetriveNumOfStationOwned = null;
        }

        /**
         * this method is used to set the tile to decore.
         *
         * @param decorated is the tile to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder tile(final Tile decorated) {
            Objects.requireNonNull(decorated,
                    "Tile To decore cannot has null value!");
            if (decorated.getCategory() != Tile.Category.STATION) {
                throw new IllegalArgumentException("the Tile isn't a Society");
            }
            this.decorated = decorated;
            return this;
        }

        /**
         * this method is used to get the function to retrieve the number
         * of station owned by a specific player.
         *
         * @param funToGetNumbOfTypeOwned to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder funNumOfCatOwned(final Function<Integer, Integer>
        funToGetNumbOfTypeOwned) {
            Objects.requireNonNull(funToGetNumbOfTypeOwned,
                    "The function to retrive the number of station owner "
                            + "cannot has null value!");
            this.funToRetriveNumOfStationOwned = funToGetNumbOfTypeOwned;
            return this;
        }

        /**
         * this method is used to set the Mortgage Value of Station.
         *
         * @param mortgageValue of station
         * @return {@link Builder} for a fluent programming
         */
        public Builder mortgage(final Double mortgageValue) {
            Objects.requireNonNull(mortgageValue,
                    "The mortgage Value cannot has null value!");
            this.doubleChecker(mortgageValue,
                    "The morgage value double wrong format!");
            this.mortgageValue = mortgageValue;
            return this;
        }

        /**
         * this method is used to set the sales value of station.
         *
         * @param salesValue of station
         * @return {@link Builder} for a fluent programming
         */
        public Builder sales(final Double salesValue) {
            Objects.requireNonNull(salesValue,
                    "The Sales Value cannot has null value!");
            this.doubleChecker(salesValue,
                    "The sales value double wrong format!");
            this.salesValue = salesValue;
            return this;
        }

        /**
         * this method is used to set the base lease value of station.
         *
         * @param lease base of station
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseOneStation(final Double lease) {
            Objects.requireNonNull(lease,
                    "The lease Value cannot has null value!");
            this.doubleChecker(lease, "the lease value double wrong format!");
            this.lease = lease;
            return this;
        }

        /**
         * this method is used to set the number of stations inside the table.
         *
         * @param numberOfStation to set
         * @return {@link Builder} for a fluent programming
         */
        public Builder numOfStations(final Integer numberOfStation) {
            Objects.requireNonNull(numberOfStation,
                    "The number of station cannot has null value!");
            this.numOfStations = numberOfStation;
            return this;
        }

        /**
         * This method is used to create the instance of
         * {@link Station} using all parameters you already pass
         * to the {@link Builder}.
         *
         * @return the Instance of {@link Station}
         * @throws NullPointerException if parameters are
         *         not all set
         */
        public Station build() {
            Objects.requireNonNull(this.decorated,
                    "STATION: Card to decor is unsetted!");
            Objects.requireNonNull(this.mortgageValue,
                    "STATION: the mortgage value is unsetted!");
            Objects.requireNonNull(this.salesValue,
                    "STATION: The sales Value is unsetted!");
            Objects.requireNonNull(this.lease,
                    "STATION: the lease value is Unsetted!");
            Objects.requireNonNull(this.numOfStations,
                    "STATION: the num of stations is Unsetted!");
            Objects.requireNonNull(this.funToRetriveNumOfStationOwned,
                    "STATION: the function to retrive the "
                            + "number of station owned is unsetted!");
            return new Station(this);
        }

        private void doubleChecker(final Double value, final String string) {
            if (value.isNaN() || value.isInfinite()) {
                throw new IllegalArgumentException(string);
            }

        }

    }

    private Station(final Builder builder) {
        super(builder.decorated, builder.mortgageValue, builder.salesValue);
        this.leaseBase = builder.lease;
        this.numOfStations = builder.numOfStations;
        this.funToRetriveNumOfStationOwned =
                builder.funToRetriveNumOfStationOwned;
    }

    @Override
    public Double getLeaseValue() {
        final int counter = getNumberOfStationOwned();
        if (counter == 0) {
            return 0.0;
        } else {
            return super.applyQuotationOnValue(this.leaseBase
                    * Math.pow(Station.BASE, counter - Station.CORRECTION));
        }
    }

    @Override
    public Map<Integer, Double> getLeaseList() {
        final Map<Integer, Double> map = new HashMap<>();
        for (Integer i = Station.ZERO;  i < this.numOfStations; i++) {
            map.put(i + Station.CORRECTION,
                    super.applyQuotationOnValue(
                            this.leaseBase * Math.pow(Station.BASE, i)));
        }
        return map;
    }

    private int getNumberOfStationOwned() {
        return super.getOwner().isEmpty() ? Station.ZERO
                : this.funToRetriveNumOfStationOwned.apply(
                        super.getOwner().get());
    }

}
