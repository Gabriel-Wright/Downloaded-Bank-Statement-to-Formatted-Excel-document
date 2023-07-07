package excelWriter;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

public class SummarySheetWriter {

	private SummarySheet summarySheet;
	private String[] headers;
	private Logger logger;
	/*
	 * ===============CONSTRUCTORS==============
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

	// Creates a summaryTable on summarySheet - at starting point startRowNum,
	// startColNum
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

	// Creates an infoTitle, always placed at Cell A1/0,0 on summarySheet.
	public void createInfoTitle() {
		Row infoRow = summarySheet.getSheet().createRow(0);
		Cell infoCell = infoRow.createCell(0);
		String infoNote = "This sheet provides totals of transactions \n"
				+"completed between " + summarySheet.getStartDate()
				+ " and " + summarySheet.getEndDate();
		infoCell.setCellStyle(summarySheet.getTitleStyle());
		infoCell.setCellValue(infoNote);
		logger.info("Inserted Title cell for summarySheet.");
	}

	// Creates a headerRow starting at point (rowNum, colNum).
	public int createHeadersRow(int rowNum, int colNum) {
		Row headerRow = summarySheet.getSheet().createRow(rowNum);
		int numColumns = headers.length;
		for (int col = 0; col < numColumns; col++) {
			Cell headerCell = headerRow.createCell(colNum + col);
			headerCell.setCellValue(headers[col]);
			headerCell.setCellStyle(summarySheet.getHeaderStyle());
		}
		logger.info("Inserted Headers Row for summary sheet at:(" + rowNum + "," + colNum + ")");
		return rowNum + 1;
	}

	// Inserts a row of data starting at point (rowNum,colNum) for data from
	// categorySheet.
	public int createDataRow(int rowNum, int colNum, CategorySheet categorySheet) {
		Row dataRow = summarySheet.getSheet().createRow(rowNum);
		Cell categoryNameCell = dataRow.createCell(colNum);
		String category = categorySheet.getCategoryName();
		// IndexedColors categoryColour =
		// summarySheet.getColourConfig().getCategoryColour(category);
		// summarySheet.setCategoryNameStyle(categoryColour);
		summarySheet.loadCategoryNameStyle(IndexedColors.INDIGO);
		categoryNameCell.setCellStyle(summarySheet.getCategoryNameStyle());
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
		logger.info("DataRow inserted for category:" + categorySheet.getCategoryName() + ". Sum total:"
				+ sumCell.getNumericCellValue());
		return rowNum + 1;
	}

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
			logger.info("SumCell inserted for column:" + headers[col] + ".StartCol CellRef:" + startCol.toString()
					+ ".endCol CellRef:" + endCol.toString());
		}

	}

	public CellReference returnPaidInSum(int month, CategorySheet categorySheet) {
		Map<Integer, CellReference> paidInSumMap = categorySheet.getmonthPaidInSumMap();
		CellReference paidInSumRef = paidInSumMap.get(month);
		return paidInSumRef;
	}

	public CellReference returnPaidOutSum(int month, CategorySheet categorySheet) {
		Map<Integer, CellReference> paidOutSumMap = categorySheet.getmonthPaidOutSumMap();
		CellReference paidOutSumRef = paidOutSumMap.get(month);
		return paidOutSumRef;
	}

	// Could definitely write this better
	public String returnFormula(CellReference paidInSumRef, CellReference paidOutSumRef) {
		String cellFormula;
		if (paidOutSumRef == null && paidInSumRef == null) {
			return "";
		} else if (paidInSumRef == null) {
			cellFormula = "-"+(paidOutSumRef.formatAsString());
		} else if (paidOutSumRef == null) {
			cellFormula = paidInSumRef.formatAsString();
		} else {
			cellFormula = paidInSumRef.formatAsString() + "-" + paidOutSumRef.formatAsString();
		}

		return cellFormula;
	}
}
