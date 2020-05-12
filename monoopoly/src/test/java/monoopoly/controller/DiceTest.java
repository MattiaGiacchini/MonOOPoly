package monoopoly.controller;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.game_engine.GameEngine;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;

public class DiceTest {

	private static final String TEST = "test";
	private Dices dicesTwo;
	private Dices dicesThree;
	private GameEngine testEngine;
	private PlayerManager playerTest;
	private final Table tableTest = new TableImpl();
	
	@Test
	public void testDiceInit() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name(TEST).balance(0.0).build());
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesThree = new DicesImpl(3, this.tableTest);
		assertTrue(this.dicesTwo.getDices().isEmpty());
		assertTrue(this.dicesThree.getDices().isEmpty());
	}
	
	@Test
	public void testDualDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name(TEST).balance(0.0).build());
		this.playerTest.setTable(this.testEngine.getTable());
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesTwo.roll(this.playerTest);
		final Integer sum = this.dicesTwo.getDices().values().stream().reduce(0, Integer::sum);
		assertSame(this.playerTest.getPlayer().getPosition(), sum);
		assertSame(this.tableTest.getNotifiedDices(), sum);
	}
	
	@Test
	public void testThreeDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name(TEST).balance(0.0).build());
		this.playerTest.setTable(this.testEngine.getTable());
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesThree.roll(this.playerTest);
		final Integer sum = this.dicesThree.getDices().values().stream().reduce(0, Integer::sum);
		assertSame(this.playerTest.getPlayer().getPosition(), sum);
		assertSame(this.tableTest.getNotifiedDices(), sum);
	}

	private void initEngine() {
		final Map<Integer, String> names = new HashMap<>();
		final Map<Integer, Double> balance = new HashMap<>();
		final Map<Integer, Integer> positions = new HashMap<>();
		final Map<Integer, States> states = new HashMap<>();
		names.put(0, TEST);
		balance.put(0, 0.0);
		positions.put(0, 0);
		states.put(0, States.IN_GAME);
		this.testEngine = new GameEngineImpl(names, balance);
		testEngine.createTable();
	}
}
