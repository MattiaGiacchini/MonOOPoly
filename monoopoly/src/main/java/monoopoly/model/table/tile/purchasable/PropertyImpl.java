package monoopoly.model.table.tile.purchasable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import monoopoly.model.table.tile.Tile;

/**
 * The class PropertyImpl is a decorator of Tile and represents all tile
 * buildable.
 */
public final class PropertyImpl extends AbstractPurchasable
implements Property {

    private static final Double  PERCENTAGE_TO_APPLY_FOR_SELLING = 0.5;
    private static final Double  VALUE_ZERO = 0.0;
    private static final Integer UNIT_TO_INCREASE_OR_DECREASE = 1;
    private static final Integer PROPERTY_WITHOUT_BUILDINGS = 0;
    private static final Integer MAX_NUMBER_OF_HOUSE = 4;
    private static final Integer MAX_NUMBER_OF_HOTEL = 1;
    private static final Integer SERIES_COMPLETE = 6;

    private final Predicate<Integer> areCategoryAllOwned;
    private final Map<Integer, Double> leaseListBaseValue;
    private final Double valueTobuildHouse;
    private final Double valueTobuildHotel;

    private Integer numberOfConstructionBuilt;


    /**
     * This nested static class is used
     * to create a new instance of PropertyImpl.
     */
    public static class Builder {

        private static final Integer LEASE_NO_BUILDING = 0;
        private static final Integer LEASE_ONE_HOUSE = 1;
        private static final Integer LEASE_TWO_HOUSE = 2;
        private static final Integer LEASE_THREE_HOUSE = 3;
        private static final Integer LEASE_FOUR_HOUSE = 4;
        private static final Integer LEASE_ONE_HOTEL = 5;
        private static final Integer LEASE_SERIES_COMPLETE = 6;
        private static final Double  MULTIPLIER_SERIES_COMPLETE = 2.0;

        private final Map<Integer, Double> leaseMap;

        private Tile decorated;
        private Double mortgageValue;
        private Double salesValue;
        private Double valueToBuildHouse;
        private Double valueToBuildHotel;
        private Predicate<Integer> allCategoryOwned;

        /**
         * this is the {@link Builder}'s constructor.
         */
        public Builder() {
            this.leaseMap = new HashMap<>();
            this.valueToBuildHouse = null;
            this.valueToBuildHotel = null;
            this.decorated = null;
            this.allCategoryOwned = null;
            this.mortgageValue = null;
            this.salesValue = null;
        }

        /**
         * this method is used to set the Tile to decore with this property.
         *
         * @param decorated is the tile to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder tile(final Tile decorated) {
            Objects.requireNonNull(decorated,
                    "the tile to decore cannot has null value!");
            if (!decorated.isBuildable()) {
                throw new IllegalArgumentException("the Tile isn't Buildable");
            }
            this.decorated = decorated;
            return this;
        }

        /**
         * this method is used to pass the predicate to use for knowing
         * if the series of property is owned by the same owner.
         *
         * @param predicate to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder predAreAllPropOwned(final Predicate<Integer> predicate) {
            Objects.requireNonNull(predicate,
                    "The function cannot has null value!");
            this.allCategoryOwned = predicate;
            return this;
        }

        /**
         * this method is used to set the mortgage value.
         *
         * @param mortgageValue of property
         * @return {@link Builder} for a fluent programming
         */
        public Builder mortgage(final Double mortgageValue) {
            this.doubleChecker(mortgageValue, "Mortgage value");
            this.mortgageValue = mortgageValue;
            return this;
        }

        /**
         * this method is used to set the sales value.
         *
         * @param salesValue of property
         * @return {@link Builder} for a fluent programming
         */
        public Builder sales(final Double salesValue) {
            this.doubleChecker(salesValue, "Sales value");
            this.salesValue = salesValue;
            return this;
        }

        /**
         * this method is used to set the value of cost to
         * to build an house.
         *
         * @param value to pay for build an house
         * @return {@link Builder} for a fluent programming
         */
        public Builder valueToBuildHouse(final Double value) {
            this.doubleChecker(value, "cost to build house");
            this.valueToBuildHouse = value;
            return this;
        }

        /**
         * this method is used to set the value of cost to
         * to build the hotel.
         *
         * @param value to pay for build an hotel
         * @return {@link Builder} for a fluent programming
         */
        public Builder valueToBuildHotel(final Double value) {
            this.doubleChecker(value, "cost to build hotel");
            this.valueToBuildHotel = value;
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * no buildings built.
         *
         * @param value of lease without buildings
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithNoBuildings(final Double value) {
            this.doubleChecker(value, "lease without buildings");
            this.leaseMap.put(LEASE_NO_BUILDING, value);
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * one house built.
         *
         * @param value of lease with one house
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithOneHouse(final Double value) {
            this.doubleChecker(value, "lease with one house");
            this.leaseMap.put(LEASE_ONE_HOUSE, value);
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * two houses built.
         *
         * @param value of lease with two house
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithTwoHouse(final Double value) {
            this.doubleChecker(value, "lease with two house");
            this.leaseMap.put(LEASE_TWO_HOUSE, value);
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * three houses built.
         *
         * @param value of lease with three house
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithThreeHouse(final Double value) {
            this.doubleChecker(value, "lease with three house");
            this.leaseMap.put(LEASE_THREE_HOUSE, value);
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * four houses built.
         *
         * @param value of lease with four house
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithFourHouse(final Double value) {
            this.doubleChecker(value, "lease with four house");
            this.leaseMap.put(LEASE_FOUR_HOUSE, value);
            return this;
        }

        /**
         * this method is used to set the value of lease with
         * the hotel built.
         *
         * @param value of lease with hotel
         * @return {@link Builder} for a fluent programming
         */
        public Builder leaseWithOneHotel(final Double value) {
            this.doubleChecker(value, "lease with an hotel");
            this.leaseMap.put(LEASE_ONE_HOTEL, value);
            return this;
        }

        /**
         * this method is used to create the new instance of PropertyImpl
         * using ALL parameters passed before.
         *
         * @return {@link PropertyImpl}
         */
        public PropertyImpl build() {
            this.objectRequireNonNull(this.decorated, "Card to decore");
            this.objectRequireNonNull(this.allCategoryOwned, "bifunction");
            this.objectRequireNonNull(this.mortgageValue, "Mortgage value");
            this.objectRequireNonNull(this.salesValue, "Salses value");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_NO_BUILDING),
                    "Lease withoutBuildings");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_ONE_HOUSE),
                    "Lease with one house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_TWO_HOUSE),
                    "Lease with two house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_THREE_HOUSE),
                    "Lease with three house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_FOUR_HOUSE),
                    "Lease with four house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_ONE_HOTEL),
                    "Lease with one hotel");

            // the series complete has double value of lease with no buildings
            this.leaseMap.put(Builder.LEASE_SERIES_COMPLETE,
                    this.leaseMap.get(Builder.LEASE_NO_BUILDING)
                    * Builder.MULTIPLIER_SERIES_COMPLETE);

            return new PropertyImpl(this);
        }

        private void objectRequireNonNull(final Object obj,
                final String string) {
            Objects.requireNonNull(obj, "PROPERTY: " + string
                    + " is unsetted!");
        }

        private void doubleChecker(final Double value, final String string) {
            Objects.requireNonNull(value,
                    "the " + string + " cannot has null value!");
            if (value.isNaN() || value.isInfinite()) {
                throw new IllegalArgumentException("the " + string
                        + " hasn't a right format");
            }

        }

    }

    private PropertyImpl(final Builder builder) {
        super(builder.decorated, builder.mortgageValue, builder.salesValue);
        this.numberOfConstructionBuilt = PropertyImpl.
                PROPERTY_WITHOUT_BUILDINGS;
        this.leaseListBaseValue = builder.leaseMap;
        this.valueTobuildHouse = builder.valueToBuildHouse;
        this.valueTobuildHotel = builder.valueToBuildHotel;
        this.areCategoryAllOwned = builder.allCategoryOwned;
    }

    @Override
    public void buildOn() {
        if (!this.isCategoryOfPropertiesAllOwned()) {
            throw new IllegalStateException("The " + super.getCategory()
            + " Category hasn't the same owner in all properties");
        } else if (this.numberOfConstructionBuilt
                .equals(this.getMaxNumberOfBuildings())) {
            throw new IllegalStateException(
                    "Maximum number of buildings reached");
        }
        this.numberOfConstructionBuilt = this.numberOfConstructionBuilt
                + PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
    }

    @Override
    public Double sellBuilding() {
        if (this.numberOfConstructionBuilt.equals(
                PropertyImpl.PROPERTY_WITHOUT_BUILDINGS)) {
            throw new IllegalStateException(
                    "The property hasn't buildings to sell");
        }
        final Double buildingsValue = this.getQuotationToSellActualBuildings();
        this.numberOfConstructionBuilt = this.numberOfConstructionBuilt
                - PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
        return buildingsValue;
    }

    @Override
    public Integer getNumberOfHouseBuilt() {
        return this.numberOfConstructionBuilt
                <= PropertyImpl.MAX_NUMBER_OF_HOUSE
                ? this.numberOfConstructionBuilt
                        : PropertyImpl.MAX_NUMBER_OF_HOUSE;
    }

    @Override
    public Integer getNumberOfHotelBuilt() {
        return this.numberOfConstructionBuilt > PropertyImpl.MAX_NUMBER_OF_HOUSE
                ? PropertyImpl.MAX_NUMBER_OF_HOTEL
                        : PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
    }

    @Override
    public Double getCostToBuildHouse() {
        return super.applyQuotationOnValue(this.valueTobuildHouse);
    }

    @Override
    public Double getCostToBuildHotel() {
        return super.applyQuotationOnValue(this.valueTobuildHotel);
    }

    @Override
    public Double getQuotationToSellHouse() {
        return super.applyQuotationOnValue(this.getCostToBuildHouse()
                * PropertyImpl.PERCENTAGE_TO_APPLY_FOR_SELLING);
    }

    @Override
    public Double getQuotationToSellHotel() {
        return super.applyQuotationOnValue(this.getCostToBuildHotel()
                * PropertyImpl.PERCENTAGE_TO_APPLY_FOR_SELLING);
    }

    @Override
    public Double getLeaseValue() {
        if (super.getOwner().isPresent() && !super.isMortgage()) {
            if (this.isCategoryOfPropertiesAllOwned()
                    && this.numberOfConstructionBuilt.equals(
                            PropertyImpl.PROPERTY_WITHOUT_BUILDINGS)) {
                return super.applyQuotationOnValue(
                        this.leaseListBaseValue
                        .get(PropertyImpl.SERIES_COMPLETE));
            } else {
                return super.applyQuotationOnValue(
                        this.leaseListBaseValue.get(
                                this.numberOfConstructionBuilt));
            }
        }
        return PropertyImpl.VALUE_ZERO;
    }

    @Override
    public Map<Integer, Double> getLeaseList() {
        final Map<Integer, Double> listWithQuotationApplied = new HashMap<>();
        for (final var elem : this.leaseListBaseValue.entrySet()) {
            listWithQuotationApplied.put(elem.getKey(),
                    super.applyQuotationOnValue(
                            elem.getValue()));
        }
        return listWithQuotationApplied;
    }

    @Override
    public boolean isBuildOnEnabled() {
        return super.getOwner().isPresent()
                && this.areCategoryAllOwned.test(super.getOwner().get())
                && this.numberOfConstructionBuilt
                < this.getMaxNumberOfBuildings();
    }

    @Override
    public boolean isSellBuildingsEnabled() {
        return super.getOwner().isPresent()
                && this.numberOfConstructionBuilt
                > PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
    }


    private double getQuotationToSellActualBuildings() {
        if (this.getNumberOfHotelBuilt() > PropertyImpl.
                PROPERTY_WITHOUT_BUILDINGS) {
            return this.getQuotationToSellHotel();
        }
        return this.getQuotationToSellHouse();
    }

    private boolean isCategoryOfPropertiesAllOwned() {
        return super.getOwner().isPresent()
                && this.areCategoryAllOwned.test(super.getOwner().get());
    }

    private int getMaxNumberOfBuildings() {
        return PropertyImpl.MAX_NUMBER_OF_HOTEL
                + PropertyImpl.MAX_NUMBER_OF_HOUSE;
    }
}
