package monoopoly.controller.bank;

import monoopoly.controller.player_manager.PlayerManager;
import monoopoly.model.Bank;
import monoopoly.model.item.Property;
import monoopoly.model.item.Tile;

public abstract class BankCommandAbstractImpl implements BankCommand{

	private final Bank bank;
	private final PlayerManager player;
	private final Tile tile; 
	
	public BankCommandAbstractImpl(final Bank bank, final PlayerManager player, final Tile tile) {
		this.bank = bank;
		this.player = player;
		this.tile = tile;
	}
	
	@Override
	abstract public void execute();
}
