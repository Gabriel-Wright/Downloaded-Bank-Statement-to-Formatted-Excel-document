package optionMenu;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Input class provides utility methods for user input handling, such as
 * inputting integers and strings, confirming inputs, and choosing colors. It
 * utilizes the Scanner class for input reading and supports confirmation
 * prompts for user input.
 * 
 * @author LORD GABRIEL
 *
 */
public class Input {

	private Scanner scanner;
	protected static final Logger logger = LogManager.getLogger(Input.class.getName());

	/*
	 * ============= CONSTRUCTORS ================
	 */

	/**
	 *
	 * Constructs a new Input object with the specified Scanner.
	 * 
	 * @param scanner - the Scanner object to use for input
	 */

	public Input(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Constructs a new Input object with no scanner object initialised.
	 */

	public Input() {

	}

	/*
	 * =========================== GETTERS ============================
	 */
	/**
	 * Returns the Scanner object associated with the Input.
	 * 
	 * @return the Scanner object
	 */

	public Scanner getScanner() {
		return scanner;
	}

	/*
	 * ========================== SETTERS =============================
	 */
	/**
	 * Sets the Scanner object associated with the Input.
	 * 
	 * @param scanner - the Scanner object to set
	 */

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	/*
	 * =========================== METHODS ===========================
	 */

	/**
	 * Prompts the user for confirmation of a given input.
	 * 
	 * @param input   - the input to confirm
	 * @param scanner - the Scanner object for reading user input
	 * @return true if the user confirms the input, false otherwise
	 */

	public boolean confirmInput(String input) {
		String confirm;
		while (true) {
			System.out.println(String.format("\nPlease enter Y if comfortable with input:%s, N if not.", input));
			confirm = scanner.nextLine().toLowerCase();
			if (confirm.equals("n")) {
				logger.info(String.format("\nNot confirming input, input: %s", input));
				return false;
			} else if (confirm.equals("y")) {
				logger.info(String.format("\nInput confirmed, input:%s", input));
				return true;
			}
		}
	}

	/**
	 * Prompts the user to input an integer with the specified prompt message.
	 * Confirmation of the input can be toggled with the confirm parameter
	 * 
	 * @param prompt  - the prompt message to display
	 * @param confirm - a boolean indicating whether to confirm the input
	 * @return the integer inputted by the user
	 */
	public int inputInteger(String prompt, boolean confirm) {
		int choice = 0;
		boolean isIntegerInputted = false;

		while (!isIntegerInputted) {
			System.out.println(prompt);
			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				// need to confirm input
				if (confirm) {
					if (confirmInput(choice + "")) {
						isIntegerInputted = true;
					}
				}

				// No need to confirm input
				if (!confirm) {
					logger.info(String.format("\nInteger inputted, returned: %s.Prompt: %s ", choice, prompt));
					return choice;
				}

			} else {
				scanner.nextLine(); // Consume invalid input
				System.out.println("\nInvalid input format. Please enter a number.");
			}
		}
		logger.info(String.format("\nInteger inputted, returned:%s .Prompt: %s", choice, prompt));
		return choice;
	}

	/**
	 * Checks whether the specified option index lies within the appropriate index
	 * range.
	 * 
	 * @param optionIndex - the option index to check
	 * @param maxIndex    - the maximum index allowed
	 * @return true if the option index is valid, false otherwise
	 */
	public boolean isValidNum(int optionIndex, int maxIndex) {
		return (optionIndex < maxIndex) && (optionIndex >= 0);
	}

	/**
	 * Prompts the user to input a string with the specified prompt message.
	 * Confirmation of the input can be toggled with the confirm parameter.
	 * 
	 * @param prompt  - the prompt message to display
	 * @param confirm - a boolean indicating whether to confirm the input
	 * @return the string inputted by the user
	 */
	public String inputOriginalString(String prompt, boolean confirm) {
		boolean isStringInputted = false;
		String stringInput = null;
		while (!isStringInputted) {
			System.out.println(prompt);
			stringInput = scanner.nextLine();
			// If we need to confirm
			if (confirm) {
				if (confirmInput(stringInput)) {
					isStringInputted = true;
				}
			}
			// If no need for confirmation
			if (!confirm) {
				isStringInputted = true;

			}
		}
		logger.info(String.format("\nString inputted, returned: %s.Prompt: %s", stringInput, prompt));
		return stringInput;
	}

