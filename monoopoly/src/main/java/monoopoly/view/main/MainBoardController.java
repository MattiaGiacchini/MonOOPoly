package monoopoly.view.main;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import monoopoly.game_engine.GameEngine;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.player.Player;
import monoopoly.view.controller.TileInfo;

public interface MainBoardController {

	/**
	 * This method sets the game controller
	 * 
	 * @param gameEngine
	 */
	void setGameEngine(GameEngine gameEngine);

	/**
	 * This method initializes the board with all the {@link Tile}s' names
	 * 
	 * @param tileNames
	 */
	void setTileNames(List<String> tileNames);

	/**
	 * This method creates an alert with the deck card drawn
	 * 
	 * @param cardCategory category of the card
	 * @param message
	 */
	void showDeckCard(String cardCategory, String message);

	/**
	 * This method updates the players' positions on the board and their balances
	 * 
	 * @param positions of the {@link Player} on the board
	 * @param balances  of the {@link Player}
	 */
	public void updatePlayers(final Map<Integer, Integer> positions, final Map<Integer, Double> balances);

	/**
	 * This method shows the current {@link Player} data
	 * 
	 * @param name    of the current {@link Player}
	 * @param balance of the current {@link Player}
	 */
	void updateCurrentPlayer(final String name, final Double balance);

	/**
	 * This method sets the players' names on the beginning of the game.
	 * 
	 * @param names
	 */
	void setPlayerNames(final Map<Integer, String> names);

	/**
	 * This method shows the list of {@link Purchasable}s owned by the chosen
	 * player.
	 * 
	 * @param properties
	 */
	void showPlayerProperties(Set<String> properties);

	/**
	 * This method displays the normal dices rolled and the speedyDice, if present.
	 * 
	 * @param dice1
	 * @param dice2
	 * @param dice3
	 */
	public void updateDices(final int dice1, final int dice2, final Optional<Integer> dice3);

	/**
	 * This method displays the dices rolled.
	 * 
	 * @param dice1
	 * @param dice2
	 */
	public void updateDices(final int dice1, final int dice2);

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

}