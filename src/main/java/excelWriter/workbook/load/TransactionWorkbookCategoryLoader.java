package excelWriter.workbook.load;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.apache.logging.log4j.Logger;
import config.color.ColorCodingConfig;
import excelWriter.workbook.CategorySheet;
import excelWriter.workbook.TransactionWorkbook;
import regex.RegexMethods;
import sqliteData.tables.TableCategory;
import sqliteData.tables.readers.TableInboundReader;
import sqliteData.tables.readers.TableOutboundReader;
import transactions.Transaction;

/**
 * 
 * The TransactionWorkbookCategoryLoader class is responsible for loading
 * category sheets in a transaction workbook.
 * 
 * It takes a TransactionWorkbook object, row and column start numbers, and a
 * RegexMethods object as input.
 * 
 * It provides methods to load category sheets, create category sheets, create
 * category sheet writers, and read transaction maps.
 * 
 * @see TransactionWorkbook
 * @see CategorySheets
 * @see CategorySheetWriter
 * @see RegexMethods
 * @author LORD GABRIEL
 */

public class TransactionWorkbookCategoryLoader {

	private TransactionWorkbook tw;
	private int rowStartNum;
	private int colStartNum;
	private RegexMethods regex;
	private Logger logger;

	/*
	 * ============ CONSTRUCTORS ===================
	 */
	/**
	 * 
	 * Constructs a TransactionWorkbookCategoryLoader object with the specified
	 * transaction workbook, row start number, column start number, and regex
	 * methods.
	 * 
	 * @param tw          - the transaction workbook to load category sheets into
	 * @param rowStartNum - the starting row number for the category sheets
	 * @param colStartNum - the starting column number for the category sheets
	 * @param regex       - the regex methods object for text manipulation
	 */

	public TransactionWorkbookCategoryLoader(TransactionWorkbook tw, int rowStartNum, int colStartNum,
			RegexMethods regex) {
		this.tw = tw;
		this.rowStartNum = rowStartNum;
		this.colStartNum = colStartNum;
		this.regex = regex;
		this.logger = tw.getLogger();

	}

	/*
	 * ============ GETTERS ====================
	 */

	/**
	 * 
	 * Returns the transaction workbook associated with the category loader.
	 * 
	 * @return the transaction workbook
	 */

	public TransactionWorkbook getTransactionWorkbook() {
		return tw;
	}

	/**
	 * 
	 * Returns the starting row number for the category sheets.
	 * 
	 * @return the starting row number
	 */

	public int getRowStartNum() {
		return rowStartNum;
	}

	/**
	 * 
	 * Returns the starting column number for the category sheets.
	 * 
	 * @return the starting column number
	 */

	public int getColStartNum() {
		return colStartNum;
	}

	/**
	 * 
	 * Returns the regex methods object.
	 * 
	 * @return the regex methods object
	 */

	public RegexMethods getRegex() {
		return regex;
	}

	/*
	 * ============ METHODS ======================
	 */
	
	/*
	 * 
	 */
	
	public void loadStyles(CategorySheet categorySheet, ColorCodingConfig colConfig) {
		categorySheet.loadCellDataStyle();
		categorySheet.loadCellSumStyle();
	}
	/**
	 * 
	 * Checks if the table gap is large enough to load the category sheets.
	 * 
	 * @param tableGap - the table gap value to check
	 * @return true if the table gap is larger than 4, false otherwise
	 */
	public boolean checkTableGap(int tableGap) {
		return (tableGap > 4);
	}

	/**
	 * 
	 * Creates a new category sheet with the given category name.
	 * 
	 * @param category - the category name
	 * @return the created category sheet
	 */

	private CategorySheet createCategorySheet(String category) {
		String sheetName = regex.removeAllNonAlphaNumeric(category);
		return new CategorySheet(tw.getWorkbook(), tw.getColConfig(), sheetName, rowStartNum, colStartNum);
	}

	/**
	 * 
	 * Creates a new CategorySheetInboundWriter object for the given category sheet.
	 * 
	 * @param categorySheet - the category sheet to create the writer for
	 * @return the created CategorySheetInboundWriter
	 */

	private CategorySheetInboundWriter createCategorySheetInboundWriter(CategorySheet categorySheet) {
		return new CategorySheetInboundWriter(categorySheet);
	}

	/**
	 * 
	 * Creates a new CategorySheetOutboundWriter object for the given category
	 * sheet.
	 * 
	 * @param categorySheet - the category sheet to create the writer for
	 * @return the created CategorySheetOutboundWriter
	 */

	private CategorySheetOutboundWriter createCategorySheetOutboundWriter(CategorySheet categorySheet) {
		return new CategorySheetOutboundWriter(categorySheet);
	}

	/**
	 * 
	 * Returns the inbound transaction map for a specific category and date range
	 * from the TableInboundReader.
	 * 
	 * @param tIR       - the TableInboundReader object to extract the transaction
	 *                  map from
	 * @param startDate - the start date of the transaction range
	 * @param endDate   - the end date of the transaction range
	 * @param category  - the category to filter the transactions by
	 * @return the inbound transaction map
	 */

	private Map<Integer, List<Transaction>> returnInboundTransactionMap(TableInboundReader tIR, String startDate,
			String endDate, String category) {
		Map<Integer, List<Transaction>> inboundTransactionMap = tIR.extractInboundTransactionMap(startDate, endDate,
				category);
		return inboundTransactionMap;
	}

