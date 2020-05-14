package monoopoly.model.table.deck;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import monoopoly.model.table.tile.Tile.Category;

/**
 * this is the {@link Deck} implementation.
 */
public class DeckImpl implements Deck {

    private final Map<Category, LinkedList<CardProperties>> deckMap;
    private final Set<Category> allTypeOfDeck;

    private CardProperties cardDrawn;

    private enum CardProperties {

        // PROBABILITY DECK
        CARD00P(Category.PROBABILITY,   "Pagate la retta ospedaliera, versate 100€",                                                                        null,   100.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD01P(Category.PROBABILITY,    "Pagate le tasse scolastiche dei vostri figli. Versate 50€ .",                                                     null,    50.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD02P(Category.PROBABILITY,    "Pagate per contributi di miglioria stradale 40€ per ogni casa e 115€ per ogni albergo che possedete",             null,    null,    40.0,   115.0,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD03P(Category.PROBABILITY,    "Ricevete 25€ per la vostra consulenza",                                                                           null,   -25.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD04P(Category.PROBABILITY,    "Vi viene rimborsata la tassa sui redditi. Incassate 20€",                                                         null,   -20.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD05P(Category.PROBABILITY,    "E' il vostro compleanno. Ogni giocatore vi regala 10€",                                                          -10.0,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD06P(Category.PROBABILITY,    "Ricevete la parcella del dottore. Pagate 50€",                                                                    null,    50.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD07P(Category.PROBABILITY,    "USCITE GRATIS DI PRIGIONE, SE CI SIETE. La carta e' conservabile fino al momento della necessita",                null,    null,    null,    null,    null,    null,    false,    false,    true,      true,    null,    null,    null,                false,    false,    false,    false),
        CARD08P(Category.PROBABILITY,    "Avete vinto il secondo premio in un concorso di bellezza! Incassate 10€",                                         null,   -10.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD09P(Category.PROBABILITY,    "Maturano gli interessi della vostra assicurazione sulla vita. Incassate 100€",                                    null,  -100.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD10P(Category.PROBABILITY,    "Andate avanti fino al VIA!. Ritirate 200€",                                                                       null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    0,       null,                false,    true,     false,    false),
        CARD11P(Category.PROBABILITY,    "Dalla vendita di uno stock di merci ricavate 50€",                                                                null,   -50.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD12P(Category.PROBABILITY,    "Maturano le cedole delle vostre azioni. Ricevete 100€",                                                           null,  -100.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD13P(Category.PROBABILITY,    "La banca riconosce un errore nel vostro estratto conto. Incassate 200€",                                          null,  -200.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD14P(Category.PROBABILITY,    "Ereditate da un lontano parente 100€",                                                                            null,  -100.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD15P(Category.PROBABILITY,    "Andate in prigione direttamente e senza passare dal VIA! Non ritirate 200€",                                      null,    null,    null,    null,    null,    null,    false,    true,     false,    false,    null,    null,    null,                false,    false,    false,    false),

        // UNEXPECTED DECK
        CARD00U(Category.UNEXPECTED,    "USCITE GRATIS DI PRIGIONE, SE CI SIETE. La carta e' conservabile fino al momento della necessita",                 null,    null,    null,    null,    null,    null,    false,    false,    true,     true,     null,    null,    null,                false,    false,    false,    false),
        CARD01U(Category.UNEXPECTED,    "Andate avanti fino alla stazione piu' vicina. Se e' libera potete acquistarla altrimenti pagate l'affitto",        null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    Category.STATION,    false,    true,     false,    false),
        CARD02U(Category.UNEXPECTED,    "Andate avanti fino al VIA! Ritirate 200€",                                                                         null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    0,       null,                false,    true,     false,    false),
        CARD03U(Category.UNEXPECTED,    "Fate 3 passi indietro. CON TANTI AUGURI!",                                                                         null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    -3,      null,    null,                false,    true,     false,    false),
        CARD04U(Category.UNEXPECTED,    "La banca vi paga un dividendo di 50€",                                                                             null,   -50.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD05U(Category.UNEXPECTED,    "Andate avanti fino alla stazione piu' vicina. Se e' libera potete acquistarla altrimenti pagate l'affitto",        null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    Category.STATION,    false,    true,     false,    false),
        CARD06U(Category.UNEXPECTED,    "Multa per eccesso di velocita'. Pagate 15€",                                                                       null,    15.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD07U(Category.UNEXPECTED,    "Andate avanti fino a Largo Colombo",                                                                               null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    24,      null,                false,    true,     false,    false),
        CARD08U(Category.UNEXPECTED,    "Fate un viaggio fino alla Stazione Sud",                                                                           null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    5,       null,                false,    true,     false,    false),
        CARD09U(Category.UNEXPECTED,    "Andate avanti fino alla Societa' piu' vicina. Se e' libera potete acquistarla altrimenti pagate l'affitto",        null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    Category.SOCIETY,    false,    true,     false,    false),
        CARD10U(Category.UNEXPECTED,    "Maturano le cedole dei vostri fondi immobiliari, incassate 150€",                                                  null,  -150.0,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD11U(Category.UNEXPECTED,    "Andate in prigione direttamente e senza passare dal VIA! Non ritirate 200€",                                       null,    null,    null,    null,    null,    null,    false,    true,     false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD12U(Category.UNEXPECTED,    "Fate un viaggio fino al Parco della Vittoria",                                                                     null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    39,      null,                false,    true,     false,    false),
        CARD13U(Category.UNEXPECTED,    "Siete stati promossi alla presidenza del consiglio di amministrazione. Pagate 50€ ad ogni giocatore",              50.0,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD14U(Category.UNEXPECTED,    "Andate avanti fino a Via Accademia.",                                                                              null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    11,      null,                false,    true,     false,    false),
        CARD15U(Category.UNEXPECTED,    "Eseguite lavori di manutenzione su tutti i vostri edifici. Pagate 25€ per ogni casa e 100€ per ogni hotel",        null,    null,    25.0,   100.0,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),

        // CALAMITY DECK                                                                                                                                    p2o      p2b       vOc      vOh      a2b     a2bp    avara   gotoj   exitj   maint   step    posT    catToReach          rndSt   mDraw   mOthe   propD
        CARD00(Category.CALAMITY,    "TASSA PATRIMONIALE\n"
                + " Il governo ha passato la patrimoniale. Tutti devono dare il 15% del loro conto corrente alla banca",                                    null,    null,    null,    null,    null,    0.15,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD01(Category.CALAMITY,    "AIUTI DAL GOVERNO\n"
                + "Il governo ha emanato una serie di aiuti economici per le vostre imprese. Ricevete tutti 500 euro dalla banca.",                         null,    null,    null,    null,    -500.0, null,    false,     false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD02(Category.CALAMITY,    "THANOS SNAP\n"
                + "Il cugino agente immobiliare di Thanos ha schioccato le dita e ha fatto scomparire numerose case nelle proprietà",                       null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    true),
        CARD03(Category.CALAMITY,    "ROBIN HOOD\n"
                + "Un ladro gentiluomo prende ai ricchi per dare ai poveri. I conti correnti dei giocatori vengono pareggiati.",                            null,    null,    null,    null,    null,    null,    true,     false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD04(Category.CALAMITY,    "URAGANO\n"
                + "Un uragano sta attraversando il monoopoly! tutti i giocatori vengono scaraventati su un'altra casella",                                  null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                true,      true,    true,     false),
        CARD05(Category.CALAMITY,    "THE WOLF OF WALL STREET\n"
                + "Hai seguito un corso di Jordan Belfort sulla speculazione selvaggia e sei diventato esageratamente bravo. "
                + "Ricevi 600 euro da tutti.",                                                                                                            -600.0,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD06(Category.CALAMITY,    "SCOMMESSA ANDATA MALE\nHai perso una scommessa. Devi dare 200 euro a tutti gli altri.",                              200.0,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false),
        CARD07(Category.CALAMITY,    "FESTA\n"
                + "Decidete di tutti di fare una festa, ma all'ultimo i tuoi compagni ti lasciano a casa!"
                + "tutti trante te vanno al parco della vittoria!",                                                                                         null,    null,    null,    null,    null,    null,    false,    false,    false,    false,    null,    39,      null,                false,    false,    true,     false),
        CARD08(Category.CALAMITY,    "ROTTURA DELLA QUARTA PARETE\nAvete consegnato il progetto di OOP, si festeggia, ma ognuno deve dare 180 euro.",       null,    null,    null,    null,   180.0,    null,    false,    false,    false,    false,    null,    null,    null,                false,    false,    false,    false);

        private final Category    originDeck;
        private final String     description;
        private final Double     moneyPlayerToOthers;
        private final Double     moneyPlayerToBank;
        private final Double     moneyValueOfHouse;
        private final Double     moneyValueOfHotel;
        private final Double     moneyAllToBank;
        private final Double     moneyAllToBankPercentage;
        private final boolean     moneyAllMakeAvarage;
        private final boolean    statusGoToJail;
        private final boolean     statusExitFromJail;
        private final boolean     statusItIsMaintainable;
        private final Integer    movementStepsToDo;
        private final Integer    movementTilePositionToGo;
        private final Category    movementCategoryToReach;
        private final boolean    movementRandomSteps;
        private final boolean     movementMoveTheDrawer;
        private final boolean     movementMoveTheOthers;
        private final boolean    propertyDestroy;

        CardProperties(final Category originDeck,
                final String description,
                final Double moneyPlayerToOthers,
                final Double moneyPlayerToBank,
                final Double moneyValueOfHouse,
                final Double moneyValueOfHotel,
                final Double moneyAllToBank,
                final Double moneyAllToBankPercentage,
                final boolean moneyAllMakeAvarage,
                final boolean statusGoToJail,
                final boolean statusExitFromJail,
                final boolean statusItIsMaintainable,
                final Integer movementStepsToDo,
                final Integer movementTilePositionToGo,
                final Category movementCategoryToReach,
                final boolean movementRandomSteps,
                final boolean movementMoveTheDrawer,
                final boolean movementMoveTheOthers,
                final boolean propertyDestroy) {
            this.originDeck = originDeck;
            this.description = description;
            this.moneyPlayerToOthers = moneyPlayerToOthers;
            this.moneyPlayerToBank = moneyPlayerToBank;
            this.moneyValueOfHouse = moneyValueOfHouse;
            this.moneyValueOfHotel = moneyValueOfHotel;
            this.moneyAllToBank = moneyAllToBank;
            this.moneyAllToBankPercentage = moneyAllToBankPercentage;
            this.moneyAllMakeAvarage = moneyAllMakeAvarage;
            this.statusGoToJail = statusGoToJail;
            this.statusExitFromJail = statusExitFromJail;
            this.statusItIsMaintainable = statusItIsMaintainable;
            this.movementStepsToDo = movementStepsToDo;
            this.movementTilePositionToGo = movementTilePositionToGo;
            this.movementCategoryToReach = movementCategoryToReach;
            this.movementRandomSteps = movementRandomSteps;
            this.movementMoveTheDrawer = movementMoveTheDrawer;
            this.movementMoveTheOthers = movementMoveTheOthers;
            this.propertyDestroy = propertyDestroy;
        }
    }

    /**
     * this is {@link DeckImpl}'s constructor.
     *
     * @param decks is a set of {@link Category}
     *        that represents the type of deck
     *        will be prepared for the game
     */
    public DeckImpl(final Set<Category> decks) {
        super();
        deckInputCheck(decks);

        this.allTypeOfDeck = decks;
        this.deckMap = new HashMap<>();
        this.cardDrawn = null;

        // creation of list (deck) inside the map
        this.allTypeOfDeck.forEach(x -> {
            this.deckMap.put(x, new LinkedList<>());
        });

        // add all card inside the list
        for (final CardProperties x : CardProperties.values()) {
            if (this.deckMap.containsKey(x.originDeck)) {
                this.deckMap.get(x.originDeck).addLast(x);
            }
        }

        this.deckMap.entrySet().forEach(entry -> {
            if (entry.getValue().isEmpty()) {
                throw new IllegalStateException("there aren't cards for the "
                        + "deck " + entry.getKey());
            }
        });

        // shuffling of all decks
        this.deckMap.values().forEach(x -> Collections.shuffle(x));

    }

    @Override
    public final void draw(final Category category) {
        categoryInputCheck(category);
        this.cardDrawn = this.deckMap.get(category).removeFirst();
        this.deckMap.get(category).addLast(this.cardDrawn);
    }

    @Override
    public final Integer getNumberCard() {
        return this.cardDrawn.ordinal();
    }

    @Override
    public final String getDescription() {
        return this.cardDrawn.description;
    }

    @Override
    public final boolean hasMoneyEffect() {
        return this.cardDrawn.moneyAllMakeAvarage
                || !Objects.isNull(this.cardDrawn.moneyAllToBank)
                || !Objects.isNull(this.cardDrawn.moneyAllToBankPercentage)
                || !Objects.isNull(this.cardDrawn.moneyPlayerToBank)
                || !Objects.isNull(this.cardDrawn.moneyPlayerToOthers)
                || thereIsntValutationOfBuildings();
    }

    @Override
    public final boolean hasStatusEffect() {
        return  this.cardDrawn.statusGoToJail
                || this.cardDrawn.statusExitFromJail;
    }

    @Override
    public final boolean hasMovementEffect() {
        return this.cardDrawn.movementMoveTheDrawer
                || this.cardDrawn.movementMoveTheOthers;
    }

    @Override
    public final boolean hasPropertyEffect() {
        return this.cardDrawn.propertyDestroy;
    }

    @Override
    public final Double getPlayersToOthers() {
        return this.cardDrawn.moneyPlayerToOthers;
    }

    @Override
    public final Double getPlayerToBank() {
        return this.cardDrawn.moneyPlayerToBank;
    }

    @Override
    public final Double getValueHouseToBank() {
        return this.cardDrawn.moneyValueOfHouse;
    }

    @Override
    public final Double getValueHotelToBank() {
        return this.cardDrawn.moneyValueOfHotel;
    }

    @Override
    public final Double getAllToBank() {
        return this.cardDrawn.moneyAllToBank;
    }

    @Override
    public final Double getAllToBankPercentage() {
        return this.cardDrawn.moneyAllToBankPercentage;
    }

    @Override
    public final boolean isMakeTheAveragePlayersBalance() {
        return this.cardDrawn.moneyAllMakeAvarage;
    }

    @Override
    public final boolean goToJail() {
        return this.cardDrawn.statusGoToJail;
    }

    @Override
    public final boolean exitFromJail() {
        return this.cardDrawn.statusExitFromJail;
    }

    @Override
    public final boolean isMaintainable() {
        return this.cardDrawn.statusItIsMaintainable;
    }

    @Override
    public final Integer getStepsToDo() {
        return this.cardDrawn.movementStepsToDo;
    }

    @Override
    public final Integer getTilePositionToGo() {
        return this.cardDrawn.movementTilePositionToGo;
    }

    @Override
    public final Category getTileCategoryToReach() {
        return this.cardDrawn.movementCategoryToReach;
    }

    @Override
    public final boolean isGenerateRandomSteps() {
        return this.cardDrawn.movementRandomSteps;
    }

    @Override
    public final boolean isMoveToApplyToPlayer() {
        return this.cardDrawn.movementMoveTheDrawer;
    }

    @Override
    public final boolean isMoveToApplyToOthers() {
        return this.cardDrawn.movementMoveTheOthers;
    }

    private void deckInputCheck(final Set<Category> decks) {
        Objects.requireNonNull(decks, "the deck cannot has null value");
        if (decks.isEmpty()) {
            throw new IllegalArgumentException("Category of decks required!");
        }
    }

    private void categoryInputCheck(final Category category) {
        Objects.requireNonNull(category, "the category of the deck to be drawn"
                + " cannot have a null value");
        if (!this.allTypeOfDeck.contains(category)) {
            throw new IllegalArgumentException("The category isn't a Deck");
        }
    }

    private boolean thereIsntValutationOfBuildings() {
        return !Objects.isNull(this.cardDrawn.moneyValueOfHotel)
                && !Objects.isNull(this.cardDrawn.moneyValueOfHouse);
    }
}
