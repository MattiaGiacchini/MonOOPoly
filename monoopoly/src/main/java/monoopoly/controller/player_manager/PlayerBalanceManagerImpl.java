package monoopoly.controller.player_manager;

import monoopoly.model.player.Player;

/**
 * This class represents a concrete BalanceManager who modifies {@link Player}'s
 * balance value
 */
public class PlayerBalanceManagerImpl implements PlayerBalanceManager {

	@Override
	public void updateBalance(Player player, Double amount) {
		player.updateBalance(amount);
	}

}
