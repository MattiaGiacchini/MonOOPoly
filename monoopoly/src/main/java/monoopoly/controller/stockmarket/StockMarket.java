package monoopoly.controller.stockmarket;

import java.util.List;
import java.util.Map;

import monoopoly.model.item.Tile.Category;

/**
 * This interface represents the Stock Market, and its history.
 */
public interface StockMarket {

    /**
     * This method allows to set a new currency, to be used when it's needed, for the purchasable tiles,
     * to change their sell/Lease/Mortgage value.
     */
    void setNewMarketValue();
    /**
     * This method returns the history of the stock market, in form of a map.
     * @return the history of the market.
     */
    List<Map<Category, Double>> getStockHistory();

    /**
     * This method returns the variation between the two last generations of stockMarket.
     * @return the map.
     */
    Map<Category, Double> getVariation();

    /**
     * This method return the last generation of the market.
     * @return the last stockMarket.
     */
    Map<Category, Double> getMarket();

}
