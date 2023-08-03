package config.color.commands;

import config.color.ColorConfigProcess;
import optionMenu.Command;

/**
 * The StatementSheetSumColor class is a command responsible for executing the process to change the color
 * of the Sum cells within an Excel document. When executed, it prompts the user to select a new color for the Sum cells
 * and updates the color coding configuration accordingly using the ColorConfigProcess.
 *
 * @see config.color.ColorConfigProcess
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */

public class StatementSheetSumColorCommand implements Command {

	private ColorConfigProcess colProcess;

	public StatementSheetSumColorCommand(ColorConfigProcess colProcess) {
		this.colProcess = colProcess;
	}

	public void execute() {
		colProcess.changeSumColor();
		colProcess.chooseProcessFirstRun("Color Coding Settings menu", colProcess.getSettings().getInput(),
				"Please review options for color coding", false);
	}
}
