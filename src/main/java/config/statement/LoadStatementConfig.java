package config.statement;

import optionMenu.Input;

import java.io.File;

/**
 * Front-end LoadStatementConfig class, used to check whether relevant Statement config
 * settings are assigned in the config file. These check methods are applied
 * when loading/reloading the application.
 * 
 * @see config.AppConfig;
 * @see config.StatementConfig;
 * @author LORD GABRIEL
 */

public class LoadStatementConfig {

	private Input input;
	
	public LoadStatementConfig(Input input) {
		this.input = input;
	}
	
	/**
	 * Checks whether statementFolder setting has been assigned
	 * 
	 * @param config - relevant config object to check setting from
	 * @return will return true if statementFolder setting is assigned, false otherwise.
	 */

	public boolean checkStatementFolderSetting(StatementConfig config) {
		String statementFolder = config.getConfig().checkProperty("statementFolder");
		return !(statementFolder == null);
	}

    /**
     * Prompts the user to choose a folder for their bank statements.
     *
     * @param config - The relevant StatementConfig object to set the folder path.
     */

	public void userChooseStatementFolder(StatementConfig config) {
		File statementFolder = null;
		while (statementFolder == null) {
			System.out.println("Please select a folder to store your bank statements");
			statementFolder = input.chooseFolder("Please select a folder to store your bank statements");
		}
		config.setStatementFolder(statementFolder);
	}
 
	/**
	 * Checks whether statementConfig settings have been assigned within AppConfig object. If
	 * they have not been assigned then the user is prompt to choose a statementFolder location.
	 * 
	 * @param StatementConfig - relevant config object to check settings and save settings to
	 */

	public void checkAndCreateStatementSettings(StatementConfig config) {
		//If statementFolder setting not assigned, ask for user input
		if(!(checkStatementFolderSetting(config))) {
			userChooseStatementFolder(config);
			config.saveStatementFolderPath();;
		}
	}
}
