package monoopoly.view.controller;

import monoopoly.view.utilities.PurchasableState;

public interface TileInfoController {

	/**
	 * This method is used to display the correct pane from stack pane according to
	 * the property owner
	 * 
	 * @param state of the {@link Purchasable} tile
	 */
	public void showPropertyControlPane(PurchasableState state);

}
