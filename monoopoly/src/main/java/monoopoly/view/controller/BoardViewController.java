package monoopoly.view.controller;

import java.util.List;
import java.util.Map;

import monoopoly.game_engine.GameEngine;

/**
 * This interface represents the view controller for the game board.
 */
public interface BoardViewController {

    /**
     * This method updates the player positions on the board.
     * 
     * @param positions players positions map
     */
    void updatePlayerPositions(Map<Integer, Integer> positions);

    /**
     * This method initializes the board with the tileNames passed as argument.
     *
     * @param tileNames the names of all the board tiles
     */
    void initializeBoard(List<String> tileNames);

    /**
     * This method sets the game controller.
     * 
     * @param gameEngine the {@link GameEngine}.
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * This method removes the specified pawn from the board.
     * 
     * @param playerID to remove.
     */
    void removePawn(int playerID);

}
