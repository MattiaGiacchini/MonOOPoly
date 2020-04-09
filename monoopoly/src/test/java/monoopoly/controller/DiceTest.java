package monoopoly.controller;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import monoopoly.controller.dices.Dices;
import monoopoly.controller.dices.DicesImpl;
import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.controller.player_manager.PlayerManagerImpl;
import monoopoly.game_engine.GameEngine;
import monoopoly.game_engine.GameEngineImpl;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile.Category;
import monoopoly.utilities.States;
import monoopoly.model.item.Table;
import monoopoly.model.item.Tile;

public class DiceTest {

	private Dices dicesTwo;
	private Dices dicesThree;
	private GameEngine testEngine;
	private PlayerManager playerTest;
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
			return 20;
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

		@Override
		public double getValueToRetrieveFromStart() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Integer getJailPosition() {
			// TODO Auto-generated method stub
			return 1;
		}
	};
	
	@Test
	void testDiceInit() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, testEngine);
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesTwo.setCurrentPlayer(playerTest);
		this.dicesThree.setCurrentPlayer(playerTest);
		assertTrue(this.dicesTwo.getDices().isEmpty());
		assertTrue(this.dicesThree.getDices().isEmpty());
	}
	
	@Test
	void testDualDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, this.testEngine);
		this.dicesTwo = new DicesImpl(2, this.tableTest);
		this.dicesTwo.setCurrentPlayer(playerTest);
		this.dicesTwo.roll(this.playerTest, this.tableTest);
		final Integer sum = this.dicesTwo.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}
	
	@Test
	void testThreeDices() {
		initEngine();
		this.playerTest = new PlayerManagerImpl(0, this.testEngine);
		this.dicesThree = new DicesImpl(3, this.tableTest);
		this.dicesThree.setCurrentPlayer(this.playerTest);
		//this.playerTest.goToPosition(0);
		this.dicesThree.roll(this.playerTest, this.tableTest);
		final Integer sum = this.dicesThree.getDices().values().stream().reduce(0, Integer::sum);
		assertTrue(this.playerTest.getPlayer().getPosition() == sum);
		assertTrue(this.tableTest.getNotifiedDices() == sum);
	}

	private void initEngine() {
		Map<Integer, String> names = new HashMap<Integer, String>();
		Map<Integer, Double> balance = new HashMap<Integer, Double>();
		Map<Integer, Integer> positions = new HashMap<Integer, Integer>();
		Map<Integer, States> states = new HashMap<Integer, States>();
		names.put(0, "test");
		balance.put (0, 0.0);
		positions.put(0, 0);
		states.put(0, States.IN_GAME);
		this.testEngine = new GameEngineImpl(names, balance, positions, states);
	}
}
