package monoopoly.view.controller.main;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import monoopoly.engine.GameEngine;
import monoopoly.model.item.tile.Tile.Category;
import monoopoly.view.controller.TileInfo;

/**
 * This interface represents the main view controller, the point of
 * communication between the controller ({@link GameEngine}) and the view.
 */
public interface MainBoardController {

    /**
     * This method sets the game controller.
     *
     * @param gameEngine game engine.
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * This method initializes the board with all the {@link Tile}s' names.
     *
     * @param tileNames board tiles names.
     */
    void setTileNames(List<String> tileNames);

    /**
     * This method creates an alert with the deck card drawn.
     *
     * @param cardCategory category of the card
     * @param message      of the card.
     */
    void showDeckCard(String cardCategory, String message);

    /**
     * This method updates the players' positions on the board and their balances.
     *
     * @param positions of the {@link Player} on the board
     * @param balances  of the {@link Player}
     */
    void updatePlayers(Map<Integer, Integer> positions, Map<Integer, Double> balances);

    /**
     * This method shows the current {@link Player} data.
     *
     * @param name    of the current {@link Player}
     * @param balance of the current {@link Player}
     */
    void updateCurrentPlayer(String name, Double balance);

    /**
     * This method sets the players' names on the beginning of the game.
     *
     * @param names to display
     */
    void setPlayerNames(Map<Integer, String> names);

    /**
     * This method shows the list of {@link Purchasable}s owned by the chosen
     * player.
     *
     * @param properties a set of all the {@link Player} properties
     * @param playerName the {@link Player}'s name to display
     */
    void showPlayerProperties(Set<String> properties, String playerName);

    /**
     * This method displays the normal dices rolled and the speedyDice, if present.
     *
     * @param dice1 first normal dice
     * @param dice2 second normal dice
     * @param dice3 speedyDice
     */
    void updateDices(int dice1, int dice2, Optional<Integer> dice3);

    /**
     * This method displays the dices rolled.
     *
     * @param dices a map of dices
     */
    void updateDices(Map<Integer, Integer> dices);

    /**
     * This method shows the property chosen by the {@link Player} or the tile where
     * the {@link Player} is standing on.
     *
     * @param info TileInfo object with the necessary informations to display
     */
    void showPropertyPane(TileInfo info);

    /**
     * This method updates the stockMarket in the view.
     *
     * @param actualStockMarket map of "new day" stock market
     * @param stockHistory      the list of all the stock market
     */
    void updateStockMarket(Map<Category, Double> actualStockMarket, List<Map<Category, Double>> stockHistory);

    /**
     * This method eliminates the player pawn from the board.
     *
     * @param playerID to delete
     */
    void deletePlayer(int playerID);

}
