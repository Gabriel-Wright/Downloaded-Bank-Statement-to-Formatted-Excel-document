package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.Logger;

public class DBConfig {

	private AppConfig config;
	private File CONFIG_FILE;
	private File dbFolder;
	private String dbName;
	private Logger logger;

	/*
	 * ===============CONSTUCTORS=============
	 */

	public DBConfig(AppConfig config) {
		this.config = config;
		this.CONFIG_FILE = config.getConfigFile();
		this.logger = config.getLogger();
	}

	/*
	 * ================== GETTERS =================
	 */
	
	public AppConfig getConfig() {
		return config;
	}
	
	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public String getDBName() {
		return dbName;
	}

	public File getDBFolder() {
		return this.dbFolder;
	}

	public Logger getLogger() {
		return logger;
	}

	/*
	 * ================== GETTERS =================
	 */

	public void setDBName(String dbName) {
		this.dbName = dbName;
	}

	/*
	 * ===============METHODS==================
	 */

	// Select DBFolder using JFileChooser.
	public boolean setDBFolder() {
		JFileChooser fileChooser = getConfig().createFileChooser();
		fileChooser.setDialogTitle("Select DB folder");

		// Set the current directory for the file chooser
		// -set here to the current working directory

		fileChooser.setCurrentDirectory(new File("."));

		// Set the file chooser to directory selection mode -->
		// this selects a folder rather than a file
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(null);
		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = fileChooser.getSelectedFile();
			logger.info("Selected folder:" + selectedFolder.getAbsolutePath());
			// return true if file selected properly
			this.dbFolder = selectedFolder;
			return true;
		} else if (result == JFileChooser.CANCEL_OPTION) {
			logger.info("Selection of DB Folder was cancelled");
			return false;
		} else if (result == JFileChooser.ERROR_OPTION) {
			logger.error("Error when attempting to choose DB Folder");
			return false;
		}
		return false;
	}

	// save DBName to config.properties file
	public void saveDBName() {
		try {
			// Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);

			// set FolderPath
			properties.setProperty("dbName", dbName);
			// save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();

			logger.info("dbName setting updated: " + dbName);

		} catch (IOException e) {
			logger.error("Failed to update setting dbName: " + e.getMessage());
		}

	}

	// save DBFolder path to config.properties file
	public void saveDBFolder() {
		try {
			// Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);

			// set FolderPath
			properties.setProperty("dbFolder", dbFolder.getAbsolutePath());
			// save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();

			logger.info("dbFolder path setting updated:"+dbFolder.getAbsolutePath());

		} catch (IOException e) {
			logger.error("Failed to update setting dbFolder path: "+e.getMessage());
		}

	}

}
