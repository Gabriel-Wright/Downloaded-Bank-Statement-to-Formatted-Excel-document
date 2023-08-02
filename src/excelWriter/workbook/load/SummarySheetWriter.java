package excelWriter.workbook.load;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import excelWriter.workbook.CategorySheet;
import excelWriter.workbook.StatementSheet;
import excelWriter.workbook.SummarySheet;
import excelWriter.workbook.TransactionWorkbook;

/**
 * 
 * The SummarySheetWriter class is responsible for writing the summary table in
 * the summary sheet of an Excel workbook. It takes a SummarySheet object as
 * input and provides methods to create the table, including the info title,
 * headers row, data rows, and totals row.
 * 
 * @see SummarySheet
 * @see StatementSheet
 * @see TransactionWorkbook
 * @see TransactionWorkbookSummaryLoader
 */

public class SummarySheetWriter {

	private SummarySheet summarySheet;
	private String[] headers;
	private Logger logger;
	/*
	 * ===============CONSTRUCTORS==============
	 */

	/**
	 * Constructs a SummarySheetWriter object with the specified summary sheet.
	 * 
	 * @param summarySheet - the summary sheet to write the summary table to
	 */

	public SummarySheetWriter(SummarySheet summarySheet) {
		this.summarySheet = summarySheet;
		this.headers = new String[] { "Category", "January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December", "Year Totals" };
		this.logger = summarySheet.getLogger();
	}

	/*
	 * ================GETTERS=================
	 */

	public SummarySheet getSummarySheet() {
		return summarySheet;
	}

	/*
	 * =================METHODS================
	 */

	/**
	 * Creates the summary table in the summary sheet.
	 * 
	 * @param startRowNum - the starting row index of the table
	 * @param startColNum - the starting column index of the table
	 */

	public void createSummaryTable(int startRowNum, int startColNum) {
		List<CategorySheet> categorySheets = summarySheet.getCategorySheets();
		int numCategorySheets = categorySheets.size();
		// Create titles
		createInfoTitle();

		// Create Headers Row
		int dataRowNum = createHeadersRow(startRowNum, startColNum);

		// Create Data Row
		int sumRowNum = 0;
		for (int i = 0; i < numCategorySheets; i++) {
			sumRowNum = createDataRow(dataRowNum + i, startColNum, categorySheets.get(i));
		}

		// Create Totals Row - indexes of startRow and startCol and at row and column
		// the totals row will be placed at.
		createTotalsRow(sumRowNum, startColNum);
	}

	/**
	 * Creates the info title at cell A1 (0, 0) in the summary sheet.
	 */

	public void createInfoTitle() {
		Row infoRow = summarySheet.getSheet().createRow(0);
		Cell infoCell = infoRow.createCell(0);
		String infoNote = String.format("This sheet provides totals of transactions \n completed between %s and %s.",
				summarySheet.getStartDate(), summarySheet.getEndDate());
		infoCell.setCellStyle(summarySheet.getTitleStyle());
		infoCell.setCellValue(infoNote);
		logger.info("Inserted Title cell for summarySheet.");
	}

	/**
	 * Creates the column headers row of the summary table.
	 * 
	 * @param rowNum - the row index of the headers row
	 * @param colNum - the starting column index of the headers row
	 * @return the row index of the next row after the headers row
	 */

	public int createHeadersRow(int rowNum, int colNum) {
		Row headerRow = summarySheet.getSheet().createRow(rowNum);
		int numColumns = headers.length;
		for (int col = 0; col < numColumns; col++) {
			Cell headerCell = headerRow.createCell(colNum + col);
			headerCell.setCellValue(headers[col]);
			headerCell.setCellStyle(summarySheet.getMonthStyle());
		}
		String log = String.format("Inserted Headers Row for summary sheet at: (%d, %d)", rowNum, colNum);
		logger.info(log);
		return rowNum + 1;
	}

	/**
	 * Inserts a data row for a specific category into the summary table.
	 * 
	 * @param rowNum        - the row index of the data row
	 * @param colNum        - the starting column index of the data row
	 * @param categorySheet - the category sheet associated with the data row
	 * @return the row index of the next row after the data row
	 */

