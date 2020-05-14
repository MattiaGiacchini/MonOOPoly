package monoopoly.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import monoopoly.model.table.card.Card;
import monoopoly.model.table.card.CardImpl;
import monoopoly.model.table.card.MoneyEffect;
import monoopoly.model.table.card.MoveEffect;
import monoopoly.model.table.card.PropertyEffect;
import monoopoly.model.table.card.StatusEffect;
import monoopoly.model.table.tile.Tile;
import monoopoly.model.table.tile.Tile.Category;

/**
 *  The TestCard verify the function of Cards.
 */
public class TestCard {

    private static final int A_10 = 10;
    private static final int EXPONENT_TOLLERANCE = -7;
    private static final int BASE_TOLLERANCE = A_10;
    private static final int A_5 = 5;
    private static final double A_0_1 = 0.1;
    private static final double A_100_0 = 100.0;
    private static final double A_80_0 = 80.0;
    private static final double A_1500_0 = 1500.0;
    private static final double A_10_0 = 10.0;
    private static final int A_6 = 6;
    private static final int A_40 = 40;
    private static final int A_20 = 20;
    private static final int A_10000 = 10_000;

    private Card card;
    private Map<Integer, Double> playersBalance;
    private Integer idDrawer;
    private Map<Integer, Integer> playersPosition;
    private Map<Integer, Integer> buildingsOnProperty;

    /**
     * this function recreate for each test an ambient to used.
     */
    @Before
    public void creationASimpleCard() {
        final Random rnd = new Random();
        buildingsOnProperty = new HashMap<>();
        final Integer numberCard = A_10;
        final Tile.Category originDeck = Tile.Category.CALAMITY;
        final String description = "vai in prigione senza passare dal via!";
        idDrawer = 3;
        playersBalance = new HashMap<>();
        playersPosition = new HashMap<>();

        Stream.iterate(1, x -> x + 1).limit(8).forEach(x -> {
            playersBalance.put(x, Math.abs(rnd.nextDouble() * A_10000));
            playersPosition.put(x, rnd.nextInt(A_40));
        });

        Stream.iterate(1, x -> x + 1).limit(A_20).forEach(x -> {
            this.buildingsOnProperty.put(x, rnd.nextInt(A_6));
        });

        card = new CardImpl.Builder()
                .cardNumber(numberCard)
                .description(description)
                .originDeck(originDeck)
                .build();

        assertFalse(card.mustThePlayerGoToJail());
        assertFalse(card.isThisCardMaintainable());
        assertFalse(card.canThePlayerExitFromJail());
        assertEquals(card.getCardNumber(), numberCard);
        assertEquals(card.getDescription(), description);
        assertEquals(card.getOriginDeck(), originDeck);
        assertEquals(card.getAbsoluteMoveToPosition(), Optional.empty());
        assertEquals(card.getRelativeMoveToPosition(), Optional.empty());
        assertEquals(card.getNumberOfBuildingsToRemove(), Optional.empty());
    }

    /**
     * this test verify the Money Effect!
     */
    @Test
    public void moneyEffect() {
        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .exchangeAllToBank(A_10_0)
                .build();
        for (final var entry : card.getValueToApplyOnPlayersBalance()
                .get()
                .entrySet()) {
            assertTrue(this.doubleEqualsWithTollerance(entry.getValue(),
                    -A_10_0));
        }

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .exchangeAllToBank(-A_10_0)
                .build();
        assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .exchangePlayerToOthers(A_10_0)
                .build();
        for (final var entry : card.getValueToApplyOnPlayersBalance()
                .get().entrySet()) {
            if (entry.getKey().equals(this.idDrawer)) {
                assertTrue(this.doubleEqualsWithTollerance(entry.getValue(),
                        -A_10_0 * (this.playersBalance.size() - 1)));
            } else {
                assertTrue(this.doubleEqualsWithTollerance(entry.getValue(),
                        A_10_0));
            }
        }

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .exchangePlayerToOthers(-A_10_0)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .build();
        assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .exchangePlayerToBank(A_1500_0)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .build();
        assertTrue(this.doubleEqualsWithTollerance(
                card.getValueToApplyOnPlayersBalance().get().get(this.idDrawer),
                -A_1500_0));

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .exchangePlayerToBank(-A_1500_0)
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
                .exchangeValueHouseToBank(A_10_0)
                .exchangeValueHotelToBank(A_10_0)
                .build();
        assertTrue(this.doubleEqualsWithTollerance(
                card.getValueToApplyOnPlayersBalance().get().get(this.idDrawer),
                -A_80_0));

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .playerNumberOfHouse(4)
                .playerNumberOfHotel(4)
                .exchangeValueHouseToBank(-A_10_0)
                .exchangeValueHotelToBank(-A_10_0)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .build();
        assertTrue(card.getValueToApplyOnPlayersBalance().isEmpty());

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .exchangeAllToBankPercentage(A_10_0 / A_100_0)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .build();
        for (final var entry : card.getValueToApplyOnPlayersBalance()
                .get().entrySet()) {
            assertTrue(this.doubleEqualsWithTollerance(
                    card.getValueToApplyOnPlayersBalance().get()
                    .get(entry.getKey()),
                    this.playersBalance.get(entry.getKey()) * -A_0_1));
        }

        card = new MoneyEffect.Builder()
                .cardToDecore(card)
                .makeTheAvaragePlayersBalance(true)
                .idDrawer(idDrawer)
                .actualPlayersBalance(playersBalance)
                .build();
        final Double testDoubleValue = card.getValueToApplyOnPlayersBalance()
                .get()
                .get(this.idDrawer) + this.playersBalance.get(this.idDrawer);
        for (final var entry : card.getValueToApplyOnPlayersBalance().get()
                .entrySet()) {
            assertTrue(this.doubleEqualsWithTollerance(
                    entry.getValue() + this.playersBalance.get(entry.getKey()),
                    testDoubleValue));
        }
    }

