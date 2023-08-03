package statementReaders.process;
import optionMenu.Command;

/**
 * The NationwideCSVCommand class is a command that triggers the process of importing bank statement data in Nationwide CSV format
 * into a database. It executes the import process by calling the necessary methods in the WriteBankStatementToDB class,
 * specifically targeting the import of Nationwide CSV format. After importing the data, it resets the menu to allow further choices
 * for statement formats.
 *
 * @see statementReaders.process.WriteBankStatementToDB
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */
public class NationwideCSVCommand implements Command {

	private WriteBankStatementToDB stToDB;
	
	public NationwideCSVCommand(WriteBankStatementToDB stToDB) {
		this.stToDB = stToDB;
	}
	
	@Override
	public void execute() {
		//Import NationwideCSV
		stToDB.importNationwideCSV();
		//Reset menu
		stToDB.chooseProcess("Please choose the appropriate format of the statement you would like to read into your database", true);
	}
}
