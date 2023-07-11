package sqliteData;

import optionMenu.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 * The TableCategory class represents the category table within an SQLite
 * database. It extends the abstract Table class and provides functionality
 * specific to the Category table. The Category table stores information related
 * to how transactions should be categorized. The processed name of a
 * transaction is stored alongside its associated category. This can be read in
 * order to categorize that transaction if it occurs again.
 * 
 * It also includes a categoryMenu object used to choose between category
 * options and to assign categories to transactions for a TableCategory object.
 * 
 * The category table contains columns for Description and Category.
 * 
 * @author LORD GABRIEL
 */

public class TableCategory extends Table {

	/**
	 * Menu object used to choose between category options and to assign categories
	 * to transactions for a TableCategory object.
	 */
	private Menu categoryMenu;

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	/**
	 * Constructs a new TableCategory object with the specified Database and
	 * categoryMenu.
	 * 
	 * @param DB           - the Database object associated with the table
	 * @param categoryMenu - the Menu object used for category selection
	 */

	public TableCategory(Database DB, Menu categoryMenu) {
		setDB(DB);
		setTableName("Category");
		setHeaders(categoryHeaders());
		setCategoryMenu(categoryMenu);
	}

	/**
	 * Constructs a new TableCategory object with the specified Database and
	 * categoryInput. A Menu object is created based on the categoryInput to handle
	 * category selection.
	 * 
	 * @param DB            - the Database object associated with the table
	 * @param categoryInput - the Input object used for category selection
	 */

	public TableCategory(Database DB, Input categoryInput) {
		setDB(DB);
		setTableName("Category");
		setHeaders(categoryHeaders());
		setCategoryMenu(new Menu("CategoryMenu", null, categoryInput));
	}

	/*
	 * ===================GETTERS=================
	 */
	/**
	 * Getter method to return TableCategory's categoryMenu.
	 * 
	 * @return categoryMenu of TableCategory object.
	 */
	public Menu getCategoryMenu() {
		return categoryMenu;
	}

	/*
	 * ===================GETTERS=================
	 */
	/**
	 * Sets the categoryMenu of the TableCategory object.
	 * 
	 * @param categoryMenu the categoryMenu object to set
	 */

	public void setCategoryMenu(Menu categoryMenu) {
		this.categoryMenu = categoryMenu;
	}

	/*
	 * =========== ABSTRACT IMPLEMENTATION ============
	 */

	/**
	 * Creates the Category table in the database. The table will be created only if
	 * it does not already exist. The table schema includes columns for Description
	 * and Category.
	 */

	public void createTable() {
		String addTable = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (\n" + "Description text PRIMARY KEY,\n"
				+ "Category text NOT NULL);";

		try (Connection conn = DriverManager.getConnection(getDB().getUrl()); Statement stmt = conn.createStatement()) {
			stmt.execute(addTable);
			String log = String.format("%s table created.", getTableName());
			logger.info(log);
		} catch (SQLException e) {
			String log = String.format("Error when creating %s table.", getTableName());
			logger.error(log);
		}
	}

	/*
	 * ================== METHODS =================
	 */

	/**
	 * Refreshes the stored category options for the categoryMenu. The options are
	 * retrieved from the Category table in the database.
	 */
	public void refreshCategoryOptions() {
		// Initialise a List so that we do not need to know size of array.
		List<String> categories = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(getDB().getUrl());
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT Category FROM Category;")) {
			// read all results
			while (resultSet.next()) {
				String category = resultSet.getString("Category");
				categories.add(category);
			}

		} catch (SQLException e) {
			String log = String.format("Error when attempting to refresh categoryOptions for %s table.",
					getTableName());
			logger.error("Error when attempting to refresh categoryOptions for " + getTableName() + "table.");
		}

		String[] options = categories.toArray(new String[categories.size()]);
		String log = String.format("Set options for categoryMenu:%s.", String.join(",", options));
		logger.info(log);
		getCategoryMenu().setOptions(options);
	}

	/**
	 * Retrieves the headers for the Category table. The headers include Description
	 * and Category.
	 * 
	 * @return a list of column headers from the category table within database.
	 */
	public List<String> categoryHeaders() {
		List<String> head = new ArrayList<>();
		head.add("Description");
		head.add("Category");
		return head;
	}

}
