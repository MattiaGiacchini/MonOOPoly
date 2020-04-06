package monoopoly.controller.trades;

import java.util.Optional;
import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Purchasable;
import monoopoly.model.trade.Trade;

public class TraderImpl implements Trader {

	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private Optional<Trade> trade;
	
	public TraderImpl() {
	}
	
	@Override
	public Trade getTrade() {
		return this.trade.get();
	}

	@Override
	public void acceptTrade() {
		if (this.trade.isPresent()){
			this.swapAlgorithm();
		} else {
			return;
		}
	}
	
	private void swapAlgorithm() {
		final Set<Purchasable> setOne = this.trade.get().getPlayerOneTradeProperty();
		final Set<Purchasable> setTwo = this.trade.get().getPlayerTwoTradeProperty();
		final double moneyOne = this.trade.get().getPlayerOneTradeMoney();
		final double moneyTwo = this.trade.get().getPlayerTwoTradeMoney();
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

	@Override
	public void changeTrade(Optional<Trade> trade) {
		this.trade = trade;
		this.playerOne = trade.get().getPlayerOne();
		this.playerTwo = trade.get().getPlayerTwo();
	}

}