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

/**
 * The Database class is used to manage and interact with SQLite database
 * files.It provides methods to check the existence of a database file, connect
 * to the database, load the database file, and delete the database file. It
 * also includes methods to check the existence of tables within the database.
 * 
 * * Note: The Database class assumes that the necessary SQLite JDBC driver is
 * available.
 * 
 * @author LORD GABRIEL
 */
public class Database {
	/**
	 * The public name of a Database
	 */
	private String dbName;
	/**
	 * The filePath of the folder where the database file is saved.
	 * 
	 */
	private String filePath;
	/**
	 * The URL string used to connect to the database.
	 */
	private String url;
	private static final Logger logger = LogManager.getLogger(Database.class.getName());

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	/**
	 * <p>
	 * Constructor for database file, as just a name is passed as parameter,
	 * database function will be made at default location.
	 * </p>
	 * 
	 * @param dbName - name of the database file
	 */
	public Database(String dbName) {
		this.dbName = dbName;
		this.filePath = Paths.get("").toAbsolutePath().toString();
		this.url = String.format("jdbc:sqlite:%s\\%s.db", this.filePath, this.dbName);
	}

	/**
	 * <p>
	 * Constructor of database class, save location of database file file and name.
	 * </p>
	 * 
	 * @param dbName   - name of the database file
	 * @param filePath - location of saved database file
	 */
	public Database(String dbName, String filePath) {
		this.dbName = dbName;
		this.filePath = filePath;
		this.url = String.format("jdbc:sqlite:%s\\%s.db", this.filePath, this.dbName);
	}

	/*
	 * =========================== GETTERS ============================
	 */
	/**
	 * <p>
	 * Getter method to return Database object's database file name
	 * </p>
	 * 
	 * @return the name of the database file
	 */
	public String getDbName() {
		return this.dbName;
	}

	/**
	 * <p>
	 * Getter method to return Database object's database file path
	 * </p>
	 * 
	 * @return the file path of a database file
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * <p>
	 * Getter method to return Database object's url(), needed for database
	 * connections
	 * </p>
	 * 
	 * @return database connection url()
	 */
	public String getUrl() {
		return this.url;
	}

	/*
	 * =========================== METHODS ===========================
	 */

	/**
	 * <p>
	 * Check whether a database file already exists for this Database object
	 * </p>
	 * 
	 * @return true / false bool value for whether a database file exists at
	 *         filePath location
	 */
	public boolean checkDB() {
		String path = String.format("%s\\%s.db", filePath, dbName);
		File file = new File(path);
		return file.exists();
	}

	/**
	 * <p>
	 * Connects to database file of name dbName at filePath location. If no database
	 * file exists, one will be created
	 * </p>
	 */
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

	/**
	 * <p>
	 * Deletes database file of name dbName at filePath location.
	 * </p>
	 */
	public void deleteDB() {
		String fullPath = String.format("%s\\%s.db", filePath, dbName);
		Path path = Paths.get(fullPath);
		try {
			Files.delete(path);
			logger.info(String.format("Deleted %s\n", fullPath));
		} catch (IOException e) {
			logger.error("Failed to delete file:", fullPath, e.getMessage());
		}
	}

	/**
	 * <p>
	 * Checks whether there is a table of name: tableName within the  database file of
	 * dbName at location filePath.
	 * </p>
	 * 
	 * @param tableName - String name of table searched for within database file
	 * @return true / false bool value for whether table exists within  database file at
	 *         filePath location
	 */
	public boolean checkTable(String tableName) {
		String query = "SELECT name FROM sqlite_master where type='table'\n" + "AND name =?";
		try (Connection connection = DriverManager.getConnection(this.url);
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, tableName);
			ResultSet resultSet = statement.executeQuery();
			boolean tableExists = resultSet.next();
			String log = String.format("Table:%s, tableExists:%s", tableName, tableExists);
			logger.info(log);
			return tableExists; // will return true if there is something to return
		} catch (SQLException e) {
			String log = String.format("Failure when checking existence of table:%s.%s", tableName, e.getMessage());
			logger.error(log);

		}

		return false;

	}

}
