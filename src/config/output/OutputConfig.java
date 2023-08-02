package config.output;

import config.AppConfig;
import config.FileConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;

import org.apache.logging.log4j.Logger;

/**
 * 
 * The OutputConfig class handles the configuration settings related to the
 * folder where output files are saved.
 * 
 * It manages the configuration file, provides methods for setting and
 * retrieving the output folder, and includes functionality to select the
 * folder using a file chooser. It also provides a method for saving the folder
 * path to the configuration file.
 * 
 * @see AppConfig
 * @author LORD GABRIEL
 */

public class OutputConfig extends FileConfig{

	private static final String PROPERTY_NAME = "outputFolder";
	private File outputFolder;

	/*
	 * ============== CONSTRUCTORS ================
	 */

	public OutputConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ================== GETTERS================
	 */

	/**
	 * Retrieves the output folder.
	 * 
	 * @return the output folder
	 */

	public File getOutputFolder() {
		return outputFolder;
	}

	/*
	 * ================== SETTERS ===================
	 */
	
	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}
	
	/*
	 * ============== SAVE PROPERTY METHODS ================
	 */

	/**
	 * Saves the selected output folder path to the configuration file.
	 */
	public void saveOutputFolderPath() {
		saveFilePath(PROPERTY_NAME, outputFolder);
	}

}
