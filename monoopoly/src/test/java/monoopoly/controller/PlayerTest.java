package monoopoly.controller;

import org.junit.Test;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;

import static org.junit.Assert.*;

import org.junit.Before;

public class PlayerTest {
	private PlayerManager manager;

	@Before
	public void initialize() {
		manager = new PlayerManagerImpl(5, new PlayerImpl.Builder().playerId(5).name("Mattia").balance(500.00)
				.position(10).state(States.IN_GAME).build());
	}

	@Test
	public void testMovement() {

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

		manager.payMoney(1000.00);
		assertTrue(manager.getPlayer().getBalance().equals(-500.00));

		manager.collectMoney(500.00);
		assertTrue(manager.getPlayer().getBalance().equals(0.00));

		manager.collectMoney(15000.00);
		assertTrue(manager.getPlayer().getBalance().equals(15000.00));
	}

	@Test
	public void testState() {

		manager.goToPosition(30);
		assertEquals(States.PRISONED, manager.getPlayer().getState());

		manager.leavePrison();
		assertEquals(States.IN_GAME, manager.getPlayer().getState());

		manager.giveUp();
		assertEquals(States.BROKE, manager.getPlayer().getState());

		manager.getPlayer().setState(States.IN_GAME);
	}

	@Test
	public void testBuilder() {
		assertEquals(manager.getPlayer().getID(), 5);
		assertEquals(manager.getPlayer().getName(), "Mattia");
		assertNotEquals(manager.getPlayer().getName(), "Aiman");
		assertEquals(manager.getPlayer().getPosition(), 20);
		assertEquals(manager.getPlayer().getState(), States.IN_GAME);

	}

	@Test(expected = IllegalStateException.class)
	public void testBuilderException() {
		new PlayerImpl.Builder().name("Mattia").build();

	}

	@Test(expected = IllegalStateException.class)
	public void testPlayerManagerException() {
		new PlayerManagerImpl(0, manager.getPlayer());
	}
}
