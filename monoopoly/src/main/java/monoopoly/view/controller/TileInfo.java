package monoopoly.view.controller;

import java.util.Map;
import java.util.Optional;

import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.TileViewCategory;

/**
 * This class represent a simple object with all the information of a tile to be
 * displayed on the view tileInfo pane.
 */
public class TileInfo {

    private final String tileName;
    private final String owner;
    private final PurchasableState state;
    private final TileViewCategory category;
    private final double currentPlayerBalance;

    private final int numHouses;
    private final Double houseCost;
    private final boolean isMortgaged;
    private final Double rentToPay;
    private final boolean rentPayed;
    private final Double purchasableValue;

    private final Optional<Map<Integer, Double>> rentValues;
    private final Double mortgageValue;
    private final Double unMortgageValue;
    private final boolean isBuildHouseEnabled;
    private final boolean isSellHouseEnabled;
    private final boolean isCurrentPlayerOnTile;

    /**
     * This class i a static builder to generate an object with the {@link Tile}
     * info to display.
     */
    public static class Builder {

        private String tileName;
        private String owner = "NONE";

        /**
         * State means if the property is owned by the current {@link Player}, if is
         * free or if is already owned.
         */
        private PurchasableState state = PurchasableState.OTHER;

        /**
         * Category indicates the typology of the tile to display.
         */
        private TileViewCategory category;
        private double currentPlayerBalance;
        private boolean isCurrentPlayerOnTile;
        private int numHouses;
        private Double houseCost = 0.00;
        private boolean isMortgaged;
        private Double rentToPay = 0.00;
        private boolean rentPayed;
        private Double purchasableValue = 0.00;
        private boolean isBuildHouseEnabled;
        private boolean isSellHouseEnabled;
        private Double mortgageValue = 0.00;
        private Double unMortgageValue = 0.00;

        /**
         * A map with the number of houses or stations ant the relative rent to pay.
         */
        private Optional<Map<Integer, Double>> rentValues = Optional.empty();

        /**
         * The builder function to set the tile name.
         * 
         * @param name of the tile.
         * @return {@link Builder}
         */
        public Builder tileName(final String name) {
            this.tileName = name;
            return this;
        }

        /**
         * The builder function to set the {@link PurchasableState}.
         * 
         * @param state to set
         * @return {@link Builder}
         */
        public Builder purchasableState(final PurchasableState state) {
            this.state = state;
            return this;
        }

        /**
         * The builder function to set the {@link TileViewCategory}.
         * 
         * @param category to set
         * @return {@link Builder}
         */
        public Builder purchasableCategory(final TileViewCategory category) {
            this.category = category;
            return this;
        }

        /**
         * The builder function to set the balance of the current {@link Player}.
         * 
         * @param balance to set
         * @return {@link Builder}
         */
        public Builder currentPlayerBalance(final Double balance) {
            this.currentPlayerBalance = balance;
            return this;
        }

        /**
         * The builder function to set if the current player is on the selected tile.
         * 
         * @param currentPlayerOnTile true or false.
         * @return {@link Builder}
         */
        public Builder currentPlayerOnSelectedTile(final boolean currentPlayerOnTile) {
            this.isCurrentPlayerOnTile = currentPlayerOnTile;
            return this;
        }

        /**
         * The builder function to set the number of houses built.
         * 
         * @param numHouses to set.
         * @return {@link Builder}
         */
        public Builder housesAmount(final int numHouses) {
            this.numHouses = numHouses;
            return this;
        }

        /**
         * The builder function to set the price to build a house.
         * 
         * @param housePrice to set.
         * @return {@link Builder}
         */
        public Builder housePrice(final Double housePrice) {
            this.houseCost = housePrice;
            return this;
        }

        /**
         * The builder function to set the mortgaging state of the porperty.
         * 
         * @param isMortgaged to set.
         * @return {@link Builder}
         */
        public Builder mortgageState(final boolean isMortgaged) {
            this.isMortgaged = isMortgaged;
            return this;
        }

        /**
         * The builder function to set the rent to be payed.
         * 
         * @param value to set.
         * @return {@link Builder}
         */
        public Builder rentToPay(final Double value) {
            this.rentToPay = value;
            return this;
        }

