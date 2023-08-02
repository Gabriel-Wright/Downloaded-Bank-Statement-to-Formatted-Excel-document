package sqliteData.tables.writers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.core.net.TcpSocketManager;

import optionMenu.*;
import sqliteData.tables.TableCategory;

/**
 * 
 * The TableCategoryWriter class extends the TableCategory class and provides
 * additional functionality to write and insert new entries into the Category
 * table of an SQLite database. It includes methods to prompt the user for
 * category inputs, assign categories to transactions, and insert new
 * description-category pairs into the table. It relies on the TableCategory
 * class for database connection and menu options. The TableCategoryWriter class
 * is designed to be used in conjunction with the TableCategory class to provide
 * comprehensive category writing and insertion functionality.
 *
 * Note: The TableCategoryWriter class assumes that the necessary tables and
 * connections have been set up prior to use.
 * 
 * @see TableCategory
 * @see optionMenu.Menu
 * @see optionMenu.MenuSelect
 * @see optionMenu.Input
 * @author LORD GABRIEL
 */

public class TableCategoryWriter extends TableCategory {

	/**
	 * MenuSelect option use to take user input for assigning categories to
	 * transactions
	 */
	private MenuSelect categoryMenuSelect;

	/**
	 * Boolean variable used to determine whether there should be an extra
	 * confirmation step required when choosing whether to append a transaction to
	 * an already existing category, or create a new one.
	 */
	private boolean appendOrCreateConfirm;
	/**
	 * Boolean variable used to determine whether there should be an extra
	 * confirmation step required inputting a new Category Confirm.
	 */
	private boolean categoryNameConfirm;
	/**
	 * Boolean variable used to determine whether there should be an extra
	 * confirmation step when selecting an option from the Category menu.
	 */
	private boolean categoryMenuChoiceConfirm;

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */
	/**
	 * Constructs a new TableCategoryWriter object with the specified TableCategory,
	 * MenuSelect, and confirmation settings.
	 * 
	 * @param tC                        - the TableCategory object associated with
	 *                                  the writer
	 * @param categoryMenuSelect        - the MenuSelect object used for category
	 *                                  menu selection
	 * @param appendOrCreateConfirm     - a boolean indicating whether to confirm
	 *                                  appending
	 * @param categoryNameConfirm       - a boolean indicating whether to confirm
	 *                                  the category name input
	 * @param categoryMenuChoiceConfirm - a boolean indicating whether to confirm
	 *                                  the category menu choice
	 */
	public TableCategoryWriter(TableCategory tC, MenuSelect categoryMenuSelect, boolean appendOrCreateConfirm,
			boolean categoryNameConfirm, boolean categoryMenuChoiceConfirm) {
		super(tC.getDB(), tC.getCategoryMenu());
		this.categoryMenuSelect = categoryMenuSelect;
		this.appendOrCreateConfirm = appendOrCreateConfirm;
		this.categoryNameConfirm = categoryNameConfirm;
		this.categoryMenuChoiceConfirm = categoryMenuChoiceConfirm;
	}

	/*
	 * ================== GETTERS =================
	 */

	/**
	 * Getter method which returns the MenuSelect object associated with the
	 * category menu.
	 * 
	 * @return the MenuSelect object
	 */

	public MenuSelect getCategoryMenuSelect() {
		return categoryMenuSelect;
	}

	/**
	 * Getter method which returns the value of the appendOrCreateConfirm flag.
	 * 
	 * @return true if confirmation is required for appending or creating, false
	 *         otherwise
	 */

	public boolean getAppendOrCreateConfirm() {
		return appendOrCreateConfirm;
	}

	/**
	 * Getter method which returns the value of the categoryNameConfirm flag.
	 * 
	 * @return true if confirmation is required for category name input, false
	 *         otherwise
	 */
	public boolean getCategoryNameConfirm() {
		return categoryNameConfirm;
	}

	/**
	 * Getter method which returns the value of the categoryMenuChoiceConfirm flag
	 * 
	 * @return true if confirmation is required when selecting options from category
	 *         menu, false otherwise
	 */

	public boolean getCategoryMenuChoiceConfirm() {
		return categoryMenuChoiceConfirm;
	}

	/*
	 * ================== SETTERS ==============
	 */

