package monoopoly.view.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface MainBoardController {

	void quitButtonPressed(ActionEvent event);

	void rollDicesButtonPressed();

	void nextTurnButtonPressed();

	void surrenderButtonPressed();

}