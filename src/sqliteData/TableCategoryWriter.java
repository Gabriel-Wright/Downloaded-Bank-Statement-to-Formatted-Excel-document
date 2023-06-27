package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.core.net.TcpSocketManager;

import optionMenu.*;

public class TableCategoryWriter extends TableCategory {

	private MenuSelect categoryMenuSelect;
	private boolean appendOrCreateConfirm;
	private boolean categoryNameConfirm;
	private boolean categoryMenuChoiceConfirm;

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	public TableCategoryWriter(TableCategory tC, MenuSelect categoryMenuSelect, boolean appendOrCreateConfirm,
			boolean categoryNameConfirm, boolean categoryMenuChoiceConfirm) {
		super(tC.getDB(),tC.getCategoryMenu());
		this.categoryMenuSelect = categoryMenuSelect;
		this.appendOrCreateConfirm = appendOrCreateConfirm;
		this.categoryNameConfirm = categoryNameConfirm;
		this.categoryMenuChoiceConfirm = categoryMenuChoiceConfirm;
	}

	/*
	 * ================== GETTERS =================
	 */

	public MenuSelect getCategoryMenuSelect() {
		return categoryMenuSelect;
	}

	public boolean getAppendOrCreateConfirm() {
		return appendOrCreateConfirm;
	}

	public boolean getCategoryNameConfirm() {
		return categoryNameConfirm;
	}

	public boolean getCategoryMenuChoiceConfirm() {
		return categoryMenuChoiceConfirm;
	}

	/*
	 * ================== SETTERS ==============
	 */
	
	public void setCategoryMenuSelect(MenuSelect categoryMenuSelect) {
		this.categoryMenuSelect=categoryMenuSelect;
	}
		
	/*
	 * ================== METHODS =================
	 */

	// Prompts user to input a new category for item - description
	public String createNewCategory(String description, String inOrOut) {
		String prompt = "Please enter a new category name for the " + inOrOut + " transaction, referenced as: "
				+ description;
		Menu categoryMenu = getCategoryMenu();
		return categoryMenu.getInput().inputOriginalString(prompt, getCategoryNameConfirm());
	}

	// Prompts user to either assign a category to a given item -description, or
	// assign it to a category that already exists
	public String assignCategoryToTransaction(String description, String inOrOut) {
		refreshCategoryOptions();

		if (getCategoryMenu().getOptions().length == 0) {
			// If no categories have been assigned yet, forced to create a first category.
			return createNewCategory(description, inOrOut);
		} else {
			return chooseExistingOrCreateNewCategory(description, inOrOut);
		}
	}

	public String chooseExistingOrCreateNewCategory(String description, String inOrOut) {
		// Printing out existing category options to assign description to.
		System.out.println("Please input an appropriate category for the following " + inOrOut
				+ " transaction described as - " + description);
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

	public String chooseCategoryFromMenu(String description) {
		categoryMenuSelect.chooseFromMenu(description, getCategoryMenuChoiceConfirm());
		return getCategoryMenu().getChoice();
	}

	// Inserts new description,category pair into table.
	// prepared statements remove SQL injection issue
	public void insertEntry(String description, String category) {
		String insert = "INSERT OR IGNORE INTO " + getTableName() + " (Description, Category) \n" + "VALUES(?, ?);";
		try (Connection conn = DriverManager.getConnection(getDB().getUrl());
				PreparedStatement stmt = conn.prepareStatement(insert)) {
			stmt.setString(1, description);
			stmt.setString(2, category);
			stmt.executeUpdate();
			logger.info(
					"Entry - description:" + description + "& category:" + category + " appended to " + getTableName());
		} catch (SQLException e) {
			logger.error("Error when attempting to insert entry- description:" + description + "& category:" + category
					+ "to categoryTable");
		}
	}

}
