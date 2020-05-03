package monoopoly.view.controller;

import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;
import monoopoly.view.utilities.PurchasableState;

public class TileInfo {

	private String tileName;
	private PurchasableState state;
	private double currentPlayerBalance;

	private int numHouses = 0;
	private Double houseCost = 0.00;
	private boolean isMortgaged = false;
	private Double rentValue = 0.00;
	private Double purchasableValue = 0.00;

	public static class Builder {

		private String tileName;
		private PurchasableState state;
		private double currentPlayerBalance;

		private int numHouses = 0;
		private Double housePrice = 0.00;
		private boolean isMortgaged = false;
		private Double rentValue = 0.00;
		private Double purchasableValue = 0.00;

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

		public Builder currentPlayerBalance(final Double balance) {
			this.currentPlayerBalance = balance;
			return this;
		}

		public Builder numHouses(final int numHouses) {
			this.numHouses = numHouses;
			return this;
		}

		public Builder housePrice(final Double housePrice) {
			this.housePrice = housePrice;
			return this;
		}

		public Builder mortgage(final boolean isMortgaged) {
			this.isMortgaged = isMortgaged;
			return this;
		}

		public Builder rentValue(final Double value) {
			this.rentValue = value;
			return this;
		}

		public Builder purchasableValue(final Double value) {
			this.purchasableValue = value;
			return this;
		}

		public TileInfo build() {

			if (this.tileName.isBlank() || this.state.equals(null)) {
				throw new IllegalStateException("Base tile info faulting");
			}

			return new TileInfo(tileName, state, currentPlayerBalance, numHouses, housePrice, isMortgaged, rentValue,
					purchasableValue);

		}

	}

	/**
	 * This private method modifies the fields of this, in order to apply button
	 * logics
	 * 
	 * @param tileName
	 * @param state
	 * @param currentPlayerBalance
	 * @param numHouses
	 * @param housePrice
	 * @param isMortgaged
	 * @param rentValue
	 * @param purchasableValue
	 */
	public TileInfo(String tileName, PurchasableState state, double currentPlayerBalance, int numHouses,
			Double housePrice, boolean isMortgaged, Double rentValue, Double purchasableValue) {
		super();
		this.tileName = tileName;
		this.state = state;
		this.currentPlayerBalance = currentPlayerBalance;
		this.numHouses = numHouses;
		this.isMortgaged = isMortgaged;
		this.rentValue = rentValue;
		this.purchasableValue = purchasableValue;
	}

	/**
	 * Returns the name of the {@link Tile} where the {@link Player} is standing on
	 * 
	 * @return the tileName
	 */
	public String getTileName() {
		return tileName;
	}

	/**
	 * Returns the state of the purchasable {@link Tile} where the {@link Player} is
	 * standing on
	 * 
	 * @return the state
	 */
	public PurchasableState getState() {
		return state;
	}

	/**
	 * Returns the current {@link Player} balance
	 * 
	 * @return the currentPlayerBalance
	 */
	public double getCurrentPlayerBalance() {
		return currentPlayerBalance;
	}

	/**
	 * Returns the number of houses of the property where the {@link Player} is
	 * standing on
	 * 
	 * @return the number of houses
	 */
	public int getNumHouses() {
		return numHouses;
	}

	/**
	 * This method returns the price to build a house in the tile where the
	 * {@link Player} is standing on
	 * 
	 * @return the houseCost
	 */
	public Double getHouseCost() {
		return houseCost;
	}

	/**
	 * This method is used to know if the {@link Purchasable} where the player is
	 * standing on is mortgaged or not
	 * 
	 * @return the true if the {@link Purchasable} is mortgaged, false otherwise.
	 */
	public boolean isMortgaged() {
		return isMortgaged;
	}

	/**
	 * Returns the amount of money that the {@link Player} must pay to the property
	 * owner
	 * 
	 * @return the rentValue
	 */
	public Double getRentValue() {
		return rentValue;
	}

	/**
	 * Returns the value of the free {@link Purchasable} tile
	 * 
	 * @return the purchasableValue
	 */
	public Double getPurchasableValue() {
		return purchasableValue;
	}

}
