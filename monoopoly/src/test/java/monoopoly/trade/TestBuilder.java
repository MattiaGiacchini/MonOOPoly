package monoopoly.trade;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.engine.GameEngineImpl;
import monoopoly.model.item.tile.purchasable.Purchasable;
import monoopoly.model.player.PlayerImpl;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.utilities.States;

public class TestBuilder {
    private final TradeBuilder tradeBuilder = new TradeBuilderImpl();
    private GameEngineImpl testEngine;
    private PlayerManager playerOne;
    private PlayerManager playerTwo;
    private static final double MONEY_ONE = 1.0;
    private static final double MONEY_TWO = 2.0;
    private Purchasable propertyOne;
    private Purchasable propertyTwo;

    @Test(expected = IllegalStateException.class)
    public void testVoidTrade() {
        tradeBuilder.build();
    }

    @Test
    public void testBlankTrade() {
        this.initEngine();
        this.initPlayers();
        final Trade trade = tradeBuilder.playerOne(playerOne).playerTwo(playerTwo).build();
        assertTrue(trade.getPlayerOne().equals(playerOne));
        assertTrue(trade.getPlayerTwo().equals(playerTwo));
        assertTrue(trade.getPlayerOneTradeProperty().isEmpty());
        assertTrue(trade.getPlayerTwoTradeProperty().isEmpty());
        assertSame(0.0, trade.getPlayerOneTradeMoney());
        assertSame(0.0, trade.getPlayerTwoTradeMoney());
    }

    @Test 
    public void testCompleteTrade() {
        final Set<Purchasable> setOne = new HashSet<>();
        final Set<Purchasable> setTwo = new HashSet<>();
        this.initEngine();
        this.initPlayers();
        this.initProperties();
        setOne.add(propertyOne);
        setTwo.add(propertyTwo);
        final Trade trade = tradeBuilder.playerOne(playerOne)
                                        .playerTwo(playerTwo)
                                        .playerOneProperties(setOne)
                                        .playerTwoProperties(setTwo)
                                        .playerOneMoney(MONEY_ONE)
                                        .playerTwoMoney(MONEY_TWO)
                                        .build();
        assertTrue(trade.getPlayerOne().equals(playerOne));
        assertTrue(trade.getPlayerTwo().equals(playerTwo));
        assertTrue(trade.getPlayerOneTradeProperty().equals(setOne));
        assertTrue(trade.getPlayerTwoTradeProperty().equals(setTwo));
        assertSame(MONEY_ONE, trade.getPlayerOneTradeMoney());
        assertSame(MONEY_TWO, trade.getPlayerTwoTradeMoney());
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
    }

    private void initProperties() {
        this.propertyOne = (Purchasable) this.testEngine.getTable().getTile(1);
        this.propertyTwo = (Purchasable) this.testEngine.getTable().getTile(3);
    }
}
