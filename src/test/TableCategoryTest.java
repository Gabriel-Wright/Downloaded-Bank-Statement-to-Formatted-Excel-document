package test;

import sqliteData.*;
import optionMenu.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class TableCategoryTest {

	private Database mockDB;
	private Menu mockMenu;
	private static MockedStatic<DriverManager> mockedDriverManager;

	@BeforeEach
	public void setup() {
		// Create mock objects for Database and Input
		mockDB = mock(Database.class);
		mockMenu = mock(Menu.class);
	}

	@BeforeAll
	public static void setupClass() {
		// Set up static mocking for DriverManager
		mockedDriverManager = mockStatic(DriverManager.class);
	}

	@AfterAll
	public static void cleanupClass() {
		// Reset static mocking for DriverManager
		mockedDriverManager.close();
	}

	@Test
	public void testTableCategoryCreateTable() {
		// Create a TableCategory instance with the mock objects
		TableCategory tableCategory = new TableCategory(mockDB, mockMenu);

		// Mock the Connection and Statement objects
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);

		try {
			// Stub the mockDB.getUrl() method to return a mock Connection
			when(mockDB.getUrl()).thenReturn("jdbc:mock");

			// Stub the DriverManager.getConnection() method to return the mock Connection
			when(DriverManager.getConnection(anyString())).thenReturn(mockConnection);

			// Stub the Connection.createStatement() method to return the mock Statement
			when(mockConnection.createStatement()).thenReturn(mockStatement);

			// Call the createTable method
			tableCategory.createTable();

			// Verify that the appropriate methods were called on the mock objects
			verify(mockDB).getUrl();
			verify(mockStatement).execute(anyString());
			verify(mockConnection).close();
		} catch (SQLException e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	public void testTableCategoryWriterInsertEntry() {
		String description = "testDescription";
		String category = "testCategory";

		// Mock TableCategory
		TableCategory mockTableCategory = mock(TableCategory.class);
		MenuSelect categoryMenuSelect = new MenuSelect(mockMenu);
		// Stub tableCategory.getDB()
		when(mockTableCategory.getDB()).thenReturn(mockDB);

		// Stub tableCategory.getTableName()
		when(mockTableCategory.getTableName()).thenReturn("Category");

		TableCategoryWriter tableCategoryWriter = new TableCategoryWriter(mockTableCategory, categoryMenuSelect, false,
				false, false);

		// Mock the DB, Connection and Statement objects
		Connection mockConnection = mock(Connection.class);
		PreparedStatement mockStatement = mock(PreparedStatement.class);
		try {
			// Stub tableCategoryWriter.getDB() method to return mockDB;
			when(mockTableCategory.getDB()).thenReturn(mockDB);

			// Stub the mockDB.getUrl() method to return a mock Connection
			when(mockDB.getUrl()).thenReturn("jdbc:mock");

			// Stub driverManager.getConnection() method to return a mock connection
			when(DriverManager.getConnection(anyString())).thenReturn(mockConnection);

			// Stub the Connection.prepareStatement() method to return the mock Statement
			when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

			tableCategoryWriter.insertEntry(description, category);
			// Verify that methods were called on insert
			verify(mockStatement).setString(1, description);
			verify(mockStatement).setString(2, category);
			verify(mockStatement).executeUpdate();
		} catch (SQLException e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	public void testTableCategoryReaderRefreshCategoryOptions() {
		// Mock TableCategory
		TableCategory mockTableCategory = mock(TableCategory.class);
		Input mockCategoryInput = mock(Input.class);
		Menu mockCategoryMenu = mock(Menu.class);

		// Stub tableCategory.getDB()
		when(mockTableCategory.getDB()).thenReturn(mockDB);

		// Stub tableCategory.getTableName()
		when(mockTableCategory.getTableName()).thenReturn("Category");

		TableCategoryReader tableCategoryReader = new TableCategoryReader(mockTableCategory);
		tableCategoryReader.setCategoryMenu(mockCategoryMenu);
		// Mock the Connection, Statement and ResultSet objects
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);
		try {
			// Stub the mockDB.getUrl() method to return a mock Connection
			when(mockDB.getUrl()).thenReturn("jdbc:mock");

			// Stub driverManager.getConnection() method to return a mock connection
			when(DriverManager.getConnection(anyString())).thenReturn(mockConnection);

			// Stub the Connection.createStatement() method to return the mock Statement
			when(mockConnection.createStatement()).thenReturn(mockStatement);

			// Stub the Statement.executeQuery() method to return the mockResultSet.
			when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

			// Stub resultSet.next() method to return true true and then false (for no more
			// results)
			when(mockResultSet.next()).thenReturn(true, true, false); // Simulate two results

			// Stub resultSet.getString() to retrieve fake categories
			when(mockResultSet.getString("Category")).thenReturn("Category1", "Category2");

			// Call the method under test
			tableCategoryReader.refreshCategoryOptions();

			// Verify that the expected methods were called
			verify(mockTableCategory).getDB();
			verify(mockDB).getUrl();
			verify(mockConnection).createStatement();
			verify(mockStatement).executeQuery("SELECT DISTINCT Category FROM Category;");
			verify(mockResultSet, times(3)).next();
			verify(mockResultSet, times(2)).getString("Category");

			// Verify that the options were set correctly in the category menu
			String[] expectedOptions = { "Category1", "Category2" };
			verify(mockCategoryMenu).setOptions(expectedOptions);
		} catch (SQLException e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	public void testTableCategoryReaderCheckCategoryOption() {
		// Mock dependencies
		TableCategory mockTableCategory = mock(TableCategory.class);
		Input mockCategoryInput = mock(Input.class);
		Menu mockCategoryMenu = mock(Menu.class);

		// Create a test instance of TableCategoryReader

		// Stub tableCategory.getDB()
		when(mockTableCategory.getDB()).thenReturn(mockDB);

		TableCategoryReader tableCategoryReader = new TableCategoryReader(mockTableCategory);
		tableCategoryReader.setCategoryMenu(mockCategoryMenu);

		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);

		// Set up the categoryMenu options
		String[] options = { "Category1", "Category2", "Category3" };
		when(mockCategoryMenu.getOptions()).thenReturn(options);
		// Stub the mockDB.getUrl() method to return a mock Connection
		try {
			when(mockDB.getUrl()).thenReturn("jdbc:mock");

			// Stub driverManager.getConnection() method to return a mock connection
			when(DriverManager.getConnection(anyString())).thenReturn(mockConnection);

			// Stub the Connection.createStatement() method to return the mock Statement
			when(mockConnection.createStatement()).thenReturn(mockStatement);

			// Stub the Statement.executeQuery() method to return the mockResultSet.
			when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

			// Stub resultSet.next() method to return true true and then false (for no more
			// results)
			when(mockResultSet.next()).thenReturn(true, true, false); // Simulate two results

			// Stub resultSet.getString() to retrieve fake categories
			when(mockResultSet.getString("Category")).thenReturn("Category1", "Category2");

			// Call the method with a category that exists in the options
			boolean result1 = tableCategoryReader.checkCategoryOption("Category2");

			// Assert that the method returns true
			Assertions.assertTrue(result1);

			// Call the method with a category that does not exist in the options
			boolean result2 = tableCategoryReader.checkCategoryOption("Category4");

			// Assert that the method returns false
			Assertions.assertFalse(result2);

		} catch (SQLException e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}

	@Test
	public void testTableCategoryReaderReadCategory() {
		String description = "TestDescription";
		String category = "TestCategory";
		// Mock dependencies
		TableCategory mockTableCategory = mock(TableCategory.class);
		Input mockCategoryInput = mock(Input.class);

		// Stub tableCategory.getDB()
		when(mockTableCategory.getDB()).thenReturn(mockDB);
		// Stub mockDB.getUrl()
		when(mockDB.getUrl()).thenReturn("jdbc:mock");

		// Creating mocks of connection
		Connection mockConnection = mock(Connection.class);
		PreparedStatement mockStatement = mock(PreparedStatement.class);
		ResultSet mockResultSet = mock(ResultSet.class);

		TableCategoryReader tableCategoryReader = new TableCategoryReader(mockTableCategory);
		tableCategoryReader.setCategoryMenu(mockMenu);

		try {
			// Stub driverManager.getConnection() method to return a mock connection
			when(DriverManager.getConnection(anyString())).thenReturn(mockConnection);

			// Stub mockConnection.prepareStatement()
			when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

			// Stub the Statement.executeQuery() method to return the mockResultSet.
			when(mockStatement.executeQuery()).thenReturn(mockResultSet);

			// Stub the PreparedStatement.setString() method
			doNothing().when(mockStatement).setString(1, description);

			// Stub resultSet.next() method to return true true and then false (for no more
			// results)
			when(mockResultSet.next()).thenReturn(true); // Simulate two results

			// Stub the ResultSet.getString() method
			when(mockResultSet.getString("Category")).thenReturn(category);

			// Call the method under test
			String result = tableCategoryReader.readCategory(description);

			Assertions.assertEquals(category, result);
		} catch (SQLException e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}
}
