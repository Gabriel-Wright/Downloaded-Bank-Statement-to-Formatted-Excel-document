package excelWriter.process.commands;

import excelWriter.process.WriteDBToExcel;
import optionMenu.Command;

/**
 * The WriteExcelDocCommand class is a command that triggers the process of
 * exporting data from the SQLite database to an Excel file. It executes the
 * export process by calling the necessary methods in the WriteDBToExcel class.
 * This command is used within the statement menu to execute the data export to
 * Excel when the corresponding option is selected.
 * 
 * @see excelWriter.process.WriteDBToExcel
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */

public class WriteExcelDocCommand implements Command {

	private WriteDBToExcel DBToXL;

	public WriteExcelDocCommand(WriteDBToExcel DBToXL) {
		this.DBToXL = DBToXL;
	}

	@Override
	public void execute() {
		DBToXL.processDBToExcel();
		DBToXL.chooseProcess(
				"If you wish to export data from your SQLite DB to an excel file, please select option 0. Otherwise select option 1.",
				false);
	}
}
