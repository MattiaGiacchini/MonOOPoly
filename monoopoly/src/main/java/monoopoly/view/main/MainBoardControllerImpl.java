package monoopoly.view.main;

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

public class MainBoardControllerImpl implements Initializable, MainBoardController {

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
	@Override
	public void quitButtonPressed(ActionEvent event) {
		this.utilities.closeApp(event);
	}

	@Override
	@FXML
	public void rollDicesButtonPressed() {
		this.nextTurn.setDisable(false);
		this.rollDices.setDisable(true);
	}

	@FXML
	@Override
	public void nextTurnButtonPressed() {
		this.nextTurn.setDisable(true);
		this.rollDices.setDisable(false);
	}

	@FXML
	@Override
	public void surrenderButtonPressed() {
		// TODO
	}

}