        /**
         * The builder function to set the {@link Purchasable} value for the purchase.
         * 
         * @param value to set.
         * @return {@link Builder}
         */
        public Builder purchasableValue(final Double value) {
            this.purchasableValue = value;
            return this;
        }

        /**
         * The builder function to set the tile owner name.
         * 
         * @param name to set
         * @return {@link Builder}
         */
        public Builder owner(final Optional<String> name) {
            if (!name.isEmpty()) {
                this.owner = name.get();
            }
            return this;
        }

        /**
         * The builder function to set the values for a property with multiple houses or
         * multiple stations.
         * 
         * @param rentValues to set.
         * @return {@link Builder}
         */
        public Builder rentValues(final Optional<Map<Integer, Double>> rentValues) {
            this.rentValues = rentValues;
            return this;
        }

        /**
         * The builder function to set the mortgage value.
         * 
         * @param mortgageValue to set.
         * @return {@link Builder}
         */
        public Builder mortgageValue(final Double mortgageValue) {
            this.mortgageValue = mortgageValue;
            return this;
        }

        /**
         * The builder function to set the price to remove a mortgage.
         * 
         * @param unMortgageValue to set.
         * @return {@link Builder}
         */
        public Builder unMortgageValue(final Double unMortgageValue) {
            this.unMortgageValue = unMortgageValue;
            return this;
        }

        /**
         * The builder function to set the possibility to build houses.
         * 
         * @param isBuildHouseEnabled to set.
         * @return {@link Builder}
         */
        public Builder buildHouseEnabled(final boolean isBuildHouseEnabled) {
            this.isBuildHouseEnabled = isBuildHouseEnabled;
            return this;
        }

        /**
         * The builder function to set the possibility to sell houses.
         * 
         * @param isSellHouseEnabled to set.
         * @return {@link Builder}
         */
        public Builder sellHouseEnabled(final boolean isSellHouseEnabled) {
            this.isSellHouseEnabled = isSellHouseEnabled;
            return this;
        }

        /**
         * The builder function to set if the {@link Player} has payed the rent in the
         * current {@link Purchasable}.
         * 
         * @param playerPayedRent to set.
         * @return {@link Builder}
         */
        public Builder rentPayed(final boolean playerPayedRent) {
            this.rentPayed = playerPayedRent;
            return this;
        }

        /**
         * @return {@link TileInfo}
         */
        public TileInfo build() {

            if (!this.category.equals(TileViewCategory.OTHER) && this.tileName.equals("")) {
                throw new IllegalStateException("Base tile info faulting");
            }

            return new TileInfo(tileName, owner, state, category, currentPlayerBalance, isCurrentPlayerOnTile,
                    numHouses, houseCost, isMortgaged, rentToPay, rentPayed, purchasableValue, rentValues,
                    mortgageValue, unMortgageValue, isBuildHouseEnabled, isSellHouseEnabled);
        }

    }

    /**
     * This method creates an object used to set the view info for a tile and in
     * order to apply button logics.
     * 
     * @param tileName             to set
     * @param owner                to set
     * @param state                to set
     * @param category             to set
     * @param currentPlayerBalance to set
     * @param currentPlayerOnTile  to set
     * @param numHouses            to set
     * @param houseCost            to set
     * @param isMortgaged          to set
     * @param rentToPay            to set
     * @param rentPayed            to set
     * @param purchasableValue     to set
     * @param rentValues           to set
     * @param mortgageValue        to set
     * @param unMortgageValue      to set
     * @param isBuildHouseEnabled  to set
     * @param isSellHouseEnabled   to set
     */
    public TileInfo(final String tileName, final String owner, final PurchasableState state,
            final TileViewCategory category, final double currentPlayerBalance, final boolean currentPlayerOnTile,
            final int numHouses, final Double houseCost, final boolean isMortgaged, final Double rentToPay,
            final boolean rentPayed, final Double purchasableValue, final Optional<Map<Integer, Double>> rentValues,
            final Double mortgageValue, final Double unMortgageValue, final boolean isBuildHouseEnabled,
            final boolean isSellHouseEnabled) {
        super();
        this.tileName = tileName;
        this.owner = owner;
        this.state = state;
        this.category = category;
        this.currentPlayerBalance = currentPlayerBalance;
        this.numHouses = numHouses;
        this.houseCost = houseCost;
        this.isMortgaged = isMortgaged;
        this.rentToPay = rentToPay;
        this.rentPayed = rentPayed;
        this.purchasableValue = purchasableValue;
        this.rentValues = rentValues;
        this.mortgageValue = mortgageValue;
        this.unMortgageValue = unMortgageValue;
        this.isBuildHouseEnabled = isBuildHouseEnabled;
        this.isSellHouseEnabled = isSellHouseEnabled;
        this.isCurrentPlayerOnTile = currentPlayerOnTile;
    }

