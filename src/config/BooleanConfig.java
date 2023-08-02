package config;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The BooleanConfig class is an abstract class that extends the BaseConfig
 * class and provides functionalities for managing boolean configuration
 * properties in the application. Subclasses of BooleanConfig are expected to
 * implement specific configurations related to boolean properties and their
 * management.
 * 
 * This class includes methods to check boolean properties from the
 * application's configuration file and also to save/update boolean properties
 * to the same file.
 * 
 * @see BaseConfig
 * @see AppConfig
 * @author LORD GABRIEL
 */
public abstract class BooleanConfig extends BaseConfig {

	/*
	 * ============ CONSTRUCTORS ==============
	 */

	public BooleanConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ============= PROPERTY CONFIG CHECK METHODS ==============
	 */

	/**
	 * Checks a boolean property with the specified name in the application's
	 * configuration file and returns its boolean value if it exists. If the
	 * property does not exist, it returns null.
	 * 
	 * @param property - the name of the boolean property to check in the
	 *                 configuration file
	 * @return the boolean value of the specified property, or null if the property
	 *         is not found
	 */

	public Boolean checkBooleanProperty(String property) {
		String propertyValue = getConfig().checkProperty(property);
		if (propertyValue != null) {
			// Property exists, check if it's a valid boolean value
			return Boolean.parseBoolean(propertyValue);
		} else {
			// Property doesn't exist in the configuration file
			return null;
		}
	}

	/*
	 * ============ WRITE TO CONFIG METHODS ===============
	 */

	/**
	 * Saves or updates a boolean property with the given name and value in the
	 * application's configuration file. If the property already exists, its value
	 * will be updated. If the property does not exist, it will be created with the
	 * specified value.
	 * 
	 * @param propertyName - the name of the boolean property to be saved or updated
	 * @param property     - the boolean value of the property to be saved or updated
	 */

	public void saveBooleanProperty(String propertyName, Boolean property) {
		try {
			// Loading pre-existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(getConfigFile().getAbsolutePath());
			properties.load(inputStream);

			// Set boolean property
			properties.setProperty(propertyName, String.valueOf(property));

			// Save updated properties
			FileOutputStream outputStream = new FileOutputStream(getConfigFile().getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info(String.format("Property %s updated: %s", propertyName, String.valueOf(property)));

		} catch (IOException e) {
			logger.error(String.format("Failed to update property %s: %s", propertyName, e.getMessage()));
		}
	}

}
