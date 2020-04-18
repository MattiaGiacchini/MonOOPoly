package monoopoly.controller.bank;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.game_engine.GameEngine;
import monoopoly.model.Bank;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;

public class BankManagerImpl implements BankManager {

	
	private final Bank bank;
	private final Table table;
	private Set<Tile> purchaseableProperties;
	private GameEngine gameEngine;
	
	public BankManagerImpl(GameEngine engine) {
		this.gameEngine = engine;
		this.table = this.gameEngine.getTable();
		this.purchaseableProperties = this.table.getFilteredTiles(Tile.class, x -> x.isPurchasable());
		this.bank = new Bank(this.purchaseableProperties);
	}
	
	@Override
	public void giveMoney(double toGive, PlayerManager player) {
		this.bank.giveMoney(toGive);
		player.collectMoney(toGive);
		if (this.bank.isBankBroken()) {
			/* THIS METHOD RETURNS THE PLAYER THAT HAS WON THE GAME
			 * final PlayerManager winningPlayer = this.gameEngine.getGameWinner();
			 */
			System.out.println("THE BANK IS BROKEN");
		}
	}

	@Override
	public void assignHouse(Tile property, PlayerManager player) {
		final BankCommand command = new BankCommand() {

			@Override
			public void execute(Bank bank) {
				checkPurchasability(property);
				Property toBuild = (Property)property;
				if (toBuild.getNumberOfHotelBuilt() < 1) {
					toBuild.buildOn();
					double moneyPaid = toBuild.getNumberOfHotelBuilt() == 1 ? toBuild.getCostToBuildHotel() : toBuild.getCostToBuildHouse();
					player.payMoney(moneyPaid);
					bank.giveMoney(-moneyPaid);
				}
			}
			
		};
		command.execute(this.bank);
	}

	@Override
	public Bank getBank() {
		return this.bank;
	}
	@Override
	public void mortgageProperty(Tile property, PlayerManager player) {
		final BankCommand command = new BankCommand() {
			
			@Override
			public void execute(Bank bank) {
				checkPurchasability(property);
				Purchasable purchasable = (Purchasable)property;
				Property toRemove = (Property)purchasable;
				if (!purchasable.isMortgage() && toRemove.getNumberOfHouseBuilt() == 0) {
					double money = purchasable.mortgage();
					bank.giveMoney(money);
					player.collectMoney(money);
					bank.getMortgagedProperties().put(property, player.getPlayer());
				}
			}
		};
		
		command.execute(this.bank);
	}
	@Override
	public void unmortgageProperty(Tile property, PlayerManager player) {
		final BankCommand command = new BankCommand() {
			
			@Override
			public void execute(Bank bank) {
				checkPurchasability(property);
				Purchasable purchasable = (Purchasable)property;
				if (purchasable.isMortgage()) {
					double money = purchasable.getMortgageValue();
					purchasable.removeMortgage();
					bank.giveMoney(-money);
					player.payMoney(money);
					bank.getMortgagedProperties().remove(property);
				}
			}
		};
		command.execute(this.bank);
	}
	@Override
	public void buyProperty(Tile property, PlayerManager player) {
		final BankCommand command = new BankCommand() {
			
			@Override
			public void execute(Bank bank) {
				checkPurchasability(property);
				Purchasable purchasable = (Purchasable)property;
				if(purchasable.getOwner().isEmpty()) {
					double money = purchasable.getSalesValue();
					purchasable.setOwner(Optional.of(player.getPlayerManagerID()));
					bank.getAssignedProperties().put(property, player.getPlayer());
					bank.giveMoney(money);
					player.collectMoney(-money);
				} else {
					throw new IllegalStateException("Property already bought!");
				}
			}
		};
		
		command.execute(this.bank);
	}
	
	private void checkPurchasability(Tile property) {
		if (!property.isPurchasable()) {
			throw new IllegalArgumentException("Property " + property.getName() + " has to be purchasable.");
		}
	}
	
	@Override
	public void sellHouse(Tile property, PlayerManager player) {
		final BankCommand command = new BankCommand() {
			
			@Override
			public void execute(Bank bank) {
				Purchasable purchasable = (Purchasable)property;
				checkOwned(purchasable);
				Property toSell = (Property)purchasable;
				double money = toSell.sellBuilding();
				player.collectMoney(money);
			}	
		};
		
		command.execute(this.bank);
	}

	
	private void checkOwned(Purchasable property) {
		if(property.getOwner().isEmpty()) {
			throw new IllegalStateException("Property doesn't have an owner");
		}
	}
}
