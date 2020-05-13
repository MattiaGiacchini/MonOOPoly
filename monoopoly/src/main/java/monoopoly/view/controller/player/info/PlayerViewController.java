package monoopoly.view.controller.player.info;

import java.util.Map;
import java.util.Set;

import monoopoly.game_engine.GameEngine;

/**
 * This interfaces represents the controller for the player informations in the
 * view.
 */
public interface PlayerViewController {

    /**
     * This method updates the balances of the players in the board view.
     *
     * @param balances a map with all the players' balances.
     */
    void updateBalances(Map<Integer, Double> balances);

    /**
     * This method updates the current player info displayed in the board view.
     * 
     * @param name    of the player actually in game.
     * @param balance of the player actually in game.
     */
    void updateCurrentPlayer(String name, Double balance);

    /**
     * This method sets the player's names on the beginning of the game.
     *
     * @param names a map of all the players' names.
     */
    void setPlayerNames(Map<Integer, String> names);

    /**
     * This method displays the player's properties.
     * 
     * @param properties owned by the player chosen.
     * @param playerName to display.
     */
    void showPlayerProperties(Set<String> properties, String playerName);

    /**
     * This method sets the gameEngine for the playerInfo controller.
     * 
     * @param gameEngine of the game.
     */
    void setGameEngine(GameEngine gameEngine);

}
