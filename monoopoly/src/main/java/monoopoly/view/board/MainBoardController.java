package monoopoly.view.board;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import monoopoly.view.controller.PlayerViewControllerImpl;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class MainBoardController implements Initializable {

	@FXML
	private Button rollDices;

	@FXML
	private Button nextTurn;

	@FXML
	private Button surrender;

	@FXML
	private Label timer;

	@FXML
	private PlayerViewControllerImpl currentPlayerController;

	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
	}

	@FXML
	public void quitButtonPressed(ActionEvent event) {
		this.utilities.closeApp(event);
	}

	@FXML
	public void rollDicesButtonPressed() {
		this.nextTurn.setDisable(false);
		this.rollDices.setDisable(true);
	}

	@FXML
	public void nextTurnButtonPressed() {
		this.nextTurn.setDisable(true);
		this.rollDices.setDisable(false);
	}

	@FXML
	public void surrenderButtonPressed() {
		// TODO
	}

}
