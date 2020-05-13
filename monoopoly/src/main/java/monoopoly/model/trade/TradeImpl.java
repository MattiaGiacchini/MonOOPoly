package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;

public class TradeImpl implements Trade {
	
	private final PlayerManager playerOne;
	private final PlayerManager playerTwo;
	private final Set<Purchasable> propertiesOne;
	private final Set<Purchasable> propertiesTwo;
	private final double moneyOne;
	private final double moneyTwo;
	
	public TradeImpl(final PlayerManager playerOne, final PlayerManager playerTwo,
				final Set<Purchasable> propertiesOne, final Set<Purchasable> propertiesTwo,
				final double moneyOne, final double moneyTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo; 
		this.propertiesOne = propertiesOne;
		this.propertiesTwo = propertiesTwo;
		this.moneyOne = moneyOne;
		this.moneyTwo = moneyTwo;
	}
	
	@Override
	public final PlayerManager getPlayerOne() {
		return this.playerOne;
	}

	@Override
	public final PlayerManager getPlayerTwo() {
		return this.playerTwo;
	}

	@Override
	public final Set<Purchasable> getPlayerOneTradeProperty() {
		return this.propertiesOne;
	}

	@Override
	public final Set<Purchasable> getPlayerTwoTradeProperty() {
		return this.propertiesTwo;
	}

	@Override
	public final double getPlayerOneTradeMoney() {
		return this.moneyOne;
	}

	@Override
	public final double getPlayerTwoTradeMoney() {
		return this.moneyTwo;
	}

}
