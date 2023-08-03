package excelWriter.process;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.AppConfig;
import config.color.ColorCodingConfig;
import excelWriter.process.commands.WriteExcelDocCommand;
import excelWriter.workbook.TransactionWorkbook;
import excelWriter.workbook.load.TransactionWorkbookCategoryLoader;
import excelWriter.workbook.load.TransactionWorkbookSummaryLoader;
import optionMenu.Command;
import optionMenu.GoBack;
import optionMenu.Input;
import optionMenu.MenuBlock;
import optionMenu.ProcessBlock;
import regex.RegexMethods;
import sqliteData.tables.TableCategory;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;
import sqliteData.tables.TableUtils;
import sqliteData.tables.readers.TableCategoryReader;
import sqliteData.tables.readers.TableInboundReader;
import sqliteData.tables.readers.TableOutboundReader;

/**
 * The WriteDBToExcel class holds the menu responsible for writing data from the
 * SQLite Database to Excel format. It handles the loading of necessary
 * components and conversion/import of transaction data.
 * 
 * @see excelWriter.workbook.TransactionWorkbook
 * @see excelWriter.workbook.TransactionWorkbookCategoryLoader
 * @see excelWriter.workbook.TransactionWorkbookSummaryLoader
 * @author LORD GABRIEL
 *
 */
public class WriteDBToExcel extends ProcessBlock {

	private Workbook excelWorkbook;
	private String workbookName;
	private String startDate;
	private String endDate;

	private Input input;
	private RegexMethods regex;
	private ColorCodingConfig colConfig;

	private TransactionWorkbook workbook;

	private TransactionWorkbookCategoryLoader workbookCategoryLoader;
	private TransactionWorkbookSummaryLoader workbookSummaryLoader;

	private TableOutbound tO;
	private TableInbound tI;
	private TableCategory tC;

	private TableCategoryReader tCR;
	private TableOutboundReader tOR;
	private TableInboundReader tIR;

	private TableUtils tU;

	/*
	 * ================ COMMAND OBJECTS ==============
	 */

	Map<String, Command> menuCommandMap;
	private Command writeExcelDocCommand = new WriteExcelDocCommand(this);
	private Command goBackCommand = new GoBack();

	/*
	 * ================= CONSTRUCTORS =================
	 */

	/**
	 * Constructs a new WriteDBToExcel instance with the required components.
	 *
	 * @param input     - the Input instance for user interaction and input
	 * @param regex     - the RegexMethods instance for handling regular expressions
	 * @param colConfig - the ColorCodingConfig instance for managing color coding
	 *                  configuration
	 * @param tO        - the TableOutbound instance for outbound transaction data
	 * @param tI        - the TableInbound instance for inbound transaction data
	 * @param tC        - the TableCategory instance for managing categories
	 * @param tU        - the TableUtils instance for database utilities
	 */

	public WriteDBToExcel(Input input, RegexMethods regex, ColorCodingConfig colConfig, TableOutbound tO,
			TableInbound tI, TableCategory tC, TableUtils tU) {
		this.input = input;
		this.regex = regex;
		this.colConfig = colConfig;
		this.tO = tO;
		this.tI = tI;
		this.tC = tC;
		this.tU = tU;
	}

	/*
	 * ================ GETTERS =======================
	 */

	public TransactionWorkbook getTransactionWorkbook() {
		return workbook;
	}

	public String getWorkbookName() {
		return workbookName;
	}

	public Input getInput() {
		return input;
	}
	/*
	 * ================= LOAD METHODS ======================
	 */

	@Override
	protected void loadMenuCommandMap() {
		Map<String, Command> tempMap = new HashMap<>();
		tempMap.put("Write DB to Excel document", writeExcelDocCommand);
		tempMap.put("Go Back", goBackCommand);

		setMenuCommandMap(tempMap);
	}

	/**
	 * Loads the TableCategoryReader to refresh category options.
	 */

	public void loadTableCategoryReader() {
		tC.refreshCategoryOptions();
		tCR = new TableCategoryReader(tC);
	}

	/**
	 * Loads the TableInboundReader and TableOutboundReader to read inbound and
	 * outbound transaction data.
	 */

	public void loadTableInboundOutboundReaders() {
		tIR = new TableInboundReader(tI, tCR, tU);
		tIR.loadTableUtilsDB();
		tOR = new TableOutboundReader(tO, tCR, tU);
		tOR.loadTableUtilsDB();
	}

