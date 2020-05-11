package monoopoly.controller.trades;

import java.util.Optional;
import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
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
		
		for(final Purchasable property : setOne) {
			property.setOwner(Optional.of(this.playerTwo.getPlayerManagerID()));
		}
		
		for(final Purchasable property : setTwo) {
			property.setOwner(Optional.of(this.playerOne.getPlayerManagerID()));
		}
		
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
