package monoopoly.view.controller.stockmarket;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TabPane;
import monoopoly.model.item.tile.Tile;
import monoopoly.model.item.tile.Tile.Category;

/**
 * This class implements the method to display the {@link StockMarket} on the
 * view.
 */
public class StockMarketViewControllerImpl implements StockMarketViewController, Initializable {

    private static final double PERCENTAGE_VALUE = 100.00;

    @FXML
    private TabPane tabPane;

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private LineChart<String, Double> brownChart;

    @FXML
    private LineChart<String, Double> lightBlueChart;

    @FXML
    private LineChart<String, Double> pinkChart;

    @FXML
    private LineChart<String, Double> orangeChart;

    @FXML
    private LineChart<String, Double> redChart;

    @FXML
    private LineChart<String, Double> yellowChart;

    @FXML
    private LineChart<String, Double> greenChart;

    @FXML
    private LineChart<String, Double> blueChart;

    @FXML
    private LineChart<String, Double> stationChart;

    @FXML
    private LineChart<String, Double> societyChart;

    private final Map<Tile.Category, LineChart<String, Double>> historyCharts = new HashMap<>();
    private final Map<Tile.Category, XYChart.Series<String, Double>> lineChartSeriesMap = new HashMap<>();
    private int stockHistoryCounter;

    private final XYChart.Series<String, Double> brownSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> lightBlueSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> pinkSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> orangeSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> redSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> yellowSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> greenSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> blueSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> societySeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> stationSeries = new XYChart.Series<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStockMarket(final Map<Category, Double> stockMarket) {

        this.tabPane.getSelectionModel().select(0);

        final XYChart.Series<String, Double> stockMarketSeries = new XYChart.Series<>();
        barChart.getData().clear();
        stockMarketSeries.getData().clear();

        stockMarket.forEach((k, v) -> {
            stockMarketSeries.getData()
                    .add(new XYChart.Data<String, Double>(k.toString(), v * PERCENTAGE_VALUE - PERCENTAGE_VALUE));
        });

        barChart.getData().add(stockMarketSeries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStockMarketHistory(final List<Map<Category, Double>> stockHistory) {
        this.historyCharts.values().forEach(x -> x.getData().clear());
        stockHistory.get(this.stockHistoryCounter).forEach((k, v) -> {
            this.lineChartSeriesMap.get(k).getData().add(new XYChart.Data<String, Double>(
                    String.valueOf(this.stockHistoryCounter), v * PERCENTAGE_VALUE - PERCENTAGE_VALUE));
            this.historyCharts.get(k).getData().clear();
            this.historyCharts.get(k).getData().add(this.lineChartSeriesMap.get(k));
        });

        this.stockHistoryCounter = this.stockHistoryCounter + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.historyCharts.put(Category.BROWN, this.brownChart);
        this.historyCharts.put(Category.LIGHT_BLUE, this.lightBlueChart);
        this.historyCharts.put(Category.PINK, this.pinkChart);
        this.historyCharts.put(Category.ORANGE, this.orangeChart);
        this.historyCharts.put(Category.RED, this.redChart);
        this.historyCharts.put(Category.YELLOW, this.yellowChart);
        this.historyCharts.put(Category.GREEN, this.greenChart);
        this.historyCharts.put(Category.BLUE, this.blueChart);
        this.historyCharts.put(Category.STATION, this.stationChart);
        this.historyCharts.put(Category.SOCIETY, this.societyChart);

        this.lineChartSeriesMap.put(Category.BROWN, this.brownSeries);
        this.lineChartSeriesMap.put(Category.LIGHT_BLUE, this.lightBlueSeries);
        this.lineChartSeriesMap.put(Category.PINK, this.pinkSeries);
        this.lineChartSeriesMap.put(Category.ORANGE, this.orangeSeries);
        this.lineChartSeriesMap.put(Category.RED, this.redSeries);
        this.lineChartSeriesMap.put(Category.YELLOW, this.yellowSeries);
        this.lineChartSeriesMap.put(Category.GREEN, this.greenSeries);
        this.lineChartSeriesMap.put(Category.BLUE, this.blueSeries);
        this.lineChartSeriesMap.put(Category.STATION, this.stationSeries);
        this.lineChartSeriesMap.put(Category.SOCIETY, this.societySeries);
    }

}
