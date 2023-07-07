package excelWriter;

import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class TransactionWorkbookSummaryLoader {
	
	private TransactionWorkbook tw;
	private SummarySheetWriter summaryWriter;
	private int rowStartNum;
	private int colStartNum;
	
	/*
	 * =============CONSTRUCTORS================
	 */
	public TransactionWorkbookSummaryLoader(TransactionWorkbook tw, int rowStartNum, int colStartNum) {
		this.tw=tw;
		this.rowStartNum = rowStartNum;
		this.colStartNum = colStartNum;
	}
	
	/*
	 * =============GETTERS====================
	 */
	
	public TransactionWorkbook getTW() {
		return tw;
	}
	
	public SummarySheetWriter getSummarySheetWriter() {
		return summaryWriter;
	}
	
	public int getRowStartNum() {
		return rowStartNum;
	}
	
	public int getColStartNum() {
		return colStartNum;
	}
	
	/*
	 * ===========SETTERS======================
	 */
	
	public void setTW(TransactionWorkbook tw) {
		this.tw=tw;
	}
	
	public void setSummarySheetWriter(SummarySheetWriter summaryWriter) {
		this.summaryWriter=summaryWriter;
	}
	
	public void setRowStartNum(int rowStartNum) {
		this.rowStartNum=rowStartNum;
	}
	
	public void setColStartNum(int colStartNum) {
		this.colStartNum=colStartNum;
	}
	
	/*
	 * ===========METHODS=====================
	 */
	
	// Creates instance of SummarySheet for TransacitonWorkbook object
	
	public void initialiseSummarySheet() {
		String startDate = tw.getStartDate();
		String endDate = tw.getEndDate();
		Workbook workbook = tw.getWorkbook();
		List<CategorySheet> categorySheets = tw.getCategorySheets();
		
		tw.setSummarySheet(new SummarySheet(workbook, tw.getColConfig(), categorySheets, startDate, endDate));
		tw.getSummarySheet().loadTitleStyle();
		tw.getSummarySheet().loadtotalStyle();
		tw.getSummarySheet().loadCellHeaderStyle(IndexedColors.AQUA);
		tw.getSummarySheet().loadCellDataStyle();
		tw.getSummarySheet().loadCellSumStyle(IndexedColors.GREY_25_PERCENT);
		tw.getSummarySheet().loadEmptyTotalSheet();
	}
	
	// Loads a summarySheetWriter object for TransactionWorkbookSummaryLoader
	// Maybe take summarySheetWriter outside class to avoid dependency injection?
	public void loadSummarySheetWriter() {
		SummarySheetWriter summaryWriter = new SummarySheetWriter(tw.getSummarySheet());
		setSummarySheetWriter(summaryWriter);
	}		
	
	// Loads SummaryTable within xls file
	public void loadSummaryTable() {
		getSummarySheetWriter().createSummaryTable(rowStartNum, colStartNum);
		tw.getSummarySheet().resizeAllColumns(rowStartNum);
	}
	
}	
