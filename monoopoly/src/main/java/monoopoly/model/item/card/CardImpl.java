package monoopoly.model.item.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.item.Tile.Category;

public final class CardImpl implements Card {
	
	// values necessary to create any Card
	private final Integer cardNumber;
	private final String description;
	private final Category originDeck;
	
	public static final class Builder{
		
		private Integer cardNumber;
		private String description;
		private Category originDeck;
		
		public Builder() {
			super();
			this.cardNumber = null;
			this.description = null;
			this.originDeck = null;
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
		this.description				  = builder.description;
		this.originDeck 				  = builder.originDeck;
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
		return Optional.empty();
	}

	@Override
	public boolean mustThePlayerGoToJail() {
		return false;
	}

	@Override
	public boolean canThePlayerExitFromJail() {
		return false;
	}

	@Override
	public boolean isThisCardMaintainable() {
		return false;
	}

	@Override
	public Optional<Map<Integer, Integer>> getMoveToPosition() {
		return  Optional.empty();
	}

	@Override
	public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
		return  Optional.empty();
	}
	
}
