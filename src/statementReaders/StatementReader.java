package statementReaders;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFileChooser;

import config.AppConfig;

 public abstract class StatementReader {
	
	private AppConfig config;
	private File CONFIG_FILE;
	private File statementFolder;
	private File statement;	

	protected static final Logger logger = LogManager.getLogger(StatementReader.class.getName());

		
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
	 * ================== SETTERS =================
	 */

	public void setConfig(AppConfig config) {
		this.config=config;
	}
	
	public void setConfig_File(File configFile) {
		this.CONFIG_FILE=configFile;
	}
	
	/*
	 * ================== METHODS =================
	 */
	
	public boolean loadStatementFolder() {
		//Attempt to load statementFolder, if setting exists. If it doesn't then flair and log.
		try {
			this.statementFolder = new File(config.checkSetting("statementFolder")); 
			logger.info("Setting found: statementFolder.");
			return true;
		} catch (NullPointerException e) {
			logger.error("Unable to find setting statementFolder");
			return false;
		}
	}
	
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
            this.statement=selectedFolder;
            return true;
        } else {
            logger.info("No file selected.");
            return false;
        }
	}
	
	//Implemented so that we can better test
	public JFileChooser createFileChooser() {
		return new JFileChooser();
	}
}
