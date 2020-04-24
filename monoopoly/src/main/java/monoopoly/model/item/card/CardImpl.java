package monoopoly.model.item.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.item.Tile.Category;

public class CardImpl implements Card {
	
	private final Integer cardNumber;
	private final String description;
	private final Category originDeck;
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
		private Optional<Map<Integer, Double>> valueToApplyOnPlayersBalance;
		private Optional<Double> valueToApplyOnBankBalance;
		private boolean goToJail;
		private boolean exitFromJail;
		private boolean maintainable;
		private Optional<Map<Integer, Integer>> moveToPosition;
		private Optional<Map<Integer, Integer>> buildingsToRemove;
		
		public Builder() {
			this.valueToApplyOnBankBalance = Optional.empty();
			this.valueToApplyOnPlayersBalance = Optional.empty();
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
		
		public CardImpl build() {
			if(Objects.isNull(this.cardNumber) ||
			   Objects.isNull(this.description) ||
			   Objects.isNull(this.originDeck)) {
				throw new IllegalStateException("missing construction elements");
			}
			return new CardImpl(this);
		}
	}

	private CardImpl(Builder builder) {
		super();
		this.cardNumber	 				  = builder.cardNumber;
		description 					  = builder.description;
		this.originDeck 				  = builder.originDeck;
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
