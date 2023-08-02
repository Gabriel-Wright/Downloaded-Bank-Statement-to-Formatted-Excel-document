package optionMenu;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sqliteData.Database;

/**
 * The Menu class represents a menu with a set of options for user selection. It
 * provides methods to set and retrieve the menu name, options, user choice,
 * choice index, and input source. Additionally, it includes a method to print
 * the available options to the console.
 * 
 * The Menu class is designed to be used in conjunction with the Input class to
 * create interactive menu systems.
 * 
 * @see Input
 * @author LORD GABRIEL
 */

public class Menu {

	private String menuName;
	private Map<String, Command> commandMap;
	private String[] options;
	private String choice;
	private int choiceIndex;
	private Command commandChoice;
	private Input input;

	/*
	 * ============= CONSTRUCTORS ================
	 */

	/**
	 * Constructs a new Menu object with the specified menu name, options, and input
	 * source.
	 *
	 * @param menuName - the name of the menu
	 * @param options  - an array of menu options
	 * @param input    - the input source for the menu
	 */

	public Menu(String menuName, String[] options, Input input) {
		this.menuName=menuName;
		this.options=options;
		this.input=input;
	}
	
	
	public Menu(String menuName, Map<String, Command> commandMap, Input input) {
		this.menuName = menuName;
		this.commandMap = commandMap;
		this.input = input;
	}

	/*
	 * =========================== GETTERS ============================
	 */

	/**
	 * Returns the name of the menu.
	 *
	 * @return the menu name
	 */

	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * Returns the options of the menu.
	 *
	 * @return an array of menu options
	 */

	public String[] getOptions() {
		return options;
	}

	/**
	 * Return's the command map of this menu object, i.e. the Map of String (option)
	 * to potential command output
	 * 
	 * @return a map <String, Command> associated with this Menu object
	 */

	public Map<String, Command> getCommandMap() {
		return commandMap;
	}

	/**
	 * Returns the user's choice from the menu.
	 *
	 * @return the user's choice
	 */

	public String getChoice() {
		return choice;
	}

	/**
	 * Returns the index of the user's choice in the menu options.
	 *
	 * @return the index of the user's choice
	 */

	public int getChoiceIndex() {
		return choiceIndex;
	}

	/**
	 * Returns the command from the user's choice in the menu
	 * 
	 * @return Command object from user's choice in menu
	 */

	public Command getCommandChoice() {
		return commandChoice;
	}

	/**
	 * Returns the input source for the menu.
	 *
	 * @return the input source
	 */

	public Input getInput() {
		return input;
	}

	/*
	 * =========================== SETTERS ============================
	 */

	/**
	 * Sets the name of the menu.
	 *
	 * @param name - the menu name to set
	 */

	public void setMenuName(String name) {
		this.menuName = name;
	}

	/**
	 * Sets the options of the menu.
	 *
	 * @param options - an array of menu options to set
	 */

	public void setOptions(String[] options) {
		this.options = options;
	}

	/**
	 * Sets the command map of the menu
	 * 
	 * @param commandMap - a <String,Command> map of options for the menu
	 */
	public void setCommandMap(Map<String, Command> commandMap) {
		this.commandMap = commandMap;
	}

	/**
	 * Sets the user's choice in the menu.
	 *
	 * @param choice - the user's choice to set
	 */

	public void setChoice(String choice) {
		this.choice = choice;
	}

	/**
	 * Sets the index of the user's choice in the menu options.
	 *
	 * @param choiceIndex - the index of the user's choice to set
	 */

	public void setChoiceIndex(int choiceIndex) {
		this.choiceIndex = choiceIndex;
	}

	/**
	 * Sets the commandChoice, the Command object associated with the user's choice
	 * in the menu.
	 * 
	 * @param commandChoice - Command object associated with a particular option.
	 */
	public void setCommandChoice(Command commandChoice) {
		this.commandChoice = commandChoice;
	}

	/**
	 * Sets the input source for the menu.
	 *
	 * @param input - the input source to set
	 */

	public void setInput(Input input) {
		this.input = input;
	}
	/*
	 * =========================== METHODS ===========================
	 */

	
	/**
	 * Sets the String array of options from this Menu object's commandMap
	 * This method should be called before using {@link #printOptions()} or
     * {@link #chooseFromMenu(String, boolean)}.
	 */
	public void loadOptions() {
		String[] tempOptions = (String[]) commandMap.keySet().toArray(new String[0]);
		setOptions(tempOptions);
	}
	
	/**
	 * Loads the commandChoice from the currently selected choice.
	 * This method should be called after {@link #chooseFromMenu(String, boolean)} to
     * get the associated command with the user's choice.
	 */
	
	public void loadCommandChoice() {
		setCommandChoice(commandMap.get(choice));
	}
	
	/**
	 * Prints the available options of the menu to the console.
	 */

	public void printOptions() {
		int numOptions = options.length;
		System.out.println("\nPlease see the following options for the " + menuName + " menu:");
		for (int i = 0; i < numOptions; i++) {
			System.out.println("[" + i + "] - " + options[i]);
		}
	}

}
