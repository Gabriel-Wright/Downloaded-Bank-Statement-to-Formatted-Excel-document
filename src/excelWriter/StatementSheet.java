package excelWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class StatementSheet {
	
	private Workbook workbook;
	private Sheet sheet;
	private CellStyle dataStyle;
	private CellStyle headerStyle;
	private Font headerFont;
	private CellStyle sumStyle;
	private Font sumFont;
	private CellStyle titleStyle;
	protected static final Logger logger = LogManager.getLogger(StatementSheet.class.getName());

	/*
	 * =========== GETTERS ==================
	 */
	
	public Workbook getWorkbook() {
		return workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}
	
	public CellStyle getDataStyle() {
		return dataStyle;
	}
	
	public CellStyle getHeaderStyle() {
		return headerStyle;
	}
	
	public CellStyle getSumStyle() {
		return sumStyle;
	}
	public CellStyle getTitleStyle() {
		return titleStyle;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	/*
	 * ============ SETTERS ===================
	 */
	
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}


	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	/*
	 * ============== METHODS ==================
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
	
	public void loadCellHeaderStyle(IndexedColors colour) {
		headerStyle = workbook.createCellStyle();
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
		
		//Setting background fill
	    headerStyle.setFillForegroundColor(colour.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	public void loadCellSumStyle(IndexedColors colour) {
		sumStyle = workbook.createCellStyle();
		sumFont = workbook.createFont();
        DataFormat dataFormat = workbook.createDataFormat();
        sumStyle.setDataFormat(dataFormat.getFormat("£#,##0.00"));
		sumFont.setItalic(true);
		sumStyle.setFont(sumFont);
		sumStyle.setBorderTop(BorderStyle.THIN);
		sumStyle.setBorderBottom(BorderStyle.THIN);
		sumStyle.setBorderLeft(BorderStyle.THIN);
		sumStyle.setBorderRight(BorderStyle.THIN);
		
		// Set the border color
		sumStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		sumStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		//Setting background fill
		sumStyle.setFillForegroundColor(colour.getIndex());
		sumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	public void loadTitleStyle() {
		titleStyle = workbook.createCellStyle();
		Font titleFont = workbook.createFont();
		titleFont.setBold(true);
		short fontHeight = 13;
		titleFont.setFontHeightInPoints(fontHeight);
		titleStyle.setFont(titleFont);
	}

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


