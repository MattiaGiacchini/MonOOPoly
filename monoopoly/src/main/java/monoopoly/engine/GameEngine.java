package monoopoly.engine;

import java.util.List;
import java.util.Map;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.table.Table;
import monoopoly.view.controller.main.MainBoardControllerImpl;

/**
 * Interface representing the brain behind
 * every action.
 */
public interface GameEngine {

    /**
     * method to create Table, monopoly's board-game.
     *
     * @return {@link Table}
     */
    Table createTable();

    /**
     * Creating all the players using all the IDs stored in maps.
     */
    void createPlayers();

    /**
     * Method to track turn by turn the current player by watching it
     * into TurnManagerImpl.
     * @return {@link PlayerManager} that is the currently gaming player
     */
    PlayerManager currentPlayer();

    /**
     * @return a list of {@link PlayerManager}
     */
    List<PlayerManager> playersList();

    /**
     * used to set the mainBoardController.
     * @param mainBoardController 
     */
    void setMainBoardController(MainBoardControllerImpl mainBoardController);

    /**
     * helpful for getting player's name by putting ID.
     * @param iD
     * @return String
     */
    String getName(int iD);

    /**
     * helpful for getting player's balance by putting ID.
     * @param iD
     * @return Double
     */
    Double getBalance(int iD);

    /**
     * You can call this method to pass your turn.
     */
    void passPlayer();

    /**
     * @return the table
     */
    Table getTable();

    /** 
     * The game ends with a leaderboard.
     */
    void endGame();

    /**
     * method to use the effect of a card.
     */
    void useCard();

    /**
     * method used to roll the dices.
     * @return {@link Map} representing each dice with its value
     */
    Map<Integer, Integer> rollDices();

    /**
     * Useful to get the names of players' properties.
     * @param iD
     */
    void giveProperties(Integer iD);

    /**
     * Useful to get the names of the tile you hit on.
     * @param tileNum
     */
    void giveTileInfo(Integer tileNum);

    /**
     * Useful to build an house.
     */
    void buildHouse();

    /**
     * Useful to sell an house.
     */
    void sellHouse();

    /**
     * Useful to mortgage. 
     */
    void mortgage();

    /**
     * Useful to un-mortgage.
     */
    void unMortgage();

    /**
     * Useful to buy a purchasable.
     */
    void buyPurchasable();

    /**
     * Useful to pay to the owner. 
     */
    void payRent();

    /**
     * A player lose.
     */
    void lose();

}
