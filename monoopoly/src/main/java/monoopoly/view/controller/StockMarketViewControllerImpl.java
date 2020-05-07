package monoopoly.view.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import monoopoly.model.item.Tile.Category;

public class StockMarketViewControllerImpl implements StockMarketViewController, Initializable {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		XYChart.Series<String, Double> brown = new XYChart.Series<String, Double>();
		brown.getData().add(new XYChart.Data<String, Double>("BROWN", 25.0));
		XYChart.Series<String, Double> lightBlue = new XYChart.Series<String, Double>();
		lightBlue.getData().add(new XYChart.Data<String, Double>("LIGHT BLUE", 150.00));
		XYChart.Series<String, Double> pink = new XYChart.Series<String, Double>();
		pink.getData().add(new XYChart.Data<String, Double>("PINK", 75.2));
		XYChart.Series<String, Double> orange = new XYChart.Series<String, Double>();
		orange.getData().add(new XYChart.Data<String, Double>("ORANGE", -25.00));
		XYChart.Series<String, Double> red = new XYChart.Series<String, Double>();
		red.getData().add(new XYChart.Data<String, Double>("RED", -90.00));
		XYChart.Series<String, Double> yellow = new XYChart.Series<String, Double>();
		yellow.getData().add(new XYChart.Data<String, Double>("YELLOW", 150.00));
		XYChart.Series<String, Double> green = new XYChart.Series<String, Double>();
		green.getData().add(new XYChart.Data<String, Double>("GREEN", 100.00));
		XYChart.Series<String, Double> blue = new XYChart.Series<String, Double>();
		blue.getData().add(new XYChart.Data<String, Double>("BLUE", 0.00));
		XYChart.Series<String, Double> station = new XYChart.Series<String, Double>();
		station.getData().add(new XYChart.Data<String, Double>("STATION", 70.00));
		XYChart.Series<String, Double> society = new XYChart.Series<String, Double>();
		society.getData().add(new XYChart.Data<String, Double>("SOCIETY", -50.0));

		chart.getData().addAll(brown, lightBlue, pink, orange, red, yellow, green, blue, station, society);

		chart.setMinWidth(20.0);
	}

}
