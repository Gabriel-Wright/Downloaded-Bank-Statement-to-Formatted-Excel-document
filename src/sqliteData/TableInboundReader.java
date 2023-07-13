package sqliteData;

import transactions.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * The TableInboundReader class extends the TableInbound class and provides
 * additional functionality to read and extract inbound transactions from the
 * Inbound table of an SQLite database. It includes methods to extract inbound
 * transactions within a specified date range and category from an SQLite
 * database database, as well as methods to organize the transactions into a map
 * based on their month. It relies on the TableCategoryReader class to check and
 * validate category options.
 * 
 * The TableInboundReader class is designed to be used in conjunction with the
 * TableCategoryReader class and TableUtils class to provide comprehensive
 * transaction reading and extraction functionality.
 * 
 * Note: The TableInboundReader class assumes that the necessary tables and
 * connections have been set up prior to use.
 * 
 * Note: The TableInboundReader class inherits methods from the TableInbound
 * class, which in turn extends the abstract Table class.
 * 
 * @see TableInbound
 * @see TableCategoryReader
 * @see Transaction
 * @see Table
 * 
 * @author LORD GABRIEL
 */

public class TableInboundReader extends TableInbound {

	/**
	 * The TableCategoryReader object used for category validation and retrieval.
	 */

	private TableCategoryReader tCR;

	/**
	 * TableUtils object used to call relevant methods for class, such as extracting
	 * Transactions from queries
	 */

	private TableUtils util;
	/*
	 * ============CONSTRUCTORS=================
	 */

	/**
	 * Constructs a new TableInboundReader object with the specified TableInbound
	 * and TableCategoryReader objects.
	 *
	 * @param tI   - the TableInbound object associated with the reader
	 * @param tCR  - the TableCategoryReader object used for category validation and
	 *             retrieval
	 * @param util - the TableUtils object used to generate queryStrings and execute
	 *             SQL queries
	 */

	public TableInboundReader(TableInbound tI, TableCategoryReader tCR, TableUtils util) {
		super(tI.getDB());
		this.tCR = tCR;
		this.util = util;
	}

	/*
	 * ============GETTERS=====================
	 */

	/**
	 * Getter method that retrieves the TableCategoryReader object associated with
	 * the TableInboundReader.
	 *
	 * @return the TableCategoryReader object associated with this reader.
	 */

	public TableCategory getTC() {
		return tCR;
	}

	/**
	 * Getter method to retrieve TableUtil object of TableInboundReader class
	 * 
	 * @return TableUtils object associated with this reader
	 */

	public TableUtils getUtil() {
		return util;
	}

	/*
	 * ============METHODS===================
	 */

	/**
	 * Assigns TableUtils object this Table object's Database object, in order to
	 * make read database
	 */

	public void loadTableUtilsDB() {
		getUtil().setDB(getDB());
	}

	/**
	 * Extracts an array of inbound transactions within the specified date range and
	 * category.
	 *
	 * @param startDate - the start date of the date range
	 * @param endDate   - the end date of the date range
	 * @param category  - the category for filtering the transactions
	 * @return an array of Transaction objects representing the extracted inbound
	 *         transactions, or null if no categories of the provided type are found
	 */

	public Transaction[] extractInboundTransactionArray(String startDate, String endDate, String category) {
		// Check whether there are any transactions with category: category
		if (!tCR.checkCategoryOption(category)) {
			System.out.println("No categories of that type found");
			logger.info("No categories of type: {} found.", category);
			return null;
		}

		// Generate queryString from TableUtils
		TableUtils util = getUtil();
		String queryString = util.buildTransactionQuery(getTableName(), startDate, endDate, category);
		// extract TransactionArray using queryString
		List<Transaction> transactions = util.executeTransactionQuery(queryString);
		Transaction[] transArr = transactions.toArray(new Transaction[0]);

		util.logTransactionArrayRead(getTableName(), startDate, endDate, category);
		return transArr;
	}

	/**
	 * Extracts a map of inbound transactions within the specified date range and
	 * category, organized by month.
	 *
	 * @param startDate - the start date of the date range
	 * @param endDate   - the end date of the date range
	 * @param category  - the category for filtering the transactions
	 * @return a map of Integer keys representing the months and List values
	 *         representing the corresponding transactions for each month, or null
	 *         if no categories of the provided type are found
	 */

	public Map<Integer, List<Transaction>> extractInboundTransactionMap(String startDate, String endDate,
			String category) {
		// Generate queryString from TableUtils
		if (!tCR.checkCategoryOption(category)) {
			System.out.println("No categories of that type found");
			logger.info("No categories of type: {} found.", category);
			return null;
		}

		TableUtils util = getUtil();
		// extract TransactionArray using queryString
		String queryString = util.buildTransactionQuery(getTableName(), startDate, endDate, category);
		Map<Integer, List<Transaction>> transactionsByMonth = util.executeTransactionQueryByMonth(queryString);

		util.logTransactionMapRead(getTableName(), startDate, endDate, category);
		return transactionsByMonth;
	}

}
