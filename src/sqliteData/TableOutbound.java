package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TableOutbound extends Table{
	
	/*
	 * ============= CONSTRUCTORS ================
	 */
	
	public TableOutbound(Database DB) {
		setDB(DB);
		setTableName("Outbound");
		setHeaders(outboundHeaders());
	}
	
	/*
	 * =========== ABSTRACT IMPLEMENTATION ============
	 */
	public void createTable() {
		String addTable = "CREATE TABLE IF NOT EXISTS "+ getTableName() +" (\n"
				+"ID text PRIMARY KEY, \n"
				+"Date text NOT NULL, \n"
				+"trType text, \n"
				+"RawDescription text NOT NULL, \n"
				+"ProcessDescription text NOT NULL, \n"
				+"Category text NOT NULL, \n"
				+"Paid_Out REAL NOT NULL,"
				+"Balance REAL NOT NULL);";
					
		try(Connection conn = DriverManager.getConnection(getDB().getUrl());
					Statement stmt = conn.createStatement()) {
			stmt.execute(addTable);
			logger.info("Created Table:" + getTableName());
		} catch (SQLException e) {
			logger.error("Failed to create table:" + getTableName() + "," + e.getMessage());
		}
	}
	
	
	/*
	 * ================== METHODS =================
	 */
	public List<String> outboundHeaders() {
		List<String> head = new ArrayList<>();
		head.add("ID");
		head.add("Date");
		head.add("trType");
		head.add("RawDescription");
		head.add("ProcessDescription");
		head.add("Category");
		head.add("Paid_Out");
		head.add("Balance");

		return head;
	}
}
