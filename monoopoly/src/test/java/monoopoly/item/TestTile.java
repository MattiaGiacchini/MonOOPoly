package monoopoly.item;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import monoopoly.model.item.Tile;
import monoopoly.model.item.TileImpl;

public class TestTile {
	Tile tile;

	@Test(expected = IllegalStateException.class)
	public void wrongSequenceBuild() {
		tile = new TileImpl.Builder()
						   .build();
	}
	
	@Test
	public void settingsCheck() {
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
		
		assertTrue(tile.getName().equals(name));
		assertTrue(tile.getCategory() == category);
		assertTrue(tile.isBuildable() == buildable);
		assertTrue(tile.isDeck() == deck);
		assertTrue(tile.isPurchasable() == purchasable);
		
	} 
	
	
}
