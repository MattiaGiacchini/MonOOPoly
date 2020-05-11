package monoopoly.game_engine;

import java.util.*;
import javafx.fxml.FXML;
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
import monoopoly.controller.managers.CardManagerImpl;
import monoopoly.controller.managers.TurnManager;
import monoopoly.controller.managers.TurnManagerImpl;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.TileDeck;
import monoopoly.model.item.card.Card;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.*;
import monoopoly.view.controller.TileInfo;
import monoopoly.view.main.MainBoardController;
import monoopoly.view.main.MainBoardControllerImpl;
import monoopoly.view.utilities.PurchasableState;
import monoopoly.view.utilities.TileViewCategory;

public class GameEngineImpl implements GameEngine {

	/**
	 * creating a map for each credential you need, reachable by player's ID
	 */
	private static final int FIRST_PLAYER = 0;

	private Map<Integer, String> name = new HashMap<>();
	private Map<Integer, Double> balance = new HashMap<>();
	private Map<Integer, Integer> position = new HashMap<>();
	private Map<Integer, States> state = new HashMap<>();
	private TurnManager turnManager = new TurnManagerImpl();
	private Table table = new TableImpl();
	private CardManagerImpl cardManager;
	private BankManager bankManager = new BankManagerImpl(this);
	private Dices dicesUse = new DicesImpl(2, this.table);
	private StockMarket stockMarket = new StockMarketImpl(this.table);
	private Integer tileHit;
	
	@FXML
	private MainBoardControllerImpl mainBoardController;
	
	/**
	 * constructor, so that when StartGame creates GameEngine, it passes
	 * every player's credentials
	 *
	 * @param name
	 * @param balance
	 * @param position
	 * @param state
	 */
	public GameEngineImpl(final Map<Integer, String> name,
						  final Map<Integer, Double> balance
						  /*final Map<Integer, Integer> position,
						  final Map<Integer, monoopoly.utilities.States> state*/) {
		this.name = name;
		this.balance = balance;
		this.position = position;
		this.state = state;
		this.tileHit = 0;
	}

	public Table createTable() {
		this.table = new TableImpl();
		return this.getTable();
	}

	private PlayerManager createPlayer(final int ID) {
		String name = this.getName(ID);
		PlayerManager pM = new PlayerManagerImpl(ID, new PlayerImpl.Builder().playerId(ID)
																			 .name(this.getName(ID))
															    			 .balance(this.getBalance(ID))
															    			 .build());
		pM.setTable(this.table);
		return pM;
	}

	public void createPlayers() {
		this.turnManager.setCurrentID(this.name.values().size() - 1);
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while (it.hasNext()) {
			this.turnManager.getPlayersList().add(this.createPlayer(it.next().getKey()));
		}
		this.initializeView();
		this.passPlayer();
	}

