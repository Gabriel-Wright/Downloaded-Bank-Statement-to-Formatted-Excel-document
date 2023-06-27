package statementReaders;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFileChooser;

import config.AppConfig;

public class statementReader {
	
	private AppConfig config;
	private File CONFIG_FILE;
	private File statementFolder;
	
	//this this statementFile can be changed -- depending on what is to be imported.
	private File statement;	

	
	protected static final Logger logger = LogManager.getLogger(statementReader.class.getName());

	
	/*
	 * ===================CONSTRUCTORS ====================
	 */
	
	//assigns CONFIG_FILE & statementFolder Files to statementReader object
	public statementReader(AppConfig config){
		this.config = config;
		this.CONFIG_FILE=config.getConfig();
		this.statementFolder = new File(config.checkSetting("statementFolder")); 
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
	
	public File getStatementFolder() {
		return statementFolder;
	}

	public File getStatement() {
		return statement;
	}

	/*
	 * ================== METHODS =================
	 */
	
	public void selectStatement() {
		JFileChooser fileChooser = new JFileChooser();
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
            this.statement=selectedFolder;
        } else {
            logger.info("No file selected.");
        }
	}
}
