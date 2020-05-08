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

    private static final double PERCENTAGE_VALUE = 100.00;

    @FXML
    private BarChart<String, Double> chart;

    @Override
    public void updateStockMarket(Map<Category, Double> stockMarket) {

        XYChart.Series<String, Double> stockMarketSeries = new XYChart.Series<String, Double>();
        chart.getData().clear();
        stockMarketSeries.getData().clear();

        stockMarket.forEach((K, V) -> {
            stockMarketSeries.getData().add(new XYChart.Data<String, Double>(K.toString(), V*PERCENTAGE_VALUE - PERCENTAGE_VALUE));
        });

        chart.getData().add(stockMarketSeries);
    }

    @Override
    public void updateStockMarketHistory(List<Map<Category, Double>> stockHistory) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
