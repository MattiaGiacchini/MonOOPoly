package monoopoly.controller;

import org.junit.Test;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.game_engine.GameEngine;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.utilities.States;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Before;

public class PlayerTest {

	private GameEngine engine;

	@Before
	public void initialize() {

		Map<Integer, String> name = new HashMap<Integer, String>();
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		Map<Integer, Integer> position = new HashMap<Integer, Integer>();
		Map<Integer, States> state = new HashMap<Integer, States>();

		name.put(1, "Daniele");
		name.put(2, "Mattia");
//		name.put(3, "Aiman");
//		name.put(6, "Cristian");

		balance.put(1, 751.11);
		balance.put(2, 5111.11);
//		balance.put(3, 8911.11);
//		balance.put(6, 4151.11);

		position.put(1, 41);
		position.put(2, 10);
//		position.put(3, 32);
//		position.put(6, 12);

		state.put(1, States.IN_GAME);
		state.put(2, States.PRISONED);
//		state.put(3, States.IN_GAME);
//		state.put(6, States.IN_GAME);

		engine = new GameEngineImpl(name, balance, position, state);

	}

	@Test
	public void testMovement() {
		/*
		 * There's an error in GameEngine to correct... it gives the next player in the
		 * list
		 */
		PlayerManager manager = new PlayerManagerImpl(1, engine);

		manager.movePlayer(10);
		assertEquals(10, manager.getPlayer().getPosition());

		manager.leavePrison();

		manager.movePlayer(10);
		assertEquals(20, manager.getPlayer().getPosition());

		manager.goToPosition(30);
		assertEquals(States.PRISONED, manager.getPlayer().getState());
		assertEquals(10, manager.getPlayer().getPosition());

		manager.leavePrison();

		manager.movePlayer(40);
		assertEquals(10, manager.getPlayer().getPosition());
	}

	@Test
	public void testBalance() {
		PlayerManager manager = new PlayerManagerImpl(1, engine);

		manager.payMoney(1000.00);
		assertTrue(manager.getPlayer().getBalance().equals(4111.11));

		manager.collectMoney(500.00);
		assertTrue(manager.getPlayer().getBalance().equals(4611.11));
	}

	@Test
	public void testState() {
		PlayerManager manager = new PlayerManagerImpl(1, engine);

		manager.goToPosition(30);
		assertEquals(States.PRISONED, manager.getPlayer().getState());

		manager.leavePrison();
		assertEquals(States.IN_GAME, manager.getPlayer().getState());

		manager.giveUp();
		assertEquals(States.BROKE, manager.getPlayer().getState());

		manager.getPlayer().setState(States.IN_GAME);
	}
}
