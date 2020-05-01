package monoopoly.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import monoopoly.model.player.Player;
import monoopoly.view.utilities.ButtonLogic;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class TileInfoControllerImpl implements TileInfoController, Initializable {

	private ButtonLogic logics = new ButtonLogicsImpl();
	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@FXML
	private Label propertyName;

	/*
	 * Free property controller pane
	 */
	@FXML
	private AnchorPane freeProperty;

	@FXML
	private Button buyPurchasable;

	@FXML
	private Button onAuctionPurchasable;

	/*
	 * My property controller pane
	 */
	@FXML
	private AnchorPane myProperty;

	@FXML
	private Button buildHouse;

	@FXML
	private Button sellHouse;

	@FXML
	private Button mortgage;

	@FXML
	private Button unMortgage;

	/*
	 * Owned property controller pane
	 */
	@FXML
	private AnchorPane ownedProperty;

	@FXML
	private Label rentValue;

	@FXML
	private Button payRent;

	/*
	 * Empty property controller pane
	 */
	@FXML
	private AnchorPane empty;

	@FXML
	private Label purchasableValue;

	@Override
	public void showPropertyControlPane(TileInfo info) {
		this.show(info);
	}

	/**
	 * This method shows the tile control pane based on the topology of tile where
	 * the {@link Player} is standing on
	 * 
	 * @param info
	 */
	private void show(final TileInfo info) {

		this.propertyName.setText(info.getTileName());

		if (info.getState().equals(PurchasableState.FREE_PROPERTY)) {
			this.freePropertyButtonsLogic(info);
		} else if (info.getState().equals(PurchasableState.MY_PROPERTY)) {
			this.myPropertyButtonsLogic(info);
		} else if (info.getState().equals(PurchasableState.OWNED_PROPERTY)) {
			this.ownedPropertyButtonsLogic(info);
		} else {
			this.empty.toFront();
		}

	}

	/**
	 * This method sets the buttons enabled or disabled based on {@link TileInfo}
	 * values and updates the labels
	 * 
	 * @param info
	 */
	private void ownedPropertyButtonsLogic(TileInfo info) {
		this.payRent.setDisable(!this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getRentValue()));
		this.rentValue.setText(this.utilities.toMoneyString(info.getRentValue()));
		this.ownedProperty.toFront();
	}

	/**
	 * This method sets the buttons enabled or disabled based on {@link TileInfo}
	 * values and updates the labels
	 * 
	 * @param info
	 */
	private void myPropertyButtonsLogic(TileInfo info) {
		this.buildHouse.setDisable(!(this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getHouseCost())
				&& this.logics.maxHouses(info.getNumHouses())));
		this.sellHouse.setDisable(!this.logics.minHouses(info.getNumHouses()));

		this.mortgage.setDisable(!info.isMortgaged());
		this.unMortgage.setDisable(info.isMortgaged());

		this.myProperty.toFront();
	}

	/**
	 * This method sets the buttons enabled or disabled based on {@link TileInfo}
	 * values and updates the labels
	 * 
	 * @param info
	 */
	private void freePropertyButtonsLogic(TileInfo info) {
		this.purchasableValue.setText(this.utilities.toMoneyString(info.getPurchasableValue()));
		this.buyPurchasable
				.setDisable(this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getPurchasableValue()));
		this.freeProperty.toFront();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
