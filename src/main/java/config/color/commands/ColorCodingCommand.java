package config.color.commands;

import config.color.ColorConfigProcess;
import config.settings.Settings;
import optionMenu.Command;

/**
 * The ColorCodingCommand class is responsible for executing the color coding
 * settings menu, as a drop down from the settings menu. When executed, it
 * displays the options for configuring the color coding properties and handles
 * the user's choices. The class interacts with the ColorConfigProcess to manage
 * the color coding configuration process.
 *
 * @see config.color.ColorConfigProcess
 * @see config.settings.Settings
 * @see optionMenu.Command
 * @author LORD GABRIEL
 */
public class ColorCodingCommand implements Command {

	private Settings setting;
	private ColorConfigProcess colorConfigProcess;

	public ColorCodingCommand(Settings setting) {
		this.setting = setting;
		colorConfigProcess = new ColorConfigProcess(setting);
	}

	public void execute() {
		colorConfigProcess.chooseProcessFirstRun("Color Coding Settings menu", setting.getInput(),
				"Please review options for color coding", false);
	}
}
