package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConfig {

	private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "//config//config.properties";
	protected static final Logger logger = LogManager.getLogger(AppConfig.class.getName());
	private File config;

	/*
	 * ===================CONSTRUCTORS ====================
	 */
	public AppConfig() {
		checkFolderFile();
		// Maybe take this outside of constructor
	}

	/*
	 * ================== GETTERS =================
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

	public void setConfigFile(File config) {
		this.config = config;
	}

	/*
	 * ================== METHODS =================
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

	public boolean checkFolder() {
		String relativePath = "config";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		File file = new File(absolutePath);
		;
		return file.exists();
	}

	// checks whether file exists
	public boolean checkFile() {
		String relativePath = "config//config.properties";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		File file = new File(absolutePath);
		return file.exists();
	}

	// creates config folder
	public void createFolder() {
		String relativePath = "config";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

		File folder = new File(absolutePath);
		folder.mkdir();
	}

	// creates configFile if doesn't exist
	public boolean createFile() {
		String relativePath = "config\\config.properties";
		String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;
		try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath)) {
			logger.info("Config file created at " + absolutePath);
			// Store an empty properties entry to format .properties file.
			Properties properties = new Properties();
			properties.store(fileOutputStream, null);
			// Return true as file created
			return true;
		} catch (IOException e) {
			logger.error("Failed to create properties file, terminating program \n" + e.getMessage());
			return false;
		}
	}

	public String checkSetting(String setting) {
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(fis);

			String propertyValue = properties.getProperty(setting);
			if (propertyValue != null) {
				logger.info("Setting \"" + setting + "\" found.");
			} else {
				logger.info("Setting \"" + setting + "\" not found.");
			}
			return propertyValue;
		} catch (IOException e) {
			logger.info("Error occurred while reading properties");
			return null;
		}
	}

	// Implemented so that we can better test
	public JFileChooser createFileChooser() {
		return new JFileChooser();
	}

}
