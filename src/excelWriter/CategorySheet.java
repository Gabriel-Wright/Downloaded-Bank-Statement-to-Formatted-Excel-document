package excelWriter;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.AppConfig;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

public class CategorySheet extends StatementSheet{

	private String categoryName;
	private Map<Integer, CellReference> monthPaidInSumMap;
	private Map<Integer, CellReference> monthPaidOutSumMap;
	private int rowStartNum;
	private int colStartNum;


	/*
	 * ==================CONSTUCTORS==============
	 */

	public CategorySheet(Workbook workbook, String categoryName, int rowStartNum, int colStartNum) {
		setWorkbook(workbook);
		this.categoryName=categoryName;
		setSheet(workbook.createSheet(categoryName));
		this.rowStartNum=rowStartNum;
		this.colStartNum=colStartNum;
	}
	
	/*
	 * ===============GETTERS=================
	 */
		
	public String getCategoryName() {
		return categoryName;
	}
	
	public Map<Integer,CellReference> getmonthPaidInSumMap() {
		return monthPaidInSumMap;
	}

	public Map<Integer,CellReference> getmonthPaidOutSumMap() {
		return monthPaidOutSumMap;
	}
	
	public int getRowStartNum() {
		return rowStartNum;
	}
	
	public int getColStartNum() {
		return colStartNum;
	}

	public Logger getLogger() {
		return logger;
	}
	
	/*
	 * ================SETTERS================
	 */
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setmonthmonthPaidInSumMapMap(Map<Integer,CellReference> monthPaidInSumMap) {
		this.monthPaidInSumMap = monthPaidInSumMap;
	}

	public void setmonthPaidOutSumMap(Map<Integer,CellReference> monthPaidOutSumMap) {
		this.monthPaidOutSumMap = monthPaidOutSumMap;
	}
	
	public void setRowStartNum(int rowStartNum) {
		this.rowStartNum = rowStartNum;
	}
	
	public void setColStartNum(int colStartNum) {
		this.colStartNum = colStartNum;
	}

	
	/*
	 * ================METHODS================
	 */
		
	public void loadEmptyMonthPaidInMap() {
		this.monthPaidInSumMap = new HashMap<>();
	}
	
	public void loadEmptyMonthPaidOutMap() {
		this.monthPaidOutSumMap = new HashMap<>();
	}

}
