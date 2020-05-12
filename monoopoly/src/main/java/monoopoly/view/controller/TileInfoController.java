package monoopoly.view.controller;

import monoopoly.game_engine.GameEngine;

/**
 * This interface represents the controller for the properties display pane.
 */
public interface TileInfoController {

    /**
     * This method is used to display the correct pane from stack pane according to
     * the property owner.
     * 
     * @param info {@link TileInfo} to be displayed
     */
    void showPropertyPane(TileInfo info);

    /**
     * This method sets the game controller.
     * 
     * @param gameEngine game engine.
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * This method resets the buttons state for the following player.
     */
    void resetButtons();

    /**
     * This method locks the buttons to act over {@link Purchasable} like building,
     * mortgaging, buying.
     */
    void lockButtons();

    /**
     * This method return true if the current {@link Player} payed the rent he must
     * pay to the {@link Purchasable} owner.
     * 
     * @return true id the current {@link Player} payed the rent.
     */
    boolean playerPayedRent();

    /**
     * This method is used to let the {@link Player} surrender without paying the
     * rent bill.
     */
    void hasPayed();

}
