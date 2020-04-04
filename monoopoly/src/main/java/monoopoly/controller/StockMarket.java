package monoopoly.controller;

import java.util.List;
import java.util.Map;

import monoopoly.model.item.Property;
import monoopoly.utilities.PurchasableCategory;

/**
 * This interface represents the Stock Market, and its history
 */
public interface StockMarket {

	/**
	 * This method allows to set a new currency, to be used when it's needed, for the properties,
	 * to change their sell/Lease/Mortgage value.
	 * @return A map <Color, Double> representing the pairs Color - Relative change.
	 */
	Map<PurchasableCategory, Double> setNewMarketValue();
	
	/**
	 * This method returns the history of the stock market, in form of a map.
	 * @return the map.
	 */
	Map<Integer, List<Map<PurchasableCategory, Double>>> getStockHistory();
	
	/**
	 * This method permits changing property values, according to the current market value.
	 * @param propertyList the list of properties;
	 * @param marketValue the current market value.
	 */
	void applyChanges(List<Property> propertyList, Map<PurchasableCategory, Double> marketValue);
}
