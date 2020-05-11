package monoopoly.controller.bank;

/**
 *	This interface represents a generic command that can be executed by the bank.
 */
public interface BankCommand {
	
	/**
	 *  This method allows the {@link Bank} to execute the specific command.
	 * 	@param bank the bank.
	 */
	void execute();
	
}
