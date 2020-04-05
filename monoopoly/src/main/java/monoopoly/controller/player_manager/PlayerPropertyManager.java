package monoopoly.controller.player_manager;

import java.util.Set;

import javax.annotation.Nonnull;

import monoopoly.model.player.Player;

/**
 * This interface manages the {@link Player}'s {@link Purchasable} tiles. All of
 * these methods will be invoked by {@link PlayerManager}.
 */
public interface PlayerPropertyManager {

	/**
	 * Allows the {@link Player} to build a house or a hotel on the chosen property.
	 * 
	 * @param property where to build on a house or a hotel
	 */
	public void buildBuilding(Property property);

	/**
	 * Allows the {@link Player} sell a house or a hotel from the chosen
	 * {@link Property}.
	 * 
	 * @param property where to sell a house or a hotel
	 */
	public void sellBuilding(Property property);

	/**
	 * Allows the {@link Player} to buy a specific free {@link Purchasable}.
	 * 
	 * @param purchasableTile table tile marketable to buy if not already owned by
	 *                        other players
	 */
	public void buyPurchasable(Purchasable purchasableTile);

	/**
	 * Allows the {@link Player} to sell a specific {@link Purchasable} if has no
	 * houses built on it.
	 * 
	 * @param purchasableTile table tile marketable to sell
	 */
	public void sellPurchasable(Purchasable purchasableTile);

	/**
	 * Allows a {@link Player} to mortgage a specific non-null, owned
	 * {@link Purchasable} tile with no houses built on. The {@link Player} will
	 * receive a monetary settlement
	 * 
	 * @param purchasableTile table tile marketable to mortgage
	 */
	public void mortgagePurchasable(Purchasable purchasableTile);

	/**
	 * Allows a {@link Player} to revoke mortgage from a specific non-null and owned
	 * Purchasable tile paying a share of money
	 * 
	 * @param purchasableTile table tile marketable to be released from mortgage
	 */
	public void unMortgagePurchasable(Purchasable purchasableTile);
	
	/**
	 * Returns the list of {@link Purchasable}s owned by the {@link Player}
	 * 
	 * @return the list of {@link Purchasable}s owned by the {@link Player}
	 */
	public Set<Purchasable> getProperties();

}
