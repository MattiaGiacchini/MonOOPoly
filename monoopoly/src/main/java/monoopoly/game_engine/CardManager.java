package monoopoly.game_engine;

import java.util.Map;
import monoopoly.game_engine.CardEffect.*;
import monoopoly.model.item.Tile;

public class CardManager {
	
	private boolean isThisCardMaintainable;
	
	private String Description;
	
	private Integer cardNumber;
	
	private Tile.Category originDeck;
	
	public CardManager(final String description, final Integer cardNumber, final Tile.Category originDeck) {
		this.isThisCardMaintainable = false;
		this.Description = description;
		this.cardNumber = cardNumber;
		this.originDeck = originDeck;
	}
	

	public monoopoly.game_engine.CardEffect knowCard(final Card card) {
		if (!card.getValueToApplyOnPlayersBalance().isEmpty()) {
			return monoopoly.game_engine.CardEffect.MONEY_EXCHANGE;
		}		
		else if (card.mustThePlayerGoToJail) {
			return monoopoly.game_engine.CardEffect.JAIL_IN;
		}
		else if (card.canThePlayerExitFromJail) {
			this.setThisCardMaintainable(true);
			return monoopoly.game_engine.CardEffect.JAIL_OUT;
		}
		else if (!card.getRelativeMoveToPosition().isEmpty()) {
			return monoopoly.game_engine.CardEffect.RELATIVE_MOVE;
		}
		else if (!card.getAbsoluteMoveToPosition().isEmpty()) {
			return monoopoly.game_engine.CardEffect.ABSOLUTE_MOVE;
		}
		else if (!card.getNumberOfBuildingsToRemove().isEmpty()) {
			return monoopoly.game_engine.CardEffect.REMOVE_BUILDINGS;
		}
	}
	
	/*ci sarà un metodo nel GameEngine che, a seconda di cos'ha restituito applyCard, 
	 *va ad applicare ai giocatori */
	
	public boolean isThisCardMaintainable() {
		return isThisCardMaintainable;
	}

	public void setThisCardMaintainable(boolean isThisCardMaintainable) {
		this.isThisCardMaintainable = isThisCardMaintainable;
	}
}