    /**
     * this test verify the Status Effect!
     */
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

    /**
     * this test verify the Property Effect!
     */
    @Test
    public void propertyEffect() {
        for (int i = 0; i < A_10; i++) {
            card = new PropertyEffect.Builder()
                    .buildingsToModify(this.buildingsOnProperty)
                    .cardToDecore(card)
                    .build();

            for (final var entry : this.buildingsOnProperty.entrySet()) {
                assertTrue(entry.getValue()
                        >= card.getNumberOfBuildingsToRemove().get()
                        .get(entry.getKey()));
            }
        }
    }


    /**
     * this test verify the Move Effect!
     */
    @Test
    public void moveEffect() {

        final BiFunction<Integer, Tile.Category, Integer> biFunction
        = new BiFunction<>() {

            @Override
            public Integer apply(final Integer t, final Category u) {
                return t + A_10;
            }

        };


        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToPlayer(true)
                .stepToDo(A_5)
                .build();
        assertEquals(card.getRelativeMoveToPosition().get().get(idDrawer), A_5);
        assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToOthers(true)
                .stepToDo(A_5)
                .build();
        card.getRelativeMoveToPosition().get().values().forEach(x -> {
            assertEquals(A_5, x);
        });
        assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToOthers(true)
                .applyToPlayer(true)
                .stepToDo(-A_5)
                .build();
        assertTrue(card.getRelativeMoveToPosition().isEmpty());
        assertTrue(card.getAbsoluteMoveToPosition().isEmpty());

        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToPlayer(true)
                .tileRetriverFromCategory(biFunction)
                .nextTileCategoryToReach(Category.CALAMITY)
                .build();
        assertEquals(card.getRelativeMoveToPosition().get().get(idDrawer),
                this.playersPosition.get(this.idDrawer) + A_10);

        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
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
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToPlayer(true)
                .tilePositionToGo(A_10)
                .build();
        assertEquals(A_10, this.playersPosition.get(this.idDrawer) == A_10
                ? A_10 : card.getAbsoluteMoveToPosition()
                        .get().get(this.idDrawer));
        assertTrue(card.getRelativeMoveToPosition().isEmpty());

        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToOthers(true)
                .tilePositionToGo(A_10)
                .build();
        assertTrue(card.getAbsoluteMoveToPosition()
                .get()
                .entrySet()
                .stream()
                .allMatch(x -> x.getValue().equals(A_10)));
        assertTrue(card.getRelativeMoveToPosition().isEmpty());


        card = new MoveEffect.Builder()
                .cardToDecore(card)
                .tableSize(A_40)
                .playersPosition(playersPosition)
                .idDrawers(idDrawer)
                .applyToPlayer(true)
                .applyToOthers(true)
                .tilePositionToGo(this.playersPosition.get(idDrawer))
                .build();
        assertFalse(card.getAbsoluteMoveToPosition().get()
                .containsKey(idDrawer));
        assertTrue(card.getRelativeMoveToPosition().isEmpty());
    }

    private boolean doubleEqualsWithTollerance(final Double a, final Double b) {
        return Math.abs(a - b) < Math.pow(BASE_TOLLERANCE, EXPONENT_TOLLERANCE);
    }
}
