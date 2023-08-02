package excelWriter.workbook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import config.color.ColorCodingConfig;

/**
 * 
 * The StatementSheet class is an abstract class that represents a sheet in an
 * Excel workbook. It is ended to be used to store data retrieved from an SQLite
 * Database. It provides common functionality and attributes for different types
 * of statement sheets. It contains methods to set and retrieve the workbook,
 * sheet, color configuration, cell styles, and logger.
 * 
 * @see ColorConfig
 * @author LORD GABRIEL
 */

public abstract class StatementSheet {

	private Workbook workbook;
	private Sheet sheet;
	private IndexedColorMap colorMap;
	private ColorCodingConfig colConfig;
	private CellStyle dataStyle;
	private XSSFCellStyle headerStyle;
	private Font headerFont;
	private XSSFCellStyle sumStyle;
	private Font sumFont;
	private CellStyle titleStyle;
	protected static final Logger logger = LogManager.getLogger(StatementSheet.class.getName());

	/*
	 * =========== GETTERS ==================
	 */

	/**
	 * Returns the workbook associated with the statement sheet.
	 * 
	 * @return the workbook
	 */

	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * Returns the sheet of the statement sheet.
	 * 
	 * @return the sheet
	 */

	public Sheet getSheet() {
		return sheet;
	}

	/**
	 * Returns the color configuration of the statement sheet.
	 * 
	 * @return the color configuration
	 */

	public ColorCodingConfig getColConfig() {
		return colConfig;
	}

	/**
	 * Returns the cell style for data cells in the statement sheet.
	 * 
	 * @return the data cell style
	 */

	public CellStyle getDataStyle() {
		return dataStyle;
	}

	/**
	 * Returns the cell style for column header cells in the statement sheet.
	 * 
	 * @return the header cell style
	 */

	public CellStyle getHeaderStyle() {
		return headerStyle;
	}

	/**
	 * Returns the cell style for sum cells in the statement sheet.
	 * 
	 * @return the sum cell style
	 */

	public CellStyle getSumStyle() {
		return sumStyle;
	}

	/**
	 * Returns the cell style for title cells in the statement sheet.
	 * 
	 * @return the title cell style
	 */

	public CellStyle getTitleStyle() {
		return titleStyle;
	}

	public Logger getLogger() {
		return logger;
	}

	/*
	 * ============ SETTERS ===================
	 */

	/**
	 * Sets the workbook for the statement sheet.
	 * 
	 * @param workbook - the workbook to set
	 */

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * Sets the sheet for the statement sheet.
	 * 
	 * @param sheet - the sheet to set
	 */

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * Sets the color configuration for the statement sheet.
	 * This is used for color coding of categorySheets.
	 * @param colConfig - the color configuration to set
	 */

	public void setColConfig(ColorCodingConfig colConfig) {
		this.colConfig = colConfig;
	}
	/*
	 * ============== METHODS ==================
	 */

	/**
	 * Loads the cell data style for the statement sheet.
	 * The data style includes formatting for currency values and cell borders.
	 */

	public void loadCellDataStyle() {
		dataStyle = workbook.createCellStyle();
		DataFormat dataFormat = workbook.createDataFormat();
		dataStyle.setDataFormat(dataFormat.getFormat("£#,##0.00"));
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);

		// Set the border color
		dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * Loads the cell header style for the statement sheet.
	 * The header style includes formatting for header cells and cell borders.
	 * 
	 * @param category - the category associated with the header style
	 */

	public void loadCellHeaderStyle(String category) {
		XSSFColor categoryColor = colConfig.checkCategoryXSSFColor(category);
		headerStyle = (XSSFCellStyle) workbook.createCellStyle();
		headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);

		// Set the border color
		headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		// Setting background fill
		headerStyle.setFillForegroundColor(categoryColor);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}

	/**
	 * Loads the cell sum style for the statement sheet.
	 * The sum style includes formatting for sum cells and cell borders.
	 * 
	 * @param colour - the color for the sum style
	 */

	public void loadCellSumStyle() {
		XSSFColor sumColor = colConfig.checkSumXSSFColor();
		sumStyle = (XSSFCellStyle) workbook.createCellStyle();
		sumFont = workbook.createFont();
		sumFont.setBold(true);
		sumStyle.setFont(headerFont);
		sumStyle.setBorderTop(BorderStyle.THIN);
		sumStyle.setBorderBottom(BorderStyle.THIN);
		sumStyle.setBorderLeft(BorderStyle.THIN);
		sumStyle.setBorderRight(BorderStyle.THIN);

		// Set the border color
		sumStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		// Setting background fill
		sumStyle.setFillForegroundColor(sumColor);
		sumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}

	/**
	 * Loads the title style for the statement sheet.
	 * The title style includes formatting for title cells.
	 */

	public void loadTitleStyle() {
		titleStyle = workbook.createCellStyle();
		Font titleFont = workbook.createFont();
		titleFont.setBold(true);
		short fontHeight = 13;
		titleFont.setFontHeightInPoints(fontHeight);
		titleStyle.setFont(titleFont);
	}

	/**
	 * Resizes all columns in the statement sheet.
	 * This method automatically adjusts the column widths based on the content of the cells.
	 * It also adjusts the column widths for merged cells, if any.
	 * 
	 * @param rowStartNum - the starting row number for the data
	 */

	public void resizeAllColumns(int rowStartNum) {
		int numColumns = sheet.getRow(rowStartNum).getLastCellNum();

		for (int col = 0; col < numColumns; col++) {
			sheet.autoSizeColumn(col); // Autosize each column individually
		}

		// Adjust the column widths for merged cells (if any)
		for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
			int firstColumn = mergedRegion.getFirstColumn();
			int lastColumn = mergedRegion.getLastColumn();

			for (int col = firstColumn; col <= lastColumn; col++) {
				sheet.autoSizeColumn(col);
			}
		}
	}

}
