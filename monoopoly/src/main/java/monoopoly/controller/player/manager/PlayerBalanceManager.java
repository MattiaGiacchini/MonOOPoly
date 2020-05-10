package monoopoly.controller.player.manager;

import monoopoly.model.player.Player;

/**
 * This interface manages the {@link Player} balance and finances. All of these
 * methods will be invoked by {@link PlayerManager}.
 */
public interface PlayerBalanceManager {

	/**
	 * Add or remove a quantity of money from {@link Player}'s balance
	 * 
	 * @param player owner of the balance to be modified
	 * @param amount money to add (positive amount value) or to remove (negative
	 *               amount value) from balance
	 */
	void updateBalance(Player player, Double amount);

}
