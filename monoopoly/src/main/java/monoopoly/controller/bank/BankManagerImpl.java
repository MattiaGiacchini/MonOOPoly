package monoopoly.controller.bank;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.engine.GameEngine;
import monoopoly.model.Bank;
import monoopoly.model.item.tile.Tile;
import monoopoly.model.item.tile.Tile.Category;
import monoopoly.model.item.tile.purchasable.Property;
import monoopoly.model.item.tile.purchasable.Purchasable;
import monoopoly.model.player.Player;

public class BankManagerImpl implements BankManager {

    private final Bank bank;
    private final GameEngine gameEngine;
    private final BankCommandExecutor executor;

    public BankManagerImpl(final GameEngine engine) {
        this.gameEngine = engine;
        this.bank = new Bank(this.gameEngine.getTable().getFilteredTiles(Tile.class, x -> x.isPurchasable()));
        this.executor = new BankCommandExecutor();
    }

    @Override
    public final void giveMoney(final double toGive, final PlayerManager player) {
        executor.executeCommand(() -> {
            this.bank.giveMoney(toGive);
            player.collectMoney(toGive);
            if (this.bank.isBankBroken()) {
                this.gameEngine.endGame();
            }
        });
    }

    @Override
    public final void assignHouse(final Tile property, final PlayerManager player) {
        executor.executeCommand(() -> {
            checkPurchasability(property);
            final Property toBuild = (Property) property;
            if (toBuild.getNumberOfHotelBuilt() < 1) {
                toBuild.buildOn();
                final double moneyPaid = toBuild.getNumberOfHotelBuilt() == 1 ? toBuild.getCostToBuildHotel()
                        : toBuild.getCostToBuildHouse();
                player.payMoney(moneyPaid);
                bank.giveMoney(-moneyPaid);
            }
        });
    }

    @Override
    public final Bank getBank() {
        return this.bank;
    }

    @Override
    public final void mortgageProperty(final Tile property, final PlayerManager player) {
        executor.executeCommand(() -> {
            checkPurchasability(property);
            final Purchasable purchasable = (Purchasable) property;
            Optional<Property> toRemove = Optional.empty();
            if (!checkStationOrSociety(purchasable)) {
                toRemove = Optional.of((Property) purchasable);
            }
            if (checkMortage(purchasable, toRemove)) {
                final double money = purchasable.mortgage();
                bank.giveMoney(money);
                player.collectMoney(money);
                bank.getMortgagedProperties().put(property, player.getPlayer());
            }
        });
    }

    private boolean checkMortage(final Purchasable purc, final Optional<Property> prop) {
        if (!purc.isMortgage() && prop.isPresent()) {
            return prop.get().getNumberOfHotelBuilt() == 0;
        } else {
            return true;
        }
    }

    /**
     * checks if a {@link Purchasable} is of {@link Category} society or station.
     * 
     * @param purch The purchasable.
     * @return if is a society or a station.
     */
    private boolean checkStationOrSociety(final Purchasable purch) {
        return Arrays.asList(Category.SOCIETY, Category.STATION).contains(purch.getCategory());
    }

    @Override
    public final void unmortgageProperty(final Tile property, final PlayerManager player) {
        executor.executeCommand(() -> {
            checkPurchasability(property);
            final Purchasable purchasable = (Purchasable) property;
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
    public final void buyProperty(final Tile property, final PlayerManager player) {
        executor.executeCommand(() -> {
            checkPurchasability(property);
            final Purchasable purchasable = (Purchasable) property;
            if (purchasable.getOwner().isEmpty()) {
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

    private void checkPurchasability(final Tile property) {
        if (!property.isPurchasable()) {
            throw new IllegalArgumentException("Property " + property.getName() + " has to be purchasable.");
        }
    }

    @Override
    public final void sellHouse(final Tile property, final PlayerManager player) {
        executor.executeCommand(() -> {
            final Purchasable purchasable = (Purchasable) property;
            checkOwned(purchasable);
            final Property toSell = (Property) purchasable;
            if (toSell.getNumberOfHouseBuilt() != 0) {
                final double money = toSell.sellBuilding();
                player.collectMoney(money);
            }
        });
    }

    private void checkOwned(final Purchasable property) {
        if (property.getOwner().isEmpty()) {
            throw new IllegalStateException("Property doesn't have an owner");
        }
    }

    @Override
    public final void removeAssignmentsFromPlayer(final PlayerManager player) {
        removePlayerFromMap(this.bank.getAssignedProperties(), player);
        removePlayerFromMap(this.bank.getMortgagedProperties(), player);
    }

    /**
     * Removes all the {@link Tile} referring to a {@link Player} in a {@link Map}
     * Tile - Player.
     * 
     * @param map    the map.
     * @param player the player.
     */
    private void removePlayerFromMap(final Map<Tile, Player> map, final PlayerManager player) {
        final Set<Tile> toRemove = getTilesInTilePlayerMapFromPlayer(map, player);
        for (final Tile tile : toRemove) {
            map.remove(tile);
        }
    }

    private Set<Tile> getTilesInTilePlayerMapFromPlayer(final Map<Tile, Player> map, final PlayerManager player) {
        return map.entrySet().stream().filter(e -> e.getValue().equals(player.getPlayer())).map(e -> e.getKey())
                .collect(Collectors.toSet());
    }
}
