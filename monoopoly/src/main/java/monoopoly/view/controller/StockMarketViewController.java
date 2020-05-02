package monoopoly.view.controller;

import java.util.List;
import java.util.Map;

import monoopoly.controller.stockmarket.StockMarket;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;

public interface StockMarketViewController {

	/**
	 * This method updates the actual {@link StockMarket} displayed on the screen
	 * 
	 * @param stockMarket
	 */
	void updateStockMarket(Map<Category, Double> stockMarket);

	/**
	 * This method updates the history of the {@link StockMarket} for each
	 * {@link Tile} category
	 * 
	 * @param stockHistory
	 */
	void updateStockMarketHistory(List<Map<Category, Double>> stockHistory);

}
