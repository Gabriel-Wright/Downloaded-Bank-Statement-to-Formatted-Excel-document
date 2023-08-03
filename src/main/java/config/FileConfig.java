
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The FileConfig class is an abstract class that extends the BaseConfig class
 * and provides functionalities for managing file-related configuration
 * properties in the application.
 * 
 * Subclasses of FileConfig are expected to implement specific configurations
 * related to file properties and their management.
 * 
 * This class includes methods to save/update file paths to the application's
 * configuration file.
 * 
 * @see BaseConfig
 * @see AppConfig
 * @author LORD GABRIEL
 */

public abstract class FileConfig extends BaseConfig {

	/*
	 * ========= CONSTRUCTORS ===========
	 */

	/**
	 * Constructs a FileConfig instance with the provided AppConfig object.
	 * 
	 * @param config - the AppConfig object to be used for configuration management
	 */

	public FileConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ========== SAVE PROPERTY METHODS ============
	 */

	/**
	 * Saves the absolute path of the specified File object to the application's
	 * configuration file with the given property name. If the property already
	 * exists, its value will be updated. If the property does not exist, it will be
	 * created with the specified value.
	 * 
	 * @param propertyName - the name of the property to be saved or updated
	 * @param propertyFile - the File object containing the absolute path to be saved
	 */

	public void saveFilePath(String propertyName, File propertyFile) {
		try {
			// Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(getConfigFile().getAbsolutePath());
			properties.load(inputStream);

			// set FolderPath
			properties.setProperty(propertyName, propertyFile.getAbsolutePath());

			// save updated properties
			FileOutputStream outputStream = new FileOutputStream(getConfigFile().getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();

			logger.info(String.format("Folder property updated: %s, path: %s", propertyName,
					propertyFile.getAbsolutePath()));
		} catch (IOException e) {
			logger.error(String.format("Failed to update property: %s", propertyName));
		}
	}

}
