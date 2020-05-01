package monoopoly.view.utilities;

import java.text.DecimalFormat;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.utilities.Pair;

/**
 * This class is an utilities class for the view controllers
 */
public class ViewUtilitiesImpl implements ViewUtilities {

	private Table table = new TableImpl();
	private DecimalFormat format = new DecimalFormat("0.00");

	@Override
	public int getBoardPosition(int x, int y, int totCells) {
		if (x == 0 || y == totCells / 4) {
			return (totCells / 2) - (x + y);
		} else {
			return (totCells / 2) + (x + y);
		}
	}

	@Override
	public void initializeBoard(GridPane pane) {
		int rows = pane.getRowCount();
		int columns = pane.getColumnCount();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i == 0 || j == 0 || i == rows - 1 || j == columns - 1) {
					Button button = this.getChild(pane, i, j);
					String name = table.getTile(this.getBoardPosition(j, i, (rows - 1 + columns - 1) * 2)).getName()
							.toUpperCase();
					button.setText(name);
				}
			}
		}
	}

	@Override
	public void closeApp(Event event) {
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");

		Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
		Button remainButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
		exitButton.setText("Exit");
		remainButton.setText("Cancel");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);

		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if (ButtonType.OK.equals(closeResponse.get())) {
			Platform.exit();
		} else {
			event.consume();
		}

	};

	/**
	 * This method returns the button of the gridPane passed as parameter in a
	 * specific position defined by parameters
	 * 
	 * @param pane   GridPane where to get the child
	 * @param row    index
	 * @param column index
	 * @return the desired Button, if present
	 */
	private Button getChild(GridPane pane, int row, int column) {
		for (Node button : pane.getChildren()) {
			if (GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == column)
				return (Button) button;
		}
		return null;
	}

	@Override
	public String toMoneyString(Double number) {
		return "€ " + this.format.format(number);
	}

	@Override
	public Pair<Integer, Integer> getCoords(final int position, final GridPane pane) {
		int rows = pane.getRowCount() - 1;
		int columns = pane.getColumnCount() - 1;
		int totCells = (rows + columns) * 2;

		int x;
		int y;

		if (position <= totCells / 2) {
			if (position <= columns) {
				x = position;
				y = 0;
			} else {
				x = columns;
				y = position - columns;
			}
		} else {
			if (position >= totCells - columns) {
				x = 0;
				y = totCells - position;
			} else {
				x = totCells - rows;
				y = rows;
			}

		}
		return revertCoords(x, y, rows, columns);
	}

	/**
	 * This methods adapts the coordinates generated to the grid pane indexing
	 * convention
	 * 
	 * @param x       value
	 * @param y       value
	 * @param rows    number
	 * @param columns number
	 * @return the coordinates of the gridCell
	 */
	private Pair<Integer, Integer> revertCoords(int x, int y, int rows, int columns) {
		x = Math.abs(columns - x);
		y = Math.abs(rows - y);
		return new Pair<Integer, Integer>(x, y);
	}

}