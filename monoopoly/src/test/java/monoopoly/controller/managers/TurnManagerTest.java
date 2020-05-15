package monoopoly.controller.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.controller.player.manager.PlayerManagerImpl;
import monoopoly.model.player.PlayerImpl;
import monoopoly.utilities.States;

/**
 * Testing TurnManagerImpl class.
 */
public class TurnManagerTest {

    private static final Double INITIAL_BALANCE = 100.0;
    private final TurnManager turnManager = new TurnManagerImpl();

    private void buildPlayers() {
        final PlayerManager pM1 = new PlayerManagerImpl(0, new PlayerImpl.Builder().playerId(0)
                                                                                   .name("p0")
                                                                                   .balance(INITIAL_BALANCE)
                                                                                   .build());
        this.turnManager.getPlayersList().add(pM1);
        final PlayerManager pM2 = new PlayerManagerImpl(1, new PlayerImpl.Builder().playerId(1)
                                                                                   .name("p1")
                                                                                   .balance(INITIAL_BALANCE)
                                                                                   .build());
        this.turnManager.getPlayersList().add(pM2);
        final PlayerManager pM3 = new PlayerManagerImpl(2, new PlayerImpl.Builder().playerId(2)
                                                                                   .name("p2")
                                                                                   .balance(INITIAL_BALANCE)
                                                                                   .build());
        this.turnManager.getPlayersList().add(pM3);
    }

    /**
     * Testing the passing player mechanism.
     */
    @Test
    public void testPassPlayer() {
        this.buildPlayers();
        this.turnManager.nextTurn();
        assertEquals(this.turnManager.getCurrentPlayer(), Integer.valueOf(1));
        this.turnManager.nextTurn();
        assertEquals(this.turnManager.getCurrentPlayer(), Integer.valueOf(2));
        this.turnManager.nextTurn();
        assertEquals(this.turnManager.getCurrentPlayer(), Integer.valueOf(0));
    }

    /**
     * Testing the recognition of the in-game players.
     */
    @Test
    public void testLastPlayer() {
        this.buildPlayers();
        assertTrue(this.turnManager.areThereOtherPlayersInGame());
        this.turnManager.getPlayersList().get(0).getPlayer().setState(States.BROKE);
        this.turnManager.getPlayersList().get(1).getPlayer().setState(States.BROKE);
        assertFalse(this.turnManager.areThereOtherPlayersInGame());
    }
}