	public PlayerManager currentPlayer() {
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			if (pM.getPlayerManagerID() == this.turnManager.getCurrentPlayer()) {
				return pM;
			}
		}
		return null;
	}

	public List<PlayerManager> playersList(){
		return this.turnManager.getPlayersList();
	}
	
	public void setMainBoardController(MainBoardControllerImpl mainBoardController) {
		this.mainBoardController = mainBoardController;
	//	this.initializeView();
	}
	
	public String getName(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.name.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found");
		}
	}

	public Double getBalance(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.balance.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found");
		}
	}

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

	public void passPlayer() {
		this.dicesUse.resetDices();
		this.turnManager.nextTurn();
		if (this.currentPlayer().getPlayer().getState() == States.BROKE) {
		    if (!this.turnManager.areThereOtherPlayersInGame()) {
		        this.getGameWinner();
		    }
		    this.passPlayer();
		}
		this.updateAlways();
		if (this.turnManager.getCurrentPlayer() == 0) {
			this.incRound();
			this.stockMarket.setNewMarketValue();
			this.mainBoardController.updateStockMarket(this.stockMarket.getMarket(), this.stockMarket.getStockHistory());
			for (PlayerManager pM: this.playersList()) {
	            if (pM.getPrisonTurnCounter() == 3) {
	                this.bankManager.giveMoney(-150, pM);
	                pM.resetPrisonCounter();
	            }
			    pM.newTurn();
			}
		}
	}
	
	public void incRound() {
		this.turnManager.setRound();
	}

	public void getGameWinner() {
	    System.out.println("pissout bitch");
		Map<Integer, Double> quotationsMap = new HashMap<>();
		for (PlayerManager pM: this.playersList()) {
			pM.setTable(this.table);
			double quotationProperties = 0;
			for (Purchasable p: pM.getProperties()) {
				quotationProperties = quotationProperties + p.getQuotation();
			}
			quotationsMap.put(pM.getPlayerManagerID(), quotationProperties + pM.getPlayer().getBalance());
		}
		this.mainBoardController.showLeaderboard(this.name, quotationsMap);
	}

	public void useCard() {
		Tile tile = this.table.getTile(this.turnManager.getPlayersList().get(this.turnManager.getCurrentPlayer())
									   								    .getPlayer().getPosition());
		Map<Integer, Double> balance = new HashMap<>();
		Map<Integer, Integer> position = new HashMap<>();
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			balance.put(pM.getPlayer().getID(), pM.getPlayer().getBalance());
		}
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			position.put(pM.getPlayer().getID(), pM.getPlayer().getPosition());
		}

		Card card = ((TileDeck)tile).idPlayerWhoHasDraw(this.turnManager.getCurrentPlayer())
                    				.actualPlayersBalance(balance)
                    				.actualPlayersPosition(position)
                    				.draw();
        this.mainBoardController.showDeckCard(card.getOriginDeck().toString(), card.getDescription());
		this.cardManager = new CardManagerImpl(card.getDescription(), card.getCardNumber(), card.getOriginDeck());
		monoopoly.utilities.CardEffect effect = this.cardManager.knowCard(card);
		if (effect == monoopoly.utilities.CardEffect.MONEY_EXCHANGE) {
			Map<Integer, Double> map = card.getValueToApplyOnPlayersBalance().get();
			for (Map.Entry<Integer, Double> entry: map.entrySet()) {
				this.bankManager.giveMoney(entry.getValue(), this.turnManager.getPlayersList().get(entry.getKey()));
			}
		}
		else if (effect == monoopoly.utilities.CardEffect.JAIL_IN) {
			this.playersList().get(this.turnManager.getCurrentPlayer()).goToPrison();
		}
		else if (effect == monoopoly.utilities.CardEffect.JAIL_OUT) {
			this.playersList().get(this.turnManager.getCurrentPlayer()).leavePrison();
		}
		else if (effect == monoopoly.utilities.CardEffect.RELATIVE_MOVE) {
			Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				this.playersList().get(entry.getKey()).movePlayer(entry.getValue());
			}
			this.giveTileInfo(this.playersList().get(this.turnManager.getCurrentPlayer())
			                                    .getPlayer()
			                                    .getPosition());
		}
		else if (effect == monoopoly.utilities.CardEffect.ABSOLUTE_MOVE) {
			Map<Integer, Integer> map = card.getAbsoluteMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				this.playersList().get(entry.getKey()).goToPosition(entry.getValue());
			}
			this.giveTileInfo(this.playersList().get(this.turnManager.getCurrentPlayer())
                    .getPlayer()
                    .getPosition());
		}
		else if (effect == monoopoly.utilities.CardEffect.REMOVE_BUILDINGS) {
			Map<Integer, Integer> map = card.getNumberOfBuildingsToRemove().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()){
				Property tileDet = (Property) this.table.getTile(entry.getKey());
				for (int i=0; i<entry.getValue(); i++) {
					tileDet.sellBuilding();
				}
			}
		}
		this.updateAlways();
	}

	public Map<Integer, Integer> rollDices() {
		this.dicesUse.roll(this.playersList().get(this.turnManager.getCurrentPlayer()));
		if (this.dicesUse.areEquals()) {
			this.playersList().get(this.turnManager.getCurrentPlayer()).leavePrison();
			this.playersList().get(this.turnManager.getCurrentPlayer()).resetPrisonCounter();
		}
		this.updateAlways();
		this.giveTileInfo(this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getPosition());
		if (this.tileHit == 0) {
		    this.bankManager.giveMoney(200, this.playersList().get(this.turnManager.getCurrentPlayer()));
		}
		return this.dicesUse.getDices();
	}

	public Set<String> giveProperties(Integer ID) {
		Set<String> properties = new HashSet<>();
		for (Purchasable p: this.playersList().get(ID).getProperties()) {
			properties.add(p.getName());
		}
		this.updateAlways();
		return properties;
	}

	public void giveTileInfo(Integer tileNum) {
		this.tileHit = tileNum;
		Tile tile = this.table.getTile(tileNum);
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
						                     .buildHouseEnabled(((Property)tile).isBuildOnEnabled())
						                     .sellHouseEnabled(((Property)tile).isSellBuildingsEnabled())
						                     .currentPlayerOnSelectedTile(this.playersList().get(this.turnManager.getCurrentPlayer())
						                                                                    .getPlayer()
						                                                                    .getPosition() == this.tileHit)
						                     .rentPayed((this.playersList().get(this.currentPlayer().getPlayerManagerID())
						                                                   .getPlayer()
						                                                   .getState()
						                                                   .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY)) 
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
						                     .rentPayed((this.playersList().get(this.currentPlayer().getPlayerManagerID())
                                                                           .getPlayer()
                                                                           .getState()
                                                                           .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY)) 
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
						                     .rentPayed((this.playersList().get(this.currentPlayer().getPlayerManagerID())
                                                                           .getPlayer()
                                                                           .getState()
                                                                           .equals(States.HAS_PAYED_RENT) && state.equals(PurchasableState.OWNED_PROPERTY)) 
                                                       || !state.equals(PurchasableState.OWNED_PROPERTY))
						                     .build();
 
        } else { // other
            tileInfo = new TileInfo.Builder().tileName(tile.getName())
            								 .purchasableState(state)
            								 .purchasableCategory(TileViewCategory.OTHER)
            								 .build();
            if (tile.isDeck()) {
                this.useCard();
            }
        }
        this.mainBoardController.showPropertyPane(tileInfo);
		this.updateAlways();
	}

	public void buildHouse() {
		this.bankManager.assignHouse(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
		this.updateAlways();
		this.giveTileInfo(this.tileHit);
	}

	public void sellHouse() {
		this.bankManager.sellHouse(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
		this.updateAlways();
		this.giveTileInfo(this.tileHit);
	}

	public void mortgage() {
		this.bankManager.mortgageProperty(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
		this.updateAlways();
		this.giveTileInfo(this.tileHit);
        this.giveTileInfo(this.tileHit);
	}

	public void unMortgage() {
		this.bankManager.unmortgageProperty(this.table.getTile(this.tileHit), this.playersList().get(this.turnManager.getCurrentPlayer()));
		this.updateAlways();
	    this.giveTileInfo(this.tileHit);
	}

	public void buyPurchasable() {
		Purchasable tile = (Purchasable)this.table.getTile(this.playersList().get(this.turnManager.getCurrentPlayer())
														 .getPlayer()
														 .getPosition());
		this.bankManager.buyProperty(tile, this.playersList().get(this.turnManager.getCurrentPlayer()));
		this.updateAlways();
	    this.giveTileInfo(this.tileHit);
	}

	public void payRent() {
		Purchasable tile = (Purchasable)this.table.getTile(this.currentPlayer()
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
		List<String> properties = new ArrayList<String>();
		for (int i = 0; i < 40; i++) {
		     properties.add(i, table.getTile(i).getName());
		}
		this.mainBoardController.setGameEngine(this);
		this.mainBoardController.setTileNames(properties);
		this.mainBoardController.setPlayerNames(this.name);
		this.updateAlways();
	}
	
	private void updateAlways() {
		Map<Integer, Integer> positions = new HashMap<>();
		Map<Integer, Double> balances = new HashMap<>();
		if (this.playersList().get(this.turnManager.getCurrentPlayer()).getPlayer().getBalance() <= 0) {
		    this.lose();
		}
		for (PlayerManager pM: this.playersList()) {
			positions.put(pM.getPlayer().getID(), pM.getPlayer().getPosition());
			balances.put(pM.getPlayer().getID(), pM.getPlayer().getBalance());
		}
		this.mainBoardController.updatePlayers(positions, balances);
		this.updateCurrentPlayer();
	}
	
	public void lose() {
	    this.bankManager.giveMoney(-this.currentPlayer().getPlayer().getBalance(), this.currentPlayer());
	    this.currentPlayer().giveUp();
	    this.mainBoardController.deletePlayer(this.currentPlayer().getPlayerManagerID());
	    for (Purchasable p: this.currentPlayer().getProperties()) {	 
	        if (p.getCategory() != Tile.Category.SOCIETY || p.getCategory() != Tile.Category.STATION || !p.isMortgage()) {
	            for (int i=0; i<((Property)p).getNumberOfHouseBuilt() + ((Property)p).getNumberOfHotelBuilt(); i++) {
	                ((Property)p).sellBuilding();
	            }
	        }
	        if (p.isMortgage()) {
	            p.removeMortgage();
	        }
	        p.setOwner(Optional.empty());
	    }
	    this.bankManager.removeAssignmentsFromPlayer(this.currentPlayer());
	    this.passPlayer();
	}
	
}
