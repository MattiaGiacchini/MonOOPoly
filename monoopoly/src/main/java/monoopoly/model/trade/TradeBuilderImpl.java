package monoopoly.model.trade;

import java.util.HashSet;
import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;


public class TradeBuilderImpl implements TradeBuilder {

	private PlayerManager playerOne;
	private PlayerManager playerTwo;
	private Set<Purchasable> propertiesOne;
	private Set<Purchasable> propertiesTwo;
	private double moneyOne;
	private double moneyTwo;
	
	
	@Override
	public final TradeBuilder playerOne(final PlayerManager player) {
		this.playerOne = player;
		return this;
	}

	@Override
	public final TradeBuilder playerTwo(final PlayerManager player) {
		this.playerTwo = player;
		return this;
	}

	@Override
	public final TradeBuilder playerOneProperties(final Set<Purchasable> properties) {
		this.propertiesOne = properties;
		return this;
	}

	@Override
	public final TradeBuilder playerTwoProperties(final Set<Purchasable> properties) {
		this.propertiesTwo = properties;
		return this;
	}

	@Override
	public final TradeBuilder playerOneMoney(final double money) {
		this.moneyOne = money;
		return this;
	}

	@Override
	public final TradeBuilder playerTwoMoney(final double money) {
		this.moneyTwo = money;
		return this;
	}

	@Override
	public final TradeImpl build() {
		if (this.playerOne == null || this.playerTwo == null) {
			throw new IllegalStateException("The trade must have at least " 
						+ "the players involved in it!");
		}
		if (this.propertiesOne == null) {
			this.propertiesOne = new HashSet<>();
		}
		if (this.propertiesTwo == null) {
			this.propertiesTwo = new HashSet<>();
		}
		return new TradeImpl(this.playerOne, this.playerTwo, this.propertiesOne,
				this.propertiesTwo, this.moneyOne, this.moneyTwo);
	}


}
