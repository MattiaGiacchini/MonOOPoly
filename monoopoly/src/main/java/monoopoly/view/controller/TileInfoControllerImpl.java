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
import monoopoly.view.utilities.ButtonLogicImpl;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.TileViewCategory;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

public class TileInfoControllerImpl implements TileInfoController, Initializable {

	private ButtonLogic logics = new ButtonLogicImpl();
	private ViewUtilities utilities = new ViewUtilitiesImpl();

	@FXML
	private Label propertyName;

	/*
	 * Free property controller pane
	 */
	@FXML
	private AnchorPane freeProperty;

	@FXML
	private Label purchasableValue;

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
	private AnchorPane emptyControl;

	@FXML
	private AnchorPane emptyInfo;

	/*
	 * General tile info
	 */
	@FXML
	private Label propertyOwner;

	@FXML
	private Label propertyValue;

	@FXML
	private Label mortgageValue;

	@FXML
	private Label unMortgageValue;

	/*
	 * Property information pane
	 */
	@FXML
	private AnchorPane property;

	@FXML
	private Label houseNumber;

	@FXML
	private Label hotelNumber;

	@FXML
	private Label baseRent;

	@FXML
	private Label rentOneHouse;

	@FXML
	private Label rentTwoHouse;

	@FXML
	private Label rentThreeHouse;

	@FXML
	private Label rentFourHouse;

	@FXML
	private Label rentOneHotel;

	@FXML
	private Label houseCost;

	/*
	 * Station information pane
	 */
	@FXML
	private AnchorPane station;

	@FXML
	private Label rentOneStation;

	@FXML
	private Label rentTwoStation;

	@FXML
	private Label rentThreeStation;

	@FXML
	private Label rentFourStation;

	/*
	 * Station information pane
	 */
	@FXML
	private AnchorPane society;

	@Override
	public void showPropertyPane(TileInfo info) {
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
		this.propertyOwner.setText(info.getOwner());
		this.propertyValue.setText(this.utilities.toMoneyString(info.getPurchasableValue()));
		this.mortgageValue.setText(this.utilities.toMoneyString(info.getMortgageValue()));
		this.unMortgageValue.setText(this.utilities.toMoneyString(info.getUnMortgageValue()));

		if (info.getState().equals(PurchasableState.FREE_PROPERTY)) {
			this.freePropertyButtonsLogic(info);
		} else if (info.getState().equals(PurchasableState.MY_PROPERTY)) {
			this.myPropertyButtonsLogic(info);
		} else if (info.getState().equals(PurchasableState.OWNED_PROPERTY)) {
			this.ownedPropertyButtonsLogic(info);
		} else {
			this.emptyControl.toFront();
		}

		if (info.getCategory().equals(TileViewCategory.SOCIETY)) {
			this.showSocietyInfo(info);
		} else if (info.getCategory().equals(TileViewCategory.STATION)) {
			this.showStationInfo(info);
		} else if (info.getCategory().equals(TileViewCategory.PROPERTY)) {
			this.showPropertyInfo(info);
		} else {
			this.emptyInfo.toFront();
			this.property.toFront();
		}
	}

	/**
	 * This method updates the informations displayed about the chosen society
	 * 
	 * @param info
	 */
	private void showSocietyInfo(TileInfo info) {
		this.society.toFront();
	}

	/**
	 * This method updates the informations displayed about the chosen station
	 * 
	 * @param info
	 */
	private void showStationInfo(TileInfo info) {
		this.rentOneStation.setText(this.utilities.toMoneyString(info.getRentValue(1)));
		this.rentTwoStation.setText(this.utilities.toMoneyString(info.getRentValue(2)));
		this.rentThreeStation.setText(this.utilities.toMoneyString(info.getRentValue(3)));
		this.rentFourStation.setText(this.utilities.toMoneyString(info.getRentValue(4)));
		this.station.toFront();
	}

	/**
	 * This method updates the informations displayed about the chosen property
	 * 
	 * @param info
	 */
	private void showPropertyInfo(TileInfo info) {
		this.houseNumber.setText(String.valueOf(info.getHousesAmount()));
		this.hotelNumber.setText(info.getHousesAmount() > 4 ? String.valueOf(1) : String.valueOf(0));
		this.baseRent.setText(this.utilities.toMoneyString(info.getRentValue(0)));
		this.rentOneHouse.setText(this.utilities.toMoneyString(info.getRentValue(1)));
		this.rentTwoHouse.setText(this.utilities.toMoneyString(info.getRentValue(2)));
		this.rentThreeHouse.setText(this.utilities.toMoneyString(info.getRentValue(3)));
		this.rentFourHouse.setText(this.utilities.toMoneyString(info.getRentValue(4)));
		this.rentOneHotel.setText(this.utilities.toMoneyString(info.getRentValue(5)));
		this.houseCost.setText(this.utilities.toMoneyString(info.getHouseCost()));
		this.property.toFront();
	}

	/**
	 * This method sets the buttons enabled or disabled based on {@link TileInfo}
	 * values and updates the labels
	 * 
	 * @param info
	 */
	private void ownedPropertyButtonsLogic(TileInfo info) {
		this.payRent.setDisable(!this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getRentToPay()));
		this.rentValue.setText(this.utilities.toMoneyString(info.getRentToPay()));
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
				&& this.logics.maxHouses(info.getHousesAmount())));
		this.sellHouse.setDisable(!this.logics.minHouses(info.getHousesAmount()));

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
				.setDisable(!this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getPurchasableValue()));
		this.freeProperty.toFront();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		this.show(new TileInfo.Builder().currentPlayerBalance(5200.00).housePrice(20.00).mortgage(false).numHouses(4)
//				.purchasableValue(250.00).rentValue(47000.00).tileName("Parco della Vittoria")
//				.purchasableState(PurchasableState.FREE_PROPERTY).build());
	}

}
