package monoopoly.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.fxml.FXML;
import monoopoly.Main;
import monoopoly.controller.bank.BankManager;
import monoopoly.controller.bank.BankManagerImpl;
import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.managers.CardManagerImpl;
import monoopoly.controller.managers.TurnManager;
import monoopoly.controller.managers.TurnManagerImpl;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.controller.stockmarket.StockMarket;
import monoopoly.controller.stockmarket.StockMarketImpl;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.TileDeck;
import monoopoly.model.item.card.Card;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.main.MainBoardControllerImpl;
import monoopoly.view.controller.ScoreboardViewControllerImpl;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.SceneManager;
import monoopoly.view.utilities.SceneManagerImpl;
import monoopoly.view.utilities.ScenePath;
import monoopoly.view.utilities.TileViewCategory;

public final class GameEngineImpl implements GameEngine {

    private static final int OUT_OF_PRISON = -150;
    private static final int VIA = 200;
    private static final int MAX_TILES = 39;

    private final Map<Integer, String> name;
    private final Map<Integer, Double> balance;
    private final TurnManager turnManager = new TurnManagerImpl();
    private Table table = new TableImpl();
    private final BankManager bankManager = new BankManagerImpl(this);
    private final Dices dicesUse = new DicesImpl(2, this.table);
    private final StockMarket stockMarket = new StockMarketImpl(this.table);
    private Integer tileHit;
    private final SceneManager sceneManager = new SceneManagerImpl();

    @FXML
    private MainBoardControllerImpl mainBoardController;

    public GameEngineImpl(final Map<Integer, String> name, final Map<Integer, Double> balance) {
        this.name = name;
        this.balance = balance;
        this.tileHit = 0;
    }

    @Override
    public Table createTable() {
        this.table = new TableImpl();
        return this.getTable();
    }

    private PlayerManager createPlayer(final int iD) {
        final PlayerManager pM = new PlayerManagerImpl(iD, new PlayerImpl.Builder().playerId(iD)
                                                                                   .name(this.getName(iD))
                                                                                   .balance(this.getBalance(iD))
                                                                                   .build());
        pM.setTable(this.table);
        return pM;
    }

    @Override
    public void createPlayers() {
        this.turnManager.setCurrentID(this.name.values().size() - 1);
        final Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
        while (it.hasNext()) {
            this.turnManager.getPlayersList().add(this.createPlayer(it.next().getKey()));
        }
        this.initializeView();
        this.passPlayer();
    }

    @Override
    public PlayerManager currentPlayer() {
        for (final PlayerManager pM: this.turnManager.getPlayersList()) {
            if (pM.getPlayerManagerID() == this.turnManager.getCurrentPlayer()) {
                return pM;
            }
        }
        return null;
    }

    @Override
    public List<PlayerManager> playersList() {
        return this.turnManager.getPlayersList();
    }

    @Override
    public void setMainBoardController(final MainBoardControllerImpl mainBoardController) {
        this.mainBoardController = mainBoardController;
    }


    @Override
    public String getName(final int iD) {
        if (this.name.keySet().contains(iD)) {
            return this.name.get(iD);
        } else {
            throw new IllegalArgumentException("No player found");
        }
    }


    @Override
    public Double getBalance(final int iD) {
        if (this.name.keySet().contains(iD)) {
            return this.balance.get(iD);
        } else {
            throw new IllegalArgumentException("No player found");
        }
    }


    @Override
    public Table getTable() {
        return this.table;
    }

    private void updateCurrentPlayer() {
        this.mainBoardController.updateCurrentPlayer(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                                       .getPlayer()
                                                                       .getName(), 
                                                     this.playersList().get(this.turnManager.getCurrentPlayer())
                                                                       .getPlayer()
                                                                       .getBalance());
    }


