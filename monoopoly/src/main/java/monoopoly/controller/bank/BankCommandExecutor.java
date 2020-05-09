package monoopoly.controller.bank;

public class BankCommandExecutor {
	
	public void executeCommand(BankCommand bankCommand) {
		bankCommand.execute();
	}
	
}
