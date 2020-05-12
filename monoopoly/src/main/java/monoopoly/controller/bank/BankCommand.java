package monoopoly.controller.bank;

/**
 * This interface represents a generic command that can be executed by the bank.
 */
public interface BankCommand {

	/**
	 * This method executes the specific command.
	 */
	void execute();

}
