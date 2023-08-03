package config.category.commands;

import config.category.CategoryConfigProcess;
import optionMenu.Command;

/**
 * The AppendOrCreateConfirmCommand class implements the Command interface and
 * represents a command that allows users to toggle the append or create
 * confirmation setting for category selection in the application.
 *
 * When executed, the AppendOrCreateConfirmCommand toggles the append or create
 * confirmation setting and then initiates the process to re-run the category
 * settings menu to reflect the updated configuration.
 *
 * @see optionMenu.Command
 * @see config.category.CategoryConfigProcess
 * @author LORD GABRIEL
 */

public class AppendOrCreateConfirmCommand implements Command {

	private CategoryConfigProcess process;

	public AppendOrCreateConfirmCommand(CategoryConfigProcess process) {
		this.process = process;
	}

	/**
	 * Executes the AppendOrCreateConfirmCommand, toggling the append or create
	 * confirmation setting for category selection.
	 *
	 * When executed, this command toggles the append or create confirmation
	 * setting, which determines whether users should be prompted to confirm their
	 * decision when selecting a category. The CategoryConfigProcess class handles
	 * the change in configuration setting.
	 *
	 * After toggling the setting, the command initiates the process to re-run the
	 * category settings menu to reflect the updated configuration.
	 */

	public void execute() {
		process.changeAppendOrCreateConfirm();
		process.chooseProcessFirstRun("Category Selection Settings", process.getSettings().getInput(), "Selecting one of these options will toggle it, i.e. switch it from true to false or vice-versa. See help on the main menu for an explanation of these options.", false);
	}
}
