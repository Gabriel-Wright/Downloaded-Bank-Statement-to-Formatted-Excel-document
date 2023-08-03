package config.db;

import config.AppConfig;
import config.StringConfig;

/**
 * The DBNameConfig class represents a configuration for the database name. It
 * extends the StringConfig class, inheriting its properties and methods for
 * managing the configuration file. This class specifically deals with the
 * configuration of the database name and provides methods to retrieve, set, and
 * save the database name to the config.properties file.
 *
 * @see config.StringConfig
 * @see config.BaseConfig
 * @see config.AppConfig
 * @see sqliteData.Databse
 * @author LORD GABRIEL
 **/
public class DBNameConfig extends StringConfig {

	private String dbName;

	/*
	 * ========= CONSTRUCTORS =========
	 */

	/**
	 * Constructs a new DBNameConfig object with the provided AppConfig instance.
	 *
	 * @param config - the AppConfig instance associated with this configuration
	 */

	public DBNameConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ========= GETTERS ============
	 */

	/**
	 * Retrieves the database name.
	 *
	 * @return the database name
	 */

	public String getDBName() {
		return dbName;
	}

	/*
	 * ========= SETTERS ==========
	 */

	/**
	 * Sets the database name.
	 *
	 * @param dbName - the database name to set
	 */

	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	/*
	 * ========= METHODS ==========
	 */

	/**
	 * Saves the database name to the config.properties file.
	 *
	 * The saveDBName method is used to save the database name to the
	 * config.properties file using the saveStringProperty method inherited from the
	 * StringConfig class. It uses the "dbName" property key as the property key for
	 * the database name in the config.properties file.
	 */

	public void saveDBName() {
		saveStringProperty("dbName", dbName);
	}
}
