package test;

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

import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.ArrayList;

import sqliteData.Database;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;
import sqliteData.tables.TableUtils;
import sqliteData.tables.readers.TableCategoryReader;
import sqliteData.tables.readers.TableInboundReader;
import sqliteData.tables.readers.TableOutboundReader;
import transactions.Transaction;

public class TableInboundOutboudReaderTest {
	
	private Logger logger;
	
	private Database 	  mockDB;
	private TableInbound  tI;
	private TableOutbound tO;
	
	private TableOutboundReader tOR;
	private TableInboundReader  tIR;
	private TableCategoryReader tCR;
	private TableUtils testUtil;
	@BeforeAll 
	void setupClass() {
		logger = Mockito.mock(Logger.class);
	}
	
	@BeforeEach
	void setup() {
		testUtil = Mockito.mock(new TableUtils(null));
		mockDB = Mockito.mock(Database.class);
		testUtil.setDB(mockDB);

	}
	
	@Test
	public void testExtractInboundTransactionArray() {
		//Mocking tables
		tCR = Mockito.mock(TableCategoryReader.class);
		tI  = Mockito.mock(TableInbound.class);
		
		//Creating spy mock of TableInboundReader - to stub any methods we do not need to test
		tIR = Mockito.spy(new TableInboundReader(tI,tCR,testUtil));
		
		//Stubbing checkCategoryOption to always return true
		when(tCR.checkCategoryOption(anyString())).thenReturn(true);
		//Stubbing buildTransactionQuery String
		when(testUtil.buildTransactionQuery(anyString(), anyString(), anyString(), anyString())).thenReturn("SELECT * FROM Inbound;");
		//Stubbing executeTransactionQuery
		List<Transaction> transactions = new ArrayList<>();
		when(testUtil.executeTransactionQuery("SELECT * FROM Inbound;")).thenReturn(transactions);
		
		Mockito.doNothing().when(testUtil).logTransactionArrayRead(anyString(), anyString(), anyString(), anyString());
		
		Assertions.assertNull(tIR.extractInboundTransactionArray("2023-02-01", "2023-02-02", "category"));
	}
	
}
