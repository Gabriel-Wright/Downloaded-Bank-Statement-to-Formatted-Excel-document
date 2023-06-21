package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class TableInbound extends Table {

	/*
	 * ============= CONSTRUCTORS ================
	 */

	public TableInbound(Database DB) {
		setDB(DB);
		setTableName("Inbound");
		setHeaders(inboundHeaders());
	}

	/*
	 * =========== ABSTRACT IMPLEMENTATION ============
	 */

	// create a new Inbound Table. Will not update if already exists.
	public void createTable() {
		String addTable = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (\n" + "ID text PRIMARY KEY, \n"
				+ "Date text NOT NULL, \n" + "trType text, \n" + "RawDescription text NOT NULL, \n"
				+ "ProcessDescription text NOT NULL, \n" + "Category text NOT NULL, \n" + "Paid_In REAL NOT NULL,"
				+ "Balance REAL NOT NULL);";

		try (Connection conn = DriverManager.getConnection(getDB().getUrl()); Statement stmt = conn.createStatement()) {
			stmt.execute(addTable);
			logger.info("Created Table:" + getTableName());
		} catch (SQLException e) {
			logger.error("Failed to create table:" + getTableName() + "," + e.getMessage());
		}
	}

	/*
	 * ================== METHODS =================
	 */
	
	public List<String> inboundHeaders() {
		List<String> head = new ArrayList<>();
		head.add("ID");
		head.add("Date");
		head.add("trType");
		head.add("RawDescription");
		head.add("ProcessDescription");
		head.add("Category");
		head.add("Paid_In");
		head.add("Balance");

		return head;
	}
}
