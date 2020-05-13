package monoopoly.view.utilities;

/**
 * This class is an utilities class for the {@link TileInfoController} buttons logic.
 */
public class ButtonLogicImpl implements ButtonLogic {

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean enoughMoney(final Double balance, final Double value) {
        return balance - value >= 0;
    }

}
