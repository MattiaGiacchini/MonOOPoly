package monoopoly.controller.player.manager;

import monoopoly.model.player.Player;

/**
 * This class represents a concrete BalanceManager who modifies {@link Player}'s
 * balance value.
 */
public class PlayerBalanceManagerImpl implements PlayerBalanceManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBalance(final Player player, final Double amount) {
        player.updateBalance(amount);
    }

}
