package monoopoly.controller.player_manager;

import java.util.Optional;

/**
 * This class implements {@link PlayerPropertyManager} methods.
 */
public class PlayerPropertyManagerImpl implements PlayerPropertyManager {

	private final int playerID;

	public PlayerPropertyManagerImpl(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public void buildBuilding(Property property) {
		if (isPossibleToBuildBuilding(property)) {
			property.buildOn();
		}
	}

	@Override
	public void sellBuilding(Property property) {
		if (isPossibleToSellBuilding(property)) {
			property.sellBuilding();
		}
	}

	@Override
	public void buyPurchasable(Purchasable purchasableTile) {
		if (this.checkFreePurchasable(purchasableTile)) {
			purchasableTile.setOwner(this.playerID);
		}
	}

	@Override
	public void sellPurchasable(Purchasable purchasableTile) {
		if (this.checkOwner(purchasableTile)) {
			purchasableTile.setOwner(Optional.empty());
		}

	}

	@Override
	public void mortgagePurchasable(Purchasable purchasableTile) {
		if (this.checkOwner(purchasableTile)) {
			purchasableTile.mortgage();
		}

	}

	@Override
	public void unMortgagePurchasable(Purchasable purchasableTile) {
		if (this.checkOwner(purchasableTile)) {
			purchasableTile.removeMortgage();
		}

	}

	/**
	 * This method checks if is possible to sell a building on a specific
	 * {@link Property}
	 * 
	 * @param property where to sell a building
	 * @return true if is possible to sell, false if not
	 */
	private boolean isPossibleToSellBuilding(Property property) {
		return (property.getNumberOfHouseBuilt() + property.getNumberOfHotelBuilt() > 0 && this.checkOwner(property));
	}

	/**
	 * This method checks if is possible to build a building on a specific
	 * {@link Property}
	 * 
	 * @param property where to build a building
	 * @return true if is possible to build, false if not
	 */
	private boolean isPossibleToBuildBuilding(Property property) {
		return (property.getNumberOfHouseBuilt() + property.getNumberOfHotelBuilt() < 5 && this.checkOwner(property));
	}

	/**
	 * This method checks if the {@link Player} related to this manager is the owner
	 * of the {@link Purchasable} parameter
	 * 
	 * @param purchasable
	 * @return true if this {@link Player} is the owner of the {@link Purchasable}
	 */
	private boolean checkOwner(Purchasable purchasable) {
		return (purchasable.getOwner.equals(this.playerID));
	}

	/**
	 * This method checks if the {@link Purchasable} has no owner
	 * 
	 * @param purchasable to control owner
	 * @return true if the {@link Purchasable} has no owner
	 */
	private boolean checkFreePurchasable(Purchasable purchasable) {
		return (purchasable.getOwner.empty());
	}

}
