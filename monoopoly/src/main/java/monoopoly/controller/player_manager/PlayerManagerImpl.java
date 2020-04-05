package monoopoly.controller.player_manager;

import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
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
	private PlayerPropertyManager propertyManager;
	private PlayerBalanceManager balanceManager = new PlayerBalanceManagerImpl();

	public PlayerManagerImpl(final int playerManagerID) {
		this.playerManagerID = playerManagerID;
		this.player = this.createPlayer();
		this.initializePlayer();
		this.propertyManager = new PlayerPropertyManagerImpl(this.playerManagerID);
	}

	private Player createPlayer() {
		return new PlayerImpl(playerManagerID);
	}

	private void initializePlayer() {
		/*this.player.setName(gameEngine.getName(playerManagerID));
		this.player.setBalance(gameEngine.getBalance(playerManagerID));
		this.player.setPosition(gameEngine.getPosition(playerManagerID));
		this.player.setState(gameEngine.getState(playerManagerID));*/
		this.player.setBalance(0.0);
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
	public PlayerPropertyManager getPropertyManager() {
		return this.propertyManager;
	}
/*
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
		this.balanceManager.updateBalance(player, -amount);
	}*/

	@Override
	public void collectMoney(Double amount) {
		this.balanceManager.updateBalance(player, amount);

	}
	/*
	@Override
	public void buildHouse(Property property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sellHouse(Property property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyPurchasable(Purchasable purchasableTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sellPurchasable(Purchasable purchasableTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mortgagePurchasable(Purchasable purchasableTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unMortgagePurchasable(Purchasable purchasableTile) {
		// TODO Auto-generated method stub
		
	}*/

}