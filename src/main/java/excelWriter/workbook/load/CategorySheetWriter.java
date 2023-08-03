package excelWriter.workbook.load;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import excelWriter.workbook.CategorySheet;
import excelWriter.workbook.StatementSheet;
import excelWriter.workbook.TransactionWorkbook;
import transactions.Transaction;

/**
 * 
 * The CategorySheetWriter class is an abstract class that provides methods for
 * writing transaction data to a category sheet in an Excel workbook. It
 * includes abstract methods that need to be implemented by subclasses.
 * 
 * @see CategorySheet
 * @see StatementSheet
 * @see TransactionWorkbook
 * @author LORD GABRIEL
 */

public abstract class CategorySheetWriter {

	private CategorySheet categorySheet;
	protected Logger logger;

	/*
	 * =============GETTERS===================
	 */

	/**
	 * Returns the category sheet associated with the writer.
	 * 
	 * @return the category sheet
	 */

	public CategorySheet getCategorySheet() {
		return categorySheet;
	}

	/**
	 * Returns the logger used for logging in the writer.
	 * 
	 * @return the logger
	 */

	public Logger getLogger() {
		return logger;
	}

	/*
	 * ===============ABSTRACT METHODS==============
	 */

	/**
	 * Adds the cell reference of a paid cell to the map of month-paid-in sum cell
	 * references.
	 * 
	 * @param month - the month of the paid cell
	 * @param x     - the row index of the paid cell
	 * @param y     - the column index of the paid cell
	 */

	public abstract void addToPaidCellRefMap(int month, int x, int y);

	/**
	 * Adds the cell reference of a paid cell to the map of month-paid-in sum cell
	 * references.
	 * 
	 * @param month   - the month of the paid cell
	 * @param cellRef - the cell reference of the paid cell
	 */

	public abstract void addToPaidCellRefMap(int month, CellReference cellRef);

	/**
	 * Inserts the title cell for the specified month in the category sheet.
	 * 
	 * @param month       - the month of the title cell
	 * @param startRow    - the starting row index of the title cell
	 * @param startColumn - the starting column index of the title cell
	 * @return the row index of the inserted title cell
	 */

	public abstract int insertTitleCell(int month, int startRow, int startColumn);

	/**
	 * Inserts the column header row in the category sheet.
	 * 
	 * @param startRow    - the starting row index of the header row
	 * @param startColumn - the starting column index of the header row
	 * @return the row index of the inserted header row
	 */

	public abstract int insertHeaderRow(int startRow, int startColumn);

	/**
	 * Inserts a transaction row into the category sheet.
	 * 
	 * @param transaction - the transaction to insert
	 * @param rowNum      - the row index where the transaction should be inserted
	 * @param startColumn - the starting column index of the transaction row
	 * @return the row index of the inserted transaction row
	 */

	public abstract int insertTransactionRow(Transaction transaction, int rowNum, int startColumn);

	/**
	 * Inserts the sum row into the category sheet.
	 * 
	 * @param startRow        - the starting row index of the sum row
	 * @param startColumn     - the starting column index of the sum row
	 * @param numTransactions - the number of transactions in the category
	 * @return the cell reference of the inserted sum cell
	 */

	public abstract CellReference insertSumRow(int startRow, int startColumn, int numTransactions);

	/*
	 * =============SETTERS====================
	 */

	/**
	 * Sets the category sheet for the writer.
	 * 
	 * @param categorySheet - the category sheet to set
	 */

	public void setCategorySheet(CategorySheet categorySheet) {
		this.categorySheet = categorySheet;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/*
	 * =============METHODS===================
	 */

	/**
	 * Creates the transaction table in the category sheet for the specified month
	 * and array of transactions.
	 * 
	 * @param month        - the month of the transactions
	 * @param transactions - the array of transactions
	 * @param startRow     - the starting row index of the table
	 * @param startColumn  - the starting column index of the table
	 */

	public void createTransactionTable(int month, Transaction[] transactions, int startRow, int startColumn) {
		int numTransactions = transactions.length;

		// insert Title of table
		int headerRowNum = insertTitleCell(month, startRow, startColumn);

		// Setting headers

		int dataRowNum = insertHeaderRow(headerRowNum, startColumn);

		// Inputting data
		int sumRowNum;
		for (int row = 0; row < numTransactions; row++) {
			sumRowNum = insertTransactionRow(transactions[row], dataRowNum + row, startColumn);
		}
		String log = String.format("Inserted %s data rows into table: %s", numTransactions,
					getCategorySheet().getCategoryName());
		logger.info(log);

		// Calculating sums of data

		CellReference sumCellRef = insertSumRow(dataRowNum, startColumn, numTransactions);

		// add month & reference to CellReference sheet
		addToPaidCellRefMap(month, sumCellRef);
	}

	/**
	 * Creates a new row in the category sheet or gets an existing row if it has
	 * already been initialized.
	 * 
	 * @param rowNum - the index of the row
	 * @return the created or existing row
	 */

	public Row createOrGetRow(int rowNum) {
		Row row = categorySheet.getSheet().getRow(rowNum);
		if (row == null) {
			row = categorySheet.getSheet().createRow(rowNum);
		}

		return row;
	}

	/**
	 * Creates a new cell in the specified row or gets an existing cell if it has
	 * already been initialized.
	 * 
	 * @param row     - the row to create or get the cell from
	 * @param cellNum - the index of the cell
	 * @return the created or existing cell
	 */

	public Cell createOrGetCell(Row row, int cellNum) {
		Cell cell = row.getCell(cellNum);
		if (cell == null) {
			cell = row.createCell(cellNum);
		}
		return cell;
	}

	/**
	 * Returns the month name for the given month number.
	 * 
	 * @param month - the month number
	 * @return the month name
	 */
	//Is this in the wrong place?
	public String returnMonth(int month) {
		Map<Integer, String> months = new HashMap<>();
		months.put(1, "January");
		months.put(2, "February");
		months.put(3, "March");
		months.put(4, "April");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "September");
		months.put(10, "October");
		months.put(11, "November");
		months.put(12, "December");
		return months.get(month);
	}
}
