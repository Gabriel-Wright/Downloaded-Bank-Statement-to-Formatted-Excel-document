package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import transactions.Transaction;

/**
 * The TableUtils class provides utility methods for executing SQL queries on
 * tables, extracting transactions from result sets, and logging.
 */

public class TableUtils {

	private Logger logger;
	/**
	 * Class Database Object, used to enable connection to Database for reading
	 * tables
	 */
	private Database DB;

	/*
	 * =============== CONSTRUCTORS ==================
	 */

	/**
	 * Constructs a TableUtils object with a specific logger, all methods within
	 * TableUtils will refer to this logger
	 * 
	 * @param logger - log4j logger used for error/info logging
	 */
	public TableUtils(Logger logger) {
		this.logger = logger;
	}

	/*
	 * ============== GETTERS =======================
	 */

	/**
	 * Returns this TableUtils object's DB object.
	 * 
	 * @return DB, database object used for database connection
	 */
	public Database getDB() {
		return DB;
	}

	/*
	 * ================ SETTERS ====================
	 */
	/**
	 * Sets class Database object
	 * 
	 * @param DB
	 */
	public void setDB(Database DB) {
		this.DB = DB;
	}

	/*
	 * ================== METHODS ======================
	 */

	/**
	 * Creates an empty list of transactions, this is useful for testing
	 * 
	 * @return an empty list of Transaction objects
	 */
	protected List<Transaction> createEmptyListTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		return transactions;
	}

	/**
	 * Create an empty hashmap of integer - transactions, useful for testing This is
	 * intended to be used to sort transactions into months
	 * 
	 * @return an empty hashmap of Transaction Objects organised by integer
	 */
	protected Map<Integer, List<Transaction>> createEmptyIntegerTransactionHashMap() {
		Map<Integer, List<Transaction>> transactionsByMonth = new HashMap<>();
		return transactionsByMonth;
	}

	/**
	 * Extracts the month value from the date string.
	 *
	 * @param date the date string in the format "YYYY-MM-DD"
	 * @return the month value as an integer
	 */
	public int extractMonthFromDate(String date) {
		String[] dateParts = date.split("-");
		return Integer.parseInt(dateParts[1]);
	}

	/**
	 * Builds an SQL query for extracting transactions within the specified date
	 * range and category.
	 *
	 * @param tableName - the name of the table to query
	 * @param startDate - the start date of the date range
	 * @param endDate   - the end date of the date range
	 * @param category  - the category for filtering the transactions
	 * @return the SQL query string
	 */
	public String buildTransactionQuery(String tableName, String startDate, String endDate, String category) {
		return "SELECT * FROM " + tableName + " WHERE strftime('%Y-%m-%d', Date) BETWEEN '" + startDate + "' AND '"
				+ endDate + "' AND Category = '" + category + "' ORDER BY 'Date' ASC;";
	}

    /**
     * Logs the successful reading of the transaction array.
     *
     * @param tableName - the name of the Table which array was read from
     * @param startDate - the start date of the date range
     * @param endDate - the end date of the date range
     * @param category - the category for filtering the transactions
     */
    public void logTransactionArrayRead(String tableName, String startDate, String endDate, String category) {
        String log = String.format("Array of transactions read from Table %s for dates between %s and %s, for category: %s",
                tableName, startDate, endDate, category);
        logger.info(log);
    }

    /**
     * Logs the successful reading of the transaction map.
     *	
     * @param tableName - the name of the Table which array was read from
     * @param startDate the start date of the date range
     * @param endDate the end date of the date range
     * @param category the category for filtering the transactions
     */
    public void logTransactionMapRead(String tableName, String startDate, String endDate, String category) {
        String log = String.format("Map of transactions, sorted per month, has been read from Table %s for dates between %s and %s, for category: %s",
                tableName, startDate, endDate, category);
        logger.info(log);
    }

	/**
	 * Extracts an transaction object from the result set.
	 *
	 * @param rs the result set containing the transaction data
	 * @return the transaction object read from the result set
	 * @throws SQLException if an SQL error occurs while extracting the transaction
	 *                      data
	 */
	public Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setDate(rs.getString("Date"));
		transaction.setProcessedDescription(rs.getString("ProcessDescription"));
		transaction.setPaidIn(rs.getDouble("Paid_In"));
		transaction.setPaidOut(rs.getDouble("Paid_Out"));
		transaction.setBalance(rs.getDouble("Balance"));
		return transaction;
	}

	/**
	 * Executes an SQL query to retrieve a list of transactions.
	 *
	 * @param queryString - the SQL query string
	 * @param startDate   - the start date of the date range
	 * @param endDate     - the end date of the date range
	 * @param category    - the category for filtering the transactions
	 * @return the list of transactions representing the extracted data
	 */

	public List<Transaction> executeTransactionQuery(String queryString) {
		// Create Empty List of Transactions.
		List<Transaction> transactions = createEmptyListTransactions();

		// Attempt to connection to DB and execute queryString on relevant Table.
		try (Connection connection = DriverManager.getConnection(getDB().getUrl());
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(queryString)) {

			// Iterate through found results, extracting Transactions from each Result.
			while (rs.next()) {
				Transaction transaction = extractTransactionFromResultSet(rs);
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			logger.error("Failed to read transactions from Query: {} . {}", queryString, e.getMessage());
		}

		return transactions;
	}

	/**
	 * Executes the SQL query and retrieves a map of transactions organised by
	 * month.
	 *
	 * @param queryString the SQL query string
	 * @param startDate   the start date of the date range
	 * @param endDate     the end date of the date range
	 * @param category    the category for filtering the transactions
	 * @return the map of transactions organised by month
	 */
	public Map<Integer, List<Transaction>> executeTransactionQueryByMonth(String queryString) {
		// Create Empty Integer, List of Transaction HashMap<>. Used to store
		// Transactions by month.
		Map<Integer, List<Transaction>> transactionsByMonth = createEmptyIntegerTransactionHashMap();

		// Attempt to connection to DB and execute queryString on relevant Table.
		try (Connection connection = DriverManager.getConnection(getDB().getUrl());
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(queryString)) {

			// Iterate through found results, extracting Transactions from each Result.
			while (rs.next()) {
				Transaction transaction = extractTransactionFromResultSet(rs);
				int month = extractMonthFromDate(transaction.getDate());
				transactionsByMonth.computeIfAbsent(month, k -> new ArrayList<>()).add(transaction);
			}
		} catch (SQLException e) {
			logger.error("Failed to read transactions into a Map {}. {}", queryString, e.getMessage());
		}

		return transactionsByMonth;
	}

}
