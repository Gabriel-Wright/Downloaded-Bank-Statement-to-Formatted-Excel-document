package excelWriter;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import transactions.Transaction;

public abstract class CategorySheetWriter {

	private CategorySheet categorySheet;
	protected Logger logger;
	
	/*
	 * =============GETTERS===================
	 */

	public CategorySheet getCategorySheet() {
		return categorySheet;
	}

	public Logger getLogger() {
		return logger;
	}
	
	/*
	 * ===============ABSTRACT METHODS==============
	 */
	
	public abstract void addToPaidCellRefMap(int month, int x, int y);
	
	public abstract void addToPaidCellRefMap(int month, CellReference cellRef);
	
	public abstract int insertTitleCell(int month, int startRow, int startColumn);
	
	public abstract int insertHeaderRow(int startRow, int startColumn);
	
	public abstract int insertTransactionRow(Transaction transaction, int rowNum, int startColumn);
	
	public abstract CellReference insertSumRow(int startRow, int startColumn, int numTransactions);
	
	/*
	 * =============SETTERS====================
	 */
	
	public void setCategorySheet(CategorySheet categorySheet) {
		this.categorySheet=categorySheet;
	}
	
	public void setLogger(Logger logger) {
		this.logger=logger;
	}
	
	/*
	 * =============METHODS===================
	 */
	// Creates or gets a new row - depending on whether row has been initialised in
	// the sheet yet.
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
			logger.info("Inserted "+numTransactions+"data rows into table:"+getCategorySheet().getCategoryName());
		}

		// Calculating sums of data

		CellReference sumCellRef = insertSumRow(dataRowNum, startColumn, numTransactions);

		// add month & reference to CellReference sheet
		addToPaidCellRefMap(month, sumCellRef);
	}
	
	public Row createOrGetRow(int rowNum) {
		Row row = categorySheet.getSheet().getRow(rowNum);
		if (row == null) {
			row = categorySheet.getSheet().createRow(rowNum);
		}

		return row;
	}
	
	public Cell createOrGetCell(Row row, int cellNum) {
		Cell cell = row.getCell(cellNum);
		if(cell == null) {
			cell = row.createCell(cellNum);
		}
		return cell;
	}
		
	public String returnMonth(int month) {
		Map<Integer,String> months = new HashMap<>();
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
