package excelWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import transactions.Transaction;

public class CategorySheetOutboundWriter extends CategorySheetWriter{
	
	/*
	 * ==================CONSTRUCTORS=====================
	 */
	
	public CategorySheetOutboundWriter(CategorySheet categorySheet) {
		setCategorySheet(categorySheet);
		setLogger(categorySheet.getLogger());
	}
	
	/*
	 * ==============IMPLEMENT ABSTRACT METHODS============
	 */
	public void addToPaidCellRefMap(int month, int x, int y) {
		CellReference categoryOutSum = new CellReference(x,y);
		getCategorySheet().getmonthPaidOutSumMap().put(month, categoryOutSum);
	}
	
	public void addToPaidCellRefMap(int month, CellReference cellRef) {
		getCategorySheet().getmonthPaidOutSumMap().put(month, cellRef);
	}
	
	public int insertTitleCell(int month, int startRow, int startColumn) {
		String title = "Outbound Transactions:" + getCategorySheet().getCategoryName() + " - " + returnMonth(month);

		Row titleRow = createOrGetRow(startRow);
		Cell titleCell = createOrGetCell(titleRow, startColumn);
		titleCell.setCellValue(title);
		logger.info("Inserted Title cell for Outbound table, Category:" + getCategorySheet().getCategoryName()
				+ " Month:" + returnMonth(month));
		return startRow + 1;
	}

	public int insertHeaderRow(int startRow, int startColumn) {
		// Set after Title Row
		Row headerRow = createOrGetRow(startRow);
		String[] headers = { "Date", "Description", "Paid Out", "Balance" };
		int numColumns = headers.length;
		// Iterate through each header
		for (int col = 0; col < numColumns; col++) {
			Cell headerCell = createOrGetCell(headerRow, startColumn + col);
			headerCell.setCellValue(headers[col]);
			headerCell.setCellStyle(getCategorySheet().getHeaderStyle());
		}

		logger.info("Inserted Header row for Outbound table, Category:" + getCategorySheet().getCategoryName());
		return startRow + 1;
	}

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
		logger.info("Inserted sumRow for Outbound table, category:" + getCategorySheet().getCategoryName()
				+ "sum set as:" + sumCell.getNumericCellValue());
		return sumCellRef;
	}

	
}

