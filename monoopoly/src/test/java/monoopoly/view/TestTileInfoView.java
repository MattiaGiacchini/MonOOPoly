package monoopoly.view;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import monoopoly.view.controller.TileInfo;
import monoopoly.view.controller.TileInfoController;
import monoopoly.view.controller.TileInfoControllerImpl;
import monoopoly.view.utilities.ButtonLogic;
import monoopoly.view.utilities.ButtonLogicImpl;
import monoopoly.view.utilities.PurchasableState;

public class TestTileInfoView {

	// TileInfo.Builder().currentPlayerBalance(5200.00).housePrice(20.00).mortgage(false).numHouses(4).purchasableValue(250.00).rentValue(47000.00).tileName("Parco
	// della Vittoria").purchasableState(PurchasableState.FREE_PROPERTY);
	TileInfoController controller = new TileInfoControllerImpl();
	private ButtonLogic logic = new ButtonLogicImpl();

	@Test
	public void testButtonLogics() {

		boolean button = true;
		button = (logic.enoughMoney(500.00, 1000.00));
		assertFalse(button);
		button = (logic.enoughMoney(500.00, 50.00));
		assertTrue(button);

		button = logic.maxHouses(4);
		assertTrue(button);

		button = logic.maxHouses(5);
		assertFalse(button);

		button = logic.minHouses(2);
		assertTrue(button);

		button = logic.minHouses(0);
		assertFalse(button);
		controller.showPropertyControlPane(new TileInfo.Builder().currentPlayerBalance(5200.00).housePrice(20.00)
				.mortgage(false).housesAmount(4).purchasableValue(250.00).rentToPay(47000.00)
				.tileName("Parco della Vittoria").purchasableState(PurchasableState.FREE_PROPERTY).build());
	}

}
