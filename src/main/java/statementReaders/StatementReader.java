package statementReaders;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFileChooser;

import config.AppConfig;
import regex.RegexMethods;
import sqliteData.tables.readers.TableCategoryReader;
import sqliteData.tables.writers.TableCategoryWriter;
import transactions.Transaction;

/**
 * 
 * The StatementReader class is an abstract class that provides common
 * functionality and attributes for reading bank unprocessed statements.
 * StatementReader is responsible for loading the statement folder and selecting
 * a statement file. It interacts with the AppConfig class and uses a
 * JFileChooser for file selection. Note: StatementReader is an abstract class
 * and cannot be instantiated directly.
 * 
 * @see AppConfig
 * @see JFileChooser
 * @author LORD GABRIEL
 */

public abstract class StatementReader {

	private AppConfig config;
	private File CONFIG_FILE;
	private File statementFolder;
	private File statement;

	protected static final Logger logger = LogManager.getLogger(StatementReader.class.getName());

	/*
	 * ================== GETTERS =================
	 */
	/**
	 * Retrieves the AppConfig object associated with the StatementReader.
	 * 
	 * @return the AppConfig object
	 */

	public AppConfig getConfig() {
		return config;
	}

	/**
	 * Retrieves the configuration file associated with the StatementReader.
	 * 
	 * @return the configuration file
	 */

	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	/**
	 * Retrieves the statement folder associated with the StatementReader.
	 * 
	 * @return the statement folder
	 */

	public File getStatementFolder() {
		return statementFolder;
	}

	/**
	 * Retrieves the statement file associated with the StatementReader.
	 * 
	 * @return the statement file
	 */

	public File getStatement() {
		return statement;
	}

	/*
	 * ================== SETTERS =================
	 */

	/**
	 * Sets the AppConfig object for the StatementReader.
	 * 
	 * @param config - the AppConfig object to set
	 */

	public void setConfig(AppConfig config) {
		this.config = config;
	}

	/**
	 * Sets the configuration file for the StatementReader.
	 * 
	 * @param configFile - the configuration file to set
	 */

	public void setConfig_File(File configFile) {
		this.CONFIG_FILE = configFile;
	}

	/*
	 * ============================= ABSTRACT METHODS =================================
	 */
	

	/**
	 * Abstract method - converts a bank statement file into an
	 * array of transactions. This can require manual input from the user when
	 * deciding how to assign categories for transactions.
	 * 
	 * @param statement   - the statement file
	 * @param tableReader - the TableCategoryReader object to retrieve category
	 *                    information
	 * @param tableWriter - the TableCategoryWriter object to assign categories to
	 *                    transactions
	 * @param regex       - the RegexMethods object for processing transaction
	 *                    details
	 * @return an array of transactions extracted from the CSV statement
	 */

	public  abstract Transaction[] convertStatement(File statement2, TableCategoryReader tCR, TableCategoryWriter tCW,
			RegexMethods regex);
	/*
	 * ================== METHODS =================
	 */

	/**
	 * Loads the statement folder based on the setting in the configuration file.
	 * 
	 * @return true if the statement folder is successfully loaded, false otherwise
	 */

	public boolean loadStatementFolder() {
		// Attempt to load statementFolder, if setting exists. If it doesn't then flair
		// and log.
		try {
			this.statementFolder = new File(config.checkProperty("statementFolder"));
			logger.info("Setting found: statementFolder.");
			return true;
		} catch (NullPointerException e) {
			logger.error("Unable to find setting statementFolder");
			return false;
		}
	}

	/**
	 * Prompts the user to select a statement file using a JFileChooser dialog.
	 * 
	 * @return true if a statement file is selected, false otherwise
	 */

	public boolean selectStatement() {
		JFileChooser fileChooser = createFileChooser();
		fileChooser.setDialogTitle("Select statements to import");
		// Set the current directory for the file chooser
		// here it is set to the current working directory.
		fileChooser.setCurrentDirectory(statementFolder);

		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(null);
		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = fileChooser.getSelectedFile();
			logger.info("Selected file: " + selectedFolder.getAbsolutePath());
			// Do something with the selected file
			this.statement = selectedFolder;
			return true;
		} else {
			logger.info("No file selected.");
			return false;
		}
	}

	/**
	 * Creates a JFileChooser object for file selection. This method can be
	 * overridden for testing purposes.
	 * 
	 * @return a JFileChooser object
	 */
	
	public JFileChooser createFileChooser() {
		return new JFileChooser();
	}

	
}
