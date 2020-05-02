package monoopoly.view.utilities;

import java.util.List;

import javafx.event.Event;
import javafx.scene.layout.GridPane;
import monoopoly.model.item.Tile;
import monoopoly.utilities.Pair;

public interface ViewUtilities {

	/**
	 * This method initializes the game board setting all the names of the
	 * {@link Tile}s
	 * 
	 * @param pane      the board gridPane
	 * @param tileNames list of all tile names
	 */
	public void initializeBoard(GridPane pane, List<String> tileNames);

	/**
	 * This method shows a popUp to confirm the exit
	 * 
	 * @param event
	 */
	public void closeApp(Event event);

	/**
	 * This method returns the string representing the double value with 2 decimal
	 * digits
	 * 
	 * @param number to convert in string
	 * @return the string
	 */
	String toMoneyString(Double number);

	/**
	 * This methods returns the grid cell coordinates relatives to a position
	 * 
	 * @param position pawn position on the board
	 * @param pane     the gridPane where the pawns are located
	 * @return the coordinates
	 */
	Pair<Integer, Integer> getCoords(int position, GridPane pane);

	/**
	 * This method return the linear position of a cell in a grid
	 * 
	 * @param x        value
	 * @param y        value
	 * @param totCells number of cells
	 * @return the linear position
	 */
	int getBoardPosition(int x, int y, int totCells);

}