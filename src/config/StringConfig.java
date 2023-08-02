package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The StringConfig class is an abstract class that extends the BaseConfig class
 * and provides functionalities for managing string-related configuration
 * properties in the application.
 * 
 * Subclasses of StringConfig are expected to implement specific configurations
 * related to string properties and their management.
 * 
 * This class includes methods to save/update string properties to the
 * application's configuration file.
 * 
 * @see BaseConfig
 * @see AppConfig
 * @author LORD GABRIEL
 */
public abstract class StringConfig extends BaseConfig {

	/**
	 * Constructs a StringConfig instance with the provided AppConfig object.
	 * 
	 * @param config - the AppConfig object to be used for configuration management
	 */

	public StringConfig(AppConfig config) {
		super(config);
	}

	/*
	 * =========== WRITE TO CONFIG METHODS ==========
	 */

	/**
	 * Saves the specified string property to the application's configuration file
	 * with the given property name. If the property already exists, its value will
	 * be updated. If the property does not exist, it will be created with the
	 * specified value.
	 * 
	 * @param propertyName - the name of the property to be saved or updated
	 * @param property     - the string value to be saved
	 */

	public void saveStringProperty(String propertyName, String property) {
		try {
			// Loading pre-existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(getConfigFile().getAbsolutePath());
			properties.load(inputStream);

			// Set boolean property
			properties.setProperty(propertyName, property);

			// Save updated properties
			FileOutputStream outputStream = new FileOutputStream(getConfigFile().getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info(String.format("Property %s updated: %s", propertyName, property));
		} catch (IOException e) {
			logger.error(String.format("Failed to update property %s: %s. %s", propertyName, property, e.getMessage()));
		}
	}

}
