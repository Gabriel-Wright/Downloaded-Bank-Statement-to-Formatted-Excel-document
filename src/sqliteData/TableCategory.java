package sqliteData;

import optionMenu.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class TableCategory extends Table {

	private Menu categoryMenu;
	private Input categoryInput;
	private boolean appendOrCreateConfirm;
	private boolean categoryNameConfirm;
	private boolean categoryMenuChoiceConfirm;

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public TableCategory(Database DB, Input categoryInput, boolean appendOrCreateConfirm, boolean categoryNameConfirm,
			boolean categoryMenuChoiceConfirm) {
		setDB(DB);
		this.appendOrCreateConfirm = appendOrCreateConfirm;
		this.categoryNameConfirm = categoryNameConfirm;
		this.categoryMenuChoiceConfirm = categoryMenuChoiceConfirm;
		this.categoryInput=categoryInput;
		categoryMenu = new Menu("Category", null, categoryInput);
		setHeaders(categoryHeaders());
	}

	/*
	 * ====================GETTERS===========================
	 */

	public Menu getCategoryMenu() {
		return categoryMenu;
	}

	public Input getCategoryInput() {
		return categoryInput;
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
	 * =====================SETTERS========================
	 */

	public void setCategoryMenu(Menu categoryMenu) {
		this.categoryMenu = categoryMenu;
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

	public List<String> categoryHeaders() {
		List<String> head = new ArrayList<>();
		head.add("Description");
		head.add("Category");
		return head;
	}


}