	/**
	 * Loads the TransactionWorkbook and its associated loaders to manage the
	 * workbook's sheets.
	 *
	 * @param startDate the start date of the transaction data
	 * @param endDate   the end date of the transaction data
	 */

	public void loadTransactionWorkbook(String startDate, String endDate) {
		workbook = new TransactionWorkbook(excelWorkbook, colConfig, startDate, endDate, tOR, tIR, tC);
		workbookCategoryLoader = new TransactionWorkbookCategoryLoader(workbook, 2, 2, regex);
		workbookSummaryLoader = new TransactionWorkbookSummaryLoader(workbook, 2, 2);
	}

	/**
	 * Loads the Excel workbook by creating a new XSSFWorkbook instance.
	 */

	public void loadExcelFile() {
		excelWorkbook = new XSSFWorkbook();
	}

	/**
	 * Sets up the workbook and loads necessary components for writing data to
	 * Excel.
	 *
	 * @param startDate the start date of the transaction data
	 * @param endDate   the end date of the transaction data
	 */

	public void workbookSetup(String startDate, String endDate) {
		loadExcelFile();
		loadTableCategoryReader();
		loadTableInboundOutboundReaders();
		loadTransactionWorkbook(startDate, endDate);
	}

	// ========================= EXPORT EXCEL SHEETS ==============================

	/**
	 * Writes category sheets to the Excel file.
	 */

	public void writeCategorySheets() {
		System.out.println("Writing categorySheets to file");
		workbookCategoryLoader.loadCategorySheets(5);
	}

	/**
	 * Writes the summary sheet to the Excel file.
	 */

	public void writeSummarySheet() {
		System.out.println("Writing summarySheet to file");
		workbookSummaryLoader.initialiseSummarySheet();
		workbookSummaryLoader.loadSummarySheetWriter();
		workbookSummaryLoader.loadSummaryTable();
	}

	/**
	 * Allows the user to input the desired output name for the Excel file.
	 */

	public void chooseOutputName() {
		String inputName = input.inputOriginalString(
				"Please select the output name for your excel file, do not include any file extenders e.g. .xls", true);
		String outputFileName = regex.removeAllNonAlphaNumeric(inputName);
		workbookName = outputFileName;
	}

	/**
	 * Prompts the user to select a date range for transactions to be stored in
	 * Excel.
	 *
	 * @return true if the user has chosen a date range, false otherwise
	 */

	public boolean chooseDateRange() {
		System.out.println(
				"Please insert your requested date range for these storing the data of these transactions. These excel sheets are intended to be organised by year.");
		System.out
				.println("Because of this, we do not reccomend inputting a date range which is greater than one year.");
		startDate = input
				.inputDateRange("Please insert the start date of your transaction data, in format (YYYY-MM-DD)", true);
		endDate = input.inputDateRange("Please insert the end date of your transaction data, in format (YYYY-MM-DD)",
				true);
		System.out.printf("Date range selected: %s - %s\n", startDate, endDate);
		boolean datesSelected = input.confirmInput(startDate + "," + endDate);

		return datesSelected;
	}

	/**
	 * Imports the SQLite database data and creates the Excel workbook.
	 */

	public boolean importDBCreateExcel() {
		boolean dateRangeChosen = chooseDateRange();
		if (!dateRangeChosen) {
			System.out.println("No date range chosen");
			return dateRangeChosen;
		}
		chooseOutputName();
		workbookSetup(startDate, endDate); // need to retrieve start and end date
		writeCategorySheets();
		writeSummarySheet();
		
		return dateRangeChosen;
	}

	/**
	 * Outputs the Excel workbook to an Excel file.
	 */

	public void outputExcel() {
		Workbook workbook = getTransactionWorkbook().getWorkbook();
		String folderLocation = colConfig.getConfig().checkProperty("outputFolder");
		String fileName = getWorkbookName();
		String path = folderLocation + "\\" + fileName + ".xlsx";
	    try (FileOutputStream fos = new FileOutputStream(path);
	            Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
			workbook.write(fos);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the data from the SQLite database to create and export the Excel
	 * workbook.
	 */

	public void processDBToExcel() {
		if(importDBCreateExcel()) {
			outputExcel();
		}
	}

}
