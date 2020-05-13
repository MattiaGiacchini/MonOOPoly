package monoopoly.view;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import monoopoly.view.utilities.ButtonLogic;
import monoopoly.view.utilities.ButtonLogicImpl;

/**
 * This class tests the tile info button logic.
 */
public class TestTileInfoView {

    private static final double PRICE = 500.00;
    private static final double LOW_BALANCE = 1_000.00;
    private static final double HIGH_BALANCE = 50.00;

    private final ButtonLogic logic = new ButtonLogicImpl();

    /**
     * Testing the button logic: enough money to pay.
     */
    @Test
    public void testButtonLogics() {

        boolean button = true;
        button = logic.enoughMoney(PRICE, HIGH_BALANCE);
        assertFalse(button);
        button = logic.enoughMoney(PRICE, LOW_BALANCE);
        assertTrue(button);
    }

}
