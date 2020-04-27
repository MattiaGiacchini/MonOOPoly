package monoopoly.view.board;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import monoopoly.view.ViewUtilities;
import monoopoly.view.ViewUtilitiesImpl;

public class MainBoardController implements Initializable {

	@FXML
	private GridPane gridPane;

	@FXML
	private Button rollDices;

	@FXML
	private Button nextTurn;

	@FXML
	private Button surrender;

	@FXML
	private Label timer;

	@FXML
	private Label currentPlayer;

	@FXML
	private Label currentPlayerBalance;

	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.currentPlayer.setText("Mattia");
		this.currentPlayerBalance.setText("€ 500,00");
	}

	@FXML
	public void cellButtonPressed(ActionEvent event) {
		Button myButton = (Button) event.getSource();
		int index = this.utilities.getBoardPosition(GridPane.getColumnIndex(myButton), GridPane.getRowIndex(myButton),
				this.gridPane.getRowCount() + this.gridPane.getColumnCount() - 2);
		System.out.println("\t " + index);
	}

}
