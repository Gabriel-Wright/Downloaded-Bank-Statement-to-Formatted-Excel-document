package statementReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.AppConfig;
import sqliteData.TableCategoryReader;
import sqliteData.TableCategoryWriter;
import transactions.Transaction;
import regex.RegexMethods;

public class NationwideCSVReader extends StatementReader {

	/*
	 * ===================CONSTRUCTORS ====================
	 */

	public NationwideCSVReader(AppConfig config) {
		setConfig(config);
		setConfig_File(config.getConfigFile());
	}

	/*
	 * ================== METHODS =================
	 */

	// Used as a check for expected formatting of selected statement.
	public boolean checkNationWideHeaders(String[] headers) {
		String[] expectedHeaders = { "\"Date", "Transaction type", "Description", "Paid out", "Paid in", "Balance\"" };
		return Arrays.equals(headers, expectedHeaders);
	}

	// Input a nationwide statement, tableCategoryReader, tableCategoryWriter and
	// regexMethod to process
	// all bank transactions from this file into an array of transactions.
	public Transaction[] convertNationWideStatement(File statement, TableCategoryReader tableReader,
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
				Transaction transaction = processNationwideTransaction(fields, tableReader, tableWriter, regex);
				transactions.add(transaction);
			}
			numTransactions = transactions.size();
			logger.info("Statement entries from NationwideCSV transferred to an array of Transactions of size "
					+ numTransactions);
		} catch (IOException e) {
			logger.error("Error when converting expected CSV statement to an array of transactions:" + e.getMessage());
		}
		return transactions.toArray(new Transaction[0]);
	}

	// Formats a transaction properly, and adds its corresponding category to the
	// categoryTable
    public Transaction processNationwideTransaction(String[] fields, TableCategoryReader tableReader,
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

 

	// Checks whether transaction already has an appropriate category within the
	// tableReader, if it doesn't assign a new one.
	public String retrieveCategory(Transaction transaction, TableCategoryReader tableReader,
			TableCategoryWriter tableWriter) {
		String readCategory = tableReader.readCategory(transaction.getProcessedDescription());
		if (readCategory == null) {
			String inOrOut;
			// Would be ideal to complete a switch here? - but cannot work with doubles?
			if (transaction.getPaidIn() > 0) {
				inOrOut = "Incoming";
			} else {
				inOrOut = "Outgoing";
			}
			readCategory = tableWriter.assignCategoryToTransaction(transaction.getProcessedDescription(), inOrOut);
		}

		return readCategory;
	}
	
	public Transaction createEmptyTransaction() {
		return new Transaction();
	}
}
