package excelWriter.workbook;

import java.io.IOException;
import java.util.List;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.color.ColorCodingConfig;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

/**
 * 
 * The SummarySheet class represents a summary sheet of all transactions within
 * a time period within an Excel workbook. It extends the StatementSheet class
 * and provides additional functionality specific to summary sheets. It contains
 * methods to set and retrieve the list of category sheets, summary name, start
 * date, end date, month style, and total style.
 * 
 * @see StatementSheet
 * @see ColorConfig
 * @see CategorySheet
 * @author LORD GABRIEL
 */

public class SummarySheet extends StatementSheet {

	private List<CategorySheet> categorySheets;
	private String summaryName;
	private XSSFCellStyle monthStyle;
	private CellStyle totalStyle;
	public String startDate;
	public String endDate;

	/*
	 * ============= CONSTRUCTORS =====================
	 */
	/**
	 * Constructs a SummarySheet object with the specified workbook, color
	 * configuration, list of category sheets, start date, and end date.
	 * 
	 * @param workbook       - the workbook to associate with the summary sheet
	 * @param colConfig      - the color configuration to use for the summary sheet
	 * @param categorySheets - the list of category sheets to include in the summary
	 *                       sheet
	 * @param startDate      - the start date of the summary period
	 * @param endDate        - the end date of the summary period
	 */

	public SummarySheet(Workbook workbook, ColorCodingConfig colConfig, List<CategorySheet> categorySheets, String startDate,
			String endDate) {
		setWorkbook(workbook);
		setColConfig(colConfig);
		this.categorySheets = categorySheets;
		this.summaryName = "Summary";
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/*
	 * ============= GETTERS =========================
	 */

	/**
	 * Returns the list of category sheets included in the summary sheet.
	 * 
	 * @return the list of category sheets
	 */

	public List<CategorySheet> getCategorySheets() {
		return categorySheets;
	}

	/**
	 * Returns the name of the summary sheet.
	 * 
	 * @return the summary sheet name
	 */

	public String getSummaryName() {
		return summaryName;
	}

	/**
	 * Returns the start date of the summary period.
	 * 
	 * @return the start date
	 */

	public String getStartDate() {
		return startDate;
	}

	/**
	 * Returns the end date of the summary period.
	 * 
	 * @return the end date
	 */

	public String getEndDate() {
		return endDate;
	}

	/**
	 * Returns the cell style for column header month cells in the summary sheet.
	 * 
	 * @return the month cell style
	 */

	public CellStyle getMonthStyle() {
		return monthStyle;
	}

	/**
	 * Returns the cell style for data total cells in the summary sheet.
	 * 
	 * @return the total cell style
	 */

	public CellStyle getTotalStyle() {
		return totalStyle;
	}

	/*
	 * ============= SETTERS =======================
	 */

	/**
	 * Sets the list of category sheets for the summary sheet.
	 * 
	 * @param categorySheets - the list of category sheets to set
	 */

	public void setCategorySheets(List<CategorySheet> categorySheets) {
		this.categorySheets = categorySheets;
	}

	/**
	 * Sets the name of the summary sheet,, this will be used as the title of the
	 * sheet within Excel. NOTE: no non-alphanumeric characters can be in this name
	 * 
	 * @param summaryName - the summary sheet name to set
	 */

	public void setSummaryName(String summaryName) {
		this.summaryName = summaryName;
	}

	/**
	 * Sets the start date of the summary period.
	 * 
	 * @param startDate - the start date to set
	 */

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Sets the end date of the summary period.
	 * 
	 * @param endDate - the end date to set
	 */

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/*
	 * ============== METHODS ======================
	 */

	/**
	 * Loads an empty summary sheet in this workbook.
	 * This method creates a new sheet with the summary name and sets it as the first sheet in the workbook.
	 */

	public void loadEmptyTotalSheet() {
		setSheet(getWorkbook().createSheet(getSummaryName()));
		getWorkbook().setSheetOrder(getSummaryName(), 0);
	}

	/**
	 * Loads the cell style for month cells in the summary sheet.
	 * The month style includes formatting for month cells and cell borders.
	 * 
	 * @param color - the color used for the month column header cell
	 */

	public void loadMonthStyle() {
		XSSFColor monthHeaderColor = getColConfig().checkSummarySheetMonthColorXSSFColor();
		monthStyle = (XSSFCellStyle) getWorkbook().createCellStyle();
		Font monthFont = getWorkbook().createFont();
		monthFont.setBold(true);
		monthStyle.setFont(monthFont);
		monthStyle.setBorderTop(BorderStyle.THIN);
		monthStyle.setBorderBottom(BorderStyle.THIN);
		monthStyle.setBorderLeft(BorderStyle.THIN);
		monthStyle.setBorderRight(BorderStyle.THIN);

		// Set the border color
		monthStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		monthStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		monthStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		monthStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		// Setting background fill
		monthStyle.setFillForegroundColor(monthHeaderColor);
		monthStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	}

	/**
	 * Loads the cell style for total cells in the summary sheet.
	 * The total style includes formatting for total cells and cell borders.
	 */

	public void loadtotalStyle() {
		totalStyle = getWorkbook().createCellStyle();
		Font totalFont = getWorkbook().createFont();
		totalFont.setBold(true);
		short fontHeight = 13;
		totalFont.setFontHeightInPoints(fontHeight);
		totalStyle.setFont(totalFont);

		// Set border style
		totalStyle.setBorderTop(BorderStyle.THICK);
		totalStyle.setBorderBottom(BorderStyle.THICK);
		totalStyle.setBorderLeft(BorderStyle.THICK);
		totalStyle.setBorderRight(BorderStyle.THICK);

		// Set the border color
		totalStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		totalStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		totalStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		totalStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

	}

}
