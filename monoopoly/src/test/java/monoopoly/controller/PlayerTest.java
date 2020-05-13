package monoopoly.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.model.item.TableImpl;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;

/**
 * A test class for the player manager.
 */
public class PlayerTest {
    private static final int TABLE_SIZE = 40;
    private static final int GO_TO_PRISON_POSITION = 30;
    private static final int FREE_PARKING_POSITION = 20;
    private static final double PLAYER_BALANCE = 500.00;
    private static final double BALANCE_VARIATION = 500.00;

    private PlayerManager manager;

    /**
     * Player manager initialization.
     */
    @Before
    public void initialize() {
        manager = new PlayerManagerImpl(2, new PlayerImpl.Builder().playerId(2).name("Mattia").balance(PLAYER_BALANCE)
                .position(10).state(States.IN_GAME).build());
        manager.setTable(new TableImpl());
    }

    /**
     * Testing the player movement.
     */
    @Test
    public void testMovement() {

        manager.movePlayer(10);
        assertEquals(FREE_PARKING_POSITION, manager.getPlayer().getPosition());

        manager.leavePrison();

        manager.movePlayer(10);
        assertEquals(10, manager.getPlayer().getPosition());

        manager.goToPosition(GO_TO_PRISON_POSITION);
        assertEquals(States.PRISONED, manager.getPlayer().getState());
        assertEquals(10, manager.getPlayer().getPosition());

        manager.leavePrison();

        manager.movePlayer(TABLE_SIZE);
        assertEquals(10, manager.getPlayer().getPosition());
    }

    /**
     * Testing the balance variations.
     */
    @Test
    public void testBalance() {

        manager.payMoney(1000.00);
        assertTrue(manager.getPlayer().getBalance().equals(-BALANCE_VARIATION));

        manager.collectMoney(BALANCE_VARIATION);
        assertTrue(manager.getPlayer().getBalance().equals(0.00));

        manager.collectMoney(BALANCE_VARIATION);
        assertTrue(manager.getPlayer().getBalance().equals(BALANCE_VARIATION));
    }

    /**
     * Testing the state variation.
     */
    @Test
    public void testState() {

        manager.goToPosition(GO_TO_PRISON_POSITION);
        assertEquals(States.PRISONED, manager.getPlayer().getState());

        manager.leavePrison();
        assertEquals(States.IN_GAME, manager.getPlayer().getState());

        manager.giveUp();
        assertEquals(States.BROKE, manager.getPlayer().getState());

        manager.getPlayer().setState(States.IN_GAME);
    }

    /**
     * Testing the {@link Player} builder.
     */
    @Test
    public void testBuilder() {
        assertEquals(manager.getPlayer().getID(), 2);
        assertEquals(manager.getPlayer().getName(), "Mattia");
        assertNotEquals(manager.getPlayer().getName(), "Aiman");
        assertEquals(manager.getPlayer().getPosition(), 10);
        assertEquals(manager.getPlayer().getState(), States.IN_GAME);

    }

    /**
     * Testing the {@link Player} builder exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testBuilderException() {
        new PlayerImpl.Builder().name("Mattia").build();

    }

    /**
     * Testing the {@link Player} movement exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testPlayerManagerException() {
        new PlayerManagerImpl(0, manager.getPlayer());
    }
}
