package monoopoly.controller.bank;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.engine.GameEngine;
import monoopoly.model.Bank;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.player.Player;

public class BankManagerImpl implements BankManager {

	
	private final Bank bank;
	private final Table table;
	private final Set<Tile> purchaseableProperties;
	private final GameEngine gameEngine;
	private final BankCommandExecutor executor;
	
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
				this.gameEngine.endGame();
			}
		});
		
	}

	@Override
	public void assignHouse(Tile property, PlayerManager player) {
		
		executor.executeCommand(() -> {
				checkPurchasability(property);
				final Property toBuild = (Property)property;
				if (toBuild.getNumberOfHotelBuilt() < 1) {
					toBuild.buildOn();
					final double moneyPaid = toBuild.getNumberOfHotelBuilt() == 1 ? toBuild.getCostToBuildHotel() : toBuild.getCostToBuildHouse();
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
			final Purchasable purchasable = (Purchasable)property;
			Optional<Property> toRemove = Optional.empty();
			if (!checkStationOrSociety(purchasable)) {
				toRemove = Optional.of((Property)purchasable);
			}
			if (!purchasable.isMortgage() && toRemove.isPresent()?
					toRemove.get().getNumberOfHouseBuilt() == 0 : true) {
					final double money = purchasable.mortgage();
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
				final Purchasable purchasable = (Purchasable)property;
				if (purchasable.isMortgage()) {
					final double money = purchasable.getMortgageValue();
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
			final Purchasable purchasable = (Purchasable)property;
			if(purchasable.getOwner().isEmpty()) {
				final double money = purchasable.getSalesValue();
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
				final Purchasable purchasable = (Purchasable)property;
				checkOwned(purchasable);
				final Property toSell = (Property)purchasable;
				if (toSell.getNumberOfHouseBuilt() != 0) {
					final double money = toSell.sellBuilding();
					player.collectMoney(money);
				}
			});
		
	}

	
	private void checkOwned(Purchasable property) {
		if(property.getOwner().isEmpty()) {
			throw new IllegalStateException("Property doesn't have an owner");
		}
	}

	@Override
	public void removeAssignmentsFromPlayer(PlayerManager player) {
		removePlayerFromMap(this.bank.getAssignedProperties(), player);
		removePlayerFromMap(this.bank.getMortgagedProperties(), player);
	}

	/**
	 * Removes all the {@link Tile} referring to a {@link Player} in a {@link Map} Tile - Player.
	 * @param map the map.
	 * @param player the player.
	 */
	private void removePlayerFromMap(Map<Tile, Player> map, PlayerManager player) {
		final Set<Tile> toRemove = getTilesInTilePlayerMapFromPlayer(map, player);
		
		for (final Tile tile : toRemove) {
			map.remove(tile);
		}
	}

	private Set<Tile> getTilesInTilePlayerMapFromPlayer(Map<Tile, Player> map, PlayerManager player) {
		return map.entrySet().stream()
							 .filter(e -> e.getValue().equals(player.getPlayer()))
							 .map(e -> e.getKey())
							 .collect(Collectors.toSet());
	}
}