    /**
     * This method returns the owner of the displayed {@link Purchasable}.
     * 
     * @return the owner
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * This method returns the category of the displayed {@link Tile}.
     * 
     * @return the category
     */
    public TileViewCategory getCategory() {
        return this.category;
    }

    /**
     * This method returns the amount of money to pay as rent.
     * 
     * @return the rentToPay
     */
    public Double getRentToPay() {
        return this.rentToPay;
    }

    /**
     * This method returns the rent to pay related to the number of houses or
     * stations.
     * 
     * @param index of the houses or stations number.
     * @return the rentValue for a specific index (amount of houses/stations)
     */
    public Double getRentValue(final Integer index) {
        return this.rentValues.get().get(index);
    }

    /**
     * This method returns the value of the mortgage. It will be added to the
     * {@link Player}'s balance.
     * 
     * @return the mortgageValue
     */
    public Double getMortgageValue() {
        return this.mortgageValue;
    }

    /**
     * This method returns the value to pay in order to remove the mortgage from the
     * chosen {@link Purchasable}.
     * 
     * @return the unMortgageValue
     */
    public Double getUnMortgageValue() {
        return this.unMortgageValue;
    }

    /**
     * Returns the name of the {@link Tile} where the {@link Player} is standing on.
     * 
     * @return the tileName
     */
    public String getTileName() {
        return this.tileName;
    }

    /**
     * Returns the state of the purchasable {@link Tile} where the {@link Player} is
     * standing on.
     * 
     * @return the state
     */
    public PurchasableState getState() {
        return this.state;
    }

    /**
     * Returns the current {@link Player} balance.
     * 
     * @return the currentPlayerBalance
     */
    public double getCurrentPlayerBalance() {
        return this.currentPlayerBalance;
    }

    /**
     * Returns if the current player is on the selected tile.
     * 
     * @return true if the current player is on the selected tile.
     */
    public boolean isCurrentPlayerOnSelectedTile() {
        return this.isCurrentPlayerOnTile;
    }

    /**
     * Returns the number of houses of the property where the {@link Player} is
     * standing on.
     * 
     * @return the number of houses
     */
    public int getHousesAmount() {
        return this.numHouses;
    }

    /**
     * This method returns the price to build a house in the tile where the
     * {@link Player} is standing on.
     * 
     * @return the houseCost
     */
    public Double getHouseCost() {
        return this.houseCost;
    }

    /**
     * This method is used to know if is possible to build a house on a specific
     * {@link Property}.
     * 
     * @return the isBuildHouseEnabled
     */
    public boolean isBuildHouseEnabled() {
        return this.isBuildHouseEnabled;
    }

    /**
     * This method is used to know if is possible to sella a house from a
     * {@link Property}.
     * 
     * @return the isSellHouseEnabled
     */
    public boolean isSellHouseEnabled() {
        return this.isSellHouseEnabled;
    }

    /**
     * This method is used to know if the {@link Purchasable} where the player is
     * standing on is mortgaged or not.
     * 
     * @return the true if the {@link Purchasable} is mortgaged, false otherwise.
     */
    public boolean isMortgaged() {
        return this.isMortgaged;
    }

    /**
     * Returns the value of the free {@link Purchasable} tile.
     * 
     * @return the purchasableValue
     */
    public Double getPurchasableValue() {
        return this.purchasableValue;
    }

    /**
     * This method returns true if the player has payed the rent, false otherwise.
     * 
     * @return true if the player has payed the rent, false otherwise.
     */
    public boolean rentPayed() {
        return this.rentPayed;
    }

}
