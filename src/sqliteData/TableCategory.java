package sqliteData;

import optionMenu.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class TableCategory extends Table {
	
	
	
	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public TableCategory(Database DB) {
		setDB(DB);
		setTableName("Category");
		setHeaders(categoryHeaders());
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
