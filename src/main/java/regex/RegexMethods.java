package regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * 
 * The RegexMethods class provides various methods for manipulating and
 * processing strings using regular expressions. It is intended to be used for
 * processing/formatting Transactions.
 * 
 * It includes methods for removing quotation marks, website references,
 * non-alphanumeric characters, * consecutive numbers, and UK postcodes. It also
 * includes methods for generating unique UUIDs and converting
 * 
 * money strings to doubles. Additionally, it provides methods for formatting
 * dates and processing descriptions.
 */

public class RegexMethods {

	/*
	 * ================== REGEX REMOVERS =================
	 */
	/**
	 * 
	 * Removes all quotation marks from the given input string.
	 * 
	 * @param input the input string
	 * @return the string with quotation marks removed
	 */
	public String removeQuotationMarks(String input) {
		// Matches double quotation mark. Any character after it as combination
		String regex = "\"";
		String result = input.replaceAll(regex, "");
		return result;
	}

	/**
	 * 
	 * Removes all website references from the given input string. E.g. www. https:,
	 * .co/uk
	 * 
	 * @param input the input string
	 * 
	 * @return the string with website references removed
	 */
	public String removeWebsiteReferences(String input) {
		// matches website references
		String regex = "(www\\.|https?://|\\.co\\.\\w{2,3}|\\.\\w{2,3})";
		// Remove website references using the regex pattern
		String result = input.replaceAll(regex, "");

		return result;
	}

	/**
	 * 
	 * Removes all non-alphanumeric characters from the given input string.
	 * 
	 * @param input the input string
	 * 
	 * @return the string with non-alphanumeric characters removed
	 */
	public String removeAllNonAlphaNumeric(String input) {
		String regex = "[^a-zA-Z\\d\\s]+";

		String result = input.replaceAll(regex, "");

		return result;
	}

	/**
	 * 
	 * Removes consecutive numbers separated by spaces from the given input string.
	 * 
	 * @param input the input string
	 * 
	 * @return the string with consecutive numbers removed
	 */
	public String removeConsecutiveNumbers(String input) {
		// Regex pattern to match groups of numbers separated by spaces
		String regex = "\\b\\d+(?:\\s\\d+)*\\b";

		// Remove number groups using the regex pattern
		String result = input.replaceAll(regex, "");

		return result;
	}

	/**
	 * 
	 * Removes UK postcodes from the given input string.
	 * 
	 * @param input the input string
	 * 
	 * @return the string with UK postcodes removed
	 */
	public String removePostcodes(String input) {
		// Regex pattern to match postcodes
		// first part of postcode has 2-4 characters in length

		String regex = "\\b[A-Z]{1,2}\\d{1,2}(\\s*\\d{1,2})?[A-Z]{1,2}\\b";

		// Remove postcodes using the regex pattern
		String result = input.replaceAll(regex, "");

		return result;
	}

	/*
	 * ================== CONVERTERS/GENERATORS =================
	 */

	/**
	 * 
	 * Generates a unique UUID based on the given transaction data.
	 * 
	 * @param date            the date of the transaction
	 * @param transactionType the type of the transaction
	 * @param description     the description of the transaction
	 * @param paidOut         the amount paid out
	 * @param paidIn          the amount paid in
	 * @param balance         the balance
	 * @return the generated UUID
	 */
	public String generateTransactionUUID(String date, String transactionType, String description, double paidOut,
			double paidIn, double balance) {
		String concatenatedData = date + transactionType + description + paidOut + paidIn + balance;
		UUID uuid = UUID.nameUUIDFromBytes(concatenatedData.getBytes());
		return uuid.toString();
	}

	/**
	 * 
	 * Converts a money string to a double by removing non-numeric characters.
	 * 
	 * @param moneyString the money string
	 * @return the converted amount as a double
	 */

	public double convertPrice(String moneyString) {
		if (moneyString.isEmpty() || moneyString.isBlank() || moneyString.equals("")) {
			return 0;
		}
		String numericString = moneyString.replaceAll("[^\\d.]", "");
		double amount = Double.parseDouble(numericString);
		return amount;
	}

	/**
	 * 
	 * Parses the amount string by removing quotation marks and converting it to a
	 * double.
	 * 
	 * @param amount the amount string
	 * @return the parsed amount as a double
	 */

	public double parseAmount(String amount) {
		String amountNoQuotations = removeQuotationMarks(amount);
		return convertPrice(amountNoQuotations);
	}

	/**
	 * 
	 * Converts a date string in the format "DDMMMYYYY" to the format "yyyy-MM-dd".
	 * 
	 * @param date the date string in "DDMMMYYYY" format
	 * @return the formatted date string in "yyyy-MM-dd" format
	 */
	public String convertDDMMYYYYDate(String date) {
		String removeQuo = removeQuotationMarks(date);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate outDate = LocalDate.parse(removeQuo, inputFormatter);
		return outDate.format(outputFormatter);
	}

	/**
	 * 
	 * Processes a description String by removing website references, non-alphanumeric
	 * characters,
	 * 
	 * consecutive numbers, and UK postcodes, and trims leading and trailing
	 * whitespace.
	 * 
	 * @param description the description string
	 * 
	 * @return the processed description string
	 */

	public String processDescription(String description) {
		String removedWebsiteReferences = removeWebsiteReferences(description);
		String removeAlphaNumeric = removeAllNonAlphaNumeric(removedWebsiteReferences);
		String removedConsecutiveNums = removeConsecutiveNumbers(removeAlphaNumeric);
		String removedPostcodes = removePostcodes(removedConsecutiveNums);

		// trim removes leading or ending whitespaces
		String result = removedPostcodes.trim();
		return result;
	}

}
