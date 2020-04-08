package monoopoly.trade;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.trade.Trade;
import monoopoly.model.trade.TradeBuilder;
import monoopoly.model.trade.TradeBuilderImpl;

public class TestBuilder {
	private TradeBuilder tradeBuilder = new TradeBuilderImpl();
	private final PlayerManager playerOne = new PlayerManagerImpl(0);
	private final PlayerManager playerTwo = new PlayerManagerImpl(1);
	private static final double MONEY_ONE = 1.0;
	private static final double MONEY_TWO = 2.0;
	private final Purchasable propertyOne = new Purchasable() {
		
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
			return null;
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
	
	private final Purchasable propertyTwo = new Purchasable() {
		
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
			return null;
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
	
	@Test(expected = IllegalStateException.class)
	public void testVoidTrade() {
		tradeBuilder.build();
	}
	
	@Test
	public void testBlankTrade() {
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
}
