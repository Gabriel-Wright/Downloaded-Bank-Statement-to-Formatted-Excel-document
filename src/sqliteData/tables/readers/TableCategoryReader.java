package sqliteData.tables.readers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import optionMenu.Input;
import optionMenu.Menu;
import sqliteData.tables.Table;
import sqliteData.tables.TableCategory;

/**
 * The TableCategoryReader class extends the TableCategory class and provides
 * additional functionality to read and retrieve category information from the
 * Category table of an SQLite database. It includes methods to check whether a
 * category exists, as well as retrieve the category associated with a specific
 * description. It relies on the TableCategory class for database connection and
 * menu options.
 * 
 * The TableCategoryReader class is designed to be used in conjunction with the
 * TableCategory class to provide comprehensive category reading and retrieval
 * functionality.
 * 
 * Note: The TableCategoryReader class assumes that the necessary tables and
 * connections have been set up prior to use.
 * 
 * @see Table
 * @see TableCategory
 * @see optionMenu.Menu
 * @author LORD GABRIEL
 */

public class TableCategoryReader extends TableCategory {

	/*
	 * ===================CONSTRUCTORS ====================
	 */
	
	/**
	 * Constructs a new TableCategoryReader object with the specified TableCategory
	 * object.
	 *
	 * @param tC - the TableCategory object associated with the reader
	 */

	public TableCategoryReader(TableCategory tC) {
		super(tC.getDB(),tC.getCategoryMenu());
	}
	
		
	/*
	 * ================== METHODS =================
	 */


	/**
	 * Checks whether a category exists within the category options.
	 *
	 * @param category - the category to check
	 * @return true if the category exists, false otherwise
	 */
	public boolean checkCategoryOption(String category) {
		refreshCategoryOptions();
		String[] options = getCategoryMenu().getOptions();
		for (String optionCategory : options) {
			if (optionCategory.equals(category)) {
				String log = String.format("Category:%s was found within categoryMenu options",category);
				logger.info(log);
				return true;
			}
		}		
		String log = String.format("Category:%s was not found within categoryMenu options",category);
		logger.info(log);
		return false;
	}
	
	/**
	 * Retrieves the category associated with the specified description.
	 *
	 * @param description - the description to retrieve the category for
	 * @return the category associated with the description, or null if no category
	 *         is found
	 */
	public String readCategory(String description) {
		String category = null;
		String query = "SELECT Category from Category WHERE Description = ?;";
		
		try(Connection conn = DriverManager.getConnection(getDB().getUrl());
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			//setting prepared statement of query to search where Description = description
			pstmt.setString(1, description);
			
			
			try(ResultSet rs = pstmt.executeQuery()) {
			//retrieve category String.
				if (rs.next()) {
					category = rs.getString("Category");
					String log = String.format("Category:%s found for description %s", category,description);
					logger.info(log);
				}
			}
		} catch (SQLException e) {
			String log = String.format("Error found when trying to locate category for description:%s",description);
			logger.error(log);
		}
		return category;
	}
}
