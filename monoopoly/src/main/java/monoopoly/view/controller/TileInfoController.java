package monoopoly.view.controller;

public interface TileInfoController {

	/**
	 * This method is used to display the correct pane from stack pane according to
	 * the property owner
	 * 
	 * @param state of the {@link Purchasable} tile
	 */
	void showPropertyControlPane(TileInfo info);

}
