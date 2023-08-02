package excelWriter.workbook.load;

import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import excelWriter.workbook.CategorySheet;
import excelWriter.workbook.SummarySheet;
import excelWriter.workbook.TransactionWorkbook;

/**
 * 
 * The TransactionWorkbookSummaryLoader class is responsible for loading the
 * summary sheet in a transaction workbook.
 * 
 * It takes a TransactionWorkbook object, row and column start numbers as input.
 * 
 * It provides methods to initialize the summary sheet, create a summary sheet
 * writer, and load the summary table.
 * 
 * @see TransactionWorkbook
 * @see SummarySheet
 * @see SummarySheetWriter
 * @author LORD GABRIEL
 */

public class TransactionWorkbookSummaryLoader {

	private TransactionWorkbook tw;
	private SummarySheetWriter summaryWriter;
	private int rowStartNum;
	private int colStartNum;

	/*
	 * =============CONSTRUCTORS================
	 */

	/**
	 * 
	 * Constructs a TransactionWorkbookSummaryLoader object with the specified
	 * transaction workbook, row start number, and column start number.
	 * 
	 * @param tw          the transaction workbook to load the summary sheet into
	 * @param rowStartNum the starting row number for the summary sheet
	 * @param colStartNum the starting column number for the summary sheet
	 */

	public TransactionWorkbookSummaryLoader(TransactionWorkbook tw, int rowStartNum, int colStartNum) {
		this.tw = tw;
		this.rowStartNum = rowStartNum;
		this.colStartNum = colStartNum;
	}

	/*
	 * =============GETTERS====================
	 */

	/**
	 * 
	 * Returns the transaction workbook associated with the summary loader.
	 * 
	 * @return the transaction workbook
	 */

	public TransactionWorkbook getTW() {
		return tw;
	}

	/**
	 * 
	 * Returns the summary sheet writer.
	 * 
	 * @return the summary sheet writer
	 */

	public SummarySheetWriter getSummarySheetWriter() {
		return summaryWriter;
	}

	/**
	 * 
	 * Returns the starting row number for the summary sheet.
	 * 
	 * @return the starting row number
	 */

	public int getRowStartNum() {
		return rowStartNum;
	}

	/**
	 * 
	 * Returns the starting column number for the summary sheet.
	 * 
	 * @return the starting column number
	 */

	public int getColStartNum() {
		return colStartNum;
	}

	/*
	 * ===========SETTERS======================
	 */

	/**
	 * 
	 * Sets the transaction workbook for the summary loader.
	 * 
	 * @param tw the transaction workbook to set
	 */

	public void setTW(TransactionWorkbook tw) {
		this.tw = tw;
	}

	/**
	 * 
	 * Sets the summary sheet writer.
	 * 
	 * @param summaryWriter the summary sheet writer to set
	 */

	public void setSummarySheetWriter(SummarySheetWriter summaryWriter) {
		this.summaryWriter = summaryWriter;
	}

	/**
	 * 
	 * Sets the starting row number for the summary sheet.
	 * 
	 * @param rowStartNum the starting row number to set
	 */

	public void setRowStartNum(int rowStartNum) {
		this.rowStartNum = rowStartNum;
	}

	/**
	 * 
	 * Sets the starting column number for the summary sheet.
	 * 
	 * @param colStartNum the starting column number to set
	 */

	public void setColStartNum(int colStartNum) {
		this.colStartNum = colStartNum;
	}

	/*
	 * ===========METHODS=====================
	 */

	/**
	 * 
	 * Initializes the summary sheet by creating a SummarySheet object in the
	 * transaction workbook.
	 */

	public void initialiseSummarySheet() {
		String startDate = tw.getStartDate();
		String endDate = tw.getEndDate();
		Workbook workbook = tw.getWorkbook();
		List<CategorySheet> categorySheets = tw.getCategorySheets();

		tw.setSummarySheet(new SummarySheet(workbook, tw.getColConfig(), categorySheets, startDate, endDate));
		tw.getSummarySheet().loadTitleStyle();
		tw.getSummarySheet().loadtotalStyle();
		tw.getSummarySheet().loadMonthStyle();
		tw.getSummarySheet().loadCellDataStyle();
		tw.getSummarySheet().loadCellSumStyle();
		tw.getSummarySheet().loadEmptyTotalSheet();
	}

	/**
	 * 
	 * Loads the summary sheet writer for the summary loader.
	 */
	public void loadSummarySheetWriter() {
		SummarySheetWriter summaryWriter = new SummarySheetWriter(tw.getSummarySheet());
		setSummarySheetWriter(summaryWriter);
	}

	/**
	 * 
	 * Loads the summary table within the transaction workbook.
	 */
	public void loadSummaryTable() {
		getSummarySheetWriter().createSummaryTable(rowStartNum, colStartNum);
		tw.getSummarySheet().resizeAllColumns(rowStartNum);
	}

}
