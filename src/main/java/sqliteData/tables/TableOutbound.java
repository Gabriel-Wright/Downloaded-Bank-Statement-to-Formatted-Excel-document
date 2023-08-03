package sqliteData.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import sqliteData.Database;
/**
 * The TableOutbound class represents an outbound table in a SQLite database. It
 * extends the abstract Table class and provides functionality specific to the
 * outbound table. The Outbound table stores information related to outbound
 * transactions.
 * 
 * @see Table
 * @see Database
 * @author LORD GABRIEL
 */

public class TableOutbound extends Table{
	
	/*
	 * ============= CONSTRUCTORS ================
	 */
	/**
	 * Constructs a new TableOutbound object with the specified Database.
	 *
	 * @param DB - the Database object associated with the table
	 */

	public TableOutbound(Database DB) {
		setDB(DB);
		setTableName("Outbound");
		setHeaders(outboundHeaders());
	}
	
	/*
	 * =========== ABSTRACT IMPLEMENTATION ============
	 */
	
	/**
	 * Creates the Outbound table in the database. The table will be created only if
	 * it does not already exist. The table schema includes columns for ID, Date,
	 * trType, RawDescription, ProcessDescription, Category, Paid_Out, and Balance.
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
			String log = String.format("Created Table: %s", getTableName());
			logger.info(log);
		} catch (SQLException e) {
			String log = String.format("Failed to create table: %s. %s", getTableName(), e.getMessage());
			logger.error(log);
		}
	}
	
	
	/*
	 * ================== METHODS =================
	 */
	
    /**
     * Returns a list of column headers for the Outbound table.
     *
     * @return Column headers to be found within Outbound table in .db file.
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
