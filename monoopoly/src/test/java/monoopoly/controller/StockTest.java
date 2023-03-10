package monoopoly.controller;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import monoopoly.controller.stockmarket.StockMarket;
import monoopoly.controller.stockmarket.StockMarketImpl;
import monoopoly.engine.GameEngine;
import monoopoly.engine.GameEngineImpl;
import monoopoly.model.table.tile.Tile.Category;
import monoopoly.model.table.tile.purchasable.Purchasable;
import monoopoly.utilities.States;
/**
 * Test class of StockMarket feature.
 */
public class StockTest {

    private GameEngine engine;
    private StockMarket market;

    private static final double PRESUMPT_INIT_QUOTATION = 1.0;
    private static final int MULTIPLIER = 100;
    /**
     * tests init.
     */
    @Test
    public void testInit() {
        final Map<Category, Double> presumptInitMap = new HashMap<>();
        initEngine();
        final Set<Category> categories = this.engine.getTable().getFilteredTiles(Purchasable.class, x -> x.isPurchasable())
                                                         .stream().map(x -> x.getCategory())
                                                         .collect(Collectors.toSet());
        for (final Category cat : categories) {
            presumptInitMap.put(cat, PRESUMPT_INIT_QUOTATION);
        }
        this.market = new StockMarketImpl(this.engine.getTable());
        assertTrue(this.market.getStockHistory().size() == 1
                   && this.market.getStockHistory().get(0).equals(presumptInitMap));
    }
    /**
     * tests generation.
     */
    @Test
    public void testMarketGen() {
        initEngine();
        final Set<Purchasable> purchasables = this.engine.getTable()
                                                   .getFilteredTiles(Purchasable.class, x -> x.isPurchasable());
        this.market = new StockMarketImpl(this.engine.getTable());
        this.market.setNewMarketValue();
        assertSame(this.market.getStockHistory().size(), 2);
        for (final Purchasable purc : purchasables) {
            assertSame(Double.compare(purc.getQuotation(), this.market.getMarket().get(purc.getCategory())), 0);
        }
    }
    /**
     * tests variation method throwing an exception.
     */
    @Test (expected = IllegalStateException.class)
    public void testVariationException() {
        initEngine();
        this.market = new StockMarketImpl(this.engine.getTable());
        this.market.getVariation();
    }
    /**
     * tests variation.
     */
    @Test
    public void testVariation() {
        initEngine();
        this.market = new StockMarketImpl(this.engine.getTable());
        this.market.setNewMarketValue();
        final Map<Category, Double> variation = this.market.getVariation();
        for (final Entry<Category, Double> entry : variation.entrySet()) {
            assertSame(entry.getValue(), MULTIPLIER * (this.market.getMarket().get(entry.getKey()) - PRESUMPT_INIT_QUOTATION));
        }
    }

    private void initEngine() {
        final Map<Integer, String> names = new HashMap<>();
        final Map<Integer, Double> balance = new HashMap<>();
        final Map<Integer, Integer> positions = new HashMap<>();
        final Map<Integer, States> states = new HashMap<>();
        names.put(0, "test");
        balance.put(0, 0.0);
        positions.put(0, 0);
        states.put(0, States.IN_GAME);
        this.engine = new GameEngineImpl(names, balance);
        this.engine.createTable();
    }
}
