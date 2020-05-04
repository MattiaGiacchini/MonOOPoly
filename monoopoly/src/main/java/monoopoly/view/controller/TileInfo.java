package monoopoly.view.controller;

import java.util.Map;
import java.util.Optional;

import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.TileViewCategory;

/**
 * This class represent a simple object with all the information of a tile to be
 * displayed on the view tileInfo pane
 */
public class TileInfo {

	private String tileName;
	private String owner;
	private PurchasableState state;
	private TileViewCategory category;
	private double currentPlayerBalance;

	private int numHouses;
	private Double houseCost;
	private boolean isMortgaged;
	private Double rentToPay;
	private Double purchasableValue;

	private Optional<Map<Integer, Double>> rentValues;
	private Double mortgageValue;
	private Double unMortgageValue;

	public static class Builder {

		private String tileName;
		private String owner = "NONE";
		private PurchasableState state;
		private TileViewCategory category;
		private double currentPlayerBalance;

		private int numHouses = 0;
		private Double houseCost = 0.00;
		private boolean isMortgaged = false;
		private Double rentToPay = 0.00;
		private Double purchasableValue = 0.00;

		private Optional<Map<Integer, Double>> rentValues = Optional.empty();
		private Double mortgageValue = 0.00;
		private Double unMortgageValue = 0.00;

		public Builder() {

		}

		public Builder tileName(final String name) {
			this.tileName = name;
			return this;
		}

		public Builder purchasableState(final PurchasableState state) {
			this.state = state;
			return this;
		}

		public Builder purchasableState(final TileViewCategory category) {
			this.category = category;
			return this;
		}

		public Builder currentPlayerBalance(final Double balance) {
			this.currentPlayerBalance = balance;
			return this;
		}

		public Builder housesAmount(final int numHouses) {
			this.numHouses = numHouses;
			return this;
		}

		public Builder housePrice(final Double housePrice) {
			this.houseCost = housePrice;
			return this;
		}

		public Builder mortgage(final boolean isMortgaged) {
			this.isMortgaged = isMortgaged;
			return this;
		}

		public Builder rentToPay(final Double value) {
			this.rentToPay = value;
			return this;
		}

		public Builder purchasableValue(final Double value) {
			this.purchasableValue = value;
			return this;
		}

		public Builder owner(final String name) {
			this.owner = name;
			return this;
		}

		public Builder rentValues(final Optional<Map<Integer, Double>> rentValues) {
			this.rentValues = rentValues;
			return this;
		}

		public Builder mortgageValue(final Double mortgageValue) {
			this.mortgageValue = mortgageValue;
			return this;
		}

		public Builder unMortgageValue(final Double unMortgageValue) {
			this.unMortgageValue = unMortgageValue;
			return this;
		}

		public TileInfo build() {

			if (this.state.equals(null)
					|| (!this.state.equals(null) && (this.tileName.equals("") || this.owner.equals("")))) {
				throw new IllegalStateException("Base tile info faulting");
			}

			return new TileInfo(tileName, owner, state, category, currentPlayerBalance, numHouses, houseCost,
					isMortgaged, rentToPay, purchasableValue, rentValues, mortgageValue, unMortgageValue);
		}

	}

	/**
	 * This private method modifies the fields of this, in order to apply button
	 * logics
	 * 
	 * @param tileName
	 * @param owner
	 * @param state
	 * @param category
	 * @param currentPlayerBalance
	 * @param numHouses
	 * @param houseCost
	 * @param isMortgaged
	 * @param rentToPay
	 * @param purchasableValue
	 * @param rentValues
	 * @param mortgageValue
	 * @param unMortgageValue
	 */
	public TileInfo(String tileName, String owner, PurchasableState state, TileViewCategory category,
			double currentPlayerBalance, int numHouses, Double houseCost, boolean isMortgaged, Double rentToPay,
			Double purchasableValue, Optional<Map<Integer, Double>> rentValues, Double mortgageValue,
			Double unMortgageValue) {
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
		this.purchasableValue = purchasableValue;
		this.rentValues = rentValues;
		this.mortgageValue = mortgageValue;
		this.unMortgageValue = unMortgageValue;
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
		return mortgageValue;
	}

	/**
	 * This method returns the value to pay in order to remove the mortgage from the
	 * chosen {@link Purchasable}.
	 * 
	 * @return the unMortgageValue
	 */
	public Double getUnMortgageValue() {
		return unMortgageValue;
	}

	/**
	 * Returns the name of the {@link Tile} where the {@link Player} is standing on.
	 * 
	 * @return the tileName
	 */
	public String getTileName() {
		return tileName;
	}

	/**
	 * Returns the state of the purchasable {@link Tile} where the {@link Player} is
	 * standing on.
	 * 
	 * @return the state
	 */
	public PurchasableState getState() {
		return state;
	}

	/**
	 * Returns the current {@link Player} balance.
	 * 
	 * @return the currentPlayerBalance
	 */
	public double getCurrentPlayerBalance() {
		return currentPlayerBalance;
	}

	/**
	 * Returns the number of houses of the property where the {@link Player} is
	 * standing on.
	 * 
	 * @return the number of houses
	 */
	public int getHousesAmount() {
		return numHouses;
	}

	/**
	 * This method returns the price to build a house in the tile where the
	 * {@link Player} is standing on.
	 * 
	 * @return the houseCost
	 */
	public Double getHouseCost() {
		return houseCost;
	}

	/**
	 * This method is used to know if the {@link Purchasable} where the player is
	 * standing on is mortgaged or not.
	 * 
	 * @return the true if the {@link Purchasable} is mortgaged, false otherwise.
	 */
	public boolean isMortgaged() {
		return isMortgaged;
	}

	/**
	 * Returns the value of the free {@link Purchasable} tile.
	 * 
	 * @return the purchasableValue
	 */
	public Double getPurchasableValue() {
		return purchasableValue;
	}

}
