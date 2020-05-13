package monoopoly.view.controller.dices;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents the controller of the dices in the view.
 */
public class DiceViewControllerImpl implements DiceViewController, Initializable {

    private static final int DICE_FACES = 6;

    @FXML
    private ImageView dice1;

    @FXML
    private ImageView dice2;

    @FXML
    private ImageView dice3;

    private final Set<ImageView> dices = new HashSet<>(Arrays.asList(dice1, dice2, dice3));

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDices(final int dice1, final int dice2, final Optional<Integer> dice3) {
        this.clearDices();
        this.updateNormalDices(dice1, dice2);
        this.updateSpeedyDice(dice3);
    }

    /**
     * This method removes the dices images displayed.
     */
    private void clearDices() {
        dices.stream().peek((x) -> {
            x.setImage(null);
        });
    }

    /**
     * This method displays the "normal" dices on the screen.
     * 
     * @param dice1 to display
     * @param dice2 to display
     */
    private void updateNormalDices(final int dice1, final int dice2) {
        this.dice1.setImage(new Image(getClass().getResourceAsStream("/dices/dice" + dice1 + ".png")));
        this.dice2.setImage(new Image(getClass().getResourceAsStream("/dices/dice" + dice2 + ".png")));
    }

    /**
     * This method displays, if present, the speedy dice value.
     * 
     * @param dice3 speedyDice to display.
     */
    private void updateSpeedyDice(final Optional<Integer> dice3) {
        final String speedyDice;
        if (dice3.isEmpty()) {
            this.swapImages();
        } else {

            if (dice3.get() <= 3) {
                speedyDice = dice3.get().toString();
            } else if (dice3.get() == DICE_FACES) {
                speedyDice = "MrM";
            } else {
                speedyDice = "Bus";
            }

            this.dice3.setImage(new Image(this.getClass().getResourceAsStream("/dices/dice" + speedyDice + ".png")));
        }
    }

    /**
     * This methods sets the visible dices in a nicer way.
     */
    private void swapImages() {
        this.dice3.setImage(this.dice2.getImage());
        this.dice2.setImage(null);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

}
