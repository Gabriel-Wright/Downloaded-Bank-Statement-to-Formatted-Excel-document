package config.color.commands;

import config.color.ColorConfigProcess;
import optionMenu.Command;

/**
 * The SummarySheetMonthColor class is a command responsible for executing the process to change the color
 * of the header row of the Summary Sheet within an Excel document. When executed, it prompts the user to select a new color
 * for the header row and updates the color coding configuration accordingly using the ColorConfigProcess.
 *
 * @see ColorConfigProcess
 * @see Command
 * @author LORD GABRIEL
 */

public class SummarySheetMonthColorCommand implements Command {

	private ColorConfigProcess colProcess;

	public SummarySheetMonthColorCommand(ColorConfigProcess colProcess) {
		this.colProcess = colProcess;
	}

	public void execute() {
		colProcess.changeSummarySheetMonthColor();
		colProcess.chooseProcessFirstRun("Color Coding Settings menu", colProcess.getSettings().getInput(),
				"Please review options for color coding", false);
	}
}
