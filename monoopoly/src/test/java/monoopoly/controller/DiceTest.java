package monoopoly.controller;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.engine.GameEngine;
import monoopoly.engine.GameEngineImpl;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;

public class DiceTest {

	private Dices dicesTwo;
	private Dices dicesThree;
	private GameEngine testEngine;
	private PlayerManager playerTest;
	private Table tableTest = new TableImpl();
	
	@Test
	void testDiceInit() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name("test").balance(0.0).build());
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesThree = new DicesImpl(3, this.tableTest);
		assertTrue(this.dicesTwo.getDices().isEmpty());
		assertTrue(this.dicesThree.getDices().isEmpty());
	}
	
	@Test
	void testDualDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name("test").balance(0.0).build());
		this.playerTest.setTable(this.testEngine.getTable());
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesTwo.roll(this.playerTest);
		final Integer sum = this.dicesTwo.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}
	
	@Test
	void testThreeDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0).name("test").balance(0.0).build());
		this.playerTest.setTable(this.testEngine.getTable());
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesThree.roll(this.playerTest);
		final Integer sum = this.dicesThree.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}

	private void initEngine() {
		Map<Integer, String> names = new HashMap<Integer, String>();
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		Map<Integer, Integer> positions = new HashMap<Integer, Integer>();
		Map<Integer, States> states = new HashMap<Integer, States>();
		names.put(0, "test");
		balance.put (0, 0.0);
		positions.put(0, 0);
		states.put(0, States.IN_GAME);
		this.testEngine = new GameEngineImpl(names, balance);
		testEngine.createTable();
	}
}
