package statementReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.AppConfig;
import sqliteData.tables.readers.TableCategoryReader;
import sqliteData.tables.writers.TableCategoryWriter;
import transactions.Transaction;
import regex.RegexMethods;

/**
 * 
 * The NationwideCSVReader class is responsible for reading and processing
 * Nationwide CSV statements. It extends the StatementReader class and provides
 * methods to check the formatting of the CSV file, convert the CSV statement
 * into an array of transactions, and process individual transactions.
 * 
 * @see StatementReader
 * @see TableCategoryReader
 * @see TableCategoryWriter
 * @see RegexMethods
 * @see Transaction
 * @author
 */

public class NationwideCSVReader extends StatementReader {

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	/**
	 * Constructs a NationwideCSVReader object with the specified AppConfig.
	 * 
	 * @param config - the AppConfig object to set
	 */

	public NationwideCSVReader(AppConfig config) {
		setConfig(config);
		setConfig_File(config.getConfigFile());
	}

	/*
	 * ================== ABSTRACT IMPLEMENTATION ====================
	 */

	/**
	 * Implements abstract method - converts a Nationwide CSV statement file into an
	 * array of transactions. This can require manual input from the user when
	 * deciding how to assign categories for transactions.
	 * 
	 * @param statement   - the Nationwide CSV statement file
	 * @param tableReader - the TableCategoryReader object to retrieve category
	 *                    information
	 * @param tableWriter - the TableCategoryWriter object to assign categories to
	 *                    transactions
	 * @param regex       - the RegexMethods object for processing transaction
	 *                    details
	 * @return an array of transactions extracted from the CSV statement
	 */
	@Override
	public Transaction[] convertStatement(File statement, TableCategoryReader tableReader,
			TableCategoryWriter tableWriter, RegexMethods regex) {

		List<Transaction> transactions = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(statement.getAbsolutePath()))) {
			// Stores the values of the entries on current line of CSV
			String line;
			int numTransactions;
			// Skip first 4 lines
			reader.readLine();
			reader.readLine();
			reader.readLine();
			reader.readLine();

			// Checking appropriate formatting of Nationwide CSV
			String headerLine = reader.readLine();
			String[] headers = headerLine.split("\",\"");

			// Check if not appropriate formatting:
			if (!checkNationWideHeaders(headers)) {
				logger.error("Unexpected formatting for expected Nationwide csv Statement. "
						+ "Unable to process inputted statement.");
				return null;
			}
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\",\""); // Array of current entry (line currently on in reader)
				Transaction transaction = manualProcessNationwideTransaction(fields, tableReader, tableWriter, regex);
				transactions.add(transaction);
			}
			numTransactions = transactions.size();
			logger.info("Statement entries from NationwideCSV transferred to an array of Transactions of size "
					+ numTransactions);
 		} catch (FileNotFoundException e) {
			logger.error("File not found when creating new BufferedReader:" + e.getMessage());
 		} catch (IOException e) {
			logger.error("Error when converting expected CSV statement to an array of transactions:"+ e.getMessage());
		}
		return transactions.toArray(new Transaction[0]);
	}

	/*
	 * ================== METHODS =================
	 */

	/**
	 * Checks the headers of the Nationwide CSV file against the expected headers to
	 * verify the formatting.
	 * 
	 * @param headers - the headers of the CSV file
	 * @return true if the headers match the expected format, false otherwise
	 */

	public boolean checkNationWideHeaders(String[] headers) {
		String[] expectedHeaders = { "\"Date", "Transaction type", "Description", "Paid out", "Paid in", "Balance\"" };
		return Arrays.equals(headers, expectedHeaders);
	}

	/**
	 * Processes a single Nationwide transaction by formatting its details and
	 * assigning a category. This method may require manual input from the user,
	 * categories are read from the Category table within the SQL database. If no
	 * category is found for this relevant transaction, the user may need to decide
	 * how to categorise this transaction.
	 * 
	 * @param fields      - the fields of the transaction from the CSV statement
	 * @param tableReader - the TableCategoryReader object to retrieve category
	 *                    information
	 * @param tableWriter - the TableCategoryWriter object to assign categories to
	 *                    transactions
	 * @param regex       - the RegexMethods object for processing transaction
	 *                    details
	 * @return the processed Transaction object
	 */

	public Transaction manualProcessNationwideTransaction(String[] fields, TableCategoryReader tableReader,
			TableCategoryWriter tableWriter, RegexMethods regex) {
		Transaction transaction = createEmptyTransaction();

		transaction.setDate(regex.convertDDMMYYYYDate(fields[0]));
		transaction.setTrType(fields[1]);
		transaction.setRawDescription(fields[2]);
		transaction.setProcessedDescription(regex.processDescription(fields[2]));
		transaction.setPaidOut(regex.parseAmount(fields[3]));
		transaction.setPaidIn(regex.parseAmount(fields[4]));
		transaction.setBalance(regex.parseAmount(fields[5]));
		transaction.setID(regex.generateTransactionUUID(transaction.getDate(), transaction.getTrType(),
				transaction.getRawDescription(), transaction.getPaidOut(), transaction.getPaidIn(),
				transaction.getBalance()));
		transaction.setCategory(retrieveCategory(transaction, tableReader, tableWriter));

		tableWriter.insertEntry(transaction.getProcessedDescription(), transaction.getCategory());
		return transaction;
	}

	/**
	 * Retrieves the category for a transaction. If the transaction does not have a
	 * category, a new category is assigned.
	 * 
	 * @param transaction - the Transaction object
	 * @param tableReader - the TableCategoryReader object to retrieve category
	 *                    information
	 * @param tableWriter - the TableCategoryWriter object to assign categories to
	 *                    transactions
	 * @return the category of the transaction
	 */

	public String retrieveCategory(Transaction transaction, TableCategoryReader tableReader,
			TableCategoryWriter tableWriter) {
		String readCategory = tableReader.readCategory(transaction.getProcessedDescription());
		if (readCategory == null) {
			String inOrOut;
			// Would be ideal to complete a switch here? - but cannot work with doubles?
			// we could mess it up here -> for the assigning make it work differently
			if (transaction.getPaidIn() > 0) {
				inOrOut = "Incoming";
				readCategory = tableWriter.assignCategoryToTransaction(transaction.getDate(), transaction.getProcessedDescription(), transaction.getPaidIn(), inOrOut);
			} else {
				inOrOut = "Outgoing";
				readCategory = tableWriter.assignCategoryToTransaction(transaction.getDate(), transaction.getProcessedDescription(), transaction.getPaidOut(), inOrOut);
			}
		}

		return readCategory;
	}

	/**
	 * Creates a new BufferedReader object for reading the statement file.
	 * 
	 * @param statement - the statement file to read
	 * @return a BufferedReader object for reading the file
	 */

	public BufferedReader createNewBufferedReader(File statement) {
		System.out.println(statement.getAbsolutePath());
		try (BufferedReader reader = new BufferedReader(new FileReader(statement.getAbsolutePath()))) {
			logger.info("Nationwide Reader made successfully.");
			return reader;
		} catch (FileNotFoundException e) {
			logger.error("File not found when creating new BufferedReader:" + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException when creating new BufferedReader:" + e.getMessage());
		}
		return null;
	}

	/**
	 * Creates an empty Transaction object.
	 * 
	 * @return an empty Transaction object
	 */

	public Transaction createEmptyTransaction() {
		return new Transaction();
	}
}
