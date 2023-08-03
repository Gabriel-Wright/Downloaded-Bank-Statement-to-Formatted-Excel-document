package statementReaders.process;

import optionMenu.Command;

/**
 * The WriteBankStatementToDBCommand class is a command that triggers the process of reading a bank statement in a specific format and
 * writing the data into a database. It executes the import process by calling the necessary methods in the WriteBankStatementToDB class.
 * This command is typically used within an option menu to initiate the statement import to the database when the corresponding option is selected.
 *
 * @see statementReaders.process.WriteBankStatementToDB
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */
public class WriteBankStatementToDBCommand implements Command{

	private WriteBankStatementToDB stToDB;
	
	public WriteBankStatementToDBCommand(WriteBankStatementToDB stToDB) {
		this.stToDB=stToDB;
	}
	
	@Override
	public void execute() {
		stToDB.chooseProcessFirstRun("Statement to DB Menu", stToDB.getInput(),"Please choose the appropriate format of the statement you would like to read into your database", true);
	}
}
