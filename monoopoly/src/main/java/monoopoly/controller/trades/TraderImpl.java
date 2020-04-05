package monoopoly.controller.trades;

import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Purchasable;
import monoopoly.model.trade.Trade;

public class TraderImpl implements Trader {

	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private final Trade trade;
	
	public TraderImpl(Trade trade) {
		this.trade = trade;
		this.playerOne = trade.getPlayerOne();
		this.playerTwo = trade.getPlayerTwo();
	}
	
	@Override
	public Trade getTrade() {
		return this.trade;
	}

	@Override
	public void acceptTrade() {
		this.swapAlgorithm();
	}
	
	private void swapAlgorithm() {
		final Set<Purchasable> setOne = this.trade.getPlayerOneTradeProperty();
		final Set<Purchasable> setTwo = this.trade.getPlayerTwoTradeProperty();
		final double moneyOne = this.trade.getPlayerOneTradeMoney();
		final double moneyTwo = this.trade.getPlayerTwoTradeMoney();
		/*
		final Set<Purchasable> tempSet = setTwo;
		final double tempMoney = moneyTwo;*/
		
		this.playerTwo.getPropertyManager().getProperties().removeAll(setTwo);
		this.playerTwo.getPropertyManager().getProperties().addAll(setOne);
		
		this.playerOne.getPropertyManager().getProperties().removeAll(setOne);
		this.playerOne.getPropertyManager().getProperties().addAll(setTwo);
		
		this.playerOne.getPlayer().updateBalance(-moneyOne);
		this.playerOne.getPlayer().updateBalance(moneyTwo);
		
		this.playerTwo.getPlayer().updateBalance(-moneyTwo);
		this.playerTwo.getPlayer().updateBalance(moneyOne);
	}

}
