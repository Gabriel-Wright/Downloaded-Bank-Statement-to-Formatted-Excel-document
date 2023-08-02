package excelWriter.workbook;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.AppConfig;
import config.color.ColorCodingConfig;
import sqliteData.tables.TableCategory;
import sqliteData.tables.TableInbound;
import sqliteData.tables.readers.TableInboundReader;
import sqliteData.tables.readers.TableOutboundReader;
import transactions.Transaction;

/**
 * 
 * The TransactionWorkbook class represents a workbook for storing transaction
 * data in Excel format. Within a TransactionWorkbook object, different
 * StatementSheet objects are stored.
 * 
 * All data is retrieved from its associated TableOutboundReader and
 * TableInboundReader objects.
 *
 * There is a list of CategorySheets, used to store data on all transactions per
 * month. There is a SummarySheet, used to calculate totals based on the values
 * retrieved from the CategorySheets.
 * 
 * This class contains methods to set and retrieve the workbook, color
 * configuration, category sheets, summary sheet, start date, end date, table
 * readers, and table category.
 * 
 * 
 * @see Workbook
 * @see ColorConfig
 * @see CategorySheet
 * @see SummarySheet
 * @see List
 * @see ArrayList
 * @see TableOutboundReader
 * @see TableInboundReader
 * @see TableCategory
 * @see Logger
 */

public class TransactionWorkbook {

	private Workbook workbook;
	private ColorCodingConfig colConfig;
	private List<CategorySheet> categorySheets;
	private SummarySheet summarySheet;
	
	/**
	 * Start date of transaction period (YYYY-MM-DD)
	 */
	private String startDate;
	
	/**
	 * End date of transaction period (YYYY-MM-DD)
	 */
	private String endDate;
	private TableOutboundReader tOR;
	private TableInboundReader tIR;
	private TableCategory tC;
	protected static final Logger logger = LogManager.getLogger(TransactionWorkbook.class.getName());

	/*
	 * ==============CONSTRUCTORS=====================
	 */

	/**
	 * Constructs a TransactionWorkbook object with the specified workbook, color
	 * configuration, start date, end date, table outbound reader, table inbound
	 * reader, and table category.
	 * 
	 * @param workbook  - the workbook to associate with the transaction workbook
	 * @param colConfig - the color configuration to use for the transaction
	 *                  workbook
	 * @param startDate - the start date of the transaction period (YYYY-MM-DD)
	 * @param endDate   - the end date of the transaction period (YYYY-MM-DD)
	 * @param tOR       - the table outbound reader to retrieve outbound
	 *                  transactions
	 * @param tIR       - the table inbound reader to retrieve inbound transactions
	 * @param tC        - the table category to retrieve transaction categories
	 */

	public TransactionWorkbook(Workbook workbook, ColorCodingConfig colConfig, String startDate, String endDate,
			TableOutboundReader tOR, TableInboundReader tIR, TableCategory tC) {
		// Initialise empty workbook - this is the xls file
		this.workbook = workbook;
		this.colConfig = colConfig;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tOR = tOR;
		this.tIR = tIR;
		this.tC = tC;
	}

	/*
	 * ===============GETTERS===========================
	 */

	/**
	 * Returns the workbook associated with the transaction workbook.
	 * 
	 * @return the workbook
	 */

	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * Returns the color configuration used in the transaction workbook.
	 * 
	 * @return the color configuration
	 */

	public ColorCodingConfig getColConfig() {
		return colConfig;
	}

	/**
	 * Returns the list of category sheets included in the transaction workbook.
	 * 
	 * @return the list of category sheets
	 */

	public List<CategorySheet> getCategorySheets() {
		return categorySheets;
	}

	/**
	 * Returns the summary sheet of the transaction workbook.
	 * 
	 * @return the summary sheet
	 */

	public SummarySheet getSummarySheet() {
		return summarySheet;
	}

	/**
	 * Returns the start date of the transactions that will be processed into the
	 * TransactionWorkbook.
	 * 
	 * @return the start date
	 */

	public String getStartDate() {
		return startDate;
	}

	/**
	 * Returns the end date of the transactions that will be processed into the
	 * TransactionWorkbook.
	 * 
	 * @return the end date
	 */

	public String getEndDate() {
		return endDate;
	}

	/**
	 * Returns the table outbound reader associated with the transaction workbook.
	 * 
	 * @return the table outbound reader
	 */

	public TableOutboundReader getTOR() {
		return tOR;
	}

	/**
	 * Returns the table inbound reader associated with the transaction workbook.
	 * 
	 * @return the table inbound reader
	 */

	public TableInboundReader getTIR() {
		return tIR;
	}

	/**
	 * Returns the table category associated with the transaction workbook.
	 * 
	 * @return the table category
	 */

	public TableCategory getTC() {
		return tC;
	}

	public Logger getLogger() {
		return logger;
	}
	/*
	 * ===============SETTERS===========================
	 */

	/**
	 * Sets the workbook for the transaction workbook.
	 * 
	 * @param workbook - the workbook to set
	 */

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * Sets the color configuration for the transaction workbook.
	 * 
	 * @param colConfig - the color configuration to set
	 */

	public void setColConfig(ColorCodingConfig colConfig) {
		this.colConfig = colConfig;
	}

	/**
	 * Sets the list of category sheets for the transaction workbook.
	 * 
	 * @param categorySheets - the list of category sheets to set
	 */

	public void setCategorySheets(List<CategorySheet> categorySheets) {
		this.categorySheets = categorySheets;
	}

	/**
	 * Sets the summary sheet for the transaction workbook.
	 * 
	 * @param summarySheet - the summary sheet to set
	 */

	public void setSummarySheet(SummarySheet summarySheet) {
		this.summarySheet = summarySheet;
	}

	/**
	 * Sets the start date of the transaction period.
	 * 
	 * @param startDate - the start date to set
	 */

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Sets the end date of the transaction period.
	 * 
	 * @param endDate - the end date to set
	 */

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Sets the table outbound reader for the transaction workbook.
	 * 
	 * @param tOR - the table outbound reader to set
	 */

	public void setTOR(TableOutboundReader tOR) {
		this.tOR = tOR;
	}

	/**
	 * Sets the table inbound reader for the transaction workbook.
	 * 
	 * @param tIR - the table inbound reader to set
	 */

	public void setTIR(TableInboundReader tIR) {
		this.tIR = tIR;
	}

	/**
	 * Sets the table category for the transaction workbook.
	 * 
	 * @param tC - the table category to set
	 */

	public void setTC(TableCategory tC) {
		this.tC = tC;
	}

	/*
	 * =============METHODS=====================
	 */

	/**
	 * Loads an empty list of category sheets into the transaction workbook.
	 */

	public void loadEmptyCategorySheets() {
		this.categorySheets = new ArrayList<>();
	}

	/**
	 * Adds a category sheet to the list of category sheets in the transaction
	 * workbook.
	 * 
	 * @param categorySheet - the category sheet to add
	 */

	public void addCategorySheet(CategorySheet categorySheet) {
		categorySheets.add(categorySheet);
	}

}
