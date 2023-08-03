package excelWriter.workbook.load;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import excelWriter.workbook.CategorySheet;
import excelWriter.workbook.StatementSheet;
import excelWriter.workbook.TransactionWorkbook;
import transactions.Transaction;

/**
 * 
 * The CategorySheetOutboundWriter class is a subclass of CategorySheetWriter
 * that provides specific implementations for writing outbound transaction data
 * to a category sheet in an Excel workbook. It includes constructors to
 * initialize the category sheet and logger, as well as implementations for
 * abstract methods defined in the superclass.
 * 
 * 
 * @see CategorySheetWriter
 * @see CategorySheet
 * @see StatementSheet
 * @see TransactionWorkbook
 * @author LORD GABRIEL
 */

public class CategorySheetOutboundWriter extends CategorySheetWriter{
	
	/*
	 * ==================CONSTRUCTORS=====================
	 */
	
	/**
	 * Constructs a CategorySheetOutboundWriter object with the specified category sheet.
	 * 
	 * @param categorySheet - the category sheet to write the outbound transactions to
	 */

	public CategorySheetOutboundWriter(CategorySheet categorySheet) {
		setCategorySheet(categorySheet);
		setLogger(categorySheet.getLogger());
	}
	
	/*
	 * ==============IMPLEMENT ABSTRACT METHODS============
	 */
	
	/**
	 * Adds the cell reference of a paid cell to the map of month-paid-out sum cell references.
	 * 
	 * @param month - the month of the paid cell
	 * @param x - the column index of the paid cell
	 * @param y - the row index of the paid cell
	 */

	public void addToPaidCellRefMap(int month, int x, int y) {
		CellReference categoryOutSum = new CellReference(x,y);
		getCategorySheet().getmonthPaidOutSumMap().put(month, categoryOutSum);
	}
	
	/**
	 * Adds the cell reference of a paid cell to the map of month-paid-out sum cell references.
	 * 
	 * @param month - the month of the paid cell
	 * @param cellRef - the cell reference of the paid cell
	 */

	public void addToPaidCellRefMap(int month, CellReference cellRef) {
		getCategorySheet().getmonthPaidOutSumMap().put(month, cellRef);
	}
	
	/**
	 * Inserts the title cell for the specified month in the category sheet.
	 * 
	 * @param month - the month of the title cell
	 * @param startRow - the starting row index of the title cell
	 * @param startColumn - the starting column index of the title cell
	 * @return the row index of the inserted title cell
	 */

	public int insertTitleCell(int month, int startRow, int startColumn) {
		String title = "Outbound Transactions:" + getCategorySheet().getCategoryName() + " - " + returnMonth(month);

		Row titleRow = createOrGetRow(startRow);
		Cell titleCell = createOrGetCell(titleRow, startColumn);
		titleCell.setCellValue(title);
		logger.info("Inserted Title cell for Outbound table, Category:" + getCategorySheet().getCategoryName()
				+ " Month:" + returnMonth(month));
		return startRow + 1;
	}

	/**
	 * Inserts the header column row in the category sheet.
	 * 
	 * @param startRow - the starting row index of the header row
	 * @param startColumn - the starting column index of the header row
	 * @return the row index of the inserted header row
	 */

	public int insertHeaderRow(int startRow, int startColumn) {
		// Set after Title Row
		Row headerRow = createOrGetRow(startRow);
		String[] headers = { "Date", "Description", "Paid Out", "Balance" };
		int numColumns = headers.length;
		// Iterate through each header
		for (int col = 0; col < numColumns; col++) {
			Cell headerCell = createOrGetCell(headerRow, startColumn + col);
			headerCell.setCellValue(headers[col]);
			getCategorySheet().loadCellHeaderStyle(getCategorySheet().getCategoryName());
			headerCell.setCellStyle(getCategorySheet().getHeaderStyle());
		}

		logger.info("Inserted Header row for Outbound table, Category:" + getCategorySheet().getCategoryName());
		return startRow + 1;
	}

	/**
	 * Inserts a transaction row into the category sheet.
	 * 
	 * @param transaction - the transaction to insert
	 * @param rowNum - the row index where the transaction should be inserted
	 * @param startColumn - the starting column index of the transaction row
	 * @return the row index of the inserted transaction row
	 */

	public int insertTransactionRow(Transaction transaction, int rowNum, int startColumn) {
		Row dataRow = createOrGetRow(rowNum);
		// Adding date info
		Cell dateCell = createOrGetCell(dataRow, startColumn);
		dateCell.setCellValue(transaction.getDate());
		dateCell.setCellStyle(getCategorySheet().getDataStyle());

		// Adding description info
		Cell descriptionCell = createOrGetCell(dataRow, startColumn + 1);
		descriptionCell.setCellValue(transaction.getProcessedDescription());
		descriptionCell.setCellStyle(getCategorySheet().getDataStyle());

		// Adding paidIn info
		Cell paidOutCell = createOrGetCell(dataRow, startColumn + 2);
		paidOutCell.setCellValue(transaction.getPaidOut());
		paidOutCell.setCellStyle(getCategorySheet().getDataStyle());

		// Adding balance info
		Cell balanceCell = createOrGetCell(dataRow, startColumn + 3);
		balanceCell.setCellValue(transaction.getBalance());
		balanceCell.setCellStyle(getCategorySheet().getDataStyle());

		int sumRowNum = rowNum + 1;
		return sumRowNum;
	}

	/**
	 * Inserts the sum row in the category sheet.
	 * 
	 * @param startRow - the starting row index of the sum row
	 * @param startColumn - the starting column index of the sum row
	 * @param numTransactions - the number of transactions in the category
	 * @return the cell reference of the inserted sum cell
	 */

	public CellReference insertSumRow(int startRow, int startColumn, int numTransactions) {
		Row sumRow = createOrGetRow(startRow + numTransactions + 3);
		int col = startColumn + 2; // Index of where paidIn is
		CellReference startOfColumn = new CellReference(startRow, col);
		CellReference endColumn = new CellReference(startRow + numTransactions + 2, col);
		// Formula for sum of column
		String formulaSum = "SUM(" + startOfColumn.formatAsString() + ":" + endColumn.formatAsString() + ")";
		// Creating totalCells
		Cell totalName = createOrGetCell(sumRow, col - 1);
		Cell sumCell = createOrGetCell(sumRow, col);
		totalName.setCellValue("Total");
		totalName.setCellStyle(getCategorySheet().getDataStyle());
		sumCell.setCellFormula(formulaSum);
		sumCell.setCellStyle(getCategorySheet().getSumStyle());

		CellReference sumCellRef = new CellReference(sumCell);
		logger.info("Inserted sumRow for Outbound table, category:" + getCategorySheet().getCategoryName());
		return sumCellRef;
	}

	
}

