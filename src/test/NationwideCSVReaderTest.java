package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import org.junit.Before;

import statementReaders.*;
import sqliteData.TableCategoryReader;
import sqliteData.TableCategoryWriter;
import transactions.Transaction;
import regex.RegexMethods;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import config.AppConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

class NationwideCSVReaderTest {
	
	private AppConfig mockConfig;
	
	@BeforeEach
	public void setup() {
		mockConfig = mock(AppConfig.class);
	}
	
	@Test
	public void testCheckNationwideHeaders() {
		String[] testHeaders = {"\"Date", "Transaction type", "Description", "Paid out", "Paid in", "Balance\"" };
		when(mockConfig.getConfigFile()).thenReturn(null);
		NationwideCSVReader reader = new NationwideCSVReader(mockConfig);

		Assertions.assertTrue(reader.checkNationWideHeaders(testHeaders));
	
	}
	
	@Test public void testRetrieveCategoryNullRead() {
		Transaction mockTransactionIn = mock(Transaction.class);
		Transaction mockTransactionOut = mock(Transaction.class);
		TableCategoryReader mockTableReader = mock(TableCategoryReader.class);
		TableCategoryWriter mockTableWriter = mock(TableCategoryWriter.class);
		
		when(mockConfig.getConfigFile()).thenReturn(null);
		NationwideCSVReader reader = new NationwideCSVReader(mockConfig);
		
		//Stubbing methods of testReader
		when(mockTableReader.readCategory("testOut")).thenReturn(null);
		when(mockTableReader.readCategory("testIn")).thenReturn(null);
		
		//Stubbing methods of mockTransactions
		when(mockTransactionOut.getProcessedDescription()).thenReturn("testOut");
		when(mockTransactionIn.getProcessedDescription()).thenReturn("testIn");
		when(mockTransactionOut.getPaidIn()).thenReturn((double) 0);
		when(mockTransactionIn.getPaidIn()).thenReturn((double) 1);
		
		//Stubbing methods of tableWriter
		when(mockTableWriter.assignCategoryToTransaction(anyString(), anyString())).thenReturn(null);
		
		reader.retrieveCategory(mockTransactionOut, mockTableReader, mockTableWriter);
		verify(mockTableWriter).assignCategoryToTransaction("testOut", "Outgoing");
		reader.retrieveCategory(mockTransactionIn, mockTableReader, mockTableWriter);
		verify(mockTableWriter).assignCategoryToTransaction("testIn", "Incoming");
	}
	
	@Test
	public void testRetrieveCategoryNotNull() {
		Transaction mockTransaction = mock(Transaction.class);
		TableCategoryReader mockTableReader = mock(TableCategoryReader.class);
		TableCategoryWriter mockTableWriter = mock(TableCategoryWriter.class);
		
		when(mockConfig.getConfigFile()).thenReturn(null);
		NationwideCSVReader reader = new NationwideCSVReader(mockConfig);
		
		//Stubbing methods of transaction
		when(mockTransaction.getProcessedDescription()).thenReturn("test");
		
		//Stubbing methods of tableReader
		when(mockTableReader.readCategory("test")).thenReturn("test");
		
		Assertions.assertEquals("test", reader.retrieveCategory(mockTransaction, mockTableReader, mockTableWriter));
	}
	
	@Test
	public void testProcessNationwideTransaction() {
		// Create an instance of NationwideCSVReader and use Mockito.spy to create a
		// partial mock
		RegexMethods regex = new RegexMethods();
		when(mockConfig.getConfigFile()).thenReturn(null);
		NationwideCSVReader reader = Mockito.spy(new NationwideCSVReader(mockConfig));
		Transaction transaction = Mockito.spy(new Transaction());
		TableCategoryReader mockTableReader = mock(TableCategoryReader.class);
		TableCategoryWriter mockTableWriter = mock(TableCategoryWriter.class);
		
		//Call for a mock transaction within method
		Mockito.doReturn(transaction).when(reader).createEmptyTransaction();
		
		//Stubbing retrieveCategory method
		Mockito.doReturn("Subscriptions").when(reader).retrieveCategory(transaction,mockTableReader,mockTableWriter);
		
		//implementing doNothing on tableWriter.insertEntry method
		doNothing().when(mockTableWriter).insertEntry(anyString(), anyString());
		
		String[] fields = {"06 Jan 2023","Visa purchase","APPLE.COM/BILL 08001076285 IE","£1.49","","£3169.43"};
		Transaction expected = new Transaction();
		expected.setDate("2023-01-06");
		expected.setTrType("Visa purchase");
		expected.setRawDescription("APPLE.COM/BILL 08001076285 IE");
		expected.setProcessedDescription("APPLEBILL  IE");
		expected.setCategory("Subscriptions");
		expected.setPaidOut(1.49);
		expected.setPaidIn(0.00);
		expected.setBalance(3169.43);
		expected.setID(regex.generateTransactionUUID(expected.getDate(), expected.getTrType(),
				expected.getRawDescription(), expected.getPaidOut(), expected.getPaidIn(),
				expected.getBalance()));
		reader.manualProcessNationwideTransaction(fields, mockTableReader, mockTableWriter, regex);
		
		Assertions.assertEquals(transaction.getDate(), expected.getDate());
		Assertions.assertEquals(transaction.getTrType(), expected.getTrType());
		Assertions.assertEquals(transaction.getRawDescription(), expected.getRawDescription());
		Assertions.assertEquals(transaction.getProcessedDescription(), expected.getProcessedDescription());
		Assertions.assertEquals(transaction.getCategory(), expected.getCategory());
		Assertions.assertEquals(transaction.getPaidOut(),expected.getPaidOut());
		Assertions.assertEquals(transaction.getPaidIn(), expected.getPaidIn());
		Assertions.assertEquals(transaction.getBalance(), expected.getBalance());
		Assertions.assertEquals(transaction.getID(), expected.getID());

		//For some reason cannot get equals?
//		Assertions.assertEquals(transaction, expected);
	}

	//Unsure how to create test - as needs to read in values from a statement? BufferReads like I have done with 
	
//	public void testConvertNationwideStatement() {
//		File mockStatement = mock(File.class);
//		RegexMethods regex = new RegexMethods();
//		when(mockConfig.getConfigFile()).thenReturn(null);
//		NationwideCSVReader reader = Mockito.spy(new NationwideCSVReader(mockConfig));
//		BufferedReader mockBufferedReader = mock(BufferedReader.class);
//		TableCategoryReader mockTableReader = mock(TableCategoryReader.class);
//		TableCategoryWriter mockTableWriter = mock(TableCategoryWriter.class);
//		Mockito.doReturn(mockBufferedReader).when(reader).createNewBufferedReader(mockStatement);
//
//		try {
//			doNothing().when(mockBufferedReader).readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
