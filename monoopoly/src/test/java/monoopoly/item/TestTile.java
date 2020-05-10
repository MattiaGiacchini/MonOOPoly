package monoopoly.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.Test;

import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.item.TileImpl;

public class TestTile {
	Tile tile;

	@Test(expected = IllegalStateException.class)
	public void wrongSequenceBuild() {
		new TileImpl.Builder()
				 	.build();
	}
	
	@Test
	public void CreationAndGetterCheck() {
		String name = "test";
		Tile.Category category = Tile.Category.GREEN;
		boolean purchasable = true;
		boolean deck = false;
		boolean buildable = true;
		
		tile = new TileImpl.Builder()
						   .name(name)
						   .category(category)
						   .deck(deck)
						   .purchasable(purchasable)
						   .buildable(buildable)
						   .build();
		
		assertEquals(tile.getName(), name);
		assertEquals(tile.getCategory(),category);
		assertTrue(tile.isBuildable());
		assertFalse(tile.isDeck());
		assertTrue(tile.isPurchasable());
		
	} 

	@Test
	public void CreationTable() {
		Table table = new TableImpl();
		assertEquals(table.getTile(0).getCategory(), Tile.Category.START);
		assertEquals(table.getTile(5).getCategory(), Tile.Category.STATION);
		assertEquals(table.getTile(10).getCategory(),Tile.Category.JAIL);
		assertEquals(table.getTile(15).getCategory(),Tile.Category.STATION);
		assertEquals(table.getTile(20).getCategory(),
		                                            Tile.Category.FREE_PARKING);
		assertEquals(table.getTile(25).getCategory(),Tile.Category.STATION);
		assertEquals(table.getTile(30).getCategory(),Tile.Category.GO_TO_JAIL);
		assertEquals(table.getTile(35).getCategory(),Tile.Category.STATION);
	}

	@Test
	public void StationTest() {
		Table table = new TableImpl();
		Purchasable purchBase, purch;
		purchBase = (Purchasable)table.getTile(5);
		
		assertEquals(purchBase.getSalesValue(), 200.0);
		assertEquals(purchBase.getLeaseValue(),   0.0);
		
		purch = (Purchasable)table.getTile(5);
		purch.setOwner(Optional.of(1));
		assertEquals(purchBase.getLeaseValue(),  25.0);
		
		purch = (Purchasable)table.getTile(15);
		purch.setOwner(Optional.of(1));
		assertEquals(purchBase.getLeaseValue(),  50.0);
		
		purch = (Purchasable)table.getTile(25);
		purch.setOwner(Optional.of(1));
		assertEquals(purchBase.getLeaseValue(),   100.0);
		
		purch = (Purchasable)table.getTile(35);
		purch.setOwner(Optional.of(1));
		assertEquals(purchBase.getLeaseValue(),   200.0);
		
		purch = (Purchasable)table.getTile(25);
		purch.setOwner(Optional.empty());
		assertEquals(purchBase.getLeaseValue(),   100.0);
		
		table.setNewQuotationToSpecificPurchasableCategory
		                                           (Tile.Category.STATION, 2.0);
		assertEquals(purchBase.getLeaseValue(),   200.0);
		assertEquals(purchBase.getSalesValue(),   400.0);
	}

	@Test
	public void SocietyTest() {
		Table table = new TableImpl();
		Purchasable purch1 = (Purchasable)table.getTile(12);
		Purchasable purch2 = (Purchasable)table.getTile(28);

		assertEquals(purch1.getSalesValue(), 150.0);
		assertEquals(purch2.getSalesValue(), 150.0);
		table.setNewQuotationToSpecificPurchasableCategory
		                                           (Tile.Category.SOCIETY, 2.0);
		assertEquals(purch1.getSalesValue(), 300.0);
		assertEquals(purch2.getSalesValue(), 300.0);
		table.setNewQuotationToSpecificPurchasableCategory
		                                           (Tile.Category.SOCIETY, 1.0);
		assertEquals(purch1.getLeaseValue(), 0.0);
		assertEquals(purch2.getLeaseValue(), 0.0);		
		table.notifyDices(5);
		assertEquals(purch1.getLeaseValue(), 0.0);
		assertEquals(purch2.getLeaseValue(), 0.0);
		purch1.setOwner(Optional.of(1));
		assertEquals(purch1.getLeaseValue(), 20.0);
		purch2.setOwner(Optional.of(1));
		assertEquals(purch1.getLeaseValue(), 50.0);
		table.setNewQuotationToSpecificPurchasableCategory
		                                           (Tile.Category.SOCIETY, 2.0);
		assertEquals(purch1.getLeaseValue(), 100.0);
		purch1.setOwner(Optional.of(2));
		assertEquals(purch1.getLeaseValue(), 40.0);
	}

	@Test
	public void PropertyTest() {
		Table table = new TableImpl();
		Property prop1 = (Property)table.getTile(1);
		Property prop2 = (Property)table.getTile(3);

		assertEquals(prop1.getSalesValue(),  60.0);
		assertEquals(prop2.getSalesValue(),  60.0);
		assertEquals(prop1.getLeaseValue(),   0.0);
		assertEquals(prop2.getLeaseValue(),   0.0);
		prop1.setOwner(Optional.of(1));
		assertEquals(prop1.getLeaseValue(),   2.0);
		prop2.setOwner(Optional.of(1));
		assertEquals(prop1.getLeaseValue(),   4.0);
		prop1.buildOn();
		assertEquals(prop1.getLeaseValue(),  10.0);
		prop1.buildOn();
		assertEquals(prop1.getLeaseValue(),  30.0);
		prop1.buildOn();
		assertEquals(prop1.getLeaseValue(),  90.0);
		prop1.buildOn();
		assertEquals(prop1.getLeaseValue(), 160.0);
		prop1.buildOn();
		assertEquals(prop1.getLeaseValue(), 250.0);
		prop2.setOwner(Optional.of(1));
		assertEquals(prop2.getLeaseValue(),   8.0);
		assertEquals(prop1.getOwner().get(),  1);
		prop1.setQuotation(2.0);
		assertEquals(prop1.getLeaseValue(), 500.0);
		prop1.setOwner(Optional.empty());
		assertEquals(prop1.getLeaseValue(),   0.0);		
	}
	
	@Test(expected = IllegalStateException.class)
	public void propertyTestingBuild() {
		Table table = new TableImpl();
		Property prop1 = (Property)table.getTile(1);
		prop1.buildOn(); 
	}
	
	@Test()
	public void specialGetterTableTest() {
		Table table = new TableImpl();
		table.getFilteredTiles(Purchasable.class,x->x.isPurchasable());
	}
	
	@Test()
	public void positionTileTableTest() {
		Table table = new TableImpl();
		for(Integer i = 0; i < table.getTableSize(); i++) {
			assertEquals(table.getTilePosition(table.getTile(i)), i);
		}
	}
}
