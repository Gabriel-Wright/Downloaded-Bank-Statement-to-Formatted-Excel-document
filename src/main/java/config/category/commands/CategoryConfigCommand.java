package config.category.commands;

import config.category.CategoryConfigProcess;
import config.settings.Settings;
import optionMenu.Command;

/**
 * The CategoryConfigCommand class implements the Command interface and
 * represents a command that allows users to access and modify the category
 * configuration settings of the application.
 * 
 * @see optionMenu.Command
 * @see config.category.CategoryConfigProcess
 */
public class CategoryConfigCommand implements Command {

	private Settings setting;
	private CategoryConfigProcess catConfigProcess;

	public CategoryConfigCommand(Settings setting) {
		this.setting = setting;
		catConfigProcess = new CategoryConfigProcess(setting);
	}

	/**
	 * Executes the CategoryConfigCommand, initiating the process to configure
	 * category-related settings.
	 *
	 * When executed, this command displays the category settings menu, allowing
	 * users to toggle various category configuration options. The
	 * CategoryConfigProcess class handles user choices and updates the
	 * configuration accordingly.
	 */

	public void execute() {
		catConfigProcess.chooseProcessFirstRun("Category Selection Settings", setting.getInput(),
				"Selecting one of these options will toggle it, i.e. switch it from true to false or vice-versa. See help on the main menu for an explanation of these options.",
				false);
	}

}
