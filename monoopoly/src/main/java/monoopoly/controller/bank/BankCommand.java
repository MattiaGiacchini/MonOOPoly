package monoopoly.controller.bank;

import monoopoly.model.Bank;

/**
 *	This interface represents a generic command that can be executed by the bank.
 */
public interface BankCommand {
	/**
	 *  This method allows the bank to execute the specific command.
	 * @param bank the bank.
	 */
	void execute(Bank bank);
}
