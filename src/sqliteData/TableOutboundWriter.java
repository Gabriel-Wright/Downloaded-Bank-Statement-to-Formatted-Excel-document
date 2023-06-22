package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import transactions.Transaction;

public class TableOutboundWriter extends TableOutbound {

	/*
	 * =========================== CONSTRUCTORS ===========================
	 */

	public TableOutboundWriter(TableOutbound table) {
		super(table.getDB());
	}

	/*
	 * =========================== METHODS ===========================
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
			logger.info("Imported " + numImports + "transactions into" + getTableName());
		} catch (SQLException e) {
			logger.error("Failed to import transactions into" + getTableName() + "table," + e.getMessage());
		}
	}

}
