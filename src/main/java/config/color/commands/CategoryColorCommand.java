package config.color.commands;

import config.color.ColorConfigProcess;
import optionMenu.Command;

/**
 * The CategoryColorCommand class is a command responsible for executing the process to change the color coding of a specific category
 * within an Excel document. When executed, it prompts the user to select a new color for the specified category and updates the color
 * coding configuration accordingly using the ColorConfigProcess.
 *
 * @see config.color.ColorConfigProcess
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */

public class CategoryColorCommand implements Command {

	private ColorConfigProcess colProcess;
	private String category;

	public CategoryColorCommand(ColorConfigProcess colProcess, String category) {
		this.colProcess = colProcess;
		this.category = category;
	}

	public void execute() {
		colProcess.changeCategoryColor(category);
		colProcess.chooseProcessFirstRun("Color Coding Settings menu", colProcess.getSettings().getInput(),
				"Please review options for color coding", false);
	}
}
