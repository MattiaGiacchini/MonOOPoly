package monoopoly.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;

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
					Button button = this.getChild(pane, i, j);
					String name = table.getTile(this.getBoardPosition(j, i, 20)).getName().toUpperCase();
					button.setText(name);
				}

			}
		}
	}

	private Button getChild(GridPane pane, int row, int column) {
		for (Node button : pane.getChildren()) {
			if (GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == column)
				return (Button) button;
		}
		return null;
	}

}