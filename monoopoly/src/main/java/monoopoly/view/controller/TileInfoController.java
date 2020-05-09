package monoopoly.view.controller;

import monoopoly.game_engine.GameEngine;
import monoopoly.model.item.Purchasable;
import monoopoly.model.player.Player;
import monoopoly.view.controller.TileInfo;

public interface TileInfoController {

    /**
     * This method is used to display the correct pane from stack pane according to
     * the property owner
     * 
     * @param {@link TileInfo} to be displayed
     */
    void showPropertyPane(TileInfo info);

    /**
     * This method sets the game controller
     * 
     * @param gameEngine
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * This method resets the buttons state for the following player.
     */
    void resetButtons();

    /**
     * This method return true if the current {@link Player} payed the rent he must
     * pay to the {@link Purchasable} owner.
     * 
     * @return true id the current {@link Player} payed the rent.
     */
    boolean playerPayedRent();

}
