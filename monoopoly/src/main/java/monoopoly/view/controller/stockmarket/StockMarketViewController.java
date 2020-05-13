package monoopoly.view.controller.stockmarket;

import java.util.List;
import java.util.Map;

import monoopoly.model.item.Tile.Category;

/**
 * This interface represents the controller for the {@link StockMarket} view.
 */
public interface StockMarketViewController {

    /**
     * This method updates the actual {@link StockMarket} displayed on the screen.
     * 
     * @param stockMarket actual {@link StockMarket} to display.
     */
    void updateStockMarket(Map<Category, Double> stockMarket);

    /**
     * This method updates the history of the {@link StockMarket} for each
     * {@link Tile} category.
     * 
     * @param stockHistory {@link StockMarket} history to display.
     */
    void updateStockMarketHistory(List<Map<Category, Double>> stockHistory);

}
