package excelWriter;

import java.io.IOException;
import java.util.List;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import config.ColourConfig;

public class SummarySheet extends StatementSheet{

	private List<CategorySheet> categorySheets;
	private String summaryName;
	private CellStyle categoryNameStyle;
	private CellStyle totalStyle;
	public String startDate;
	public String endDate;
	private ColourConfig colConfig;

	/*
	 * ============= CONSTRUCTORS =====================
	 */

	public SummarySheet(Workbook workbook, ColourConfig colConfig, List<CategorySheet> categorySheets, String startDate,
			String endDate) {
		setWorkbook(workbook);
		this.colConfig = colConfig;
		this.categorySheets = categorySheets;
		this.summaryName = "Summary";
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/*
	 * ============= GETTERS =========================
	 */
	public List<CategorySheet> getCategorySheets() {
		return categorySheets;
	}

	public String getSummaryName() {
		return summaryName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public CellStyle getCategoryNameStyle() {
		return categoryNameStyle;
	}
	
	public CellStyle getTotalStyle() {
		return totalStyle;
	}
	
	public ColourConfig getColourConfig() {
		return colConfig;
	}
		
	/*
	 * ============= SETTERS =======================
	 */
	
	public void setCategorySheets(List<CategorySheet> categorySheets) {
		this.categorySheets = categorySheets;
	}


	public void setSummaryName(String summaryName) {
		this.summaryName = summaryName;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/*
	 * ============== METHODS ======================
	 */
	
	public void loadEmptyTotalSheet() {
		setSheet(getWorkbook().createSheet(getSummaryName()));
	}
		
	public void loadCategoryNameStyle(IndexedColors colour) {
		categoryNameStyle = getWorkbook().createCellStyle();
		Font categoryFont = getWorkbook().createFont();
		categoryFont.setBold(true);
		categoryNameStyle.setFont(categoryFont);
		categoryNameStyle.setBorderTop(BorderStyle.THIN);
		categoryNameStyle.setBorderBottom(BorderStyle.THIN);
		categoryNameStyle.setBorderLeft(BorderStyle.THIN);
		categoryNameStyle.setBorderRight(BorderStyle.THIN);

		// Set the border color
		categoryNameStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		categoryNameStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		categoryNameStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		categoryNameStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		//Setting background fill
		categoryNameStyle.setFillForegroundColor(colour.getIndex());
		categoryNameStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	public void loadtotalStyle() {
		totalStyle = getWorkbook().createCellStyle();
		Font totalFont = getWorkbook().createFont();
		totalFont.setBold(true);
		short fontHeight = 13;
		totalFont.setFontHeightInPoints(fontHeight);
		totalStyle.setFont(totalFont);
		
		//Set border style
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
