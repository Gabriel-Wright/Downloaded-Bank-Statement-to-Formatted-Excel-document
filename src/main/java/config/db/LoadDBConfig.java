package config.db;

import java.io.File;

import regex.RegexMethods;
import optionMenu.Input;

/**
 * Front-end LoadDBConfig class, used to check whether relevant DB config
 * settings are assigned in the config file. These check methods are applied
 * when loading/reloading the application.
 * 
 * @see config.AppConfig;
 * @see config.DBConfig;
 * @author LORD GABRIEL
 */
public class LoadDBConfig {

	private RegexMethods regex;
	private Input input;
	/*
	 * ================ CONSTRUCTORS ====================
	 */

	/**
	 * Constructs LoadDBConfig object, taking RegexMethod and Input object to
	 * process user input.
	 * 
	 * @param regex - RegexMethods instance.
	 * @param input - Input instance.
	 */

	public LoadDBConfig(RegexMethods regex, Input input) {
		this.regex = regex;
		this.input = input;
	}

	/*
	 * ============== PROPERTY CHECKERS ===================
	 */
	
	/**
	 * Checks whether dbName property has been assigned
	 * 
	 * @param config - relevant config object to check property from
	 * @return true if the dbName property is assigned, false otherwise.
	 */

	public boolean checkDBNameSetting(DBNameConfig config) {
		String dbName = config.getConfig().checkProperty("dbName");
		return !(dbName == null);
	}

	/**
	 * Prompts the user to input a name for the database. NOTE: The user is prompted
	 * to not include alpha-numeric characters, but the method will attempt to
	 * remove this characters anyway.
	 *
	 */

	public void userChooseDBName(DBNameConfig config) {
		System.out.println("Please input a name for your database. This will be the name of your .db file, "
				+ "where all of your raw transaction data is stored.");
		System.out.println("Do not include non-alphanumeric characters.");
		String inputName = input.inputOriginalString("Input a name for your database of transactions below:", true);
		String dbName = regex.removeAllNonAlphaNumeric(inputName);
		config.setDBName(dbName);
		;
	}

	/**
	 * Checks whether dbFolder setting has been assigned
	 * 
	 * @param config - relevant config object to check setting from
	 * @return will return true if dbFolder setting is assigned, false otherwise.
	 */

	public boolean checkDBFolderSetting(DBFolderConfig config) {
		String dbFolder = config.getConfig().checkProperty("dbFolder");
		return !(dbFolder == null);
	}
	
    /**
     * Prompts the user to choose a folder for the database.
     *
     * @param config The relevant DBConfig object to set the folder path.
     */

	public void userChooseDBFolder(DBFolderConfig config) {
		File dbFolder = null;
		while (dbFolder == null) {
			System.out.println("Please select a folder to save your databases");
			dbFolder = input.chooseFolder("Please select a folder to save your databases");
		}
		config.setDBFolder(dbFolder);
	}

	/**
	 * Checks whether DB settings have been assigned within AppConfig object. If
	 * they have not been assigned then the user is prompt to choose location and
	 * assign dbName.
	 * 
	 * @param config - relevant config object to check settings and save settings to
	 */
	public void checkAndCreateDBSettings(DBNameConfig dbNameconfig, DBFolderConfig dbFolderConfig) {
		// If one of the settings not assigned, ask for user input
		if (!checkDBNameSetting(dbNameconfig) || !checkDBFolderSetting(dbFolderConfig)) {
			// User chooses the name and location of databases
			userChooseDBName(dbNameconfig);
			userChooseDBFolder(dbFolderConfig);
			dbFolderConfig.saveDbFolderPath();
			dbNameconfig.saveDBName();
		}
	}
}
