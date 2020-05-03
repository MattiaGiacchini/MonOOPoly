package monoopoly.controllermanagers;

import java.util.Map;

import monoopoly.model.item.Tile;
import monoopoly.utilities.CardEffect;
import monoopoly.utilities.CardEffect.*;

public class CardManagerImpl implements CardManager {
	
	private boolean isThisCardMaintainable;
	
	private String Description;
	
	private Integer cardNumber;
	
	private Tile.Category originDeck;
	
	public CardManagerImpl(final String description, final Integer cardNumber, final Tile.Category originDeck) {
		this.isThisCardMaintainable = false;
		this.Description = description;
		this.cardNumber = cardNumber;
		this.originDeck = originDeck;
	}
	

	public monoopoly.utilities.CardEffect knowCard(final Card card) {
		if (!card.getValueToApplyOnPlayersBalance().isEmpty()) {
			return monoopoly.utilities.CardEffect.MONEY_EXCHANGE;
		}		
		else if (card.mustThePlayerGoToJail) {
			return monoopoly.utilities.CardEffect.JAIL_IN;
		}
		else if (card.canThePlayerExitFromJail) {
			this.setThisCardMaintainable(true);
			return monoopoly.utilities.CardEffect.JAIL_OUT;
		}
		else if (!card.getRelativeMoveToPosition().isEmpty()) {
			return monoopoly.utilities.CardEffect.RELATIVE_MOVE;
		}
		else if (!card.getAbsoluteMoveToPosition().isEmpty()) {
			return monoopoly.utilities.CardEffect.ABSOLUTE_MOVE;
		}
		else if (!card.getNumberOfBuildingsToRemove().isEmpty()) {
			return monoopoly.utilities.CardEffect.REMOVE_BUILDINGS;
		}
	}
	
	public boolean isThisCardMaintainable() {
		return isThisCardMaintainable;
	}

	public void setThisCardMaintainable(boolean isThisCardMaintainable) {
		this.isThisCardMaintainable = isThisCardMaintainable;
	}
}
