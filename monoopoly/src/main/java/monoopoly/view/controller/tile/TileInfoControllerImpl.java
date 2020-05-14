package monoopoly.view.controller.tile;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import monoopoly.engine.GameEngine;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.utilities.ButtonLogic;
import monoopoly.view.utilities.ButtonLogicImpl;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.TileViewCategory;
import monoopoly.view.utilities.ViewUtilities;
import monoopoly.view.utilities.ViewUtilitiesImpl;

/**
 * This class implements the methods to display the {@link Purchasable} info and
 * a controller pane to manage the {@link Purchasable} according to its owner.
 *
 * This class contains even the logic of the buttons activation.
 */
public class TileInfoControllerImpl implements TileInfoController, Initializable {

    private static final int MAX_NUM_HOUSES = 4;
    private static final int MAX_NUM_HOTEL = 1;
    private final ButtonLogic logics = new ButtonLogicImpl();
    private final ViewUtilities utilities = new ViewUtilitiesImpl();
    private GameEngine gameEngine;
    private boolean playerPayedRent = true;

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

    /**
     * This method builds a house over the chosen {@link Purchasable} when the
     * button is pressed.
     */
    @FXML
    public void buildHouseButtonPressed() {
        this.gameEngine.buildHouse();
    }

    /**
     * This method sells a house from the chosen {@link Purchasable} when the button
     * is pressed.
     */
    @FXML
    public void sellHouseButtonPressed() {
        this.gameEngine.sellHouse();
    }

    /**
     * This method mortgages the chosen {@link Purchasable} when the button is
     * pressed.
     */
    @FXML
    public void mortgageButtonPressed() {
        this.gameEngine.mortgage();
    }

    /**
     * This method remove the mortgage from the chosen {@link Purchasable} when the
     * button is pressed.
     */
    @FXML
    public void removeMortgageButtonPressed() {
        this.gameEngine.unMortgage();
    }

    /**
     * This method buys the chosen {@link Purchasable} when the button is pressed.
     */
    @FXML
    public void buyPurchasableButtonPressed() {
        this.buyPurchasable.setDisable(true);
        this.gameEngine.buyPurchasable();
    }

