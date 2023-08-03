package config.category.commands;

import config.category.CategoryConfigProcess;
import optionMenu.Command;

/**
 * The CategoryMenuChoiceConfirmCommand class implements the Command interface
 * and represents a command that allows users to toggle the category menu choice
 * confirmation setting in the application.
 *
 * When executed, the CategoryMenuChoiceConfirmCommand toggles the category menu
 * choice confirmation setting and then initiates the process to re-run the
 * category settings menu to reflect the updated configuration.
 * 
 * @see optionMenu.Command
 * @see config.config.CategoryConfigProcess
 */

public class CategoryMenuChoiceConfirmCommand implements Command {

	private CategoryConfigProcess process;

	public CategoryMenuChoiceConfirmCommand(CategoryConfigProcess process) {
		this.process = process;
	}

	/**
	 * Executes the CategoryMenuChoiceConfirmCommand, toggling the category menu
	 * choice confirmation setting.
	 *
	 * When executed, this command toggles the category menu choice confirmation
	 * setting, which determines whether users should be prompted to confirm their
	 * decision after choosing a category option from the category menu. The
	 * CategoryConfigProcess class handles the change in configuration setting.
	 *
	 * After toggling the setting, the command initiates the process to re-run the
	 * category settings menu to reflect the updated configuration.
	 */

	public void execute() {
		process.changeCategoryMenuChoiceConfirm();
		process.chooseProcessFirstRun("Category Selection Settings", process.getSettings().getInput(), "Selecting one of these options will toggle it, i.e. switch it from true to false or vice-versa. See help on the main menu for an explanation of these options.", false);
	}
}