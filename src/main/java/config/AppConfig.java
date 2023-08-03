package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * The AppConfig class handles the configuration properties of the application.
 * It manages the configuration folder and file, and provides methods for
 * checking and retrieving properties from the configuration file.
 * 
 * The configuration properties are stored in a "config.properties" file within
 * the application's configuration folder.
 * 
 * @author LORD GABRIEL
 */

public class AppConfig {

	protected static final Logger logger = LogManager.getLogger(AppConfig.class.getName());
	private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "//config//config.properties";
	private File config;

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	/**
	 * Constructs a new AppConfig instance.
	 */

	public AppConfig() {
		checkFolderFile();
	}

	/*
	 * ================== GETTERS =================
	 */

	/**
	 * Retrieves the configuration file.
	 * 
	 * @return the configuration file
	 */

	public File getConfigFile() {
		return config;
	}

	public Logger getLogger() {
		return logger;
	}

	/*
	 * ================== SETTERS =================
	 */

	/**
	 * Sets the configuration file.
	 * 
	 * @param config the configuration file to set
	 */

	public void setConfigFile(File config) {
		this.config = config;
	}

	/*
	 * ================== METHODS =================
	 */

	/**
	 * Checks if the configuration folder and file both exist.
	 * 
	 * @return true if the folder and file both exist, false otherwise
	 */

	public void checkFolderFile() {
		if (!checkFolder()) {
			logger.info("No config folder found - attempting to create");
			createFolder();
		}
		if (!checkFile()) {
			logger.info("No config file found - attempting to create");
			boolean fileCreated = false;
			while (!fileCreated) {
				fileCreated = createFile();
			}
		}
		// assign config file
		this.setConfigFile(new File(CONFIG_FILE_PATH));
		logger.info("config.properties file found: " + CONFIG_FILE_PATH.toString());

	}

	/**
	 * Checks if the configuration folder exists.
	 * 
	 * @return true if the folder exists, false otherwise
	 */

	public boolean checkFolder() {
		String relativePath = "config";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		File file = new File(absolutePath);
		;
		return file.exists();
	}

	/**
	 * Checks if the configuration file exists.
	 * 
	 * @return true if the file exists, false otherwise
	 */
	public boolean checkFile() {
		String relativePath = "config//config.properties";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		File file = new File(absolutePath);
		return file.exists();
	}

	/**
	 * Creates the configuration folder if it does not exist.
	 */
	public void createFolder() {
		String relativePath = "config";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

		File folder = new File(absolutePath);
		folder.mkdir();
	}

	/**
	 * Creates the configuration file if it does not exist.
	 * 
	 * @return true if the file was created, false otherwise
	 */
	public boolean createFile() {
		String relativePath = "config\\config.properties";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath)) {
			String log = String.format("Config file created at %s", absolutePath);
			logger.info(log);
			// Store an empty properties entry to format .properties file.
			Properties properties = new Properties();
			properties.store(fileOutputStream, null);
			// Return true as file created
			return true;
		} catch (IOException e) {
			String log = String.format("Failed to create properties file %s", e.getMessage());
			logger.error(log);
			return false;
		}
	}

	/**
	 * Checks a specific property in the configuration file and retrieves its value.
	 * 
	 * @param property - the setting to check in configuration file
	 * @return the String value of the setting, or null if not found
	 */

	public String checkProperty(String property) {
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(fis);

			String propertyValue = properties.getProperty(property);
			if (propertyValue != null) {
				logger.info("Setting \"" + property + "\" found.");
			} else {
				logger.info("Setting \"" + property + "\" not found.");
			}
			return propertyValue;
		} catch (IOException e) {
			logger.info("Error occurred while reading properties");
			return null;
		}
	}

}
