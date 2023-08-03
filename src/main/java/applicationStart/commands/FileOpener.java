package applicationStart.commands;

import optionMenu.Command;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * The FileOpener class represents a command to open a specific file using the
 * default system application. It implements the Command interface to be used as
 * a command in a menu-based application. The FileOpener is designed to open a
 * file specified by its file path. If the file exists, it will be opened with
 * the default system application associated with its file type. If the file
 * does not exist, an error message will be displayed indicating that the file
 * was not found. This class utilizes the java.awt.Desktop class to open the
 * file with the default application.
 * 
 * @author LORD GABRIEL
 * @see optionMenu.Command
 */
public class FileOpener implements Command {

	private static final Logger logger = LogManager.getLogger(FileOpener.class.getName());

	private String filePath;
	private File file;
	
	/**
	 * Constructs a new FileOpener object with the specified file path.
	 *
	 * @param filePath - the path of the file to be opened
	 */

	public FileOpener(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Loads the file object with the file specified by the file path.
	 */

	public void loadFile() {
		file = new File(filePath);
	}

	/**
	 * Opens the PDF file with the default system application.
	 * If the file exists, it will be opened using the default application associated with its file type.
	 * If the file does not exist, an error message will be displayed indicating that the file was not found.
	 */

	public void openPDFfile() {
		System.out.println("\n\nOpening file at:" + filePath + " now.");
		if (file.exists()) {
			try {
				Desktop.getDesktop().open(file);
				logger.info(String.format("File Opened: %s.", filePath));
			} catch (IOException e) {
				logger.error(String.format("Error opening file: %s. %s", filePath, e.getMessage()));
				System.out.println("Error opening this file.");
			}
		} else {
			logger.error(String.format("No guide file found: %s", filePath));
			System.out.println("\n\nNo guide file was found, please check whether you have deleted/moved this file");
		}
	}

	/**
	 * Executes the command to open the specified file.
	 * This method is implemented from the Command interface and is called when the command is executed.
	 */

	public void execute() {
		loadFile();
		openPDFfile();
	}
}
