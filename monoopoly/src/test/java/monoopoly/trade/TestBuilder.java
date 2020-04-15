package monoopoly.trade;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.utilities.States;

public class TestBuilder {
	private TradeBuilder tradeBuilder = new TradeBuilderImpl();
	private GameEngineImpl testEngine;
	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private static final double MONEY_ONE = 1.0;
	private static final double MONEY_TWO = 2.0;
	private Purchasable propertyOne;
	private Purchasable propertyTwo;
	private Purchasable propertyThree;
	private Purchasable propertyFour;
	
	@Test(expected = IllegalStateException.class)
	public void testVoidTrade() {
		tradeBuilder.build();
	}
	
	@Test
	public void testBlankTrade() {
		this.initEngine();
		this.initPlayers();
		final Trade trade = tradeBuilder.setPlayerOne(playerOne).setPlayerTwo(playerTwo).build();
		assertTrue(trade.getPlayerOne().equals(playerOne));
		assertTrue(trade.getPlayerTwo().equals(playerTwo));
		assertTrue(trade.getPlayerOneTradeProperty().isEmpty());
		assertTrue(trade.getPlayerTwoTradeProperty().isEmpty());
		assertTrue(Double.compare(0.0, trade.getPlayerOneTradeMoney()) == 0);
		assertTrue(Double.compare(0.0, trade.getPlayerTwoTradeMoney()) == 0);
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
		final Trade trade = tradeBuilder.setPlayerOne(playerOne)
										.setPlayerTwo(playerTwo)
										.setPlayerOneProperties(setOne)
										.setPlayerTwoProperties(setTwo)
										.setPlayerOneMoney(MONEY_ONE)
										.setPlayerTwoMoney(MONEY_TWO)
										.build();
		assertTrue(trade.getPlayerOne().equals(playerOne));
		assertTrue(trade.getPlayerTwo().equals(playerTwo));
		assertTrue(trade.getPlayerOneTradeProperty().equals(setOne));
		assertTrue(trade.getPlayerTwoTradeProperty().equals(setTwo));
		assertTrue(Double.compare(MONEY_ONE, trade.getPlayerOneTradeMoney()) == 0);
		assertTrue(Double.compare(MONEY_TWO, trade.getPlayerTwoTradeMoney()) == 0);
	}
	
	private void initEngine() {
		Map<Integer, String> names = new HashMap<Integer, String>();
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		Map<Integer, Integer> positions = new HashMap<Integer, Integer>();
		Map<Integer, States> states = new HashMap<Integer, States>();
		names.put(0, "one");
		balance.put (0, 0.0);
		positions.put(0, 0);
		states.put(0, States.IN_GAME);
		names.put(1, "two");
		balance.put(1, 0.0);
		positions.put(1, 0);
		states.put(1, States.IN_GAME);
		this.testEngine = new GameEngineImpl(names, balance, positions, states);
		testEngine.createTable();
	}
	
	private void initPlayers() {
		this.playerOne = new PlayerManagerImpl(0, this.testEngine);
		this.playerTwo = new PlayerManagerImpl(1, this.testEngine);
	}
	
	private void initProperties() {
		this.propertyOne = (Purchasable) this.testEngine.getTable().getTile(1);
		System.out.println(this.propertyOne.toString());
		System.out.println(this.testEngine.getTable().getTile(1).toString());
		System.out.println(this.testEngine.getTable().getTile(1).getCategory() + this.testEngine.getTable().getTile(1).getName());
		this.propertyTwo = (Purchasable) this.testEngine.getTable().getTile(3);
		this.propertyThree = (Purchasable) this.testEngine.getTable().getTile(5);
		this.propertyFour = (Purchasable) this.testEngine.getTable().getTile(6);
	}
}
