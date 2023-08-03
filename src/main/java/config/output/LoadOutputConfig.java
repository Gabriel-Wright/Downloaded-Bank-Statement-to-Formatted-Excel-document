package config.output;

import optionMenu.Input;

import java.io.File;
/**
 * Front-end LoadOutputConfig class, used to check whether relevant Output config
 * settings are assigned in the config file. These check methods are applied
 * when loading/reloading the application.
 * 
 * @see config.AppConfig;
 * @see config.OutputConfig;
 * @author LORD GABRIEL
 */

public class LoadOutputConfig {
	
	private Input input;
	
	public LoadOutputConfig(Input input) {
		this.input = input;
	}
	
	/**
	 * Checks whether outputFolder setting has been assigned
	 * 
	 * @param config - relevant config object to check setting from
	 * @return will return true if outputFolder setting is assigned, false otherwise.
	 */

	public boolean checkOutputFolderSetting(OutputConfig config) {
		String outputFolder = config.getConfig().checkProperty("outputFolder");
		return !(outputFolder == null);
	}

    /**
     * Prompts the user to choose a folder for their excel file outputs.
     *
     * @param config - The relevant OutputConfig object to set the folder path.
     */

	public void userChooseOutputFolder(OutputConfig config) {
		File outputFolder = null;
		while (outputFolder == null) {
			System.out.println("Please select a folder to save your excel file outputs to");
			outputFolder = input.chooseFolder("Please select a folder to save your excel outputs to");
		}
		config.setOutputFolder(outputFolder);
	}
 
	/**
	 * Checks whether outputFolder settings have been assigned within AppConfig object. If
	 * they have not been assigned then the user is prompt to choose a outputFolder location.
	 * 
	 * @param OutputConfig - relevant config object to check settings and save settings to
	 */

	public void checkAndCreateOutputSettings(OutputConfig config) {
		//If outputFolder setting not assigned, ask for user input
		if(!(checkOutputFolderSetting(config))) {
			userChooseOutputFolder(config);
			config.saveOutputFolderPath();
		}
	}

}
