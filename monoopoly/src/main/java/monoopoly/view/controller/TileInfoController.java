package monoopoly.view.controller;

import monoopoly.view.controller.TileInfo;

public interface TileInfoController {

	/**
	 * This method is used to display the correct pane from stack pane according to
	 * the property owner
	 * 
	 * @param {@link TileInfo} to be displayed
	 */
	void showPropertyPane(TileInfo info);

}
