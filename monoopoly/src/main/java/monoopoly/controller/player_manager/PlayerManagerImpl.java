package monoopoly.controller.player_manager;

import java.util.Set;

import monoopoly.GameEngine;
import monoopoly.controller.Trader;
import monoopoly.model.Trade;
import monoopoly.model.TradeBuilder;
import monoopoly.model.player.Player;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;

/**
 * This class represents one of the playerManagers which will manage a single
 * {@link Player}
 */
public class PlayerManagerImpl implements PlayerManager {

	private final int playerManagerID;
	private Player player;
	private PlayerPropertyManager propertyManager = new PlayerPropertyManagerImpl();
	private PlayerBalanceManager balanceManager = new PlayerBalanceManagerImpl();

	private TradeBuilder tradeBuilder;
	private Trader trader;
	private GameEngine gameEngine;

	public PlayerManagerImpl(final int playerManagerID, final GameEngine gameEngine) {
		this.playerManagerID = playerManagerID;
		this.gameEngine = gameEngine.getGameEngine;
		this.player = this.createPlayer();
		this.initializePlayer();
	}

	private Player createPlayer() {
		return new PlayerImpl(playerManagerID);
	}

	private void initializePlayer() {
		this.player.setName(gameEngine.getName(this.playerManagerID));
		this.player.setBalance(gameEngine.getBalance(this.playerManagerID));
		this.player.setPosition(gameEngine.getPosition(this.playerManagerID));
		this.player.setState(gameEngine.getState(this.playerManagerID));
	}

	@Override
	public int getPlayerManagerID() {
		return this.playerManagerID;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void movePlayer(int steps) {
		this.player.updatePosition(steps);
	}

	@Override
	public void goToPosition(int position) {
		this.player.setPosition(position);
	}

	@Override
	public void giveUp() {
		this.player.setState(States.BROKE);
	}

	@Override
	public void buildHouse(Property property) {
		this.propertyManager.updateHouseNumber(property, 1);
	}

	@Override
	public void sellHouse(Property property) {
		this.propertyManager.updateHouseNumber(property, -1);
	}

	@Override
	public void buyPurchasable(Purchasable purchasableTile) {
		this.propertyManager.buyPurchasable(purchasableTile);
	}

	@Override
	public void sellPurchasable(Purchasable purchasableTile) {
		this.propertyManager.sellPurchasable(purchasableTile);
	}

	@Override
	public void mortgagePurchasable(Purchasable purchasableTile) {
		this.propertyManager.mortgagePurchasable(purchasableTile);
	}

	@Override
	public void unMortgagePurchasable(Purchasable purchasableTile) {
		this.propertyManager.unMortgagePurchasable(purchasableTile);
	}

	@Override
	public void payMoney(Double amount) {
		this.balanceManager.updateBalance(this.player, -amount);
	}

	@Override
	public void collectMoney(Double amount) {
		this.balanceManager.updateBalance(this.player, amount);

	}

	@Override
	public Trade createTradeOffer() {
		this.tradeBuilder.build();		
	}

	@Override
	public void acceptTrade() {
		this.trader.acceptTrade();
	}
	
	@Override
	public void declineTrade() {
		this.trader.declineTrade();
	}

	@Override
	public void setOffererOffer(Set<Purchasable> offererRealEstate, Double offererMoney) {
		this.tradeBuilder.setPlayerOne(this.player);
		this.tradeBuilder.setPlayerOneProperties(offererRealEstate);
		this.tradeBuilder.setPlayerOneMoney(offererMoney);
	}

	@Override
	public void setContractorRequest(Player contractor, Set<Purchasable> contractorRealEstate,
			Double contractorMoney) {
		
		this.tradeBuilder.setPlayerTwo(contractor);
		this.tradeBuilder.setPlayerTwoProperties(contractorRealEstate);
		this.tradeBuilder.setPlayerTwoMoney(contractorMoney);
	}

}