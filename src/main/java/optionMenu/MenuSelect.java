package optionMenu;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The MenuSelect class represents a menu selection mechanism for choosing
 * options from a menu. It interacts with the Input and Menu classes to handle
 * user input and provide menu selection functionality. MenuSelect allows the
 * user to choose an option from a given menu by displaying the menu options,
 * prompting for input, and validating the chosen option. It also supports
 * optional confirmation of the selected option. MenuSelect relies on the Input
 * and Menu classes for input handling and menu information.
 * 
 * @see Input
 * @see Menu
 * @author LORD GABRIEL
 */

public class MenuSelect {

	private Input input;
	protected static final Logger logger = LogManager.getLogger(MenuSelect.class.getName());
	private Menu menu;
	/*
	 * ============= CONSTRUCTORS ================
	 */

	/**
	 * Constructs a MenuSelect object with the specified Menu.
	 * 
	 * @param menu - the Menu object associated with the menu selection
	 */

	public MenuSelect(Menu menu) {
		this.menu = menu;
		this.input = menu.getInput();
	}

	/*
	 * =================GETTERS====================
	 */

	/**
	 * Retrieves the Menu associated with the MenuSelect.
	 * 
	 * @return the Menu object
	 */

	public Menu getMenu() {
		return menu;
	}

	/**
	 * Retrieves the Input associated with the MenuSelect.
	 * 
	 * @return the Input object
	 */

	public Input getInput() {
		return input;
	}

	/*
	 * =========================== METHODS ===========================
	 */

	/**
	 * Prompts the user to choose an option from the menu. This will set the values
	 * of choice and choiceIndex within the menu object, to the user's chosen
	 * option. 
	 * 
	 * NOTE: This will not set the commandChoice object of the chosen option.
	 * 
	 * @param prompt  - the prompt message to display
	 * @param confirm - a boolean flag indicating whether to confirm the selected
	 *                option
	 * 
	 */

	public void chooseFromMenu(String prompt, boolean confirm) {
		Scanner scanner = input.getScanner(); // getting scanner from Input object that belongs to Menu.
		boolean isChoiceMade = false;
		int selectOptionIndex = -1;
		while (!isChoiceMade) { // checks whether choice made from Options yet
			selectOptionIndex = -1;
			menu.printOptions();
			System.out.println(prompt);
			try {
				selectOptionIndex = input.inputInteger("", false); // confirm toggle on int is completed later
				if (input.isValidNum(selectOptionIndex, menu.getOptions().length)) {
					// Checks whether to confirm input or not
					if (confirm) {
						if (input.confirmInput(menu.getOptions()[selectOptionIndex])) {
							isChoiceMade = true;
						}
					}
					if (!confirm) {
						isChoiceMade = true;
					}
				}
			} catch (Exception e) {
				// This will never run -- since inputInteger checks whether valid input?
				System.out.println("Invalid input format, please input a number.");
				scanner.nextLine(); // consume invalid input
			}
		}
		menu.setChoiceIndex(selectOptionIndex); // setChoiceIndex and choice within Menu object
		String[] options = menu.getOptions();
		String choice = options[selectOptionIndex];
		// Set menuChoice and commandChoice
		menu.setChoice(options[selectOptionIndex]);
		System.out.println(String.format("Chose option: %s", choice));
		String log = String.format("Choice: %s - this is option %d. Selected from Menu %s.", choice, selectOptionIndex,
				menu.getMenuName());
		logger.info(log);
	}

}
