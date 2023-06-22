package sqliteData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	private String dbName; // name of db to call or create
	private String filePath; // file location
	private String url; // combined url for connecting to sqlite3
	private static final Logger logger = LogManager.getLogger(Database.class.getName());

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	// If no filePath is given - DB is created within the program's working
	// directory.
	public Database(String dbName) {
		this.dbName = dbName;
		this.filePath = Paths.get("").toAbsolutePath().toString();
		this.url = "jdbc:sqlite:" + this.filePath + "\\" + this.dbName + ".db";
	}

	public Database(String dbName, String filePath) {
		this.dbName = dbName;
		this.filePath = filePath;
		this.url = "jdbc:sqlite:" + this.filePath + "\\" + this.dbName + ".db";
	}

	/*
	 * =========================== GETTERS ============================
	 */

	public String getDbName() {
		return this.dbName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public String getUrl() {
		return this.url;
	}

	/*
	 * =========================== METHODS ===========================
	 */

	// checks whether DB exists
	public boolean checkDB() {
		File file = new File(this.filePath + "\\" + this.dbName + ".db");
		return file.exists();
	}

	public void loadDB() {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				logger.info("Driver name: {}", meta.getDriverName());
				logger.info("A new DB has been created.");
			}
		} catch (SQLException e) {
			logger.error("Failed to load DB", e);
		}
	}

	public void deleteDB() {
		String filePath = this.filePath + "\\" + this.dbName + ".db";
		Path path = Paths.get(filePath);
		try {
			Files.delete(path);
			logger.info("Deleted %s\n", filePath);
		} catch (IOException e) {
			logger.error("Failed to delete file:" + filePath + e.getMessage());
		}
	}

	// check whether table exists within DB
	public boolean checkTable(String tableName) {
		String query = "SELECT name FROM sqlite_master where type='table'\n" + "AND name =?";
		try (Connection connection = DriverManager.getConnection(this.url);
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, tableName);
			ResultSet resultSet = statement.executeQuery();
			boolean tableExists = resultSet.next();
			logger.info("Table:" + tableName + ", tableExists:" + tableExists);
			return tableExists; // will return true if there is something to return
		} catch (SQLException e) {

			logger.error("Failure when checking existence of table: "+tableName+".  "+ e.getMessage());

		}

		return false;

	}

}
