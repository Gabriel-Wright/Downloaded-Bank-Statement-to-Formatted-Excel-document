package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCategoryWriter extends TableCategory {

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	public TableCategoryWriter(TableCategory tC) {
		super(tC.getDB());
	}

	/*
	 * ================== METHODS =================
	 */

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
