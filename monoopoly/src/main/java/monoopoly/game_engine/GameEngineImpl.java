package monoopoly.game_engine;

import java.util.*;

import monoopoly.controller.bank.BankManager;
import monoopoly.controller.bank.BankManagerImpl;
import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.controller.player_manager.TurnManager;
import monoopoly.controller.player_manager.TurnManagerImpl;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.utilities.*;

public class GameEngineImpl implements GameEngine {

	/**
	 * creating a map for each credential you need, reachable by player's ID
	 */
	private static final int FIRST_PLAYER = 0;

	private Map<Integer, String> name = new HashMap<>();

	private Map<Integer, Double> balance = new HashMap<>();

	private Map<Integer, Integer> position = new HashMap<>();

	private Map<Integer, monoopoly.utilities.States> state = new HashMap<>();

	private TurnManager turnManager = new TurnManagerImpl(this.FIRST_PLAYER);

	private Map<Integer, Integer> dices;

	private Table table;

	private CardManager cardManager;
	
	private BankManager bankManager = new BankManagerImpl(this);

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
						  final Map<Integer, Double> balance,
						  final Map<Integer, Integer> position,
						  final Map<Integer, monoopoly.utilities.States> state) {
		this.name = name;
		this.balance = balance;
		this.position = position;
		this.state = state;
	}

	public Table createTable() {
		this.table = new TableImpl();
		return this.getTable();
	}

	public PlayerManager createPlayer(final int ID) {
		return new PlayerManagerImpl(ID,this);
	}

	public void createPlayers() {
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while(it.hasNext()) {
			//this.createPlayer(it.next().getKey());
			this.turnManager.getPlayersList().add(this.createPlayer(it.next().getKey()));
		}
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

	/*
	 * Getters reachable by PlayerManager by just putting ID
	 */
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

	public int getPosition(final int ID) {
		if (this.name.keySet().contains(ID)) {
			return this.position.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found");
		}
	}

	public monoopoly.utilities.States getState(final int ID){
		if (this.name.keySet().contains(ID)) {
			return this.state.get(ID);
		}
		else {
			throw new java.lang.IllegalArgumentException("No player found");
		}
	}

	public Map<Integer, Double> getBalance() {
		return balance;
	}

	public Map<Integer, Integer> getPosition() {
		return position;
	}
	
	public Table getTable() {
		return this.table;
	}

	@Override
	public Map<Integer, Integer> getDices() {
		return this.dices;
	}

	public PlayerManager passPlayer() {
		return this.turnManager.nextTurn();
	}
	
	@Override
	public void updateDices(Map<Integer, Integer> dices) {
		this.dices = dices;
	}

	public PlayerManager getGameWinner() {
		Integer current;
		for (Map.Entry<Integer, Double> entry: this.balance.entrySet()) {
			if (entry.getValue() > current) {
				current = entry.getKey();
			}
		}
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			if (pM.getPlayerManagerID() == current) {
				return pM;
			}
		}
		/*Double max = this.balance.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ?
				 1 : -1).get().getValue();*/
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
		Card card = tile.idPlayerWhoHasDrawID(this.turnManager.getCurrentPlayer())
				.actualPlayersBalance(balance)
				.actualPlayersPosition(position)
				.actualBankBalance(this.bankManager.getBank().getBankBudget())
				.draw();
		this.cardManager = new CardManager(card.getDescription, card.getCardNumber, card.getOriginDeck);
		monoopoly.game_engine.CardEffect effect = this.cardManager.knowCard(card);
		if (effect == monoopoly.game_engine.CardEffect.MONEY_EXCHANGE) {
			Map<Integer, Double> map = card.getValueToApplyOnPlayersBalance().get(); 
			for (Map.Entry<Integer, Double> entry: map.entrySet()) {
				this.bankManager.giveMoney(entry.getValue(), this.turnManager.getPlayersList().get(entry.getKey()));
			}
		}
		else if (effect == monoopoly.game_engine.CardEffect.JAIL_IN) {
			this.turnManager.getPlayersList().get(this.turnManager.getCurrentPlayer())
											 .getPlayer().setState(monoopoly.utilities.States.PRISONED);
		}
		else if (effect == monoopoly.game_engine.CardEffect.JAIL_IN) {
			//TODO 
		}
		else if (effect == monoopoly.game_engine.CardEffect.RELATIVE_MOVE) {
			Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				Integer currentPos = this.turnManager.getPlayersList().get(entry.getKey())
																	  .getPlayer().getPosition();
				this.turnManager.getPlayersList().get(entry.getKey())			
												 .getPlayer().setPosition(currentPos + entry.getValue());
			}
		}
		else if (effect == monoopoly.game_engine.CardEffect.ABSOLUTE_MOVE) {
			Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				this.turnManager.getPlayersList().get(entry.getKey())
												 .getPlayer().setPosition(entry.getValue());
			}
		}
		else if (effect == monoopoly.game_engine.CardEffect.REMOVE_BUILDINGS) {
			Map<Integer, Integer> map = card.getNumberOfBuildingsToRemove().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()){
				Property tileDet = (Property) this.table.getTile(entry.getKey());
				for (int i=0; i<entry.getValue(); i++) {
					tileDet.sellBuilding();
				}
			}
		}


	}




		

	


}
