package monoopoly.controller.dices;

import java.util.Map;

import monoopoly.controller.dices.SpeedyDice.Faces;

public interface DicesObserver {

	/**
	 * This method notifies the sum of the first two dices.
	 * @param sum what came out.
	 */
	void notifyNormalDices(Map<Integer, Integer> diceMap);
	
	/**
	 * This method notifies the result of the roll of the speedy Dice
	 * @param face what came out.
	 */
	void notifySpeedyDice(Faces face);
	
}