    @Override
    public void passPlayer() {
        this.dicesUse.resetDices();
        this.turnManager.nextTurn();
        if (this.currentPlayer().getPlayer().getState() == States.BROKE) {
            if (!this.turnManager.areThereOtherPlayersInGame()) {
                this.endGame();
            }
            this.passPlayer();
        }
        this.updateAlways();
        if (this.turnManager.getCurrentPlayer() == 0) {
            this.incRound();
            this.stockMarket.setNewMarketValue();
            this.mainBoardController.updateStockMarket(this.stockMarket.getMarket(), this.stockMarket.getStockHistory());
            for (final PlayerManager pM: this.playersList()) {
                if (pM.getPrisonTurnCounter() == 3) {
                    this.bankManager.giveMoney(OUT_OF_PRISON, pM);
                    pM.resetPrisonCounter();
                }
                pM.newTurn();
            }
        }
    }

    public void incRound() {
        this.turnManager.setRound();
    }

    @Override
    public void endGame() {
        ScoreboardViewControllerImpl scoreboardViewContollerImpl;
        final Map<Integer, Double> quotationsMap = new HashMap<>();
        for (final PlayerManager pM: this.playersList()) {
            pM.setTable(this.table);
            double quotationProperties = 0;
            for (final Purchasable p: pM.getProperties()) {
                quotationProperties = quotationProperties + p.getQuotation();
            }
            quotationsMap.put(pM.getPlayerManagerID(), quotationProperties + pM.getPlayer().getBalance());
        }
        this.sceneManager.loadScene(ScenePath.SCOREBOARD, Main.getPrimaryStage());
        scoreboardViewContollerImpl = this.sceneManager.getLeaderboardController();
        scoreboardViewContollerImpl.showLeaderboard(this.name, quotationsMap);
    }

    @Override
    public void useCard() {
        CardManagerImpl cardManager;
        final Tile tile = this.table.getTile(this.turnManager.getPlayersList().get(this.turnManager.getCurrentPlayer())
                                                                              .getPlayer().getPosition());
        final Map<Integer, Double> balance = new HashMap<>();
        final Map<Integer, Integer> position = new HashMap<>();
        for (final PlayerManager pM: this.playersList()) {
            if (!pM.isBroken()) {
                balance.put(pM.getPlayer().getID(), pM.getPlayer().getBalance());
            }
        }
        for (final PlayerManager pM: this.playersList()) {
            if (!pM.isBroken()) {
                position.put(pM.getPlayer().getID(), pM.getPlayer().getPosition());
            }
        }

        final Card card = ((TileDeck) tile).idPlayerWhoHasDraw(this.currentPlayer().getPlayerManagerID())
                                           .actualPlayersBalance(balance)
                                           .actualPlayersPosition(position)
                                           .draw();
        this.mainBoardController.showDeckCard(card.getOriginDeck().toString(), card.getDescription());
        cardManager = new CardManagerImpl();
        final monoopoly.utilities.CardEffect effect = cardManager.knowCard(card);
        if (effect == monoopoly.utilities.CardEffect.MONEY_EXCHANGE) {
            final Map<Integer, Double> map = card.getValueToApplyOnPlayersBalance().get();
            for (final Map.Entry<Integer, Double> entry: map.entrySet()) {
                this.bankManager.giveMoney(entry.getValue(), this.turnManager.getPlayersList().get(entry.getKey()));
            }
        } else if (effect == monoopoly.utilities.CardEffect.JAIL_IN) {
            this.currentPlayer().goToPrison();
        } else if (effect == monoopoly.utilities.CardEffect.JAIL_OUT) {
            this.playersList().get(this.turnManager.getCurrentPlayer()).leavePrison();
        } else if (effect == monoopoly.utilities.CardEffect.RELATIVE_MOVE) {
            final Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
            for (final Map.Entry<Integer, Integer> entry: map.entrySet()) {
                this.playersList().get(entry.getKey()).movePlayer(entry.getValue());
            }
            this.giveTileInfo(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                .getPlayer()
                                                .getPosition());
            this.checkCard();
        } else if (effect == monoopoly.utilities.CardEffect.ABSOLUTE_MOVE) {
            final Map<Integer, Integer> map = card.getAbsoluteMoveToPosition().get();
            for (final Map.Entry<Integer, Integer> entry: map.entrySet()) {
                this.playersList().get(entry.getKey()).goToPosition(entry.getValue());
            }
            if (this.currentPlayer().getPlayer().getPosition() == 0) {
                this.bankManager.giveMoney(VIA, this.playersList().get(this.turnManager.getCurrentPlayer()));
            }
            this.giveTileInfo(this.playersList().get(this.turnManager.getCurrentPlayer())
                    .getPlayer()
                    .getPosition());
        } else if (effect == monoopoly.utilities.CardEffect.REMOVE_BUILDINGS) {
            final Map<Integer, Integer> map = card.getNumberOfBuildingsToRemove().get();
            for (final Map.Entry<Integer, Integer> entry: map.entrySet()) {
                final Property tileDet = (Property) this.table.getTile(entry.getKey());
                for (int i = 0; i < entry.getValue(); i++) {
                    tileDet.sellBuilding();
                }
            }
        }
        this.giveTileInfo(this.currentPlayer().getPlayer().getPosition());
    }

