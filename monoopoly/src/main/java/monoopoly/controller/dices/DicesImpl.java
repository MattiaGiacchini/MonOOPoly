package monoopoly.controller.dices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

public class DicesImpl implements Dices{

	private PlayerManager currentPlayer;
	private final Table gameTable;
	private Map<Integer, Integer> dices;
	private final Random random;
	private final int numberOfDices;
	private static final int RANDOM_DICE_BOUND = 5;
	/**
	 * constructor.
	 * @param number the number of dices.
	 * @param table the table.
	 */
	public DicesImpl(int number, Table table) {
		this.gameTable = table;
		this.dices = new HashMap<Integer, Integer>();
		this.random = new Random();
		this.numberOfDices = number;
	}
	
	@Override
	public void roll(PlayerManager playerManager, Table table) {
		for (int i = 0; i < this.numberOfDices; i++) {
			this.dices.put(i, random.nextInt(RANDOM_DICE_BOUND) + 1);
			//TODO comunica il dado al player
		}
		final int diceSum = this.dices.values().stream().reduce(0, Integer::sum);
		//TODO logica prigione
		this.currentPlayer.movePlayer(diceSum);
		this.gameTable.notifyDices(diceSum);
	}

	@Override
	public void setCurrentPlayer(PlayerManager playerManager) {
		this.currentPlayer = playerManager;
	}
	
	@Override
	public Map<Integer, Integer> getDices(){
		return Collections.unmodifiableMap(this.dices);
	}

	@Override
	public void resetDices() {
		this.dices.clear();
		
	}

}
