package monoopoly.game_engine;

import java.util.*;

import monoopoly.controller.bank.BankManager;
import monoopoly.controller.bank.BankManagerImpl;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.controllermanagers.CardManagerImpl;
import monoopoly.controllermanagers.TurnManager;
import monoopoly.controllermanagers.TurnManagerImpl;
import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.player.PlayerImpl;
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

	private CardManagerImpl cardManager;
	
	//private BankManager bankManager = new BankManagerImpl(this);

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
		String name = this.getName(ID);
		return new PlayerManagerImpl(ID, new PlayerImpl.Builder().playerId(ID)
																 .name(this.getName(ID))
												    			 .balance(this.getBalance(ID))
												    			 .build());
	}

	public void createPlayers() {
		Iterator<Map.Entry<Integer, String>> it = name.entrySet().iterator();
		while(it.hasNext()) {
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

	/*public int getPosition(final int ID) {
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
	}*/

	/*public Map<Integer, Double> getBalance() {
		return balance;
	}

	public Map<Integer, Integer> getPosition() {
		return position;
	}*/
	
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
		Integer winner = -1;
		Double greatest = 0.0;
		Map<Integer, Double> quotationsMap = new HashMap<>();
		for (PlayerManager pM: this.turnManager.getPlayersList()) {
			pM.setTable(this.table);
			double quotationProperties = 0;
			for (Purchasable p: pM.getProperties()) {
				quotationProperties = quotationProperties + p.getQuotation();
			}
			quotationsMap.put(pM.getPlayer().getID(), quotationProperties + pM.getPlayer().getBalance());	
		}
		for (Map.Entry<Integer, Double> entry: quotationsMap.entrySet()) {
			if (entry.getValue() > greatest) {
				winner = entry.getKey();
			}
		}
		return this.turnManager.getPlayersList().get(winner);
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
		Card card = tile.idPlayerWhoHasDraw(this.turnManager.getCurrentPlayer())
				.actualPlayersBalance(balance)
				.actualPlayersPosition(position)
				.draw();
		this.cardManager = new CardManagerImpl(card.getDescription, card.getCardNumber, card.getOriginDeck);
		monoopoly.utilities.CardEffect effect = this.cardManager.knowCard(card);
		if (effect == monoopoly.utilities.CardEffect.MONEY_EXCHANGE) {
			Map<Integer, Double> map = card.getValueToApplyOnPlayersBalance().get(); 
			for (Map.Entry<Integer, Double> entry: map.entrySet()) {
				this.bankManager.giveMoney(entry.getValue(), this.turnManager.getPlayersList().get(entry.getKey()));
			}
		}
		else if (effect == monoopoly.utilities.CardEffect.JAIL_IN) {
			this.playersList().get(this.turnManager.getCurrentPlayer())
							  .getPlayer().setState(monoopoly.utilities.States.PRISONED);
		}
		else if (effect == monoopoly.utilities.CardEffect.JAIL_OUT) {
			thisplayersList().get(this.turnManager.getCurrentPlayer())
							 .getPlayer().hasPrisonCard();
		}
		else if (effect == monoopoly.utilities.CardEffect.RELATIVE_MOVE) {
			Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				Integer currentPos = this.playersList().get(entry.getKey())
													   .getPlayer().getPosition();
				this.playersList().get(entry.getKey())			
								  .getPlayer().setPosition(currentPos + entry.getValue());
			}
		}
		else if (effect == monoopoly.utilities.CardEffect.ABSOLUTE_MOVE) {
			Map<Integer, Integer> map = card.getRelativeMoveToPosition().get();
			for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
				this.playersList().get(entry.getKey())
								  .getPlayer().setPosition(entry.getValue());
			}
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
	}




		

	


}
