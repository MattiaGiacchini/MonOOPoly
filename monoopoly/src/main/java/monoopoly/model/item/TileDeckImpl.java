package monoopoly.model.item;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import monoopoly.model.item.card.*;
import monoopoly.model.item.deck.Deck;

public class TileDeckImpl extends AbstractTileDecorator implements TileDeck {

	private final Deck deck;
	private final Table table;
	private Integer idDrawer;
	private Map<Integer, Double> playersBalance;
	private Map<Integer, Integer> playersPosition;
	
	public static class Builder{
		private Tile tileToDecore;
		private Deck deck;
		private Table table;
		
		public Builder() {
			this.deck = null;
			this.tileToDecore = null;
			this.table = null;
		}
		
		public Builder tileToDecore(Tile tile) {
			Objects.requireNonNull(tile, "the tile to decore cannot has null value");
			if(!tile.isDeck()) {
				throw new IllegalArgumentException("the tile hasn't a deck Category");
			}
			this.tileToDecore = tile;
			return this;
		}
		
		public Builder deck(Deck deck) {
			Objects.requireNonNull(deck, "the deck cannot has null value");
			this.deck = deck;
			return this;
		}
		
		public Builder table(Table table) {
			Objects.requireNonNull(table, "the table cannot has null value");
			this.table = table;
			return this;
		}
		
		public TileDeckImpl build() {
			Objects.requireNonNull(this.tileToDecore, "there is no tile to decore setted!");
			Objects.requireNonNull(this.deck, "there is no deck setted!");
			Objects.requireNonNull(this.table, "there is table setted!");
			return new TileDeckImpl(this);
		}
		
	}

	private TileDeckImpl(Builder builder) {
		super(builder.tileToDecore);
		this.deck = builder.deck;
		this.table = builder.table;
	}

	@Override
	public TileDeck idPlayerWhoHasDraw(Integer idDrawer) {
		Objects.requireNonNull(idDrawer, "the IdDrawer cannot has null value");
		this.idDrawer = idDrawer;
		return this;
	}

	@Override
	public TileDeck actualPlayersBalance(Map<Integer, Double> playersBalance) {
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
	public TileDeck actualPlayersPosition(Map<Integer, Integer> playersPosition) {
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
		this.deck.draw(super.getCategory());
		Card card = this.generateBaseCard();

		if (this.deck.hasMoneyEffect()) {
			card = this.decoreWithMoneyEffect(card);
		}

		if (this.deck.hasStatusEffect()) {
			card = this.decoreWithStatusEffect(card);
		}

		if (this.deck.hasPropertyEffect()) {
			card = this.decoreWithPropertyEffect(card);
		}

		if (this.deck.hasMovementEffect()) {
			card = this.decoreWithMovementEffect(card);
		}

		return card;
	}

	private Card generateBaseCard() {
		return new CardImpl.Builder()
						   .originDeck(super.getCategory())
						   .cardNumber(this.deck.getNumberCard())
						   .description(this.deck.getDescription())
						   .build();
	}

	private Card decoreWithMoneyEffect(final Card card) {
		return new MoneyEffect.Builder()
							  .actualPlayersBalance(this.playersBalance)
							  .cardToDecore(card)
							  .idDrawer(this.idDrawer)
							  .exchangePlayerToOthers(this.deck.getPlayersToOthers())
							  .exchangePlayerToBank(this.deck.getPlayerToBank())
							  .playerNumberOfHotel(this.actualNumberPlayerHotel())
							  .playerNumberOfHouse(this.actualNumberPlayerHouse())
							  .exchangeValueHouseToBank(this.deck.getValueHouseToBank())
							  .exchangeValueHotelToBank(this.deck.getValueHotelToBank())
							  .exchangeAllToBank(this.deck.getAllToBank())
							  .exchangeAllToBankPercentage(this.deck.getAllToBankPercentage())
							  .makeTheAvaragePlayersBalance(this.deck.getMakeTheAveragePlayersBalance())
							  .build();
	}

	private Card decoreWithStatusEffect(Card card) {
		return new StatusEffect.Builder()
							   .cardToDecore(card)
							   .goToJail(this.deck.goToJail())
							   .exitFromJail(this.deck.exitFromJail())
							   .maintainable(this.deck.isMaintainable())
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
							 .stepToDo(this.deck.getStepsToDo())
							 .tilePositionToGo(this.deck.getTilePositionToGo())
							 .nextTileCategoryToReach(this.deck.getTileCategoryToReach())
							 .tileRetriverFromCategory(Objects.isNull(this.deck.getTileCategoryToReach())
									 				   ? null : this.getCategoryTileRetriver())
							 .generateRandomStep(this.deck.isGenerateRandomSteps())
							 .applyToPlayer(this.deck.isMoveToApplyToPlayer())
							 .applyToOthers(this.deck.isMoveToApplyToOthers())
							 .build();
	}

	private BiFunction<Integer,Category,Integer> getCategoryTileRetriver() {
		return (x,y)->{
			Set<Integer> possiblePosition = 
			this.table.getFilteredTiles(Tile.class, el->el.getCategory() == y)
					  .stream()
					  .map(el->this.table.getTilePosition(el))
					  .collect(Collectors.toSet());
			
			if(possiblePosition.stream().anyMatch(el->el > x)) {
				return possiblePosition.stream()
									   .filter(el -> el > x)
									   .min(Comparator.comparing(Integer::valueOf))
									   .get() - x;	
			}
			return possiblePosition.stream().min(Comparator.comparing(Integer::valueOf)).get()
				   + this.table.getTableSize() - x;									
		};
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
