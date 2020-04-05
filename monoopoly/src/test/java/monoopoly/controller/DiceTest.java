package monoopoly.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;
import monoopoly.utilities.PurchasableCategory;

public class DiceTest {

	private Dices dicesTwo;
	private Dices dicesThree;
	private PlayerManager playerTest = new PlayerManagerImpl(0);
	private Table tableTest = new Table() {
		
		private int diceSum;
		
		
		@Override
		public void setNewQuotationToSpecificPurchasableCategory(PurchasableCategory category, double quotation) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		@Override
		public Tile getTile(Integer position) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Purchasable> getPurchasablesTilesforSpecificPlayer(Integer idPlayer) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int currentDices() {
			// TODO Auto-generated method stub
			return this.diceSum;
		}

		@Override
		public void notifyDices(int sum) {
			// TODO Auto-generated method stub
			this.diceSum = sum;
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
		assertTrue(this.tableTest.currentDices() == sum);
	}
	
	@Test
	void testThreeDices() {
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesThree.setCurrentPlayer(this.playerTest);
		this.playerTest.goToPosition(0);
		this.dicesThree.roll(this.playerTest, this.tableTest);
		final Integer sum = this.dicesThree.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.currentDices() == sum);
	}
}
