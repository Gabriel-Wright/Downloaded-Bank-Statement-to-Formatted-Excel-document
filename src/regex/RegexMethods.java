package regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public class RegexMethods {
	
	/*
	 * ================== REGEX REMOVERS =================
	 */
	// Removes all quotation marks
	public String removeQuotationMarks(String input) {
		// Matches double quotation mark. Any character after it as combination
		String regex = "\"";
		String result = input.replaceAll(regex, "");
		return result;
	}
	
	//Removes all website references from String
	public String removeWebsiteReferences(String input) {
		//matches website references
		String regex = "(www\\.|https?://|\\.co\\.\\w{2,3}|\\.\\w{2,3})";
	    // Remove website references using the regex pattern
	    String result = input.replaceAll(regex, "");

	    return result;
	}

	//Removes all  non alpha numeric characters from a given string
	public String removeAllNonAlphaNumeric(String input) {
		String regex ="[^a-zA-Z\\d\\s]+";
		
		String result=input.replaceAll(regex, "");
		
		return result;
	}

	//Removes all consecutive numbers separated by spaces from a given string
	public String removeConsecutiveNumbers(String input) {
	    // Regex pattern to match groups of numbers separated by spaces
	    String regex = "\\b\\d+(?:\\s\\d+)*\\b";

	    // Remove number groups using the regex pattern
	    String result = input.replaceAll(regex, "");

	    return result;
	}

	//Removes all UK Postcode references from a given string
	public String removePostcodes(String input) {
	    // Regex pattern to match postcodes
		//first part of postcode has 2-4 characters in length
		
	    //String regex = "\\b[A-Z]{1,2}\\d{0-2}(\\s*\\d{1,2})?[A-Z]{1,2}\\b";
		String regex = "\\b[A-Z]{1,2}\\d{1,2}(\\s*\\d{1,2})?[A-Z]{1,2}\\b";

	    // Remove postcodes using the regex pattern
	    String result = input.replaceAll(regex, "");

	    return result;
	}

	/*
	 * ================== CONVERTERS/GENERATORS =================
	 */

	// Generates a unique UUID
	public String generateTransactionUUID(String date, String transactionType, String description, double paidOut,
			double paidIn, double balance) {
		String concatenatedData = date + transactionType + description + paidOut + paidIn + balance;
		UUID uuid = UUID.nameUUIDFromBytes(concatenatedData.getBytes());
		return uuid.toString();
	}

	// Removes all non-numeric characters from money String, so that they can be
	// converted to a double.
	public double convertPrice(String moneyString) {
		if (moneyString.isEmpty() || moneyString.isBlank() || moneyString.equals("")) {
			return 0;
		}
		String numericString = moneyString.replaceAll("[^\\d.]", "");
		double amount = Double.parseDouble(numericString);
		return amount;
	}

	// Formats DDMMMYYYYDate into yyyy-MM-dd, e.g. 03 Jan 2000 becomes 2000-01-03
	public String convertDDMMYYYYDate(String date) {
		String removeQuo = removeQuotationMarks(date);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate outDate = LocalDate.parse(removeQuo, inputFormatter);
		return outDate.format(outputFormatter);
	}
		
	public String processDescription(String description) {
		String removedWebsiteReferences = removeWebsiteReferences(description);
		String removeAlphaNumeric		= removeAllNonAlphaNumeric(removedWebsiteReferences);
		String removedConsecutiveNums	= removeConsecutiveNumbers(removeAlphaNumeric);
		String removedPostcodes 		= removePostcodes(removedConsecutiveNums);
		
		//trim removes leading or ending whitespaces
		String result = removedPostcodes.trim();
		return result;
	}

}
