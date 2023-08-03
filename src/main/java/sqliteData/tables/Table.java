package sqliteData.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sqliteData.Database;

/**
 * Table objects represent the tables found within .db files. A DB is passed
 * into a Table, through which all connections to the physical .db files are
 * handled.
 * 
 * @see Database
 * @author LORD GABRIEL
 *
 */
public abstract class Table {

	/**
	 * The public name of a Table
	 */
	private String tableName;
	/**
	 * Database object, enables connection to the .db file where tables can be
	 * saved.
	 */
	private Database DB;
	/**
	 * Headers of columns for tables found within .db files.
	 */
	private List<String> headers;
	protected static final Logger logger = LogManager.getLogger(Table.class.getName());

	/*
	 * =========================== GETTERS ============================
	 */

	/**
	 * <p>
	 * Getter method to return Table object's tableName
	 * </p>
	 * 
	 * @return the name of table found within .db file.
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * <p>
	 * Getter method for Database object, which enables all connection to .db file
	 * where tables are saved.
	 * </p>
	 * 
	 * @return the database object, through which connections to .db file are
	 *         possible.
	 */
	public Database getDB() {
		return DB;
	}

	/**
	 * <p>
	 * Getter method to return the headers of columns for tables found within .db
	 * files.
	 * </p>
	 * 
	 * @return
	 */
	public List<String> getHeaders() {
		return headers;
	}

	public Logger getLogger() {
		return logger;
	}

	/*
	 * =========================== SETTERS ============================
	 */

	/**
	 * <p>
	 * Setter method to assign Table object's header values.
	 * </p>
	 * 
	 * @param headers - Column headers to be assigned
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	/**
	 * <p>
	 * Setter method to assign Table object's tableName.
	 * </p>
	 * 
	 * @param tableName - public name of a Table within Table object.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * <p>
	 * Setter method to assign Table object's Database connection.
	 * </p>
	 * 
	 * @param DB - Database object through which connection to physical .db file is
	 *           possible.
	 */
	public void setDB(Database DB) {
		this.DB = DB;
	}
	/*
	 * =========================== METHODS ===========================
	 */
	

	/**
	 * <p>
	 * Creates an empty table within .db file from DB class variable.
	 * </p>
	 */
	abstract public void createTable();

	/**
	 * <p>
	 * Deletes a Table of name: tableName within .db file from DB class variable.
	 * </p>
	 */
	public void deleteTable() {
		String deletion = String.format("DROP TABLE IF EXISTS %s;", tableName);
		try (Connection connection = DriverManager.getConnection(DB.getUrl());
				Statement stmt = connection.createStatement()) {
			stmt.execute(deletion);
			logger.info("Table:" + tableName + "deleted");
		} catch (SQLException e) {
			logger.error("Table:" + tableName + "failed to be deleted," + e.getMessage());
		}
	}

	// replaces table object's designated tableName if it exists.
	/**
	 * <p>
	 * Replaces a Table of name: tableName within .db file from DB class variable,
	 * if this Table exists.
	 * </p>
	 */
	public void replaceTable() {
		this.deleteTable();
		this.createTable();
	}

	/**
	 * <p>
	 * Returns the column headers of table:"tableName" within .db file from DB class
	 * variable.
	 * </p>
	 * 
	 * @return a list of Strings, each representing one of the headers of a tables
	 *         columns within .db file for table:tableName.
	 */
	// check designated tableColumns - this is used for comparing later
	public List<String> tableColumns() {
		String checkColumns = "SELECT * FROM " + tableName + " LIMIT 1";
		List<String> columns = new ArrayList<>();
		// collect metadata from this query
		try (Connection conn = DriverManager.getConnection(DB.getUrl());
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(checkColumns);) {

			ResultSetMetaData rsmd = rs.getMetaData(); // Column metadata
			int columnCount = rsmd.getColumnCount(); // get number of columns
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i); // Adds column names
				columns.add(columnName);
			}
			logger.info("Columns for table" + tableName + ":" + columns.toString());
		} catch (SQLException e) {
			logger.error("Failed to check columns," + e.getMessage());
		}
		return columns;
	}

}
