import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.text.DecimalFormat;

import regex.RegexMethods;

class RegexMethodsTest {
	Random rand = new Random();
	RegexMethods regex = new RegexMethods();

	
	@Test
	void testRemoveQuotationMarks() {
		String input = "Hello\" guys what's going on\"";
		String expectedOutput = "Hello guys what's going on";
		
		String actualOutput = regex.removeQuotationMarks(input);
		Assertions.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	void testRemoveWebsiteReferences() {

		String input = "Visit our website at www.example.com for more information";
		String expectedOutput = "Visit our website at example for more information";

		String actualOutput = regex.removeWebsiteReferences(input);
		Assertions.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	void testRemoveAlphaNumeric() {

		String test = "** hello* m&^y name";
		String expected = " hello my name";

		String actualOutput = regex.removeAllNonAlphaNumeric(test);

		Assertions.assertEquals(expected, actualOutput);
	}

	@Test
	void testRemoveConsecutiveNumbers() {
		int block1 = rand.nextInt(0, 1000000);
		int block2 = rand.nextInt(0, 1000000);

		String tex = "Test " + block1 + " blocks" + block2;
		String expectedOutput = "Test  blocks" + block2;

		String actualOutput = regex.removeConsecutiveNumbers(tex);

		Assertions.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	void testRemovePostcodes() {
		String postCodeString = "Coventry CV4 7EY";
		String expectedOutput = "Coventry ";

		String actualOutput = regex.removePostcodes(postCodeString);
		Assertions.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	void testConvertPrice() {
		int decimal = rand.nextInt(0, 100);
		int whole = rand.nextInt(0, 1000000);
		
		String price = "£" + whole + "." + decimal;
		double convertedPrice = regex.convertPrice(price);
		
		double solution = whole + 0.01 * decimal;
		
	    DecimalFormat df = new DecimalFormat("#.00"); // Format to two decimal places
	    String expectedFormatted = df.format(solution);
	    String actualFormatted = df.format(convertedPrice);

		Assertions.assertEquals(expectedFormatted, actualFormatted);
	}

	@Test
	void testGenerateUUID() {
		String id_1 = regex.generateTransactionUUID("date", "trType", "description", 10.00,
				10.00, 10.00);
		String id_2 = regex.generateTransactionUUID("date", "trType", "description", 10.00,
				10.00, 11.00);
		String id_3 = regex.generateTransactionUUID("date", "trType", "description", 10.00,
				10.00, 11.00);
		Assertions.assertNotEquals(id_1, id_2);
		Assertions.assertEquals(id_2, id_3);
	}
	
	@Test
	void testProcessDescription() {
		String test = "TESCO STORES 2325 COVENTRY CV4 8EY GB";
		String expected = "TESCO STORES  COVENTRY  GB";
		String output = regex.processDescription(test);

		String test2 = "SQ *KOKORO BRACKNELL Bracknell RG1 8GB APPLEPAY 7391";
		String expected2 = "SQ KOKORO BRACKNELL Bracknell  APPLEPAY";
		String output2 = regex.processDescription(test2);

		Assertions.assertEquals(expected, output);
		Assertions.assertEquals(expected2, output2);

	}
	
	@Test
	void testConvertDate() {
		String input = "03 Jan 2000";
		String expected="2000-01-03";
		String output = regex.convertDDMMYYYYDate(input);
		
		Assertions.assertEquals(output, expected);
	}
}
