package excelWriter.workbook;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.AppConfig;
import config.color.ColorCodingConfig;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

/**
 * 
 * The CategorySheet class represents a sheet in an Excel workbook that contains
 * transaction data for a specific category. It extends the StatementSheet class
 * and provides methods to manipulate and retrieve data related to the category.
 * 
 * @see StatementSheet
 * @author LORD GABRIEL
 */

public class CategorySheet extends StatementSheet {

	private String categoryName;

	/**
	 * For each CategorySheet object, there exists a monthPaidInSumMap. Used to map the
	 * sum total of paidIn transactions for that category for each month.
	 */
	private Map<Integer, CellReference> monthPaidInSumMap;
	/**
	 * For each CategorySheet object, there exists a monthPaidOutSumMap. Used to map the
	 * sum total of paidOut transactions for that category for each month.
	 */
	private Map<Integer, CellReference> monthPaidOutSumMap;
	private int rowStartNum;
	private int colStartNum;

	/*
	 * ==================CONSTUCTORS==============
	 */

	/**
	 * Constructs a CategorySheet object with the specified workbook, color
	 * configuration, category name, row start number, and column start number.
	 * 
	 * @param workbook     - the Workbook object to set
	 * @param colConfig    - the ColorConfig object to set
	 * @param categoryName - the name of the category
	 * @param rowStartNum  - the starting row number for the category data
	 * @param colStartNum  - the starting column number for the category data
	 */

	public CategorySheet(Workbook workbook, ColorCodingConfig colConfig, String categoryName, int rowStartNum,
			int colStartNum) {
		setWorkbook(workbook);
		setColConfig(colConfig);
		this.categoryName = categoryName;
		setSheet(workbook.createSheet(categoryName));
		this.rowStartNum = rowStartNum;
		this.colStartNum = colStartNum;
	}

	/*
	 * ===============GETTERS=================
	 */

	/**
	 * Returns the relevant name of the category for this categorySheet.
	 * 
	 * @return the category name
	 */

	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Returns a map, months correspond to paid-in sum references.
	 * 
	 * @return the map of month paid-in sum references
	 */

	public Map<Integer, CellReference> getmonthPaidInSumMap() {
		return monthPaidInSumMap;
	}

	/**
	 * Returns a map, months correspond to paid-out sum references.
	 * 
	 * @return the map of month paid-out sum references
	 */

	public Map<Integer, CellReference> getmonthPaidOutSumMap() {
		return monthPaidOutSumMap;
	}

	/**
	 * Returns the starting row number for all tables within this CategorySheet.
	 * 
	 * @return the row start number
	 */

	public int getRowStartNum() {
		return rowStartNum;
	}

	/**
	 * Returns the starting column number for all tables within this CategorySheet.
	 * 
	 * @return the column start number
	 */

	public int getColStartNum() {
		return colStartNum;
	}

	public Logger getLogger() {
		return logger;
	}

	/*
	 * ================SETTERS================
	 */

	/**
	 * Sets the name of the category. This will be used as the title of the
	 * sheet within Excel. NOTE: no non-alphanumeric characters can be in this name
	 * 
	 * @param categoryName - the category name to set
	 */

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Sets the map of month paid-in sum references.
	 * 
	 * @param monthPaidInSumMap - the map of month paid-in sum references to set
	 */

	public void setmonthmonthPaidInSumMapMap(Map<Integer, CellReference> monthPaidInSumMap) {
		this.monthPaidInSumMap = monthPaidInSumMap;
	}

	/**
	 * Sets the map of month paid-out sum references.
	 * 
	 * @param monthPaidOutSumMap - the map of month paid-out sum references to set
	 */

	public void setmonthPaidOutSumMap(Map<Integer, CellReference> monthPaidOutSumMap) {
		this.monthPaidOutSumMap = monthPaidOutSumMap;
	}

	/**
	 * Sets the starting row number for the category data.
	 * 
	 * @param rowStartNum - the row start number to set
	 */

	public void setRowStartNum(int rowStartNum) {
		this.rowStartNum = rowStartNum;
	}

	/**
	 * Sets the starting column number for the category data.
	 * 
	 * @param colStartNum - the column start number to set
	 */

	public void setColStartNum(int colStartNum) {
		this.colStartNum = colStartNum;
	}

	/*
	 * ================METHODS================
	 */

	/**
	 * Loads an empty map for month paid-in sums.
	 */

	public void loadEmptyMonthPaidInMap() {
		this.monthPaidInSumMap = new HashMap<>();
	}

	/**
	 * Loads an empty map for month paid-out sums.
	 */

	public void loadEmptyMonthPaidOutMap() {
		this.monthPaidOutSumMap = new HashMap<>();
	}

}
