package monoopoly.trade;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.controller.trades.Trader;
import monoopoly.controller.trades.TraderImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;
import monoopoly.model.trade.TradeImpl;
import monoopoly.utilities.PurchasableCategory;

public class TestTrader {
	
	private PlayerManager playerOne = new PlayerManagerImpl(0);
	private PlayerManager playerTwo = new PlayerManagerImpl(1);
	private Purchasable propertyOne = new Purchasable() {
		
		@Override
		public boolean isPurchasable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setQuotation(double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOwner(Integer newOwnerIdentify) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeMortgage() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public double mortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean isMortgage() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public double getSalesValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getQuotation() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public PurchasableCategory getPurchaseCategory() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<Integer> getOwner() {
			// TODO Auto-generated method stub
			return Optional.empty();
		}
		
		@Override
		public double getMortgageValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getLeaseValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getCostToRemoveMortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private Purchasable propertyTwo = new Purchasable() {
		
		@Override
		public boolean isPurchasable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setQuotation(double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOwner(Integer newOwnerIdentify) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeMortgage() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public double mortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean isMortgage() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public double getSalesValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getQuotation() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public PurchasableCategory getPurchaseCategory() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<Integer> getOwner() {
			// TODO Auto-generated method stub
			return Optional.empty();
		}
		
		@Override
		public double getMortgageValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getLeaseValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getCostToRemoveMortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private Purchasable propertyThree = new Purchasable() {
		
		@Override
		public boolean isPurchasable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setQuotation(double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOwner(Integer newOwnerIdentify) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeMortgage() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public double mortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean isMortgage() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public double getSalesValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getQuotation() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public PurchasableCategory getPurchaseCategory() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<Integer> getOwner() {
			// TODO Auto-generated method stub
			return Optional.empty();
		}
		
		@Override
		public double getMortgageValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getLeaseValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getCostToRemoveMortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private Purchasable propertyFour = new Purchasable() {
		
		@Override
		public boolean isPurchasable() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setQuotation(double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setOwner(Integer newOwnerIdentify) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeMortgage() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public double mortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean isMortgage() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public double getSalesValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getQuotation() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public PurchasableCategory getPurchaseCategory() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Optional<Integer> getOwner() {
			// TODO Auto-generated method stub
			return Optional.empty();
		}
		
		@Override
		public double getMortgageValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getLeaseValue() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public double getCostToRemoveMortgage() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private static final double MONEYONE = 100;
	private static final double MONEYTWO = 50;
	private TradeBuilder builder = new TradeBuilderImpl();
	
	@Test
	public void testTradeWithoutMoney() {
		this.playerOne.getPropertyManager().buyPurchasable(propertyOne);;
		this.playerOne.getPropertyManager().buyPurchasable(propertyTwo);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyThree);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyFour);
		
		final Set<Purchasable> setOne = new HashSet<Purchasable>();
		final Set<Purchasable> setTwo = new HashSet<Purchasable>();
		
		setOne.add(propertyOne);
		setTwo.add(propertyThree);
		
		
		final Trade trade = builder.setPlayerOne(playerOne)
								   .setPlayerTwo(playerTwo)
								   .setPlayerOneProperties(setOne)
								   .setPlayerTwoProperties(setTwo)
								   .build();
		final Trader traderTest = new TraderImpl(trade);
		traderTest.acceptTrade();
		
		assertTrue(this.playerOne.getPropertyManager().getProperties().contains(propertyThree));
		assertTrue(this.playerTwo.getPropertyManager().getProperties().contains(propertyOne));
	}
	
	@Test
	public void testTradeWithoutProperties() {
		this.playerOne.collectMoney(this.MONEYONE);
		this.playerTwo.collectMoney(this.MONEYTWO);
		
		final Trade trade = builder.setPlayerOne(playerOne)
								   .setPlayerTwo(playerTwo)
								   .setPlayerOneMoney(MONEYONE)
								   .setPlayerTwoMoney(MONEYTWO)
								   .build();
		
		final Trader traderTest = new TraderImpl(trade);
		traderTest.acceptTrade();
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), this.MONEYTWO) == 0);
		assertTrue(Double.compare(this.playerTwo.getPlayer().getBalance(), this.MONEYONE) == 0);
	}
	
	@Test
	public void testCompleteTrade() {
		this.playerOne.collectMoney(this.MONEYONE);
		this.playerTwo.collectMoney(this.MONEYTWO);
		this.playerOne.getPropertyManager().buyPurchasable(propertyOne);;
		this.playerOne.getPropertyManager().buyPurchasable(propertyTwo);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyThree);
		this.playerTwo.getPropertyManager().buyPurchasable(propertyFour);
		
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
		final Trader traderTest = new TraderImpl(trade);
		traderTest.acceptTrade();
		
		assertTrue(this.playerOne.getPropertyManager().getProperties().contains(propertyThree));
		assertTrue(this.playerTwo.getPropertyManager().getProperties().contains(propertyOne));
		assertTrue(Double.compare(this.playerOne.getPlayer().getBalance(), this.MONEYTWO) == 0);
		assertTrue(Double.compare(this.playerTwo.getPlayer().getBalance(), this.MONEYONE) == 0);
		
	}
}
