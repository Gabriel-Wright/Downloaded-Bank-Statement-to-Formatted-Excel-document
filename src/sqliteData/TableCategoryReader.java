package sqliteData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableCategoryReader extends TableCategory {

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public TableCategoryReader(TableCategory tC) {
		super(tC.getDB(), tC.getCategoryInput(), tC.getAppendOrCreateConfirm(), tC.getCategoryNameConfirm(),
				tC.getCategoryMenuChoiceConfirm());
	}

	/*
	 * ================== METHODS =================
	 */

	// Refresh stored categoryOptions for categoryMenu.
	public void refreshCategoryOptions() {
		// Initialise a List so that we do not need to know size of array.
		List<String> categories = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(getDB().getUrl());
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("SELECT DISTINCT Category FROM Category;")) {
			// read all results
			while (resultSet.next()) {
				String category = resultSet.getString("Category");
				categories.add(category);
			}

		} catch (SQLException e) {
			logger.error("Error when attempting to refresh categoryOptions for " + getTableName() + "table.");
		}

		String[] options = categories.toArray(new String[categories.size()]);
		logger.info("Set options for categoryMenu:" + options.toString());
		getCategoryMenu().setOptions(options);
	}

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