	/**
	 * Sets the MenuSelect object associated with the category menu.
	 */

	public void setCategoryMenuSelect(MenuSelect categoryMenuSelect) {
		this.categoryMenuSelect = categoryMenuSelect;
	}

	/*
	 * ================== METHODS =================
	 */

	/**
	 * Prompts the user to input a new category for a transaction description.
	 * 
	 * @param description - the description of the transaction
	 * @param inOrOut     - a string indicating whether the transaction is incoming
	 *                    or outgoing
	 * @return the user-inputted category name
	 */
	public String createNewCategory(String description, String inOrOut) {
		String prompt = String.format("Please enter a category name for the %s transaction, described as: %s", inOrOut,
				description);
		Menu categoryMenu = getCategoryMenu();
		return categoryMenu.getInput().inputOriginalString(prompt, getCategoryNameConfirm());
	}

	/**
	 * Prompts the user to either assign a category to a given transaction
	 * description or assign it to a category that already exists.
	 * 
	 * @param description - the description of the transaction
	 * @param inOrOut     - a string indicating whether the transaction is incoming
	 *                    or outgoing
	 * @return the selected category name
	 */
	public String assignCategoryToTransaction(String description, String inOrOut) {
		refreshCategoryOptions();

		if (getCategoryMenu().getOptions().length == 0) {
			// If no categories have been assigned yet, forced to create a first category.
			return createNewCategory(description, inOrOut);
		} else {
			return chooseExistingOrCreateNewCategory(description, inOrOut);
		}
	}

	/**
	 * Prompts the user to choose an existing category or create a new one for the
	 * given transaction description.
	 * 
	 * @param description - the description of the item
	 * @param inOrOut     - a string indicating whether the transaction is incoming
	 *                    or outgoing
	 * @return the selected category name
	 */
	public String chooseExistingOrCreateNewCategory(String description, String inOrOut) {
		// Printing out existing category options to assign description to.
		String introPrompt = String.format(
				"Please input an appropriate category for the following %s transaction described as: %s", inOrOut,
				description);
		System.out.println(introPrompt);
		System.out.println("Either append this item to an already existing category or create a new one");
		getCategoryMenu().printOptions();

		// Creating appendOrCreate menu - users can choose to assign a new item to an
		// already existing category, or create a new one.
		Input categoryInput = categoryMenuSelect.getInput();
		Menu appendOrCreateMenu = new Menu("Append or Create", new String[] { "Append", "Create" }, categoryInput);
		MenuSelect appendOrCreateSelect = new MenuSelect(appendOrCreateMenu);
		appendOrCreateSelect.chooseFromMenu(description, getAppendOrCreateConfirm());

		if (appendOrCreateMenu.getChoice().equalsIgnoreCase("Append")) {
			return chooseCategoryFromMenu(description);
		} else if (appendOrCreateMenu.getChoice().equalsIgnoreCase("Create")) {
			return createNewCategory(description, inOrOut);
		}

		return null;
	}

	/**
	 * Prompts the user to choose a category from the menu for the given transaction
	 * description.
	 * 
	 * @param description - the description of the transaction
	 * @return the selected category name
	 */
	public String chooseCategoryFromMenu(String description) {
		categoryMenuSelect.chooseFromMenu(description, getCategoryMenuChoiceConfirm());
		return getCategoryMenu().getChoice();
	}

	/**
	 * Inserts a new description,category pair into the table.
	 * 
	 * @param description - the description of the transaction
	 * @param category    - the category name to insert
	 */
	public void insertEntry(String description, String category) {
		String insert = "INSERT OR IGNORE INTO " + getTableName() + " (Description, Category) \n" + "VALUES(?, ?);";
		try (Connection conn = DriverManager.getConnection(getDB().getUrl());
				PreparedStatement stmt = conn.prepareStatement(insert)) {
			stmt.setString(1, description);
			stmt.setString(2, category);
			stmt.executeUpdate();
			String log = String.format("Entry - description: %s & category: %s appended to: %s", description, category,
					getTableName());
			logger.info(log);
		} catch (SQLException e) {
			String log = String.format(
					"Error when attempting to insert entry - description: %s & category;%s appended to: %s",
					description, category, getTableName());
			logger.error(log);
		}
	}

}
