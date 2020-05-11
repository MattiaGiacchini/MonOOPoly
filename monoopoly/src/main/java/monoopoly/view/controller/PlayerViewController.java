package monoopoly.view.controller;

import java.util.Map;
import java.util.Set;

import monoopoly.game_engine.GameEngine;

public interface PlayerViewController {

    /**
     * This method updates the balances of the players in the board view
     *
     * @param balances a map with all the players' balances
     */
    void updateBalances(final Map<Integer, Double> balances);

    /**
     * This method updates the current player info displayed in the board view
     * 
     * @param name    of the player actually in game
     * @param balance of the player actually in game
     */
    void updateCurrentPlayer(final String name, final Double balance);

    /**
     * This method sets the player's names on the beginning of the game
     *
     * @param names a map of all the players' names
     */
    void setPlayerNames(final Map<Integer, String> names);

    /**
     * This method displays the player's properties
     * 
     * @param properties owned by the player chosen
     * @param playerName to display
     */
    void showPlayerProperties(Set<String> properties, String playerName);

    /**
     * This method sets the gameEngine for the playerInfo controller
     * 
     * @param gameEngine
     */
    void setGameEngine(GameEngine gameEngine);

}