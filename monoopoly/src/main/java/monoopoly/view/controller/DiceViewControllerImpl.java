package monoopoly.view.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiceViewControllerImpl implements DiceViewController {

	@FXML
	private ImageView dice1;

	@FXML
	private ImageView dice2;

	@FXML
	private ImageView dice3;

	private Set<ImageView> dices = new HashSet<ImageView>(Arrays.asList(dice1, dice2, dice3));

	@Override
	public void updateDices(int dice1, int dice2, Optional<Integer> dice3) {
		this.clearDices();
		this.updateNormalDices(dice1, dice2);
		this.updateSpeedyDice(dice3);
	}

	/**
	 * This method removes the dices images displayed
	 */
	private void clearDices() {
		dices.stream().peek((x) -> {
			x.setImage(null);
		});
	}

	/**
	 * This method displays the "normal" dices on the screen
	 * 
	 * @param dice1
	 * @param dice2
	 */
	private void updateNormalDices(int dice1, int dice2) {
		this.dice1.setImage(new Image("/dices/dice" + dice1 + ".png"));
		this.dice2.setImage(new Image("/dices/dice" + dice2 + ".png"));
	}

	/**
	 * This method displays, if present, the speedy dice value
	 * 
	 * @param dice3
	 */
	private void updateSpeedyDice(Optional<Integer> dice3) {
		final String speedyDice;
		if (dice3.isEmpty()) {
			this.swapImages();
		} else {
			if (dice3.get() <= 3) {
				speedyDice = dice3.get().toString();
			} else if (dice3.get() == 6) {
				speedyDice = "MrM";
			} else {
				speedyDice = "Bus";
			}
			this.dice3.setImage(new Image("/dices/dice" + speedyDice + ".png"));
		}
	}

	/**
	 * This methods sets the visible dices in a nicer way
	 */
	private void swapImages() {
		this.dice3.setImage(this.dice2.getImage());
		this.dice2.setImage(null);
	}

}