    /**
     * This method pays the rent to the {@link Purchasable} owner when the button is
     * pressed.
     */
    @FXML
    public void payRentButtonPressed() {
        this.payRent.setDisable(true);
        this.playerPayedRent = true;
        this.gameEngine.payRent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPropertyPane(final TileInfo info) {
        this.show(info);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetButtons() {
        this.buyPurchasable.setDisable(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lockButtons() {
        this.buyPurchasable.setDisable(true);
        this.payRent.setDisable(true);
        this.buildHouse.setDisable(true);
        this.sellHouse.setDisable(true);
        this.mortgage.setDisable(true);
        this.unMortgage.setDisable(true);
    }

    /**
     * This method shows the tile control pane based on the topology of tile where
     * the {@link Player} is standing on.
     *
     * @param info
     */
    private void show(final TileInfo info) {
        this.propertyName.setText(info.getTileName());
        this.propertyOwner.setText(info.getOwner());
        this.propertyValue.setText(this.utilities.toMoneyString(info.getPurchasableValue()));
        this.mortgageValue.setText(this.utilities.toMoneyString(info.getMortgageValue()));
        this.unMortgageValue.setText(this.utilities.toMoneyString(info.getUnMortgageValue()));

        /*
         * Here is shown the right pane with property control from the stack pane
         * depending on the PurchasableState.
         */
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
            this.showSocietyInfo();
            this.disableHouseBuildButtons();
        } else if (info.getCategory().equals(TileViewCategory.STATION)) {
            this.showStationInfo(info);
            this.disableHouseBuildButtons();
        } else if (info.getCategory().equals(TileViewCategory.PROPERTY)) {
            this.showPropertyInfo(info);
        } else {
            this.emptyInfo.toFront();
            this.emptyControl.toFront();
        }
    }

    /**
     * This method sets disabled the buttons to buy or sell houses for stations and
     * societies.
     */
    private void disableHouseBuildButtons() {
        this.buildHouse.setDisable(true);
        this.sellHouse.setDisable(true);
    }

    /**
     * This method updates the informations displayed about the chosen society.
     *
     */
    private void showSocietyInfo() {
        this.society.toFront();
    }

    /**
     * This method updates the informations displayed about the chosen station.
     *
     * @param info to display.
     */
    private void showStationInfo(final TileInfo info) {
        this.rentOneStation.setText(this.utilities.toMoneyString(info.getRentValue(1)));
        this.rentTwoStation.setText(this.utilities.toMoneyString(info.getRentValue(2)));
        this.rentThreeStation.setText(this.utilities.toMoneyString(info.getRentValue(3)));
        this.rentFourStation.setText(this.utilities.toMoneyString(info.getRentValue(4)));
        this.station.toFront();
    }

    /**
     * This method updates the informations displayed about the chosen property.
     *
     * @param info to display.
     */
    private void showPropertyInfo(final TileInfo info) {
        this.houseNumber.setText(
                String.valueOf(info.getHousesAmount() <= MAX_NUM_HOUSES ? String.valueOf(info.getHousesAmount())
                        : String.valueOf(MAX_NUM_HOUSES)));
        this.hotelNumber
                .setText(info.getHousesAmount() > MAX_NUM_HOUSES ? String.valueOf(MAX_NUM_HOTEL) : String.valueOf(0));
        this.baseRent.setText(this.utilities.toMoneyString(info.getRentValue(0)));
        this.rentOneHouse.setText(this.utilities.toMoneyString(info.getRentValue(1)));
        this.rentTwoHouse.setText(this.utilities.toMoneyString(info.getRentValue(2)));
        this.rentThreeHouse.setText(this.utilities.toMoneyString(info.getRentValue(3)));
        this.rentFourHouse.setText(this.utilities.toMoneyString(info.getRentValue(4)));
        this.rentOneHotel.setText(this.utilities.toMoneyString(info.getRentValue(MAX_NUM_HOUSES + MAX_NUM_HOTEL)));
        this.houseCost.setText(this.utilities.toMoneyString(info.getHouseCost()));

        this.property.toFront();
    }

    /**
     * This method sets the buttons enabled or disabled based on {@link TileInfo}
     * values and updates the labels.
     *
     * @param info to display.
     */
    private void ownedPropertyButtonsLogic(final TileInfo info) {
        this.payRent.setDisable(!(!info.rentPayed() && info.isCurrentPlayerOnSelectedTile()));
        this.playerPayedRent = this.payRent.isDisabled() && (info.rentPayed() || !info.isCurrentPlayerOnSelectedTile());
        this.rentValue.setText(this.utilities.toMoneyString(info.getRentToPay()));
        this.ownedProperty.toFront();
    }

    /**
     * This method sets the buttons enabled or disabled based on {@link TileInfo}
     * values and updates the labels.
     *
     * @param info to display.
     */
    private void myPropertyButtonsLogic(final TileInfo info) {
        this.buildHouse.setDisable(!(this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getHouseCost())
                && info.isBuildHouseEnabled()));
        this.sellHouse.setDisable(!info.isSellHouseEnabled());

        this.mortgage.setDisable(info.isMortgaged() || info.getHousesAmount() > 0);
        this.unMortgage.setDisable(!info.isMortgaged() || !(info.isMortgaged()
                && this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getUnMortgageValue())));

        this.myProperty.toFront();
    }

    /**
     * This method sets the buttons enabled or disabled based on {@link TileInfo}
     * values and updates the labels.
     *
     * @param info to display.
     */
    private void freePropertyButtonsLogic(final TileInfo info) {
        this.purchasableValue.setText(this.utilities.toMoneyString(info.getPurchasableValue()));
        this.buyPurchasable
                .setDisable(!(this.logics.enoughMoney(info.getCurrentPlayerBalance(), info.getPurchasableValue())
                        && info.isCurrentPlayerOnSelectedTile()));
        this.freeProperty.toFront();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.freeProperty.toFront();
        this.emptyControl.toFront();
        this.propertyName.setText("START");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean playerPayedRent() {
        return this.playerPayedRent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hasPayed() {
        this.playerPayedRent = true;
    }

}
