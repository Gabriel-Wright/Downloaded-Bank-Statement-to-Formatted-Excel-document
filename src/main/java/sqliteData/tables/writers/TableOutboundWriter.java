package sqliteData.tables.writers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sqliteData.tables.TableOutbound;
import transactions.Transaction;

/**
 * 
 * The TableOutboundWriter class is responsible for importing transactions into
 * the outbound table of the SQLite database. It extends the TableOutbound class
 * and provides a method to import an array of transactions into the table.
 * 
 * @see TableOutbound
 * @see transactions.Transaction
 * @author
 */

public class TableOutboundWriter extends TableOutbound {

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	/**
	 * Constructs a TableOutboundWriter object with the specified TableOutbound object.
	 * 
	 * @param table - the TableOutbound object to set
	 */

	public TableOutboundWriter(TableOutbound table) {
		super(table.getDB());
	}

	/*
	 * =========================== METHODS ===========================
	 */

	/**
	 * Imports an array of transactions into the outbound table of the database.
	 * 
	 * @param transactions - the array of transactions to import
	 */

	public void importData(Transaction[] transactions) {
		int numImports = transactions.length;
		int i = 0;
		String insert;
		// connect to DB,
		try (Connection conn = DriverManager.getConnection(getDB().getUrl()); Statement stmt = conn.createStatement()) {
			// for each import add to table
			for (i = 0; i < numImports; i++) {
				Transaction tr = transactions[i];
				if (tr.getPaidIn() <= 0 && tr.getPaidOut() > 0) {
					insert = "INSERT OR IGNORE INTO " + getTableName()
							+ " (ID, Date,trType,RawDescription,ProcessDescription," + "Category,Paid_Out,Balance) \n"
							+ "VALUES ('" + tr.getID() + "', '" + tr.getDate() + "', '" + tr.getTrType() + "', '"
							+ tr.getRawDescription() + "', '" + tr.getProcessedDescription() + "', '" + tr.getCategory()
							+ "', '" + tr.getPaidOut() + "', '" + tr.getBalance() + "');";
					stmt.execute(insert);
				}
			}
			logger.info(String.format("Imported {} transactions into {}", numImports, getTableName()));
		} catch (SQLException e) {
			logger.error(String.format("Failed to import transactions into {} table. {}", getTableName(), e.getMessage()));
		}
	}

}
