package monoopoly.model.item.card;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import monoopoly.model.item.Tile.Category;

public abstract class AbstractCardDecorator implements Card {
	
	private final Card decoratedCard;
	
	public AbstractCardDecorator(Card decoratedCard) {
		super();
		Objects.requireNonNull(decoratedCard,"the cart to decore has null value");
		this.decoratedCard = decoratedCard;
	}

	@Override
	public Integer getCardNumber() {
		return this.decoratedCard.getCardNumber();
	}

	@Override
	public String getDescription() {
		return this.decoratedCard.getDescription();
	}

	@Override
	public Category getOriginDeck() {
		return this.decoratedCard.getOriginDeck();
	}

	@Override
	public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
		return this.decoratedCard.getValueToApplyOnPlayersBalance();
	}

	@Override
	public boolean mustThePlayerGoToJail() {
		return this.decoratedCard.mustThePlayerGoToJail();
	}

	@Override
	public boolean canThePlayerExitFromJail() {
		return this.decoratedCard.mustThePlayerGoToJail();
	}

	@Override
	public boolean isThisCardMaintainable() {
		return this.decoratedCard.isThisCardMaintainable();
	}

	@Override
	public Optional<Map<Integer, Integer>> getMoveToPosition() {
		return this.decoratedCard.getMoveToPosition();
	}

	@Override
	public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
		return this.decoratedCard.getNumberOfBuildingsToRemove();
	}

}
