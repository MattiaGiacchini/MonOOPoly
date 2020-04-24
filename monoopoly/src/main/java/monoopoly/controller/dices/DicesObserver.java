package monoopoly.controller.dices;

public interface DicesObserver {

	/**
	 * This method notifies the sum of the first two dices.
	 * @param sum what came out.
	 */
	void notifyNormalDices(int sum);
	
	/**
	 * This method notifies the result of the roll of the speedy Dice
	 * @param number what came out.
	 */
	void notifySpeedyDice(int number);
	
}
