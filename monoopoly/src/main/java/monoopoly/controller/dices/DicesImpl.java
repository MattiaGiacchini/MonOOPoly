package monoopoly.controller.dices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Table;

public class DicesImpl implements Dices {

	private final Table gameTable;
	private final Map<Integer, Integer> dices;
	private final Random random;
	private final int numberOfDices;
	private static final int RANDOM_DICE_BOUND = 5;
	/**
	 * constructor.
	 * @param number the number of dices.
	 * @param table the table.
	 */
	public DicesImpl(final int number, final Table table) {
		this.gameTable = table;
		this.dices = new HashMap<>();
		this.random = new Random();
		this.numberOfDices = number;
	}
	
	@Override
	public final void roll(final PlayerManager playerManager) {
		for (int i = 0; i < this.numberOfDices; i++) {
			this.dices.put(i, random.nextInt(RANDOM_DICE_BOUND) + 1);
		}
		final int diceSum = this.dices.values().stream().reduce(0, Integer::sum);
		playerManager.movePlayer(diceSum);
		this.gameTable.notifyDices(diceSum);
	}
	
	@Override
	public final Map<Integer, Integer> getDices() {
		return Collections.unmodifiableMap(this.dices);
	}

	@Override
	public final void resetDices() {
		this.dices.clear();
	}

	@Override
	public final boolean areEquals() {
		return this.dices.get(0).equals(this.dices.get(1));
	}
	

}
