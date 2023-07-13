package sqliteData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;

import sqliteData.TableUtils;
import transactions.Transaction;

class TableUtilsTest {

	TableUtils testUtil;
	Database mockDB;
	private static MockedStatic<DriverManager> mockedDriverManager;
	Logger logger;
	
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

	
	@BeforeEach
	void setup() {
		// No logger needed
		logger = Mockito.mock(Logger.class);
		testUtil = Mockito.spy(new TableUtils(null));
		mockDB = Mockito.mock(Database.class);
		testUtil.setDB(mockDB);
	}

	@Test
	void testCreateEmptyListTransactions() {
		List<Transaction> emptyList = new ArrayList<>();
		List<Transaction> testList = testUtil.createEmptyListTransactions();
		Assertions.assertEquals(testList, emptyList);
	}

	@Test
	void testCreateEmptyIntegerTransactionMap() {
		Map<Integer, List<Transaction>> emptyMap = new HashMap<>();
		Map<Integer, List<Transaction>> testMap = testUtil.createEmptyIntegerTransactionHashMap();
		Assertions.assertEquals(emptyMap, testMap);
	}

	@Test
	void testExtractMonthFromdate() {
		String date = "2023-02-12";
		Assertions.assertEquals(2, testUtil.extractMonthFromDate(date));
	}

	@Test
	void testBuildTransactionQuery() {
		String tableName = "Inbound";
		String startDate = "2023-02-12";
		String endDate = "2023-03-10";
		String category = "Test";

		String expectedQuery = "SELECT * FROM Inbound WHERE strftime('%Y-%m-%d', Date) BETWEEN '2023-02-12' AND '2023-03-10' AND Category = 'Test' ORDER BY 'Date' ASC;";
		String outputQuery = testUtil.buildTransactionQuery(tableName, startDate, endDate, category);
		Assertions.assertEquals(expectedQuery, outputQuery);
	}
	
	@Test 
	void testExtractTransactionFromResultSet() throws SQLException {
        // Mock the ResultSet
        ResultSet rs = Mockito.mock(ResultSet.class);
        
        // Set up the mocked ResultSet with the desired values
        Mockito.when(rs.getString("Date")).thenReturn("2023-07-11");
        Mockito.when(rs.getString("ProcessDescription")).thenReturn("Sample Description");
        Mockito.when(rs.getDouble("Paid_In")).thenReturn(100.0);
        Mockito.when(rs.getDouble("Paid_Out")).thenReturn(50.0);
        Mockito.when(rs.getDouble("Balance")).thenReturn(50.0);

        //Call the method to extract the transaction
        Transaction transaction = testUtil.extractTransactionFromResultSet(rs);
        
        //Assert the expected values
        Assertions.assertEquals("2023-07-11",transaction.getDate());
        Assertions.assertEquals("Sample Description", transaction.getProcessedDescription());
        Assertions.assertEquals(100.0, transaction.getPaidIn());
        Assertions.assertEquals(50.0, transaction.getPaidOut());
        Assertions.assertEquals(50.0, transaction.getBalance());
	}
	
	@Test
	void testExecuteTransactionQuery() throws SQLException {
		//Mock the necessary objects
		Connection connection = Mockito.mock(Connection.class);
		Statement statement   = Mockito.mock(Statement.class);
		ResultSet resultSet   = Mockito.mock(ResultSet.class);
		
		//Stub interactions for connection, statement and resultSet
		Mockito.when(mockDB.getUrl()).thenReturn("jdbc:mock");
		Mockito.when(DriverManager.getConnection(anyString())).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false); // Simulate a single result
        
        //Create transaction object
        Transaction transaction = new Transaction();
        transaction.setProcessedDescription("TestDescription");
        transaction.setBalance(300);
        
        //Stub transaction to be extracted
        Mockito.when(testUtil.extractTransactionFromResultSet(resultSet)).thenReturn(transaction);
        
        // Call the method to execute the transaction query
        List<Transaction> transactions = testUtil.executeTransactionQuery("SELECT * FROM Transactions");
        // Assert the expected values
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(transaction.getBalance(),transactions.get(0).getBalance());
        assertEquals(transaction.getProcessedDescription(),transactions.get(0).getProcessedDescription());

	}
	
	@Test
	void testExecuteTransactionQueryByMonth() throws SQLException {
		//Mock the necessary objects
		Connection connection = Mockito.mock(Connection.class);
		Statement statement   = Mockito.mock(Statement.class);
		ResultSet resultSet   = Mockito.mock(ResultSet.class);
		
		//Stub interactions for connection, statement and resultSet
		Mockito.when(mockDB.getUrl()).thenReturn("jdbc:mock");
		Mockito.when(DriverManager.getConnection(anyString())).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false); // Simulate a single result
        
        //Create transaction object
        Transaction transaction = new Transaction();
        transaction.setDate("2023-02-12");
        transaction.setProcessedDescription("TestDescription");
        //Stub transaction to be extracted
        Mockito.when(testUtil.extractTransactionFromResultSet(resultSet)).thenReturn(transaction);
        
        // Call the method to execute the transaction query
        Map<Integer,List<Transaction>> transactions = testUtil.executeTransactionQueryByMonth("SELECT * FROM Transactions");
        // Assert the expected values
        Assertions.assertNotNull(transactions);
        Assertions.assertNotNull(transactions.get(2));
        List<Transaction> transactionsUnderMonth2 = transactions.get(2);
        Assertions.assertEquals(1,transactionsUnderMonth2.size());
        Assertions.assertEquals(transaction.getProcessedDescription(), transactionsUnderMonth2.get(0).getProcessedDescription());
	}
}
