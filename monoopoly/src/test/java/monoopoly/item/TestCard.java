package monoopoly.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import monoopoly.model.item.card.*;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;

public class TestCard {

	Card card;
	Integer numberCard ;
	Map<Integer,Double> playersBalance;
	Integer idDrawer;
	Double bankValue;
	Map<Integer,Integer> playersPosition;
	Map<Integer,Integer> buildingsOnProperty;
	Tile.Category originDeck;
	Double testDoubleValue;

	@Before
	public void CreationASimpleCard() {
		Random rnd = new Random();
		buildingsOnProperty = new HashMap<>();
		numberCard = 10;
		originDeck = Tile.Category.CALAMITY;
		String description = "vai in prigione senza passare dal via!";
		idDrawer = 3;
		playersBalance = new HashMap<>();
		bankValue = 9000.0;
		playersPosition = new HashMap<>();

		Stream.iterate(1, x->x+1).limit(8).forEach(x->{
			playersBalance.put(x, Math.abs(rnd.nextDouble()*10000));
			playersPosition.put(x, rnd.nextInt(40));
		});

		Stream.iterate(1, x->x+1).limit(20).forEach(x->{
			this.buildingsOnProperty.put(x, rnd.nextInt(6));
		});
		
		card = new CardImpl.Builder()
						   .cardNumber(numberCard)
						   .description(description)
						   .originDeck(originDeck)
						   .build();

		assertFalse(card.mustThePlayerGoToJail());
		assertFalse(card.isThisCardMaintainable());
		assertFalse(card.canThePlayerExitFromJail());
		assertEquals(card.getCardNumber(),					numberCard);
		assertEquals(card.getDescription(), 				description);
		assertEquals(card.getOriginDeck(), 					originDeck);
		assertEquals(card.getAbsoluteMoveToPosition(), 		Optional.empty());
		assertEquals(card.getRelativeMoveToPosition(), 		Optional.empty());
		assertEquals(card.getNumberOfBuildingsToRemove(),	Optional.empty());
	}

