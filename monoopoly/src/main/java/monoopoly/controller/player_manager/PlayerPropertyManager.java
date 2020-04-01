package monoopoly.controller.player_manager;

import javax.annotation.Nonnull;

/**
 * This interface manages the {@link Player}'s {@link Purchasable} tiles. All of
 * these methods will be invoked by {@link PlayerManager}.
 */
interface PlayerPropertyManager {

	/**
	 * Is a method that could add or remove houses/hotel to a specific
	 * {@link Property} Hotels are considered as a "fifth house".
	 * 
	 * 
	 * @param property  is the {@link Property} that is going to be modified
	 * @param variation is the number (positive or negative) of houses to
	 *                  add/remove.
	 */
	public void updateHouseNumber(@Nonnull Property property, int variation);

	/**
	 * Allows a {@link Player} to buy a specific non-null and free Purchasable tile
	 * 
	 * @param purchasableTile to be purchased, if not already owned by other players
	 */
	public void buyProperty(@Nonnull Purchasable purchasableTile);

	/**
	 * Allows a {@link Player} to sell a specific owned Purchasable tile, only if
	 * has no houses built on
	 * 
	 * @param purchasableTile to be sold
	 */
	public void sellProperty(@Nonnull Purchasable purchasableTile);

	/**
	 * Allows a {@link Player} to mortgage a specific non-null, owned Purchasable
	 * tile with no houses built on. the player will receive a monetary settlement
	 * 
	 * @param purchasableTile to mortgage
	 */
	public void mortgageProperty(@Nonnull Purchasable purchasableTile);

	/**
	 * Allows a {@link Player} to revoke mortgage from a specific non-null and owned
	 * Purchasable tile paying a share of money
	 * 
	 * @param purchasableTile to be freed by mortgage
	 */
	public void unMortgageProperty(@Nonnull Purchasable purchasableTile);

}
