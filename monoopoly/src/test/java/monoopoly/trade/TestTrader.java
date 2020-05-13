package monoopoly.trade;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.controller.trades.Trader;
import monoopoly.controller.trades.TraderImpl;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.player.PlayerImpl;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.utilities.States;

public class TestTrader {

    private static final int TILE_6 = 6;
    private static final int TILE_5 = 5;
    private PlayerManager playerOne;
    private PlayerManager playerTwo;
    private GameEngineImpl testEngine;
    private Purchasable propertyOne;
    private Purchasable propertyTwo;
    private Purchasable propertyThree;
    private Purchasable propertyFour;

    private static final double MONEYONE = 100;
    private static final double MONEYTWO = 50;
    private final TradeBuilder builder = new TradeBuilderImpl();

    @Test
    public void testTradeWithoutMoney() {
        this.initEngine();
        this.initPlayers();
        this.initProperties();
        this.propertyOne.setOwner(Optional.of(0));
        this.propertyTwo.setOwner(Optional.of(0));
        this.propertyThree.setOwner(Optional.of(1));
        this.propertyFour.setOwner(Optional.of(1));
        final Set<Purchasable> setOne = new HashSet<>();
        final Set<Purchasable> setTwo = new HashSet<>();
        setOne.add(propertyOne);
        setTwo.add(propertyThree);
        final Trade trade = builder.playerOne(playerOne)
                                   .playerTwo(playerTwo)
                                   .playerOneProperties(setOne)
                                   .playerTwoProperties(setTwo)
                                   .build();
        final Trader traderTest = new TraderImpl();
        traderTest.changeTrade(Optional.of(trade));
        traderTest.acceptTrade();
        assertTrue(this.playerOne.getProperties().contains(propertyThree));
        assertTrue(this.playerTwo.getProperties().contains(propertyOne));
    }

    @Test
    public void testTradeWithoutProperties() {
        this.initEngine();
        this.initPlayers();
        this.initProperties();
        this.playerOne.collectMoney(MONEYONE);
        this.playerTwo.collectMoney(MONEYTWO);
        final Trade trade = builder.playerOne(playerOne)
                                   .playerTwo(playerTwo)
                                   .playerOneMoney(MONEYONE)
                                   .playerTwoMoney(MONEYTWO)
                                   .build();
        final Trader traderTest = new TraderImpl();
        traderTest.changeTrade(Optional.of(trade));
        traderTest.acceptTrade();
        assertSame(this.playerOne.getPlayer().getBalance(), MONEYTWO);
        assertSame(this.playerTwo.getPlayer().getBalance(), MONEYONE);
    }

    @Test
    public void testCompleteTrade() {
        this.initEngine();
        this.initPlayers();
        this.initProperties();
        this.playerOne.collectMoney(MONEYONE);
        this.playerTwo.collectMoney(MONEYTWO);
        this.propertyOne.setOwner(Optional.of(0));
        this.propertyTwo.setOwner(Optional.of(0));
        this.propertyThree.setOwner(Optional.of(1));
        this.propertyFour.setOwner(Optional.of(1));
        final Set<Purchasable> setOne = new HashSet<>();
        final Set<Purchasable> setTwo = new HashSet<>();
        setOne.add(propertyOne);
        setTwo.add(propertyThree);
        final Trade trade = builder.playerOne(playerOne)
                                   .playerTwo(playerTwo)
                                   .playerOneProperties(setOne)
                                   .playerTwoProperties(setTwo)
                                   .playerOneMoney(MONEYONE)
                                   .playerTwoMoney(MONEYTWO)
                                   .build();
        final Trader traderTest = new TraderImpl();
        traderTest.changeTrade(Optional.of(trade));
        traderTest.acceptTrade();
        assertTrue(this.playerOne.getProperties().contains(propertyThree));
        assertTrue(this.playerTwo.getProperties().contains(propertyOne));
        assertSame(this.playerOne.getPlayer().getBalance(), MONEYTWO);
        assertSame(this.playerTwo.getPlayer().getBalance(), MONEYONE);
    }

    private void initEngine() {
        final Map<Integer, String> names = new HashMap<>();
        final Map<Integer, Double> balance = new HashMap<>();
        final Map<Integer, Integer> positions = new HashMap<>();
        final Map<Integer, States> states = new HashMap<>();
        names.put(0, "one");
        balance.put(0, 0.0);
        positions.put(0, 0);
        states.put(0, States.IN_GAME);
        names.put(1, "two");
        balance.put(1, 0.0);
        positions.put(1, 0);
        states.put(1, States.IN_GAME);
        this.testEngine = new GameEngineImpl(names, balance);
        testEngine.createTable();
    }

    private void initPlayers() {
        this.playerOne = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name("0").balance(0.0).build());
        this.playerTwo = new PlayerManagerImpl(1, new PlayerImpl.Builder().playerId(1).name("1").balance(0.0).build());
        this.playerOne.setTable(this.testEngine.getTable());
        this.playerTwo.setTable(this.testEngine.getTable());
    }

    private void initProperties() {
        this.propertyOne = (Purchasable) this.testEngine.getTable().getTile(1);
        this.propertyTwo = (Purchasable) this.testEngine.getTable().getTile(3);
        this.propertyThree = (Purchasable) this.testEngine.getTable().getTile(TILE_5);
        this.propertyFour = (Purchasable) this.testEngine.getTable().getTile(TILE_6);
    }
}
