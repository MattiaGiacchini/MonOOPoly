package monoopoly.view.controller;

import java.util.Optional;

/**
 * This interface represents the dices view controller and is used to update the
 * dices displayed on the board.
 */
public interface DiceViewController {

    /**
     * This method updates the dices displayed.
     * 
     * @param dice1 first dice value
     * @param dice2 second dice value
     * @param dice3 speedy dice optional value
     */
    void updateDices(int dice1, int dice2, Optional<Integer> dice3);

}
