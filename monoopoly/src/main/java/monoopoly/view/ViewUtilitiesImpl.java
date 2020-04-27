package monoopoly.view;

public class ViewUtilitiesImpl implements ViewUtilities {

	@Override
	public int getBoardPosition(int x, int y, int totCells) {

		if (x == 0 || y == totCells / 2) {
			return totCells  - (x + y);
		} else {
			return totCells + (x + y);
		}
	}
}