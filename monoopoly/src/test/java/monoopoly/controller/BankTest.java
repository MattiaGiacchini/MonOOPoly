package monoopoly.controller;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.bank.BankManager;
import monoopoly.controller.bank.BankManagerImpl;
import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.game_engine.GameEngine;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.utilities.States;

public class BankTest {

	private GameEngine engine;
	private BankManager bankManager;
	private PlayerManager playerOne;
	private Property tileBuilt;
	
	private static final double A_LITTLE_MONEY = 150.0;
	private static final double A_TON_OF_MONEY = 1500000.0;
	private static final int PROPERTY_ID = 1;
	
	@Test
	public void testMoneyGiving() {
		this.initEngine();
		this.bankManager.giveMoney(A_LITTLE_MONEY, this.playerOne);
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), A_LITTLE_MONEY) == 0);
	}
	
	@Test
	public void testBankBreaking() {
		this.initEngine();
		this.bankManager.giveMoney(A_TON_OF_MONEY, this.playerOne);
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), A_TON_OF_MONEY) == 0);
	}
	
	@Test
	public void testHouseAssignment() {
		this.initEngine();
		this.bankManager.giveMoney( 10 * A_LITTLE_MONEY, this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(3), this.playerOne);
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		final Purchasable otherTile = (Purchasable)this.engine.getTable().getTile(3);
		final Property otherProperty = (Property)otherTile;
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(),  10 * A_LITTLE_MONEY - 
				this.tileBuilt.getSalesValue() - 
				otherProperty.getSalesValue() - 
				this.tileBuilt.getCostToBuildHouse()) == 0
				&& this.tileBuilt.getNumberOfHouseBuilt().equals(1));
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		assertTrue(this.tileBuilt.getNumberOfHouseBuilt().equals(4)
				&& this.tileBuilt.getNumberOfHotelBuilt().equals(1));
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		
		assertTrue(this.tileBuilt.getNumberOfHouseBuilt().equals(4)
				&& this.tileBuilt.getNumberOfHotelBuilt().equals(1));
	}
	
	private void fetchProperty() {
		Purchasable tilePurchased = (Purchasable)this.engine.getTable().getTile(PROPERTY_ID);
		this.tileBuilt = (Property)tilePurchased;
	}

	@Test
	public void testPropertyBuy() {
		this.initEngine();
		this.bankManager.giveMoney(A_LITTLE_MONEY, this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		Set<Purchasable> testSet = new HashSet<>();
		testSet.add((Purchasable)this.engine.getTable().getTile(PROPERTY_ID));
		fetchProperty();
		assertTrue(this.engine.getTable().getPurchasableTilesforSpecificPlayer(this.playerOne.getPlayerManagerID())
																				   .equals(testSet)
					&& Double.compare(this.playerOne.getPlayer().getBalance(), A_LITTLE_MONEY - 
							this.tileBuilt.getSalesValue()) == 0);
	}
	
	@Test
	public void testHouseSelling() {
		this.initEngine();
		this.bankManager.giveMoney(3 * A_LITTLE_MONEY, this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(3), this.playerOne);
		this.bankManager.assignHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.sellHouse(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		final Purchasable otherTile = (Purchasable)this.engine.getTable().getTile(3);
		final Property otherProperty = (Property)otherTile;
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), 3 * A_LITTLE_MONEY -
					tileBuilt.getSalesValue() - otherProperty.getSalesValue()
					- tileBuilt.getCostToBuildHouse() + tileBuilt.getQuotationToSellHouse()) == 0
				&& this.tileBuilt.getNumberOfHouseBuilt().equals(0));
	}
	
	@Test
	public void testMortgage() {
		this.initEngine();
		this.bankManager.giveMoney(A_LITTLE_MONEY, this.playerOne);
		this.bankManager.buyProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		this.bankManager.mortgageProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		assertTrue(this.tileBuilt.isMortgage() && Double.compare(this.playerOne.getPlayer().getBalance(), A_LITTLE_MONEY 
					+ tileBuilt.getMortgageValue()) == 0);
		this.bankManager.unmortgageProperty(this.engine.getTable().getTile(PROPERTY_ID), this.playerOne);
		fetchProperty();
		assertTrue(this.tileBuilt.isMortgage() && Double.compare(this.playerOne.getPlayer().getBalance(), A_LITTLE_MONEY) == 0);
	}
	
	private void initEngine() {
		Map<Integer, String> names = new HashMap<Integer, String>();
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		Map<Integer, Integer> positions = new HashMap<Integer, Integer>();
		Map<Integer, States> states = new HashMap<Integer, States>();
		names.put(0, "test");
		balance.put (0, 0.0);
		positions.put(0, 0);
		states.put(0, States.IN_GAME);
		this.engine = new GameEngineImpl(names, balance, positions, states);
		this.engine.createTable();
		this.playerOne = new PlayerManagerImpl(0, this.engine);
		this.bankManager = new BankManagerImpl(this.engine);
	}
}
