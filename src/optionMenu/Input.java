package optionMenu;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sqliteData.Database;

/**
 * The Input class provides utility methods for user input handling, such as
 * inputting integers and strings, confirming inputs, and choosing colors.
 * It utilizes the Scanner class for input reading and supports confirmation prompts for user input.
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
	 * @param input - the input to confirm
	 * @param scanner - the Scanner object for reading user input
	 * @return true if the user confirms the input, false otherwise
	 */

	public boolean confirmInput(String input, Scanner scanner) {
		String confirm;
		while (true) {
			System.out.println(String.format("Please enter Y if comfortable with input:{}, N if not.",input));
			confirm = scanner.nextLine().toLowerCase();
			if (confirm.equals("n")) {
				logger.info(String.format("Not confirming input, input: {}",input));
				return false;
			} else if (confirm.equals("y")) {
				logger.info(String.format("Input confirmed, input:{}" ,input));
				return true;
			}
		}
	}

	/**
	 * 	Prompts the user to input an integer with the specified prompt message.
	 * Confirmation of the input can be toggled with the confirm parameter
	 * 	@param prompt - the prompt message to display
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
					if (confirmInput(choice + "", scanner)) {
						isIntegerInputted = true;
					}
				}

				// No need to confirm input
				if (!confirm) {
					logger.info(String.format("Integer inputted, returned: {}.Prompt: {} ",choice,prompt));
					return choice;
				}

			} else {
				scanner.nextLine(); // Consume invalid input
				System.out.println("Invalid input format. Please enter a number.");
			}
		}
		logger.info(String.format("Integer inputted, returned:{} .Prompt: {}",choice,prompt));
		return choice;
	}

	/**
	 * Checks whether the specified option index lies within the appropriate index range.
	 * @param optionIndex - the option index to check
	 * @param maxIndex - the maximum index allowed
	 * @return true if the option index is valid, false otherwise
	 */
	public boolean isValidNum(int optionIndex, int maxIndex) {
		return (optionIndex < maxIndex) && (optionIndex >= 0);
	}

	/**
	 * Prompts the user to input a string with the specified prompt message.
	 * Confirmation of the input can be toggled with the confirm parameter.
	 * 	@param prompt - the prompt message to display
	 * 	@param confirm - a boolean indicating whether to confirm the input
	 * 	@return the string inputted by the user
	 */
	public String inputOriginalString(String prompt, boolean confirm) {
		boolean isStringInputted = false;
		String stringInput = null;
		while (!isStringInputted) {
			System.out.println(prompt);
			stringInput = scanner.nextLine();
			// If we need to confirm
			if (confirm) {
				if (confirmInput(stringInput, scanner)) {
					isStringInputted = true;
				}
			}
			// If no need for confirmation
			if (!confirm) {
				isStringInputted = true;

			}
		}
		logger.info(String.format("String inputted, returned: {}.Prompt: {}", stringInput,prompt));
		return stringInput;
	}
	
	/**
	 * Opens a color chooser dialog for the user to choose a color.
	 * @param prompt - the prompt message to display
	 * @param displayedColor - the color initially displayed in the color chooser dialog
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