    @Override
    public Map<Integer, Integer> rollDices() {
        final int prevPos = this.currentPlayer().getPlayer().getPosition();
        this.dicesUse.roll(this.playersList().get(this.turnManager.getCurrentPlayer()));
        if (this.dicesUse.areEquals()) {
            this.playersList().get(this.turnManager.getCurrentPlayer()).leavePrison();
            this.playersList().get(this.turnManager.getCurrentPlayer()).resetPrisonCounter();
        }
        this.updateAlways();
        this.giveTileInfo(this.currentPlayer().getPlayer().getPosition());
        if (prevPos + this.dicesUse.getDices().get(0) + this.dicesUse.getDices().get(1) > MAX_TILES) {
            this.bankManager.giveMoney(VIA, this.playersList().get(this.turnManager.getCurrentPlayer()));
        }
        this.checkCard();
        return this.dicesUse.getDices();
    }

    @Override
    public void giveProperties(final Integer iD) {
        if (iD < this.playersList().size()) {
            final Set<String> properties = new HashSet<>();
            for (final Purchasable p: this.playersList().get(iD).getProperties()) {
                properties.add(p.getName());
            }
            this.updateAlways();
            this.mainBoardController.showPlayerProperties(properties, this.playersList().get(iD).getPlayer().getName());
        }
    }