	/**
	 * 
	 * Returns the outbound transaction map for a specific category and date range
	 * from the TableOutboundReader.
	 * 
	 * @param tOR       - the TableOutboundReader object to extract the transaction
	 *                  map from
	 * @param startDate - the start date of the transaction range
	 * @param endDate   - the end date of the transaction range
	 * @param category  - the category to filter the transactions by
	 * @return the outbound transaction map
	 */

	private Map<Integer, List<Transaction>> returnOutboundTransactionMap(TableOutboundReader tOR, String startDate,
			String endDate, String category) {
		Map<Integer, List<Transaction>> outboundTransactionMap = tOR.extractOutboundTransactionMap(startDate, endDate,
				category);
		return outboundTransactionMap;
	}

	/**
	 * 
	 * Returns the union of two sets of months.
	 * 
	 * @param inMonths  - the set of months for inbound transactions
	 * @param outMonths - the set of months for outbound transactions
	 * @return the union of the two sets of months
	 */

	private Set<Integer> returnUnionMonths(Set<Integer> inMonths, Set<Integer> outMonths) {
		Set<Integer> unionMonths = new HashSet<>(inMonths);
		unionMonths.addAll(outMonths);
		return unionMonths;
	}

	/**
	 * 
	 * Reads the transaction map for a specific month from the given transaction
	 * map.
	 * 
	 * @param transactionMap the transaction map to read from
	 * @param month          the month to retrieve transactions for
	 * @return an array of transactions for the specified month
	 */

	public Transaction[] readTransactionMap(Map<Integer, List<Transaction>> transactionMap, int month) {
		return transactionMap.get(month).toArray(new Transaction[0]);
	}

	/**
	 * 
	 * Loads all distinct category sheets for the given date range and each
	 * category.
	 * 
	 * @param tableGap - the table gap value to use when spacing the tables in the
	 *                 category sheets
	 */
	public void loadCategorySheets(int tableGap) {
		if (!checkTableGap(tableGap)) {
			System.out.println("Failed to load categorySheets, as tableGap must be at least 5");
			String log = String.format("Failed to load categorySheets, as tableGap must be at least 5. tableGap = %s",
					tableGap);
			logger.error(log);
		}
		// Loading an empty categorySheets list
		tw.loadEmptyCategorySheets();
		// Getting relevant tables needed to loadCategorySheets
		TableCategory tC = tw.getTC();
		TableInboundReader tIR = tw.getTIR();
		TableOutboundReader tOR = tw.getTOR();

		// Getting date range
		String startDate = tw.getStartDate();
		String endDate = tw.getEndDate();

		tC.refreshCategoryOptions();
		String[] categories = tC.getCategoryMenu().getOptions();

		for (String category : categories) {
			// Extract inboundTransactionMap & outboundTransactionMap from
			// TableInbound/OutboundReaders
			Map<Integer, List<Transaction>> inboundTransactionMap = returnInboundTransactionMap(tIR, startDate, endDate,
					category);
			Map<Integer, List<Transaction>> outboundTransactionMap = returnOutboundTransactionMap(tOR, startDate,
					endDate, category);
			// Checking months where inbound/outbound transactions for desired category were
			// found
			Set<Integer> inMonths = inboundTransactionMap.keySet();
			Set<Integer> outMonths = outboundTransactionMap.keySet();
			Set<Integer> unionMonths = returnUnionMonths(inMonths, outMonths);
			// Below alphanumeric sheetname, since cannot have sheets with non alphanumeric
			// characters in xls
			CategorySheet categorySheet = createCategorySheet(category);
			CategorySheetInboundWriter csIW = createCategorySheetInboundWriter(categorySheet);
			CategorySheetOutboundWriter csOW = createCategorySheetOutboundWriter(categorySheet);
						
			categorySheet.loadEmptyMonthPaidInMap();
			categorySheet.loadEmptyMonthPaidOutMap();

			//Load relevant styles of categorySheet
			categorySheet.loadCellDataStyle();
			categorySheet.loadCellSumStyle();
			categorySheet.loadCellHeaderStyle(category);
			
			// and load in relevant color for each sum
			int startRow = categorySheet.getRowStartNum();
			int startCol = categorySheet.getColStartNum();
			// Iterate through the union of inMonths and outMonthsfor this category
			for (int month : unionMonths) {
				// Create an inbound table for this category if
				// there were transactions for this category during that month,
				// spacing them apart by adding tableGap to startCol value

				if (inMonths.contains(month)) {
					Transaction[] inTransactions = readTransactionMap(inboundTransactionMap, month);
					csIW.createTransactionTable(month, inTransactions, startRow, startCol);
					startCol += tableGap;
				}

				// Create an outbound table for this category if
				// there were transactions for this category during that month,
				// spacing them apart by adding tableGap to startCol value

				if (outMonths.contains(month)) {
					Transaction[] outTransactions = readTransactionMap(outboundTransactionMap, month);
					csOW.createTransactionTable(month, outTransactions, startRow, startCol);
					startCol += tableGap;
				}

			}
			//categorySheet.resizeAllColumns(startRow);
			tw.addCategorySheet(categorySheet);
			String log = String.format("Generated categorySheet: %s", categorySheet.getCategoryName());
			logger.info(log);
		}
	}
}
