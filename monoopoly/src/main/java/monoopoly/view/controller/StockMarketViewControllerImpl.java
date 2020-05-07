package monoopoly.view.controller;

import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import monoopoly.model.item.Tile.Category;

public class StockMarketViewControllerImpl implements StockMarketViewController {

	@FXML
	private BarChart<String, Double> chart;

	@Override
	public void updateStockMarket(Map<Category, Double> stockMarket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStockMarketHistory(List<Map<Category, Double>> stockHistory) {
		// TODO Auto-generated method stub

	}

}
