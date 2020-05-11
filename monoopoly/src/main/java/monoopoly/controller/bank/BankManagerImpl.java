package monoopoly.controller.bank;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.game_engine.GameEngine;
import monoopoly.model.Bank;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;

public class BankManagerImpl implements BankManager {

	
	private final Bank bank;
	private final Table table;
	private Set<Tile> purchaseableProperties;
	private GameEngine gameEngine;
	private BankCommandExecutor executor;
	
	public BankManagerImpl(GameEngine engine) {
		this.gameEngine = engine;
		this.table = this.gameEngine.getTable();
		this.purchaseableProperties = this.table.getFilteredTiles(Tile.class, x -> x.isPurchasable());
		this.bank = new Bank(this.purchaseableProperties);
		this.executor = new BankCommandExecutor();
	}
	
	@Override
	public void giveMoney(double toGive, PlayerManager player) {
		
		executor.executeCommand(() -> {
			this.bank.giveMoney(toGive);
			player.collectMoney(toGive);
			if (this.bank.isBankBroken()) {
				 final PlayerManager winningPlayer = this.gameEngine.getGameWinner();
				 System.out.println("THE BANK IS BROKEN, game has been won by player " + winningPlayer.getPlayerManagerID());
			}
		});
		
	}

	@Override
	public void assignHouse(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
				checkPurchasability(property);
				Property toBuild = (Property)property;
				if (toBuild.getNumberOfHotelBuilt() < 1) {
					toBuild.buildOn();
					double moneyPaid = toBuild.getNumberOfHotelBuilt() == 1 ? toBuild.getCostToBuildHotel() : toBuild.getCostToBuildHouse();
					player.payMoney(moneyPaid);
					bank.giveMoney(-moneyPaid);
				}
			});
		
	}

	@Override
	public Bank getBank() {
		return this.bank;
	}
	
	@Override
	public void mortgageProperty(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
			checkPurchasability(property);
			Purchasable purchasable = (Purchasable)property;
			Optional<Property> toRemove = Optional.empty();
			if (!checkStationOrSociety(purchasable)) {
				toRemove = Optional.of((Property)purchasable);
			}
			if (!purchasable.isMortgage() && toRemove.isPresent()?
					toRemove.get().getNumberOfHouseBuilt() == 0 : true) {
					double money = purchasable.mortgage();
					bank.giveMoney(money);
					player.collectMoney(money);
					bank.getMortgagedProperties().put(property, player.getPlayer());
			}
		});
		
	}
	
	/**
	 * checks if a {@link Purchasable} is of {@link Category} society or station.
	 * @param purch The purchasable.
	 * @return if is a society or a station.
	 */
	private boolean checkStationOrSociety(Purchasable purch) {
		return (Arrays.asList(Category.SOCIETY, Category.STATION).contains(purch.getCategory()));
	}
	
	@Override
	public void unmortgageProperty(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
				checkPurchasability(property);
				Purchasable purchasable = (Purchasable)property;
				if (purchasable.isMortgage()) {
					double money = purchasable.getMortgageValue();
					purchasable.removeMortgage();
					bank.giveMoney(-money);
					player.payMoney(money);
					bank.getMortgagedProperties().remove(property);
				}
			});
		
	}
	@Override
	public void buyProperty(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
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
		});
		
	}
	
	private void checkPurchasability(Tile property) {
		if (!property.isPurchasable()) {
			throw new IllegalArgumentException("Property " + property.getName() + " has to be purchasable.");
		}
	}
	
	@Override
	public void sellHouse(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
				Purchasable purchasable = (Purchasable)property;
				checkOwned(purchasable);
				Property toSell = (Property)purchasable;
				if (toSell.getNumberOfHouseBuilt() != 0) {
					double money = toSell.sellBuilding();
					player.collectMoney(money);
				}
			});
		
	}

	
	private void checkOwned(Purchasable property) {
		if(property.getOwner().isEmpty()) {
			throw new IllegalStateException("Property doesn't have an owner");
		}
	}
}
