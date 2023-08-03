package config.settings;

import optionMenu.Command;

/**
 * The SettingsCommand class represents a command to execute the settings menu.
 * It implements the Command interface, allowing it to be used as a command
 * object within the menu system.
 *
 * The SettingsCommand class is associated with the Settings class, which
 * handles configuration settings for the application. When executed, the
 * SettingsCommand calls the chooseProcessFirstRun method of the associated
 * Settings class, displaying the settings menu and allowing users to
 * interactively modify config properties through the Input class.
 *
 * @see optionMenu.Command
 * @see Settings
 * @author LORD GABRIEL
 */
public class SettingsCommand implements Command {

	private Settings settings;

	public SettingsCommand(Settings settings) {
		this.settings = settings;
	}

	/**
	 * Executes the settings menu command.
	 *
	 * The execute method is called when the settings menu command is selected. It
	 * calls the chooseProcessFirstRun method of the associated Settings class,
	 * displaying the settings menu and allowing users to interactively modify
	 * config properties through the Input class.
	 */

	public void execute() {
		settings.chooseProcessFirstRun("Settings Menu", settings.getInput(),
				"Please see the following options for changing settings", false);
	}
}
