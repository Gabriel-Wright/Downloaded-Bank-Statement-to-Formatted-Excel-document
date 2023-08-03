package config.statement;

import config.AppConfig;
import config.FileConfig;

import java.io.File;

/**
 * 
 * The StatementConfig class handles the configuration settings related to the
 * folder where bank statements are saved.
 * 
 * It manages the configuration file, provides methods for setting and
 * retrieving the statement folder, and includes functionality to select the
 * folder using a file chooser. It also provides a method for saving the folder
 * path to the configuration file.
 * 
 * @see AppConfig
 * @see statementReaders.StatementReader
 * @author LORD GABRIEL
 */

public class StatementConfig extends FileConfig{

	private static final String PROPERTY_NAME = "statementFolder";
	private File statementFolder;
	
	/*
	 * ===================CONSTRUCTORS ====================
	 */

	/**
	 * Constructs a new StatementConfig instance.
	 * 
	 * @param config - the AppConfig instance
	 */

	public StatementConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ================== GETTERS =================
	 */

	/**
	 * Retrieves the statement folder.
	 * 
	 * @return the statement folder
	 */

	public File getStatementFolder() {
		return statementFolder;
	}

	/*
	 * ================== SETTERS =================
	 */

	public void setStatementFolder(File statementFolder) {
		this.statementFolder = statementFolder;
	}

	/*
	 * =============== SAVE PROPERTY METHODS ==============
	 */
	
	/**
	 * Saves the selected statement folder path to the configuration file.
	 */
	public void saveStatementFolderPath() {
		saveFilePath(PROPERTY_NAME,statementFolder);
	}
}
