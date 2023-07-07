package excelWriter;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.AppConfig;
import config.ColourConfig;
import sqliteData.TableCategory;
import sqliteData.TableInbound;
import sqliteData.TableInboundReader;
import sqliteData.TableOutboundReader;
import transactions.Transaction;

public class TransactionWorkbook {

	private Workbook workbook;
	private ColourConfig colConfig;
	private List<CategorySheet> categorySheets;
	private SummarySheet summarySheet;
	private String startDate;
	private String endDate;
	private TableOutboundReader tOR;
	private TableInboundReader tIR;
	private TableCategory tC;
	protected static final Logger logger = LogManager.getLogger(TransactionWorkbook.class.getName());

	/*
	 * ==============CONSTRUCTORS=====================
	 */

	public TransactionWorkbook(Workbook workbook, ColourConfig colConfig, String startDate, String endDate,
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

	public Workbook getWorkbook() {
		return workbook;
	}

	public ColourConfig getColConfig() {
		return colConfig;
	}
	
	public List<CategorySheet> getCategorySheets() {
		return categorySheets;
	}

	public SummarySheet getSummarySheet() {
		return summarySheet;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public TableOutboundReader getTOR() {
		return tOR;
	}

	public TableInboundReader getTIR() {
		return tIR;
	}

	public TableCategory getTC() {
		return tC;
	}

	public Logger getLogger() {
		return logger;
	}
	/*
	 * ===============SETTERS===========================
	 */
	
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public void setColConfig(ColourConfig colConfig) {
		this.colConfig = colConfig;
	}
	
	public void setCategorySheets(List<CategorySheet> categorySheets) {
		this.categorySheets = categorySheets;
	}

	public void setSummarySheet(SummarySheet summarySheet) {
		this.summarySheet = summarySheet;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public void setTOR(TableOutboundReader tOR) {
		this.tOR = tOR;
	}

	public void setTIR(TableInboundReader tIR) {
		this.tIR = tIR;
	}

	public void setTC(TableCategory tC) {
		this.tC = tC;
	}

	/*
	 * =============METHODS=====================
	 */
	
	public void loadEmptyCategorySheets() {
		this.categorySheets = new ArrayList<>();
	}
	
	public void addCategorySheet(CategorySheet categorySheet) {
		categorySheets.add(categorySheet);
	}

}
