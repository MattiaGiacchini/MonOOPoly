package monoopoly.view.utilities;

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

public class ViewUtilitiesImpl implements ViewUtilities {

	private Table table = new TableImpl();

	@Override
	public int getBoardPosition(int x, int y, int totCells) {

		if (x == 0 || y == totCells / 2) {
			return totCells - (x + y);
		} else {
			return totCells + (x + y);
		}
	}

	@Override
	public void initializeBoard(GridPane pane) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if (i == 0 || j == 0 || i == 10 || j == 10) {
					// TODO Devo modificare la condizione per prendere la posizione (usando le
					// dimensioni del pane passato come parametro)
					Button button = this.getChild(pane, i, j);
					String name = table.getTile(this.getBoardPosition(j, i, 20)).getName().toUpperCase();
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

}