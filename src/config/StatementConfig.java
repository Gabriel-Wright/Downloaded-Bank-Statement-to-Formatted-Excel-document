package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Properties;

import org.apache.logging.log4j.Logger;

import javax.swing.JFileChooser;

public class StatementConfig {

	private File CONFIG_FILE;
	private File statementFolder;
	private Logger logger;
	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public StatementConfig(AppConfig config) {
		this.CONFIG_FILE = config.getConfigFile();
		this.logger = config.getLogger();
	}

	/*
	 * ================== GETTERS =================
	 */

	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public File getStatementFolder() {
		return statementFolder;
	}

	/*
	 * ================== METHODS =================
	 */

	//Set folder where Statements are saved.
	public boolean setStatementFolder() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select statements folder");
		// Set the current directory for the file chooser
		// here it is set to the current working directory.
		fileChooser.setCurrentDirectory(new File("."));

		// Set the file chooser to directory selection mode --> this selects
		// a testing area.
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(null);
		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = fileChooser.getSelectedFile();
			logger.info("Selected folder: " + selectedFolder.getAbsolutePath());
			// Return true if file selected properly
			this.statementFolder = selectedFolder;
			return true;
		} else if (result == JFileChooser.CANCEL_OPTION) {
			logger.info("No folder selected.");
			return false;
		} else if (result == JFileChooser.ERROR_OPTION) {
			logger.info("Error occurred while selecting folder.");
			return false;
		}
		return false;
	}

	//saves selected folder path to config.properties file
	public void saveFolderPath() {
		try {
			//Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
			
			//set FolderPath
			properties.setProperty("statementFolder", statementFolder.getAbsolutePath());
			
			//save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			
			System.out.println("Properties updated");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
