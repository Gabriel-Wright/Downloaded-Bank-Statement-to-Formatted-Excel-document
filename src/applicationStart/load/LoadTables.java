package applicationStart.load;

import sqliteData.Database;
import sqliteData.tables.Table;
import sqliteData.tables.TableCategory;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;

import java.util.List;

/**
 * The front-end class LoadTables class is responsible for checking and creating
 * database tables if they do not exist or if the table structure needs to be
 * updated.
 */

public class LoadTables {

	/**
	 * Checks the tables in the database and creates or updates them if necessary.
	 *
	 * @param DB The Database object representing the SQLite database.
	 * @param tI The TableInbound object representing the inbound transactions
	 *           table.
	 * @param tO The TableOutbound object representing the outbound transactions
	 *           table.
	 * @param tC The TableCategory object representing the categories table.
	 */

	
	//Could break this up into smaller methods.
	public void checkAndCreateTables(Database DB, TableInbound tI, TableOutbound tO, TableCategory tC) {
		Table[] tables = new Table[3];
		tables[0] = tI;
		tables[1] = tO;
		tables[2] = tC;

		for (Table table : tables) {
			String tableName = table.getTableName();
			if (!DB.checkTable(tableName)) {
				table.createTable();
				System.out.println("Created table:" + tableName);
			} else {
				List<String> columns = table.tableColumns();
				if (!columns.equals(table.getHeaders())) {
					table.replaceTable();
				}
			}
		}
	}
}
