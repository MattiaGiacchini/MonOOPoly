package monoopoly.controller.dices;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.item.Table;

public class SpeedyDice extends AbstractDiceDecorator{
	
	public enum Faces {
		ONE,
		TWO,
		THREE,
		BUS,
		MR_MONOOPOLY;
	}

	private int number;
	private final Random speedyRandom;
	private static final int DICE_BOUND = 5;
	private Map<Integer, Faces> facesMap;
	
	public SpeedyDice(Dices toDecorate) {
		super(toDecorate);
		this.speedyRandom = new Random();
		initMap();
	}
		

	private void initMap() {
		this.facesMap = new HashMap<Integer, SpeedyDice.Faces>();
		this.facesMap.put(1, Faces.ONE);
		this.facesMap.put(2, Faces.TWO);
		this.facesMap.put(3, Faces.THREE);
		this.facesMap.put(4, Faces.BUS);
		this.facesMap.put(5, Faces.BUS);
		this.facesMap.put(6, Faces.MR_MONOOPOLY);
		
	}


	@Override
	public void roll() {
		super.roll();
		this.number = speedyRandom.nextInt(DICE_BOUND) + 1;
		int sum = super.getDices().values().stream().reduce(0, Integer::sum) + 
				this.number <= 3? this.number : 0;
		this.notifyObservers();
	}
	
	private void notifyObservers() {
		for (DicesObserver obs : super.getObserverSet()) {
			obs.notifySpeedyDice(this.facesMap.get(this.number));
		}
	}

}
