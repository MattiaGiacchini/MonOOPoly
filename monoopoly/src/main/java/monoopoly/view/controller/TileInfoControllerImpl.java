package monoopoly.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import monoopoly.view.utilities.PurchasableState;

public class TileInfoControllerImpl implements TileInfoController, Initializable {

	@FXML
	private AnchorPane freeProperty;

	@FXML
	private AnchorPane myProperty;

	@FXML
	private AnchorPane ownedProperty;

	@Override
	public void showPropertyControlPane(PurchasableState state) {
		if (state.equals(PurchasableState.FREE_PROPERTY)) {
			this.freeProperty.toFront();
		} else if (state.equals(PurchasableState.MY_PROPERTY)) {
			this.myProperty.toFront();
		} else {
			this.ownedProperty.toFront();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.showPropertyControlPane(PurchasableState.MY_PROPERTY);
	}

	

}
