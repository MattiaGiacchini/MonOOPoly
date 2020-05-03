package monoopoly.model.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import monoopoly.model.item.card.*;
import monoopoly.model.item.decks.TypeDeck;

public class DeckImpl extends AbstractTileDecorator implements Deck {

	private final TypeDeck typeDeck;
	private final Table table;
	private Integer idDrawer;
	private Map<Integer, Double> playersBalance;
	private Map<Integer, Integer> playersPosition;

	public DeckImpl(Tile decorated, TypeDeck typeDeck, Table table) {
		super(decorated);
		this.typeDeck = typeDeck;
		this.table = table;
	}

	@Override
	public Deck idPlayerWhoHasDraw(Integer idDrawer) {
		Objects.requireNonNull(idDrawer, "the IdDrawer cannot has null value");
		this.idDrawer = idDrawer;
		return this;
	}

	@Override
	public Deck actualPlayersBalance(Map<Integer, Double> playersBalance) {
		Objects.requireNonNull(playersBalance, "The Players Balance cannot has null value");
		playersBalance.entrySet().forEach(entry -> {
			if (entry.getValue().isNaN() || entry.getValue().isInfinite()) {
				throw new IllegalArgumentException("the values of playersBalance aren't correct");
			}
		});
		this.playersBalance = Collections.unmodifiableMap(playersBalance);
		return this;
	}

	@Override
	public Deck actualPlayersPosition(Map<Integer, Integer> playersPosition) {
		Objects.requireNonNull(playersPosition, "The Players Position cannot has null value");
		this.playersPosition = Collections.unmodifiableMap(playersPosition);
		return this;
	}

	@Override
	public Card draw() {
		Objects.requireNonNull(this.idDrawer, "the identifier of the player who is to draw has not been set");
		Objects.requireNonNull(this.playersBalance, "players balance unsetted before draw");
		Objects.requireNonNull(this.playersPosition, "players position unsetted before draw");
		if (!this.playersBalance.containsKey(this.idDrawer)) {
			throw new IllegalArgumentException(
					"the Player's identifier who is to draw isn't inside the playersBalance");
		}
		if (!this.playersPosition.containsKey(this.idDrawer)) {
			throw new IllegalArgumentException(
					"the Player's identifier who is to draw isn't inside the playersPosition");
		}
		
		Card card = this.generateNewCard();
		
		this.idDrawer = null;
		this.playersBalance = null;
		this.playersPosition = null;
		
		return card;

	}

	private Card generateNewCard() {
		this.typeDeck.draw();
		Card card = this.generateBaseCard();

		if (this.typeDeck.hasMoneyEffect()) {
			card = this.decoreWithMoneyEffect(card);
		}

		if (this.typeDeck.hasStatusEffect()) {
			card = this.decoreWithStatusEffect(card);
		}

		if (this.typeDeck.hasMovementEffect()) {
			card = this.decoreWithMovementEffect(card);
		}

		if (this.typeDeck.hasPropertyEffect()) {
			card = this.decoreWithPropertyEffect(card);
		}

		return card;
	}

	private Card generateBaseCard() {
		return new CardImpl.Builder()
						   .originDeck(super.getCategory())
						   .cardNumber(this.typeDeck.getNumberCard())
						   .description(this.typeDeck.getDescription())
						   .build();
	}

	private Card decoreWithMoneyEffect(final Card card) {
		return new MoneyEffect.Builder()
							  .actualPlayersBalance(this.playersBalance)
							  .cardToDecore(card)
							  .idDrawer(this.idDrawer)
							  .exchangePlayerToOthers(this.typeDeck.getPlayersToOthers())
							  .exchangePlayerToBank(this.typeDeck.getPlayerToBank())
							  .playerNumberOfHotel(this.actualNumberPlayerHotel())
							  .playerNumberOfHouse(this.actualNumberPlayerHouse())
							  .exchangeValueHouseToBank(this.typeDeck.getValueHouseToBank())
							  .exchangeValueHotelToBank(this.typeDeck.getValueHotelToBank())
							  .exchangeAllToBank(this.typeDeck.getAllToBank())
							  .exchangeAllToBankPercentage(this.typeDeck.getAllToBankPercentage())
							  .makeTheAvaragePlayersBalance(this.typeDeck.getMakeTheAvaragePlayersBalance())
							  .build();
	}

	private Card decoreWithStatusEffect(Card card) {
		return new StatusEffect.Builder()
							   .cardToDecore(card)
							   .goToJail(this.typeDeck.goToJail())
							   .exitFromJail(this.typeDeck.exitFromJail())
							   .maintainable(this.typeDeck.isMaintainable())
							   .build();
	}

	private Card decoreWithPropertyEffect(Card card) {
		return new PropertyEffect.Builder()
								 .cardToDecore(card)
								 .buildingsToModify(this.getMapOfProperty())
								 .build();
	}

	private Card decoreWithMovementEffect(Card card) {
		return new MoveEffect.Builder()
							 .cardToDecore(card)
							 .idDrawers(this.idDrawer)
							 .playersPosition(this.playersPosition)
							 .tableSize(this.table.getTableSize())
							 .stepToDo(this.typeDeck.getStepsToDo())
							 .tilePositionToGo(this.typeDeck.getTilePositionToGo())
							 .nextTileCategoryToReach(this.typeDeck.getTileCategoryToReach())
							 .tileRetriverFromCategory(this.typeDeck.getTileRetriver(this.getMapOfCategory()))
							 .generateRandomStep(this.typeDeck.isGenerateRandomSteps())
							 .applyToPlayer(this.typeDeck.isMoveToApplyToPlayer())
							 .applyToOthers(this.typeDeck.isMoveToApplyToOthers())
							 .build();
	}

	private Map<Integer,Category> getMapOfCategory() {
		return Collections.unmodifiableMap(
			   this.table.getFilteredTiles(Tile.class, x->true)
			   			 .stream()
						 .collect(
								 ()->new HashMap<>(),
						  		 (map,el)->map.put(table.getTilePosition(el),
						  				           el.getCategory()),
								 (map1,map2)->map1.putAll(map2)
								));
	}

	private Map<Integer, Integer> getMapOfProperty() {
		return Collections.unmodifiableMap(
			   this.table.getFilteredTiles(Property.class, x->x.isBuildable())
						 .stream()
						 .collect(()-> new HashMap<Integer,Integer>(),
								  (map,el)->map.put(table.getTilePosition(el),
										  			el.getNumberOfHotelBuilt() + 
										  			el.getNumberOfHouseBuilt()),
								  (map1,map2)->map1.putAll(map2)));
	}

	private Integer actualNumberPlayerHouse() {
		return getPropertyOfDrawer().stream()
			    				    .mapToInt(x->x.getNumberOfHouseBuilt())
			    				    .sum();
	}

	private Integer actualNumberPlayerHotel() {
		return getPropertyOfDrawer().stream()
								    .mapToInt(x->x.getNumberOfHotelBuilt())
								    .sum();
	}

	private Set<Property> getPropertyOfDrawer() {
		return table.getFilteredTiles(Property.class,
									  x->x.isBuildable() &&
									  ((Purchasable)x).getOwner().isPresent() && 
									  ((Purchasable)x).getOwner().get() == this.idDrawer);
	}

}