	/**
	 * Prompts the user to input a date range in the format YYYY-MM-DD. Validates
	 * the input and returns a String representing the selected date range in the
	 * specified format.
	 *
	 * @param prompt  - the prompt message to display
	 * @param confirm - a boolean indicating whether to confirm the input
	 * @return the String representing the selected date range in the format
	 *         YYYY-MM-DD
	 */

	public String inputDateRange(String prompt, boolean confirm) {
		LocalDate date = null;
		boolean isDateInputted = false;
		String dateInput = "";
		while (!isDateInputted) {
			System.out.println(prompt);
			dateInput = scanner.nextLine();

			// checks initial YYYY-MM-DD format (not including testing the digits that can
			// appear there).
			if (isValidDate(dateInput)) {
				try {
					// Parse to LocalDate to check whether valid digits for YYYY-MM-DD format
					date = LocalDate.parse(dateInput);
					if (confirm) {
						if (confirmInput(dateInput)) {
							isDateInputted = true;
						}
					} else {
						isDateInputted = true;
					}
				} catch (Exception e) {
					System.out.println("\nInvalid date format. Please try again. Expected format (YYYY-MM-DD)");
				}
			} else {
				System.out.println("\nInvalid date format. Please try again. Expected format (YYYY-MM-DD)");
			}
		}
		logger.info(String.format("\nDate inputted, returned: %s.Prompt: %s", date, prompt));
		return dateInput;
	}

	/**
	 * Checks whether the specified input string is a valid date in the format
	 * YYYY-MM-DD.
	 *
	 * @param input - the input string to check
	 * @return true if the input string is a valid date in the format YYYY-MM-DD,
	 *         false otherwise
	 */

	private boolean isValidDate(String input) {
		Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	/**
	 * Creates a new JFileChooser instance.
	 * 
	 * @return the newly created JFileChooser instance
	 */
	public JFileChooser createFileChooser() {
		return new JFileChooser();
	}

    /**
     * Opens a file chooser dialog for the user to select a folder.
     *
     * @param dialogTitle - The title of the file chooser dialog.
     * @return The selected folder as a File object, or null if no folder was selected
     * or an error occurred.
     */

	public File chooseFolder(String dialogTitle) {
		JFileChooser fileChooser = createFileChooser();
		fileChooser.setDialogTitle(dialogTitle);
		// Set the current directory for the file chooser
		// here it is set to the current working directory
		fileChooser.setCurrentDirectory(new File("."));
		
		// Set the file chooser to directory selection mode --> this selects a testing area
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(null);
		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = fileChooser.getSelectedFile();
			logger.info(String.format("Selected output folder: %s", selectedFolder.getAbsolutePath()));
			// Return selected folder
			return selectedFolder;
		} else if (result == JFileChooser.CANCEL_OPTION) {
			logger.info("No folder selected");
			return null;
		} else if (result ==JFileChooser.ERROR_OPTION) {
			logger.info("Error occured while selecting folder");
			return null;
		}
		return null;
	}

    /**
     * Opens a file chooser dialog for the user to select a file.
     *
     * @param dialogTitle - The title of the file chooser dialog.
     * @return The selected file as a object, or null if no file was selected
     * or an error occurred.
     */

	public File chooseFile(String dialogTitle) {
		JFileChooser fileChooser = createFileChooser();
		fileChooser.setDialogTitle(dialogTitle);

		fileChooser.setCurrentDirectory(new File("."));

		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(null);
		// Check if a file was selected
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			logger.info(String.format("Selected File: %s" , selectedFile.getAbsolutePath()));
			// Return selectedFolder
			return selectedFile;
		} else if (result == JFileChooser.CANCEL_OPTION) {
			logger.info("No file selected.");
			return null;
		} else if (result == JFileChooser.ERROR_OPTION) {
			logger.info("Error occurred while selecting file.");
			return null;
		}
		return null;
	}

	
	/**
	 * Opens a color chooser dialog for the user to choose a color.
	 * 
	 * @param prompt         - the prompt message to display
	 * @param displayedColor - the color initially displayed in the color chooser
	 *                       dialog
	 * @return the color chosen by the user
	 */
	
	public Color chooseColor(String prompt, Color displayedColor) {
		// Create a new JFrame as parent component
		JFrame frame = new JFrame();

		// Show color chooser dialogue and get the selected color
		Color color = JColorChooser.showDialog(frame, prompt, displayedColor);
		// Return selected color
		return color;
	}

}
