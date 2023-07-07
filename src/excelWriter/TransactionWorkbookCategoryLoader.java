package excelWriter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.IndexedColors;

import regex.RegexMethods;
import sqliteData.TableCategory;
import sqliteData.TableInboundReader;
import sqliteData.TableOutboundReader;
import transactions.Transaction;
import config.ColourConfig;

public class TransactionWorkbookCategoryLoader {

	private TransactionWorkbook tw;
	private int rowStartNum;
	private int colStartNum;
	private RegexMethods regex;
	private Logger logger;

	/*
	 * ============ CONSTRUCTORS ===================
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

	public TransactionWorkbook getTransactionWorkbook() {
		return tw;
	}

	public int getRowStartNum() {
		return rowStartNum;
	}

	public int getColStartNum() {
		return colStartNum;
	}

	public RegexMethods getRegex() {
		return regex;
	}

	/*
	 * ============ METHODS ======================
	 */

	// Checks table gap is large enough
	public boolean checkTableGap(int tableGap) {
		return (tableGap > 4);
	}

	private CategorySheet createCategorySheet(String category) {
		String sheetName = regex.removeAllNonAlphaNumeric(category);
		return new CategorySheet(tw.getWorkbook(), sheetName, rowStartNum, colStartNum);
	}

	private CategorySheetInboundWriter createCategorySheetInboundWriter(CategorySheet categorySheet) {
		return new CategorySheetInboundWriter(categorySheet);
	}

	private CategorySheetOutboundWriter createCategorySheetOutboundWriter(CategorySheet categorySheet) {
		return new CategorySheetOutboundWriter(categorySheet);
	}

	private Map<Integer, List<Transaction>> returnInboundTransactionMap(TableInboundReader tIR, String startDate,
			String endDate, String category) {
		Map<Integer, List<Transaction>> inboundTransactionMap = tIR.extractInboundTransactionMap(startDate, endDate,
				category);
		return inboundTransactionMap;
	}

	private Map<Integer, List<Transaction>> returnOutboundTransactionMap(TableOutboundReader tOR, String startDate,
			String endDate, String category) {
		Map<Integer, List<Transaction>> outboundTransactionMap = tOR.extractOutboundTransactionMap(startDate, endDate,
				category);
		return outboundTransactionMap;
	}
	
	private Set<Integer> returnUnionMonths(Set<Integer> inMonths, Set<Integer> outMonths) {
		Set<Integer> unionMonths = new HashSet<>(inMonths);
		unionMonths.addAll(outMonths);
		return unionMonths;
	}
	
	public Transaction[] readTransactionMap(Map<Integer,List<Transaction>> transactionMap, int month ) {
		return transactionMap.get(month).toArray(new Transaction[0]);
	}
	// Loads all distinct categorySheets for date range, for each category.
	public void loadCategorySheets(int tableGap) {
		if (!checkTableGap(tableGap)) {
			System.out.println("Failed to load categorySheets, as tableGap must be at least 5");
			logger.error("Failed to load categorySheets, as tableGap must be at least 5: tableGap =" + tableGap);
		}		
		//Loading an empty categorySheets list
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
			Set<Integer> unionMonths = returnUnionMonths(inMonths,outMonths);
			// Below alphanumeric sheetname, since cannot have sheets with non alphanumeric
			// characters in xls
			CategorySheet categorySheet = createCategorySheet(category);
			CategorySheetInboundWriter csIW = createCategorySheetInboundWriter(categorySheet);
			CategorySheetOutboundWriter csOW = createCategorySheetOutboundWriter(categorySheet);
			categorySheet.loadCellDataStyle();
			categorySheet.loadCellHeaderStyle(IndexedColors.LIGHT_GREEN);
			categorySheet.loadCellSumStyle(IndexedColors.GREY_25_PERCENT);
			categorySheet.loadEmptyMonthPaidInMap();
			categorySheet.loadEmptyMonthPaidOutMap();
			
			// Load in ColourConfig troubles
			ColourConfig config = tw.getColConfig();
			// TO DO - load in relevant colour for given category for headerStyle

			// and load in relevant colour for each sum
			int startRow = categorySheet.getRowStartNum();
			int startCol = categorySheet.getColStartNum();
			// Iterate through the union of inMonths and outMonthsfor this category
			for (int month : unionMonths) {
				// Create an inbound table for this category if
				// there were transactions for this category during that month,
				// spacing them apart by adding tableGap to startCol value

				if (inMonths.contains(month)) {
					Transaction[] inTransactions = readTransactionMap(inboundTransactionMap,month);
					csIW.createTransactionTable(month, inTransactions, startRow, startCol);
					startCol += tableGap;
				}

				// Create an outbound table for this category if
				// there were transactions for this category during that month,
				// spacing them apart by adding tableGap to startCol value

				if (outMonths.contains(month)) {
					Transaction[] outTransactions = readTransactionMap(outboundTransactionMap,month);
					csOW.createTransactionTable(month, outTransactions, startRow, startCol);
					startCol += tableGap;
				}

			}
			categorySheet.resizeAllColumns(startRow);
			tw.addCategorySheet(categorySheet);
			logger.info("Generated categorySheet:"+categorySheet.getCategoryName());
		}
	}
}
