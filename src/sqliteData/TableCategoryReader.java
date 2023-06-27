package sqliteData;

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

public class TableCategoryReader extends TableCategory {

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public TableCategoryReader(TableCategory tC) {
		super(tC.getDB(),tC.getCategoryMenu());
	}
	
		
	/*
	 * ================== METHODS =================
	 */


	// Checks whether Category exists within categoryTable
	public boolean checkCategoryOption(String category) {
		refreshCategoryOptions();
		String[] options = getCategoryMenu().getOptions();
		for (String optionCategory : options) {
			if (optionCategory.equals(category)) {
				logger.info("Category:"+category+" was found within categoryMenu options");
				return true;
			}
		}		
		logger.info("Category:"+category+" was not found within categoryMenu options");
		return false;
	}
	
	//retrieves category from primary key (Description)
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
					logger.info("Category:"+category+" found for description:"+description);

				}
			}
		} catch (SQLException e) {
			logger.error("Error found when trying to locate category for description:"+description);
		}
		return category;
	}
}
