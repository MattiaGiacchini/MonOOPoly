package monoopoly.game_engine;

import java.util.Map;
import monoopoly.game_engine.CardEffect.*;

public class CardManager {
	
	public monoopoly.game_engine.CardEffect knowCard(final Card card) {
		if (!card.getValueToApplyOnPlayersWallet().isEmpty()) {
			/*Map<Integer, Double> map = card.getValueToApplyOnPlayersWallet().get();
			for (Map.Entry<Integer, Double> entry: map.entrySet()) {
				/*vado nella mappa che rappresenta il balance e per ogni 
				 * key incremento il value
			}*/
			return monoopoly.game_engine.CardEffect.TO_ALL;
		}
		else if (!card.getValueToApplyOnBank().isEmpty()) {
			return monoopoly.game_engine.CardEffect.BANK_EXCHANGE;
		}
	}
	
	/*ci sarà un metodo nel GameEngine che, a seconda di cos'ha restituito applyCard, 
	 *va ad applicare ai giocatori */
}
