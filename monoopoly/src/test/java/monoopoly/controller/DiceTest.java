package monoopoly.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Purchasable.Category;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;

public class DiceTest {

	private Dices dicesTwo;
	private Dices dicesThree;
	private PlayerManager playerTest = new PlayerManagerImpl(0);
	private Table tableTest = new Table() {
		
		int diceSum = 0;
		
		@Override
		public void setNewQuotationToSpecificPurchasableCategory(Category category, double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void notifyDices(int sum) {
			// TODO Auto-generated method stub
			this.diceSum = sum;
		}
		
		@Override
		public Tile getTile(Integer position) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Integer getTableSize() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Set<Purchasable> getPurchasableTilesforSpecificPlayer(Integer idPlayer) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getNotifiedDices() {
			// TODO Auto-generated method stub
			return this.diceSum;
		}
	};
	
	@Test
	void testDiceInit() {
		this.playerTest = new PlayerManagerImpl(0);
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesTwo.setCurrentPlayer(playerTest);
		this.dicesThree.setCurrentPlayer(playerTest);
		assertTrue(this.dicesTwo.getDices().isEmpty());
		assertTrue(this.dicesThree.getDices().isEmpty());
	}
	
	@Test
	void testDualDices() {
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesTwo.setCurrentPlayer(playerTest);
		this.dicesTwo.roll(this.playerTest, this.tableTest);
		final Integer sum = this.dicesTwo.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}
	
	@Test
	void testThreeDices() {
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesThree.setCurrentPlayer(this.playerTest);
		this.playerTest.goToPosition(0);
		this.dicesThree.roll(this.playerTest, this.tableTest);
		final Integer sum = this.dicesThree.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}
}
