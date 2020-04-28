package monoopoly.model.item.card;

import java.util.Collections;
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
	public Optional<Double> getValueToApplyOnBankBalance() {
		return this.decoratedCard.getValueToApplyOnBankBalance();
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

	protected Integer getIdPlayerWhoHasDrow() {
		if(this.decoratedCard instanceof CardImpl) {
			return ((CardImpl)this.decoratedCard).IdPlayerWhoHasDraw;
		} else if(AbstractCardDecorator.class.isAssignableFrom(this.decoratedCard.getClass())) {
			return ((AbstractCardDecorator)this.decoratedCard).getIdPlayerWhoHasDrow();
		}   
		throw new IllegalStateException();
	}
	
	protected Map<Integer, Double> getActualPlayersBalance() {
		if(this.decoratedCard instanceof CardImpl) {
			return Collections.unmodifiableMap(((CardImpl)this.decoratedCard).actualPlayersBalance);
		} else if(AbstractCardDecorator.class.isAssignableFrom(this.decoratedCard.getClass())) {
			return Collections.unmodifiableMap(((AbstractCardDecorator)this.decoratedCard).getActualPlayersBalance());
		}   
		throw new IllegalStateException();
	}

	protected Double getActualBankBalance() {
		if(this.decoratedCard instanceof CardImpl) {
			return ((CardImpl)this.decoratedCard).actualBankBalance;
		} else if(AbstractCardDecorator.class.isAssignableFrom(this.decoratedCard.getClass())) {
			return ((AbstractCardDecorator)this.decoratedCard).getActualBankBalance();
		} 
		throw new IllegalStateException();
	}

	protected Map<Integer, Integer> getActualPlayersPosition() {
		if(this.decoratedCard instanceof CardImpl) {
			return Collections.unmodifiableMap(((CardImpl)this.decoratedCard).actualPlayersPosition);
		} else if(AbstractCardDecorator.class.isAssignableFrom(this.decoratedCard.getClass())) {
			return Collections.unmodifiableMap(((AbstractCardDecorator)this.decoratedCard).getActualPlayersPosition());
		}   
		throw new IllegalStateException();
	}	

}
