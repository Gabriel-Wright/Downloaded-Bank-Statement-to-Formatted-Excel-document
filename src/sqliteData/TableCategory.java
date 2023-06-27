package sqliteData;

import optionMenu.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class TableCategory extends Table {
	
	private Menu categoryMenu;
	
	
	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public TableCategory(Database DB, Menu categoryMenu) {
		setDB(DB);
		setTableName("Category");
		setHeaders(categoryHeaders());
		setCategoryMenu(categoryMenu);
	}
	
	public TableCategory(Database DB, Input categoryInput) {
		setDB(DB);
		setTableName("Category");
		setHeaders(categoryHeaders());
		setCategoryMenu(new Menu("CategoryMenu",null, categoryInput));
	}
	

	/*
	 * ===================GETTERS=================
	 */
	
	public Menu getCategoryMenu() {
		return categoryMenu;
	}
	
	/*
	 * ===================GETTERS=================
	 */
	
	public void setCategoryMenu(Menu categoryMenu) {
		this.categoryMenu=categoryMenu;
	}
	
	/*
	 * =========== ABSTRACT IMPLEMENTATION ============
	 */

	public void createTable() {
		String addTable = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (\n" + "Description text PRIMARY KEY,\n"
				+ "Category text NOT NULL);";

		try (Connection conn = DriverManager.getConnection(getDB().getUrl()); Statement stmt = conn.createStatement()) {
			stmt.execute(addTable);
			logger.info(getTableName() + " table created.");
		} catch (SQLException e) {
			logger.error("Error when creating " + getTableName() + " table.");
		}
	}

	/*
	 * ================== METHODS =================
	 */

	// Refresh stored categoryOptions for categoryMenu.
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
			logger.error("Error when attempting to refresh categoryOptions for " + getTableName() + "table.");
		}

		String[] options = categories.toArray(new String[categories.size()]);
		logger.info("Set options for categoryMenu:" + String.join(", ", options));
		getCategoryMenu().setOptions(options);
	}

	public List<String> categoryHeaders() {
		List<String> head = new ArrayList<>();
		head.add("Description");
		head.add("Category");
		return head;
	}


}
