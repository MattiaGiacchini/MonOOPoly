package monoopoly.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import monoopoly.model.item.card.*;
import monoopoly.model.item.Tile;

public class TestCard {

	Card card;
	Integer numberCard ;
	Map<Integer,Double> playersBalance;
	Integer idDrower;
	Double bankValue;
	Map<Integer,Integer> playersPosition;
	Tile.Category originDeck;
	Double testDoubleValue;

	@Before
	public void CreationASimpleCard() {
		Random rnd = new Random();

		numberCard = 10;
		originDeck = Tile.Category.CALAMITY;
		String description = "vai in prigione senza passare dal via!";
		idDrower = 3;
		playersBalance = new HashMap<>();
		bankValue = 9000.0;
		playersPosition = new HashMap<>();

		Stream.iterate(1, x->x+1).limit(8).forEach(x->{
			playersBalance.put(x, Math.abs(rnd.nextDouble()*10000));
			playersPosition.put(x, x+1);
		});

		card = new CardImpl.Builder()
						   .cardNumber(numberCard)			// io
						   .description(description)		// io
						   .originDeck(originDeck)			// io
						   .idPlayerWhoHasDraw(idDrower)			// aiman
						   .actualPlayersBalance(playersBalance)	// aiman
						   .actualPlayersPosition(playersPosition)	// aiman
						   .actualBankBalance(bankValue)			// aiman
						   .build();
		
		assertEquals(card.getCardNumber(),					numberCard);
		assertEquals(card.getDescription(), 				description);
		assertEquals(card.getOriginDeck(), 					originDeck);
		assertEquals(card.getMoveToPosition(), 				Optional.empty());
		assertEquals(card.getValueToApplyOnBankBalance(), 	Optional.empty());
		assertFalse(card.mustThePlayerGoToJail());
		assertFalse(card.canThePlayerExitFromJail());
		assertEquals(card.getNumberOfBuildingsToRemove(),	Optional.empty());
	}

	@Test
	public void moneyEffect() {
		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .exchangeAllToBank(10.0)
							  .build();
		assertEquals(card.getValueToApplyOnBankBalance().get(), 80.0);
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), -10.0));
		}

		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .exchangeAllToBank(-10.0)
							  .build();
		assertTrue(card.getValueToApplyOnBankBalance().isEmpty());
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
							  .cardToDecore(card)
							  .ExchangePlayerToOthers(10.0)
							  .build();
		assertTrue(card.getValueToApplyOnBankBalance().isEmpty());
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			if(entry.getKey() == this.idDrower) {
				assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), -10.0*(this.playersBalance.size() - 1)));
			} else {
				assertTrue(this.doubleEqualsWithTollerance(entry.getValue(), 10.0)); 
			}
		}
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .ExchangePlayerToOthers(-10.0)
				  .build();
		assertTrue(card.getValueToApplyOnBankBalance().isEmpty());
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .ExchangePlayerToBank(1500.0)
				  .build();
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnBankBalance().get(), 1500.0));
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnPlayersBalance().get().get(this.idDrower), -1500.0));

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .ExchangePlayerToBank(-1500.0)
				  .build();
		assertTrue(card.getValueToApplyOnBankBalance().isEmpty());
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .PlayerNumberOfHouse(4)
				  .PlayerNumberOfHotel(4)
				  .ExchangeValueHouseToBank(10.0)
				  .ExchangeValueHotelToBank(10.0)
				  .build();
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnBankBalance().get(), 80.0));
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnPlayersBalance().get().get(this.idDrower), -80.0));

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .PlayerNumberOfHouse(4)
				  .PlayerNumberOfHotel(4)
				  .ExchangeValueHouseToBank(-10.0)
				  .ExchangeValueHotelToBank(-10.0)
				  .build();
		assertTrue(card.getValueToApplyOnBankBalance().isEmpty());
		assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .exchangeAllToBankPercentage(0.1)
				  .build();
		
		testDoubleValue = 0.0;
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnPlayersBalance().get().get(entry.getKey()),  
					   this.playersBalance.get(entry.getKey())*-0.1));
			testDoubleValue += this.playersBalance.get(entry.getKey())*0.1;
		}
		assertTrue(this.doubleEqualsWithTollerance(card.getValueToApplyOnBankBalance().get(), testDoubleValue ));
		
		card = new MoneyEffect.Builder()
				  .cardToDecore(card)
				  .makeTheAvaragePlayersBalance(true)
				  .build();
		testDoubleValue = card.getValueToApplyOnPlayersBalance().get().get(this.idDrower)
				    + this.playersBalance.get(this.idDrower);		
		for(Entry<Integer,Double> entry : card.getValueToApplyOnPlayersBalance().get().entrySet()) {
			assertTrue(this.doubleEqualsWithTollerance(entry.getValue() + this.playersBalance.get(entry.getKey()), testDoubleValue));
		}
		
	}

	private boolean doubleEqualsWithTollerance(Double a, Double b) {
		return Math.abs(a-b) < Math.pow(10, -7);
	}
}
