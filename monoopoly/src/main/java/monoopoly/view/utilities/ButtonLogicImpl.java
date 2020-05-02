package monoopoly.view.utilities;

public class ButtonLogicImpl implements ButtonLogic {

	private static final int MAX_HOUSES = 5;

	@Override
	public boolean enoughMoney(Double balance, Double value) {
		return balance - value >= 0;
	}

	@Override
	public boolean maxHouses(int numHouses) {
		return numHouses < MAX_HOUSES;
	}

	@Override
	public boolean minHouses(int numHouses) {
		return numHouses > 0;
	}

}
