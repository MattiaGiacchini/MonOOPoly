package monoopoly.controller.bank;
/**
 * This class has to only purpose to execute objects of the {@link BankCommand} class.
 */
public class BankCommandExecutor {
    /**
     * Executes a command.
     * @param bankCommand the command to be executed.
     */
    public void executeCommand(final BankCommand bankCommand) {
        bankCommand.execute();
    }
}
