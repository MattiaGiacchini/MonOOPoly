package monoopoly.model.trade;

import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Purchasable;

public class TradeImpl implements Trade {
	
	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private Set<Purchasable> propertiesOne;
	private Set<Purchasable> propertiesTwo;
	private double moneyOne;
	private double moneyTwo;
	
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
	public PlayerManager getPlayerOne() {
		return this.playerOne;
	}

	@Override
	public PlayerManager getPlayerTwo() {
		return this.playerTwo;
	}

	@Override
	public Set<Purchasable> getPlayerOneTradeProperty() {
		return this.propertiesOne;
	}

	@Override
	public Set<Purchasable> getPlayerTwoTradeProperty() {
		return this.propertiesTwo;
	}

	@Override
	public double getPlayerOneTradeMoney() {
		return this.moneyOne;
	}

	@Override
	public double getPlayerTwoTradeMoney() {
		return this.moneyTwo;
	}

}