	@Test
	public void moneyEffect() {
		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .idDrawer(idDrawer)
							  .actualPlayersBalance(playersBalance)
							  .exchangeAllToBank(10.0)
							  .build();
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), -10.0));
		}

		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .idDrawer(idDrawer)
							  .actualPlayersBalance(playersBalance)
							  .exchangeAllToBank(-10.0)
							  .build();
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .idDrawer(idDrawer)
							  .actualPlayersBalance(playersBalance)
							  .exchangePlayerToOthers(10.0)
							  .build();
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			if(entry.getKey() == this.idDrawer) {
				assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), -10.0*(this.playersBalance.size() - 1)));
			} else {
				assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), 10.0)); 
			}
		}
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .exchangePlayerToOthers(-10.0)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .exchangePlayerToBank(1500.0)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnPlayersBalance().get().get(this.idDrawer), -1500.0));

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .exchangePlayerToBank(-1500.0)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .playerNumberOfHouse(4)
				  .playerNumberOfHotel(4)
				  .exchangeValueHouseToBank(10.0)
				  .exchangeValueHotelToBank(10.0)
				  .build();
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnPlayersBalance().get().get(this.idDrawer), -80.0));

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .playerNumberOfHouse(4)
				  .playerNumberOfHotel(4)
				  .exchangeValueHouseToBank(-10.0)
				  .exchangeValueHotelToBank(-10.0)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .exchangeAllToBankPercentage(10.0/100.0)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(
					   card.getValueToApplyOnPlayersBalance().get().get(entry.getKey()),  
					   this.playersBalance.get(entry.getKey())*-0.1));
		}
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .makeTheAvaragePlayersBalance(true)
				  .idDrawer(idDrawer)
				  .actualPlayersBalance(playersBalance)
				  .build();
		testDoubleValue = card.getValueToApplyOnPlayersBalance().get().get(this.idDrawer)
				    + this.playersBalance.get(this.idDrawer);		
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(entry.getValue() + this.playersBalance.get(entry.getKey()), testDoubleValue));
		}
	}

	@Test
	public void statusEffect() {
		card = new StatusEffect.Builder()
							   .cardToDecore(card)
							   .maintainable(false)
							   .goToJail(true)
							   .exitFromJail(false)
							   .build();
		assertTrue(card.mustThePlayerGoToJail());
		assertFalse(card.canThePlayerExitFromJail());
		assertFalse(card.isThisCardMaintainable());

		card = new StatusEffect.Builder()
							   .cardToDecore(card)
							   .maintainable(true)
							   .goToJail(false)
							   .exitFromJail(true)
							   .build();
		assertFalse(card.mustThePlayerGoToJail());
		assertTrue(card.canThePlayerExitFromJail());
		assertTrue(card.isThisCardMaintainable());
	}

	@Test
	public void propertyEffect() {
		for(int i = 0; i < 10; i ++) {
			card = new PropertyEffect.Builder()
									 .buildingsToModify(this.buildingsOnProperty)
									 .cardToDecore(card)
									 .build();
			
			for(Entry<Integer,Integer> entry : this.buildingsOnProperty.entrySet()) {
				assertTrue(entry.getValue() >= card.getNumberOfBuildingsToRemove().get().get(entry.getKey()));
			}
		}
	}	
	

	@Test
	public void moveEffect() {
		
		BiFunction<Integer,Tile.Category, Integer> biFunction = new BiFunction<Integer,Tile.Category, Integer>() {

			@Override
			public Integer apply(Integer t, Category u) {
				return t+10;
			}
			
		};
		
		
		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToPlayer(true)
							 .stepToDo(5)
							 .build();
		assertEquals(card.getRelativeMoveToPosition().get().get(idDrawer), 5);
		assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToOthers(true)
							 .stepToDo(5)
							 .build();
		card.getRelativeMoveToPosition().get().values().forEach(x->{
			assertEquals(5, x);
		});
		assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToOthers(true)
							 .applyToPlayer(true)
							 .stepToDo(-5)
							 .build();
		assertTrue(card.getRelativeMoveToPosition().isEmpty());
		assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToPlayer(true)
							 .tileRetriverFromCategory(biFunction)
							 .nextTileCategoryToReach(Category.CALAMITY)
							 .build();
		assertEquals(card.getRelativeMoveToPosition().get().get(idDrawer), 
				  this.playersPosition.get(this.idDrawer) + 10);

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .generateRandomStep(true)
							 .applyToOthers(true)
							 .applyToPlayer(true)
							 .build();
		assertTrue(card.getRelativeMoveToPosition().isPresent());
		assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToPlayer(true)
							 .tilePositionToGo(10)
							 .build();		
		assertEquals(10, card.getAbsoluteMoveToPosition().get().get(this.idDrawer));
		assertTrue(card.getAbsoluteMoveToPosition().get().keySet().stream().allMatch(x->x==this.idDrawer));
		assertTrue(card.getRelativeMoveToPosition().isEmpty());

		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToOthers(true)
							 .tilePositionToGo(10)
							 .build();		
		assertTrue(card.getAbsoluteMoveToPosition().get().entrySet().stream().allMatch(x->x.getValue() == 10));
		assertTrue(card.getAbsoluteMoveToPosition().get().size() == this.playersPosition.size());
		assertTrue(card.getRelativeMoveToPosition().isEmpty());


		card = new MoveEffect.Builder()
							 .cardToDecore(card)
							 .tableSize(40)
							 .playersPosition(playersPosition)
							 .idDrawers(idDrawer)
							 .applyToPlayer(true)
							 .applyToOthers(true)
							 .tilePositionToGo(this.playersPosition.get(idDrawer))
							 .build();		
		assertFalse(card.getAbsoluteMoveToPosition().get().containsKey(idDrawer));
		assertTrue(card.getRelativeMoveToPosition().isEmpty());
	}
	
	private boolean doubleEqualsWithTollerance(Double a, Double b) {
		return Math.abs(a-b) < Math.pow(10, -7);
	}
}
