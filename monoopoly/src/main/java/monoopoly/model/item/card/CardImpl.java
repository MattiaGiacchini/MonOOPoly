package monoopoly.model.item.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.item.Tile.Category;

public final class CardImpl implements Card {

	// values necessary to create any Card decoration 
	protected final Integer IdPlayerWhoHasDraw;
	protected final Map<Integer, Double> actualPlayersBalance;
	protected final Double actualBankBalance;
	protected final Map<Integer,Integer> actualPlayersPosition;
	
	// values necessary to create any Card
	private final Integer cardNumber;
	private final String description;
	private final Category originDeck;
	
	// output values generated on the various decorator
	private final Optional<Map<Integer, Double>> valueToApplyOnPlayersBalance;
	private final Optional<Double> valueToApplyOnBankBalance;
	private final boolean goToJail;
	private final boolean exitFromJail;
	private final boolean maintainable;
	private final Optional<Map<Integer, Integer>> moveToPosition;
	private final Optional<Map<Integer, Integer>> buildingsToRemove;
	
	public static final class Builder{
		
		private Integer cardNumber;
		private String description;
		private Category originDeck;
		private Integer IdPlayerWhoHasDraw;
		private Map<Integer, Double> actualPlayersBalance;
		private Double actualBankBalance;
		private Map<Integer,Integer> actualPlayersPosition;
		
		private Optional<Map<Integer, Double>> valueToApplyOnPlayersBalance;
		private Optional<Double> valueToApplyOnBankBalance;
		private boolean goToJail;
		private boolean exitFromJail;
		private boolean maintainable;
		private Optional<Map<Integer, Integer>> moveToPosition;
		private Optional<Map<Integer, Integer>> buildingsToRemove;
		
		public Builder() {
			super();
			this.cardNumber = null;
			this.description = null;
			this.originDeck = null;
			this.IdPlayerWhoHasDraw = null;
			this.actualPlayersBalance = null;
			this.actualBankBalance = null;
			this.actualPlayersPosition = null;
			this.valueToApplyOnPlayersBalance = Optional.empty();
			this.valueToApplyOnBankBalance = Optional.empty();
			this.goToJail = false;
			this.exitFromJail = false;
			this.maintainable = false;
			this.moveToPosition = Optional.empty();
			this.buildingsToRemove = Optional.empty();
		}

		public Builder cardNumber(Integer number) {
			Objects.requireNonNull(number, "the Card number has null value");
			if(number < 0) {
				throw new IllegalArgumentException("number Card must be a positive value");
			}
			this.cardNumber = number;
			return this;
		}
		
		public Builder originDeck(Category category) {
			Objects.requireNonNull(category,"the Card origin-deck has null value");
			this.originDeck = category;
			return this;
		}
		
		public Builder description(String description) {
			Objects.requireNonNull(description, "The card description has NULL Value");
			this.description = description;
			return this;
		}
		
		public Builder idPlayerWhoHasDraw(Integer value) {
			Objects.requireNonNull(value, "the id player cannot a null value");
			this.IdPlayerWhoHasDraw = value;
			return this;
		}

		public Builder actualPlayersBalance(Map<Integer, Double> map){
			Objects.requireNonNull(map, "the map of actual Players Balance cannot a null value");
			map.entrySet().forEach(x->this.doubleChecker(x.getValue()));
			this.actualPlayersBalance = map;
			return this;
		}
		
		public Builder actualBankBalance(Double value) {
			Objects.requireNonNull(value, "the bank value cannot a null value");
			this.doubleChecker(value);
			this.actualBankBalance = value;
			return this;
		}
		
		public Builder actualPlayersPosition(Map<Integer,Integer> map) {
			Objects.requireNonNull(map, "the map of actual player position cannot a null value");
			this.actualPlayersPosition = map;
			return this;
		}
		
		public CardImpl build() {
			if(Objects.isNull(this.cardNumber) ||
			   Objects.isNull(this.description) ||
			   Objects.isNull(this.originDeck) ||
			   Objects.isNull(this.IdPlayerWhoHasDraw) ||
			   Objects.isNull(this.actualBankBalance) ||
			   Objects.isNull(this.actualPlayersBalance) ||
			   Objects.isNull(this.actualPlayersPosition)) {
				throw new IllegalStateException("missing construction elements");
			}
			return new CardImpl(this);
		}
		
		private void doubleChecker(Double value) {
			if(Objects.nonNull(value) && 
			   value.isInfinite() || 
			   value.isNaN()) {
				throw new IllegalArgumentException("The double value hasn't a correnct format");
			}
		}
	}

	private CardImpl(Builder builder) {
		super();
		this.IdPlayerWhoHasDraw 		  = builder.IdPlayerWhoHasDraw;
		this.cardNumber	 				  = builder.cardNumber;
		this.description				  = builder.description;
		this.originDeck 				  = builder.originDeck;
		this.actualPlayersPosition 		  = builder.actualPlayersPosition;
		this.actualPlayersBalance 		  = builder.actualPlayersBalance;
		this.actualBankBalance 			  = builder.actualBankBalance;
		this.valueToApplyOnPlayersBalance = builder.valueToApplyOnPlayersBalance;
		this.valueToApplyOnBankBalance 	  = builder.valueToApplyOnBankBalance;
		this.goToJail 					  = builder.goToJail;
		this.exitFromJail 				  = builder.exitFromJail;
		this.maintainable 				  = builder.maintainable;
		this.moveToPosition 			  = builder.moveToPosition;
		this.buildingsToRemove 			  = builder.buildingsToRemove;
	}

	@Override
	public Integer getCardNumber() {
		return this.cardNumber;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Category getOriginDeck() {
		return this.originDeck;
	}

	@Override
	public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
		return this.valueToApplyOnPlayersBalance;
	}

	@Override
	public Optional<Double> getValueToApplyOnBankBalance() {
		return this.valueToApplyOnBankBalance;
	}

	@Override
	public boolean mustThePlayerGoToJail() {
		return this.goToJail;
	}

	@Override
	public boolean canThePlayerExitFromJail() {
		return this.exitFromJail;
	}

	@Override
	public boolean isThisCardMaintainable() {
		return this.maintainable;
	}

	@Override
	public Optional<Map<Integer, Integer>> getMoveToPosition() {
		return this.moveToPosition;
	}

	@Override
	public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
		return this.buildingsToRemove;
	}
	
}