	public int createDataRow(int rowNum, int colNum, CategorySheet categorySheet) {
		Row dataRow = summarySheet.getSheet().createRow(rowNum);
		Cell categoryNameCell = dataRow.createCell(colNum);
		String category = categorySheet.getCategoryName();
		// IndexedColors categoryColour =
		// summarySheet.getColourConfig().getCategoryColour(category);
		// summarySheet.setCategoryNameStyle(categoryColour);
		summarySheet.loadCellHeaderStyle(category);
		categoryNameCell.setCellStyle(summarySheet.getHeaderStyle());
		categoryNameCell.setCellValue(category);
		CellStyle dataStyle = summarySheet.getDataStyle();
		// Iterate through each month - add data for that given category
		for (int col = 1; col <= 12; col++) {
			Cell dataCell = dataRow.createCell(colNum + col);
			dataCell.setCellStyle(dataStyle);
			CellReference paidInSumRef = returnPaidInSum(col, categorySheet);
			CellReference paidOutSumRef = returnPaidOutSum(col, categorySheet);
			String monthlyFormula = returnFormula(paidInSumRef, paidOutSumRef);

			if (monthlyFormula.isEmpty()) {
				dataCell.setCellValue(0);
			} else {
				dataCell.setCellFormula(monthlyFormula);
			}
		}
		// Add an entry for Year Sum
		Cell sumCell = dataRow.createCell(colNum + 13);
		CellReference janCell = new CellReference(rowNum, colNum + 1);
		CellReference decCell = new CellReference(rowNum, colNum + 12);
		// categorySheet.setCellStyle(summarySheet.getsumStyle());
		String sumFormula = "SUM(" + janCell.formatAsString() + ":" + decCell.formatAsString() + ")";
		sumCell.setCellFormula(sumFormula);
		sumCell.setCellStyle(summarySheet.getSumStyle());
		String log = String.format("DataRow inserted for category:%s", categorySheet.getCategoryName());
		logger.info(log);
		return rowNum + 1;
	}

	/**
	 * Returns the cell reference of the paid-in sum cell for a specific month and
	 * category.
	 * 
	 * @param month         - the month number
	 * @param categorySheet - the category sheet associated with the cell reference
	 * @return the cell reference of the paid-in sum cell
	 */

	public void createTotalsRow(int rowNum, int colNum) {
		int numCategories = summarySheet.getCategorySheets().size();
		// No sumStyle implemented
		// CellStyle sumStyle = summarySheet.getSumStyle();
		Row sumRow = summarySheet.getSheet().createRow(rowNum);
		Cell totalNameCell = sumRow.createCell(colNum);
		totalNameCell.setCellStyle(summarySheet.getTotalStyle());
		totalNameCell.setCellValue("Totals");
		for (int col = 1; col <= 13; col++) {
			Cell sumCell = sumRow.createCell(colNum + col);
			// sumCell.setCellStyle(sumStyle);
			CellReference startCol = new CellReference(rowNum - numCategories, colNum + col);
			CellReference endCol = new CellReference(rowNum - 1, colNum + col);
			String sumFormula = "SUM(" + startCol.formatAsString() + ":" + endCol.formatAsString() + ")";
			sumCell.setCellFormula(sumFormula);
			sumCell.setCellStyle(summarySheet.getSumStyle());
			String log = String.format("SumCell inserted for column:%s. startCol CellRef:%s. endCol CellRef:%s.",
					headers[col], startCol.toString(), endCol.toString());
			logger.info(log);
		}

	}

	/**
	 * Returns the cell reference of the paid-in sum cell for a specific month and
	 * category.
	 * 
	 * @param month         - the month number
	 * @param categorySheet - the category sheet associated with the cell reference
	 * @return the cell reference of the paid-in sum cell
	 */

	public CellReference returnPaidInSum(int month, CategorySheet categorySheet) {
		Map<Integer, CellReference> paidInSumMap = categorySheet.getmonthPaidInSumMap();
		CellReference paidInSumRef = paidInSumMap.get(month);
		return paidInSumRef;
	}

	/**
	 * Returns the cell reference of the paid-out sum cell for a specific month and
	 * category.
	 * 
	 * @param month         - the month number
	 * @param categorySheet - the category sheet associated with the cell reference
	 * @return the cell reference of the paid-out sum cell
	 */

	public CellReference returnPaidOutSum(int month, CategorySheet categorySheet) {
		Map<Integer, CellReference> paidOutSumMap = categorySheet.getmonthPaidOutSumMap();
		CellReference paidOutSumRef = paidOutSumMap.get(month);
		return paidOutSumRef;
	}

	/**
	 * Returns the formula string based on the cell references of the paid-in and
	 * paid-out sum cells.
	 * 
	 * @param paidInSumRef  - the cell reference of the paid-in sum cell
	 * @param paidOutSumRef - the cell reference of the paid-out sum cell
	 * @return the formula string
	 */

	// Could definitely write this better
	public String returnFormula(CellReference paidInSumRef, CellReference paidOutSumRef) {
		String cellFormula;
		if (paidOutSumRef == null && paidInSumRef == null) {
			return "";
		} else if (paidInSumRef == null) {
			cellFormula = "-" + (paidOutSumRef.formatAsString());
		} else if (paidOutSumRef == null) {
			cellFormula = paidInSumRef.formatAsString();
		} else {
			cellFormula = paidInSumRef.formatAsString() + "-" + paidOutSumRef.formatAsString();
		}

		return cellFormula;
	}
}
