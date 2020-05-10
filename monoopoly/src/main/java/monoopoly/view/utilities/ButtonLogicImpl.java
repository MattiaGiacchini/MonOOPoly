package monoopoly.view.utilities;

public class ButtonLogicImpl implements ButtonLogic {

    @Override
    public boolean enoughMoney(Double balance, Double value) {
        return balance - value >= 0;
    }

}