    @Override
    public void giveTileInfo(final Integer tileNum) {
        this.tileHit = tileNum;
        final Tile tile = this.table.getTile(tileNum);
        PurchasableState state = PurchasableState.OTHER;
        if (tile.isPurchasable()) {
            if (((Purchasable) tile).getOwner().isEmpty()) {
                state = PurchasableState.FREE_PROPERTY;
            } else if (this.turnManager.getCurrentPlayer().equals(((Purchasable) tile).getOwner().get())) {
                state = PurchasableState.MY_PROPERTY;
            } else if (!this.turnManager.getCurrentPlayer().equals(((Purchasable) tile).getOwner().get())) {
                state = PurchasableState.OWNED_PROPERTY;
            } 
        }
 
        TileInfo tileInfo;
        if (tile.isBuildable()) { // property
            tileInfo = new TileInfo.Builder().tileName(tile.getName())
                                             .purchasableState(state)
                                             .purchasableCategory(TileViewCategory.PROPERTY)
                                             .currentPlayerBalance(this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getBalance())
                                             .housesAmount(((Property) tile).getNumberOfHouseBuilt() + ((Property) tile).getNumberOfHotelBuilt())
                                             .housePrice(((Property) tile).getCostToBuildHouse())
                                             .mortgageState(((Purchasable) tile).isMortgage()).rentToPay(((Purchasable) tile).getLeaseValue())
                                             .purchasableValue(((Purchasable) tile).getSalesValue())
                                             .owner(Optional.of(((Purchasable) tile).getOwner().isEmpty() ? "NONE"
                                                    : this.playersList().get(((Purchasable) tile).getOwner().get()).getPlayer().getName()))
                                             .rentValues(Optional.of(((Purchasable) tile).getLeaseList()))
                                             .mortgageValue(((Purchasable) tile).getMortgageValue())
                                             .unMortgageValue(((Purchasable) tile).getCostToRemoveMortgage())
                                             .buildHouseEnabled(((Property) tile).isBuildOnEnabled())
                                             .sellHouseEnabled(((Property) tile).isSellBuildingsEnabled())
                                             .currentPlayerOnSelectedTile(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                                                            .getPlayer()
                                                                                            .getPosition() == this.tileHit)
                                             .rentPayed(this.playersList().get(this.currentPlayer().getPlayerManagerID())
                                                                           .getPlayer()
                                                                           .getState()
                                                                           .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY) 
                                                       || !state.equals(PurchasableState.OWNED_PROPERTY))
                                             .build();
        } else if (tile.getCategory() == Category.STATION) { // station
            tileInfo = new TileInfo.Builder().tileName(tile.getName())
                                             .purchasableState(state)
                                             .purchasableCategory(TileViewCategory.STATION)
                                             .currentPlayerBalance(this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getBalance())
                                             .mortgageState(((Purchasable) tile).isMortgage()).rentToPay(((Purchasable) tile).getLeaseValue())
                                             .purchasableValue(((Purchasable) tile).getSalesValue())
                                             .owner(Optional.of(((Purchasable) tile).getOwner().isEmpty() ? "NONE"
                                                    : this.playersList().get(((Purchasable) tile).getOwner().get()).getPlayer().getName()))
                                             .rentValues(Optional.of(((Purchasable) tile).getLeaseList()))
                                             .mortgageValue(((Purchasable) tile).getMortgageValue())
                                             .unMortgageValue(((Purchasable) tile).getCostToRemoveMortgage())
                                             .currentPlayerOnSelectedTile(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                                                            .getPlayer()
                                                                                            .getPosition() == this.tileHit)
                                             .rentPayed(this.playersList().get(this.currentPlayer().getPlayerManagerID())
                                                                           .getPlayer()
                                                                           .getState()
                                                                           .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY) 
                                                       || !state.equals(PurchasableState.OWNED_PROPERTY))
                                             .build();
 
        } else if (tile.getCategory() == Category.SOCIETY) { // SOCIETY
            tileInfo = new TileInfo.Builder().tileName(tile.getName())
                                             .purchasableState(state)
                                             .purchasableCategory(TileViewCategory.SOCIETY)
                                             .currentPlayerBalance(this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getBalance())
                                             .mortgageState(((Purchasable) tile).isMortgage()).rentToPay(((Purchasable) tile).getLeaseValue())
                                             .purchasableValue(((Purchasable) tile).getSalesValue())
                                             .owner(Optional.of(((Purchasable) tile).getOwner().isEmpty() ? "NONE"
                                                    : this.playersList().get(((Purchasable) tile).getOwner().get()).getPlayer().getName()))
                                             .mortgageValue(((Purchasable) tile).getMortgageValue())
                                             .unMortgageValue(((Purchasable) tile).getCostToRemoveMortgage())
                                             .currentPlayerOnSelectedTile(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                                                            .getPlayer()
                                                                                            .getPosition() == this.tileHit)
                                             .rentPayed(this.playersList().get(this.currentPlayer().getPlayerManagerID())
                                                                           .getPlayer()
                                                                           .getState()
                                                                           .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY) 
                                                       || !state.equals(PurchasableState.OWNED_PROPERTY))
                                             .build();
 
        } else { // other
            tileInfo = new TileInfo.Builder().tileName(tile.getName())
                                             .purchasableState(state)
                                             .purchasableCategory(TileViewCategory.OTHER)
                                             .build();
        }
        this.mainBoardController.showPropertyPane(tileInfo);
        this.updateAlways();
    }

    @Override
    public void buildHouse() {
        this.bankManager.assignHouse(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
        this.updateAlways();
        this.giveTileInfo(this.tileHit);
    }

    @Override
    public void sellHouse() {
        this.bankManager.sellHouse(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
        this.updateAlways();
        this.giveTileInfo(this.tileHit);
    }

    @Override
    public void mortgage() { 
        this.bankManager.mortgageProperty(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
        this.updateAlways();
        this.giveTileInfo(this.tileHit);
        this.giveTileInfo(this.tileHit);
    }

    @Override
    public void unMortgage() {
        this.bankManager.unmortgageProperty(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
        this.updateAlways();
        this.giveTileInfo(this.tileHit);
    }

    @Override
    public void buyPurchasable() {
        final Purchasable tile = (Purchasable) this.table.getTile(this.playersList().get(this.turnManager.getCurrentPlayer())
                                                         .getPlayer()
                                                         .getPosition());
        this.bankManager.buyProperty(tile, this.playersList().get(this.turnManager.getCurrentPlayer()));
        this.updateAlways();
        this.giveTileInfo(this.tileHit);
    }

    @Override
    public void payRent() {
        final Purchasable tile = (Purchasable) this.table.getTile(this.currentPlayer()
                                                         .getPlayer()
                                                         .getPosition());
        //toglie soldi al debitore
        this.bankManager.giveMoney(-tile.getLeaseValue(), this.currentPlayer());
        //li da al proprietario
        this.bankManager.giveMoney(tile.getLeaseValue(), this.playersList().get(tile.getOwner().get()));
        this.currentPlayer().hasPayedRent();
        this.updateAlways();
    }

    private void initializeView() {
        final List<String> properties = new ArrayList<>();
        for (int i = 0; i <= MAX_TILES; i++) {
             properties.add(i, table.getTile(i).getName());
        }
        this.mainBoardController.setGameEngine(this);
        this.mainBoardController.setTileNames(properties);
        this.mainBoardController.setPlayerNames(this.name);
        this.updateAlways();
    }

    private void updateAlways() {
        final Map<Integer, Integer> positions = new HashMap<>();
        final Map<Integer, Double> balances = new HashMap<>();
        if (this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getBalance() <= 0) {
            this.lose();
        }
        for (final PlayerManager pM: this.playersList()) {
            positions.put(pM.getPlayer().getID(), pM.getPlayer().getPosition());
            balances.put(pM.getPlayer().getID(), pM.getPlayer().getBalance());
        }
        this.mainBoardController.updatePlayers(positions, balances);
        this.updateCurrentPlayer();
    }

    @Override
    public void lose() {
        this.bankManager.giveMoney(-this.currentPlayer().getPlayer().getBalance(), this.currentPlayer());
        this.currentPlayer().giveUp();
        this.mainBoardController.deletePlayer(this.currentPlayer().getPlayerManagerID());
        for (final Purchasable p: this.currentPlayer().getProperties()) {
            if (p.getCategory() != Tile.Category.SOCIETY && p.getCategory() != Tile.Category.STATION && !p.isMortgage()) {
                for (int i = 0; i < ((Property) p).getNumberOfHouseBuilt() + ((Property) p).getNumberOfHotelBuilt(); i++) {
                    ((Property) p).sellBuilding();
                }
            }
            if (p.isMortgage()) {
                p.removeMortgage();
            }
            p.setOwner(Optional.empty());
        }
        this.bankManager.removeAssignmentsFromPlayer(this.currentPlayer());
    }

    private void checkCard() {
        if (this.table.getTile(this.currentPlayer().getPlayer().getPosition()).isDeck()) {
            this.useCard();
        }
    }

}
