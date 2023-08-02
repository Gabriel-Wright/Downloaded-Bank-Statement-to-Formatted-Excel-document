package excelWriter.process.commands;

import excelWriter.process.WriteDBToExcel;
import optionMenu.Command;

/**
 * The WriteDBToExcelCommand class is a command that triggers the process of exporting data from the SQLite database to an Excel file. 
 * It executes the export process by calling the necessary methods in the WriteDBToExcel class. This command is used the main menu 
 * to give the user the choice of exporting data to Excel or proceeding with other options.
 * 
 * @see excelWriter.process.WriteDBToExcel
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */
public class WriteDBToExcelCommand implements Command{

	private WriteDBToExcel dbToXL;
	
	public WriteDBToExcelCommand(WriteDBToExcel dbToXL) {
		this.dbToXL = dbToXL;
	}
	
	
	
	@Override
	public void execute() {
		dbToXL.chooseProcessFirstRun("Excel Export Menu", dbToXL.getInput(),"If you wish to export data from your SQLite DB to an excel file, please select option 0. Otherwise select option 1.", false);
	}
}
