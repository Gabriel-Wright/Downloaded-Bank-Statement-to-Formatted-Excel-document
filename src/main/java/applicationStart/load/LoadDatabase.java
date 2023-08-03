package applicationStart.load;

import sqliteData.Database;

/**
 * Front-end LoadDatabase class, used to check whether Database .db file exists
 * for this application. This check method is applied when loading/reloading the
 * application. If a .db file is not found, then a new one will be loaded.
 * 
 * @see sqliteData.Database;
 * @author LORD GABRIEL
 *
 */
public class LoadDatabase {
	
	/**
	 * Checks whether Database .db file exists for this application.
	 * 
	 * @param DB - Database object, for which the associated .db file is checked.
	 */
	public void checkAndCreateDB(Database DB) {
		if (!DB.checkDB()) {
			System.out.println("No DB found, loading new DB");
			DB.loadDB();
		}
	}
}
