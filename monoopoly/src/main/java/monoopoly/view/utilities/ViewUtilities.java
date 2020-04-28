package monoopoly.view;

import javafx.scene.layout.GridPane;

public interface ViewUtilities {

	/**
	 * This method return the linear position of a cell in a grid
	 * 
	 * @param x        value
	 * @param y        value
	 * @param totCells number of cells
	 * @return the linear position
	 */
	public int getBoardPosition(int x, int y, int totCells);

	/**
	 * This method initializes the game board setting all the names of the
	 * {@link Tile}s
	 * 
	 * @param pane the board gridPane
	 */
	public void initializeBoard(GridPane pane);

}