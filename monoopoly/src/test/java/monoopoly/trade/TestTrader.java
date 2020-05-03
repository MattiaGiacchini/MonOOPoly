package monoopoly.trade;

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
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.model.trade.TradeImpl;
import monoopoly.utilities.States;

public class TestTrader {
	
	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private GameEngineImpl testEngine;
	private Purchasable propertyOne;
	private Purchasable propertyTwo;
	private Purchasable propertyThree;
	private Purchasable propertyFour;
	
	private static final double MONEYONE = 100;
	private static final double MONEYTWO = 50;
	private TradeBuilder builder = new TradeBuilderImpl();
	
	@Test
	public void testTradeWithoutMoney() {
		this.initEngine();
		this.initPlayers();
		this.initProperties();
		/*this.playerOne.getPropertyManager().buyPurchasable(propertyOne);
		this.playerOne.getPropertyManager().buyPurchasable(propertyTwo);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyThree);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyFour);*/
		this.propertyOne.setOwner(Optional.of(0));
		this.propertyTwo.setOwner(Optional.of(0));
		this.propertyThree.setOwner(Optional.of(1));
		this.propertyFour.setOwner(Optional.of(1));
		
		final Set<Purchasable> setOne = new HashSet<Purchasable>();
		final Set<Purchasable> setTwo = new HashSet<Purchasable>();
		
		setOne.add(propertyOne);
		setTwo.add(propertyThree);
		
		
		final Trade trade = builder.setPlayerOne(playerOne)
								   .setPlayerTwo(playerTwo)
								   .setPlayerOneProperties(setOne)
								   .setPlayerTwoProperties(setTwo)
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
		this.playerOne.collectMoney(this.MONEYONE);
		this.playerTwo.collectMoney(this.MONEYTWO);
		
		final Trade trade = builder.setPlayerOne(playerOne)
								   .setPlayerTwo(playerTwo)
								   .setPlayerOneMoney(MONEYONE)
								   .setPlayerTwoMoney(MONEYTWO)
								   .build();
		
		final Trader traderTest = new TraderImpl();
		traderTest.changeTrade(Optional.of(trade));
		traderTest.acceptTrade();
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), this.MONEYTWO) == 0);
		assertTrue(Double.compare(this.playerTwo.getPlayer().getBalance(), this.MONEYONE) == 0);
	}
	
	@Test
	public void testCompleteTrade() {
		this.initEngine();
		this.initPlayers();
		this.initProperties();
		this.playerOne.collectMoney(this.MONEYONE);
		this.playerTwo.collectMoney(this.MONEYTWO);
		/*this.playerOne.getPropertyManager().buyPurchasable(propertyOne);;
		this.playerOne.getPropertyManager().buyPurchasable(propertyTwo);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyThree);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyFour);*/
		this.propertyOne.setOwner(Optional.of(0));
		this.propertyTwo.setOwner(Optional.of(0));
		this.propertyThree.setOwner(Optional.of(1));
		this.propertyFour.setOwner(Optional.of(1));
		
		final Set<Purchasable> setOne = new HashSet<Purchasable>();
		final Set<Purchasable> setTwo = new HashSet<Purchasable>();
		
		setOne.add(propertyOne);
		setTwo.add(propertyThree);
		
		
		final Trade trade = builder.setPlayerOne(playerOne)
								   .setPlayerTwo(playerTwo)
								   .setPlayerOneProperties(setOne)
								   .setPlayerTwoProperties(setTwo)
								   .setPlayerOneMoney(MONEYONE)
								   .setPlayerTwoMoney(MONEYTWO)
								   .build();
		final Trader traderTest = new TraderImpl();
		traderTest.changeTrade(Optional.of(trade));
		traderTest.acceptTrade();
		
		assertTrue(this.playerOne.getProperties().contains(propertyThree));
		assertTrue(this.playerTwo.getProperties().contains(propertyOne));
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), this.MONEYTWO) == 0);
		assertTrue(Double.compare(this.playerTwo.getPlayer().getBalance(), this.MONEYONE) == 0);
		
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
		/*System.out.println(this.propertyOne.toString());
		System.out.println(this.testEngine.getTable().getTile(1).toString());
		System.out.println(this.testEngine.getTable().getTile(1).getCategory() + this.testEngine.getTable().getTile(1).getName());*/
		this.propertyTwo = (Purchasable) this.testEngine.getTable().getTile(3);
		this.propertyThree = (Purchasable) this.testEngine.getTable().getTile(5);
		this.propertyFour = (Purchasable) this.testEngine.getTable().getTile(6);
	}
}
