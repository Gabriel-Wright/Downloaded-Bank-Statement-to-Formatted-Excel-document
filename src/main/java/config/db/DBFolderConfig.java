package config.db;

import config.AppConfig;
import config.FileConfig;

import java.io.File;

/**
 * The DBFolderConfig class represents a configuration for the database folder
 * path. It extends the FileConfig class, inheriting its properties and methods
 * for managing the configuration file. This class specifically deals with the
 * configuration of the database folder path and provides methods to retrieve,
 * set, and save the folder path to the config.properties file.
 *
 * @see FileConfig
 * @see AppConfig
 * @see sqliteData.Database
 * @author LORD GABRIEL
 */

public class DBFolderConfig extends FileConfig {

	private static final String PROPERTY_NAME = "dbFolder";
	private File dbFolder;

	/*
	 * =========== CONSTRUCTORS ===========
	 */

	/**
	 * Constructs a new DBFolderConfig object with the provided AppConfig instance.
	 *
	 * @param config - the AppConfig instance associated with this configuration
	 */

	public DBFolderConfig(AppConfig config) {
		super(config);
	}

	/*
	 * =========== GETTERS =============
	 */

	/**
	 * Retrieves the database folder file object.
	 *
	 * @return the database folder file object
	 */

	public File getDBFolder() {
		return dbFolder;
	}

	/*
	 * =========== SETTERS ==============
	 */

	/**
	 * Sets the database folder file object.
	 *
	 * @param dbFolder - the database folder file object to set
	 */

	public void setDBFolder(File dbFolder) {
		this.dbFolder = dbFolder;
	}

	/*
	 * ============== SAVING PROPERTY METHODS ================
	 */

	/**
	 * Saves the database folder path to the config.properties file.
	 *
	 * The saveDbFolderPath method is used to save the file path of the database
	 * folder to the config.properties file using the saveFilePath method inherited
	 * from the FileConfig class. It uses the PROPERTY_NAME constant as the property
	 * key for the database folder path in the config.properties file.
	 */

	public void saveDbFolderPath() {
		saveFilePath(PROPERTY_NAME, dbFolder);
	}

}
