package monoopoly.controller.stockmarket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile.Category;

public class StockMarketImpl implements StockMarket {
	

	private Map<Category, Double> actualMarket;
	private List<Map<Category, Double>> stockHistory;
	private Set<Purchasable> purchasables;
	private final Random random;
	private Set<Category> purchasableCategories;
	
	private static final double RANDOM_MEAN = 100.0;
	private static final double RANDOM_DEVIATION = 10.0;
	private static final double RANDOM_DIVIDER = 100.0;
	private static final double INIT_QUOTATION = 1.0;
	
	public StockMarketImpl(Table table) {
		this.purchasables = table.getFilteredTiles(Purchasable.class, x -> x.isPurchasable());
		this.purchasableCategories = this.purchasables.stream().map(x -> x.getCategory()).collect(Collectors.toSet());;
		this.stockHistory = new ArrayList<Map<Category,Double>>();
		this.actualMarket = initMarket();
		this.random = new Random();
	}
	
	private Map<Category, Double> initMarket() {
		this.actualMarket = new TreeMap<>();
		for (Category cat : this.purchasableCategories) {
			this.actualMarket.put(cat, INIT_QUOTATION);
		}
		
		for (Purchasable purchasable : this.purchasables) {
			purchasable.setQuotation(this.actualMarket.get(purchasable.getCategory()));
		}
		
		this.stockHistory.add(this.actualMarket);
		return this.actualMarket;
		
	}

	@Override
	public void setNewMarketValue() {
		this.actualMarket = new TreeMap<>();
		for (Category cat : this.purchasableCategories) {
			this.actualMarket.put(cat, getPercentile());
		}
		
		for (Purchasable purchasable : this.purchasables) {
			purchasable.setQuotation(this.actualMarket.get(purchasable.getCategory()));
		}
		
		this.stockHistory.add(this.actualMarket);
		
	}

	@Override
	public List<Map<Category, Double>> getStockHistory() {
		return Collections.unmodifiableList(this.stockHistory);
	}
	
	@Override
	public Map<Category, Double> getMarket(){
		return this.stockHistory.get(this.stockHistory.size() - 1);
	}
	
	@Override
	public Map<Category, Double> getVariation(){
		
		if (this.stockHistory.size() < 2) {
			throw new IllegalStateException("The stock market hasn't got two generations yet.");
		}
		
		Map<Category, Double> toReturn = new HashMap<>();
		
		for (Category cat : this.purchasableCategories) {
			toReturn.put(cat,  100 * (this.stockHistory.get(this.stockHistory.size() - 1).get(cat) 
					- this.stockHistory.get(this.stockHistory.size() - 2).get(cat)));
		}
		return toReturn;
		
	}
	
	private double getPercentile() {
		/* generate a number in a normal distribution, with average = 100 and standard deviation = 10.
		 * This means that the 70% of the generated numbers will vary between 100 +/- 10 ( 90 < x < 110)
		 * This will mean that the number generated will be, in the most cases, in the range 0.9/1.1.
		 */
		double toReturn = random.nextGaussian() * RANDOM_DEVIATION + RANDOM_MEAN;
		while (toReturn < 5.0 && toReturn > 250.0) {
			toReturn = random.nextGaussian() * RANDOM_DEVIATION + RANDOM_MEAN;
		}
		System.out.println(toReturn);
		return toReturn / RANDOM_DIVIDER;
		
		
	}
	
}
