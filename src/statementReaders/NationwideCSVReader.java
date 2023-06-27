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
				Transaction transaction = singleNationwideTransactionProcess(fields, tableReader, tableWriter, regex);
				transactions.add(transaction);
			}
			numTransactions = transactions.size();
			logger.info("Statement entries from NationwideCSV transferred to an array of Transactions of size "
					+ numTransactions);
		} catch (IOException e) {
			logger.error("Error when converting expected CSV statement to an array of transactions:" + e.getMessage());
		}
		return null;
	}

	// Formats a transaction properly, and adds its corresponding category to the
	// categoryTable
	public Transaction singleNationwideTransactionProcess(String[] fields, TableCategoryReader tableReader,
			TableCategoryWriter tableWriter, RegexMethods regex) {
		// Creating empty transaction which we will add the data from fields into.
		Transaction transaction = new Transaction();
		// Set date of transaction, converting from expected DDMMMYYYY format to
		// DD-MM-YYYY
		String date = fields[0];
		String convertedDate = regex.convertDDMMYYYYDate(date);
		transaction.setDate(regex.convertDDMMYYYYDate(convertedDate));

		// Set transaction type of transaction
		String trType = fields[1];
		transaction.setTrType(trType);

		// Set raw description of transaction
		String rawDescription = fields[2];
		transaction.setRawDescription(rawDescription);

		// Set processed description of transaction
		String processedDescription = regex.processDescription(rawDescription);
		transaction.setProcessedDescription(processedDescription);

		// Set paidOut value of transaction
		String paidOut = fields[3];
		String paidOutNoQuotations = regex.removeQuotationMarks(paidOut);
		Double paidOutDouble = regex.convertPrice(paidOutNoQuotations);
		transaction.setPaidOut(paidOutDouble);

		// Set paidIn value of transaction
		String paidIn = fields[4];
		String paidInNoQuotations = regex.removeQuotationMarks(paidIn);
		Double paidInDouble = regex.convertPrice(paidInNoQuotations);
		transaction.setPaidIn(paidInDouble);

		// Set balance value of transaction
		String balance = fields[5];
		String balanceNoQuotations = regex.removeQuotationMarks(balance);
		Double balanceDouble = regex.convertPrice(balanceNoQuotations);
		transaction.setBalance(balanceDouble);

		// Set uuID
		String uuid = regex.generateTransactionUUID(convertedDate, trType, rawDescription, paidOutDouble, paidInDouble,
				balanceDouble);
		transaction.setID(uuid);

		// Set category
		String assignedCategory = retrieveCategory(transaction, tableReader, tableWriter);
		transaction.setCategory(assignedCategory);

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

}
