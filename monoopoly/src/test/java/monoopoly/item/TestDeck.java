package monoopoly.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import monoopoly.model.item.Property;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.TileDeck;
import monoopoly.model.item.card.Card;
import monoopoly.model.item.deck.Deck;
import monoopoly.model.item.deck.DeckImpl;

public class TestDeck {

	Table table;
	TileDeck tileDeck;
	Deck deck;
	Set<Tile.Category> allTypeDeck;
	Map<Integer,Double> playersBalance;
	Map<Integer,Integer> playersPosition;
	Integer idDrawer;
	Map<Tile.Category,LinkedList<TileDeck>> deckPosition;
	Card card;

	@Before
	public void TestDeckCreator() {
		Random rnd = new Random();
		this.table = new TableImpl();
		this.playersBalance = new HashMap<>();
		this.playersPosition = new HashMap<>();
		this.deckPosition = new HashMap<>();
		this.idDrawer = 3;

		this.allTypeDeck = Set.of(Category.CALAMITY,
								  Category.UNEXPECTED,
								  Category.PROBABILITY);

		this.deckPosition.put(Category.CALAMITY, new LinkedList<>());
		this.deckPosition.put(Category.UNEXPECTED, new LinkedList<>());
		this.deckPosition.put(Category.PROBABILITY, new LinkedList<>());

		this.tileDeck = (TileDeck)table.getTile(2);
		this.deck = new DeckImpl(this.allTypeDeck);
		this.table.getFilteredTiles(TileDeck.class, x->x.isDeck()).forEach(x->{
			this.deckPosition.get(x.getCategory()).add(x);
		});
		this.table.getFilteredTiles(Property.class, x->x.isBuildable())
        .forEach(x->x.setOwner(Optional.of(5)));
		this.table.getFilteredTiles(Property.class, x->x.isBuildable())
		          .forEach(x->{
		        	  for(int i = rnd.nextInt(5); i < 5; i++) {
		        		  x.buildOn();
		        	  }
		          });

		Stream.iterate(1, x->x+1).limit(6).forEach(x->{
				this.playersBalance.put(x, ((Integer)rnd.nextInt(1500))
				                                        .doubleValue());
				this.playersPosition.put(x, 20);
			});


	}

	@Test
	public void cardPresence() {
		Map<Tile.Category,List<Integer>> cardsNumber = new HashMap<>();

		for(Tile.Category cat : this.allTypeDeck) {
			cardsNumber.put(cat, new ArrayList<>());
		}

		for(int i = 0; i < 16; i++) {
			deck.draw(Category.PROBABILITY);
			cardsNumber.get(Category.PROBABILITY).add(deck.getNumberCard());
		}
		for(int i = 0; i < 16; i++) {
			deck.draw(Category.UNEXPECTED);
			cardsNumber.get(Category.UNEXPECTED).add(deck.getNumberCard());
		}
		for(int i = 0; i < 9; i++) {
			deck.draw(Category.CALAMITY);
			cardsNumber.get(Category.CALAMITY).add(deck.getNumberCard());
		}

		assertEquals(16, cardsNumber.get(Category.PROBABILITY).size());
		assertEquals(16, cardsNumber.get(Category.UNEXPECTED) .size());
		assertEquals( 9, cardsNumber.get(Category.CALAMITY)   .size());
	}

	@Test
	public void testUniqueDeck() {
		Set<Integer> cardsNumber = new HashSet<>();

		this.allTypeDeck.forEach(type->{
			for(int i = 0; i < (type.equals(Category.CALAMITY) ? 9 : 16); i++) {
				this.tileDeck = this.deckPosition.get(type).removeFirst();

				this.card =	this.tileDeck.actualPlayersBalance(playersBalance)
										 .actualPlayersPosition(playersPosition)
										 .idPlayerWhoHasDraw(idDrawer)
										 .draw();

				cardsNumber.add(card.getCardNumber());

				this.deckPosition.get(type).addLast(this.tileDeck);
			}

			if(type == Category.CALAMITY) {
				assertEquals( 9, cardsNumber.size());
			} else {
				assertEquals( 16, cardsNumber.size());
			}

			cardsNumber.clear();
		});
	}
}
