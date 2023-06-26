package sqliteData;

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


public abstract class Table {

	private String tableName;
	private Database DB;
	private List<String> headers;
	protected static final Logger logger = LogManager.getLogger(Table.class.getName());
	
	/*
	 * =========================== GETTERS ============================
	 */

	public String getTableName() {
		return tableName;
	}

	public Database getDB() {
		return DB;
	}

	public List<String> getHeaders() {
		return headers;
	}
	
	public Logger getLogger() {
		return logger;
	}

	/*
	 * =========================== SETTERS ============================
	 */

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setDB(Database DB) {
		this.DB=DB;
	}
	/*
	 * =========================== METHODS ===========================
	 */
	
	//createTable within SQL DB
	abstract public void createTable(); 

	//deleteTable within SQL DB
	public void deleteTable() { 
		String deletion = "DROP TABLE IF EXISTS "+tableName+";";
		 try (Connection connection = DriverManager.getConnection(DB.getUrl());
	             Statement stmt = connection.createStatement()) {
			 stmt.execute(deletion);
			 logger.info("Table:"+tableName+"deleted");
		 }
		 catch (SQLException e) {
			 logger.error("Table:"+tableName+"failed to be deleted," +e.getMessage());
		 }
	}
	
	//replaces table object's designated tableName if it exists.
	public void replaceTable() {
			this.deleteTable();
			this.createTable();
		}
	
	//check designated tableColumns - this is used for comparing later
	public List<String> tableColumns() {
		String checkColumns	 = "SELECT * FROM "+tableName+" LIMIT 1";
		List<String> columns = new ArrayList<>();
		//collect  metadata from this query
		try(Connection conn 		= DriverManager.getConnection(DB.getUrl());
			Statement stmt 			= conn.createStatement();
			ResultSet rs 			= stmt.executeQuery(checkColumns);) {
			
			ResultSetMetaData rsmd 	= rs.getMetaData(); //Column metadata
			int columnCount 		= rsmd.getColumnCount(); //get number of columns
			for(int i =1;i<=columnCount;i++) {
				String columnName = rsmd.getColumnName(i); //Adds column names
				columns.add(columnName);
			}
			logger.info("Columns for table"+tableName+":"+columns.toString());
		} catch (SQLException e){
			logger.error("Failed to check columns,"+e.getMessage());
		}
		return columns;
	}
	
}
