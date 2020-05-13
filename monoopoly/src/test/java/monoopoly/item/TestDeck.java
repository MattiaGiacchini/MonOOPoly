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

/**
 * this test verify the deck where you can draw card.
 */
public class TestDeck {

    private static final int MAX_NUM_OF_BUILDINGS = 5;
    private static final int ID_OWNER_OF_PROPERTY = 5;
    private static final int PLAYERS_POSITION = 20;
    private static final int MAX_BALANCE = 1500;
    private static final int NUM_OF_PLAYERS = 6;
    private static final int DECK_SIZE = 16;
    private static final int CALAMITY_DECK_SIZE = 9;

    @SuppressWarnings("PMD.SingularField")
    private Table table;
    private TileDeck tileDeck;
    private Deck deck;
    private Set<Tile.Category> allTypeDeck;
    private Map<Integer, Double> playersBalance;
    private Map<Integer, Integer> playersPosition;
    private Integer idDrawer;
    private Map<Tile.Category, LinkedList<TileDeck>> deckPosition;
    private Card card;

    /**
     * this method generate the ambient for each test!
     * i added the suppressWarnig to avoid PMD launch error for
     */
    @SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
    @Before
    public void testDeckCreator() {
        final Random rnd = new Random();
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

        this.tileDeck = (TileDeck) table.getTile(2);
        this.deck = new DeckImpl(this.allTypeDeck);
        this.table.getFilteredTiles(TileDeck.class, x -> x.isDeck())
        .forEach(x -> {
            this.deckPosition.get(x.getCategory()).add(x);
        });
        this.table.getFilteredTiles(Property.class, x -> x.isBuildable())
        .forEach(x -> x.setOwner(Optional.of(ID_OWNER_OF_PROPERTY)));
        this.table.getFilteredTiles(Property.class, x -> x.isBuildable())
        .forEach(x -> {
            for (int i = rnd.nextInt(MAX_NUM_OF_BUILDINGS);
                    i < MAX_NUM_OF_BUILDINGS; i++) {
                x.buildOn();
            }
        });

        Stream.iterate(1, x -> x + 1).limit(NUM_OF_PLAYERS).forEach(x -> {
            this.playersBalance.put(x, ((Integer) rnd.nextInt(MAX_BALANCE))
                    .doubleValue());
            this.playersPosition.put(x, PLAYERS_POSITION);
        });


    }

    /**
     *  this test verify the presence of all card inside all decks.
     */
    @Test
    public void cardPresence() {
        final Map<Tile.Category, List<Integer>> cardsNumber = new HashMap<>();

        for (final var cat : this.allTypeDeck) {
            cardsNumber.put(cat, new ArrayList<>());
        }

        for (int i = 0; i < DECK_SIZE; i++) {
            deck.draw(Category.PROBABILITY);
            cardsNumber.get(Category.PROBABILITY).add(deck.getNumberCard());
        }
        for (int i = 0; i < DECK_SIZE; i++) {
            deck.draw(Category.UNEXPECTED);
            cardsNumber.get(Category.UNEXPECTED).add(deck.getNumberCard());
        }
        for (int i = 0; i < CALAMITY_DECK_SIZE; i++) {
            deck.draw(Category.CALAMITY);
            cardsNumber.get(Category.CALAMITY).add(deck.getNumberCard());
        }

        assertEquals(DECK_SIZE, cardsNumber.get(Category.PROBABILITY).size());
        assertEquals(DECK_SIZE, cardsNumber.get(Category.UNEXPECTED) .size());
        assertEquals(CALAMITY_DECK_SIZE,
                cardsNumber.get(Category.CALAMITY).size());
    }

    /**
     * this single test verify the function of single deck around the map.
     */
    @Test
    public void testUniqueDeck() {
        final Set<Integer> cardsNumber = new HashSet<>();

        this.allTypeDeck.forEach(type -> {
            for (int i = 0; i < (type.equals(Category.CALAMITY)
                    ? CALAMITY_DECK_SIZE : DECK_SIZE); i++) {
                this.tileDeck = this.deckPosition.get(type).removeFirst();

                this.card = this.tileDeck.actualPlayersBalance(playersBalance)
                        .actualPlayersPosition(playersPosition)
                        .idPlayerWhoHasDraw(idDrawer)
                        .draw();

                cardsNumber.add(card.getCardNumber());

                this.deckPosition.get(type).addLast(this.tileDeck);
            }

            if (type.equals(Category.CALAMITY)) {
                assertEquals(CALAMITY_DECK_SIZE, cardsNumber.size());
            } else {
                assertEquals(DECK_SIZE, cardsNumber.size());
            }

            cardsNumber.clear();
        });
    }
}
