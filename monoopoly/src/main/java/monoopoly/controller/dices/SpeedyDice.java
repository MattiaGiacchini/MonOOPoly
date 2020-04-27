package monoopoly.controller.dices;

import java.util.Random;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

public class SpeedyDice extends AbstractDiceDecorator{

	private int number;
	private final Random speedyRandom;
	private static final int DICE_BOUND = 5;
	
	public SpeedyDice(Dices toDecorate) {
		super(toDecorate);
		this.speedyRandom = new Random();
	}
		

	@Override
	public void roll() {
		super.roll();
		this.number = speedyRandom.nextInt(DICE_BOUND) + 1;
		this.notifyObservers();
	}
	
	private void notifyObservers() {
		for (DicesObserver obs : super.getObserverSet()) {
			obs.notifySpeedyDice(this.number);
		}
	}

}
