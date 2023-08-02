package config.color;

import config.ColorConfig;
import config.ColorPalette;
import config.AppConfig;
import optionMenu.Menu;

import java.awt.Color;

import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * The ColorCodingConfig class represents the configuration for color coding
 * properties within the application. It extends the ColorConfig class to inherit
 * functionality related to color configuration handling and saves specific
 * color properties for various aspects of the application.
 *
 * The class interacts with the AppConfig class, which manages the application's
 * configuration properties, and the ColorPalette class, which provides
 * color-related functionality. It stores color properties for categories, the
 * summary sheet table header, and sum cells within the Excel document.
 *
 * @see config.ColorConfig
 * @see config.AppConfig
 * @see config.ColorPalette
 * @author LORD GABRIEL
 */
public class ColorCodingConfig extends ColorConfig {

	public Color categoryColor;
	public Color summarySheetMonthColor;
	public Color sumColor;

	/**
	 * Constructs a new ColorCodingConfig object with the provided AppConfig
	 * instance.
	 *
	 * @param config - the AppConfig instance associated with this color coding
	 *               configuration
	 */

	public ColorCodingConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ========= GETTERS ======================
	 */

	/**
	 * Retrieves the color used for category coding.
	 *
	 * @return the color used for category coding
	 */

	public Color getCategoryColor() {
		return categoryColor;
	}

	/**
	 * Retrieves the color used for the summary sheet table header.
	 *
	 * @return the color used for the summary sheet table header
	 */

	public Color getSummarySheetMonthColor() {
		return summarySheetMonthColor;
	}

	/**
	 * Retrieves the color used for sum cells within the Excel document.
	 *
	 * @return the color used for sum cells
	 */

	public Color getSumColor() {
		return sumColor;
	}

	/*
	 * ========== SETTERS =============
	 */

	public void setCategoryColor(Color categoryColor) {
		this.categoryColor = categoryColor;
	}

	public void setSummarySheetMonthColor(Color summarySheetMonthColor) {
		this.summarySheetMonthColor = summarySheetMonthColor;
	}

	public void setSumColor(Color sumColor) {
		this.sumColor = sumColor;
	}

	/*
	 * ========= CHECK SAVED SETTING METHODS ===========
	 */

	/**
	 * Retrieves the XSSFColor object for the summary sheet table header color from
	 * the color configuration properties.
	 *
	 * @return the XSSFColor object for the summary sheet table header color
	 */

	public XSSFColor checkSummarySheetMonthColorXSSFColor() {
		return checkColConfigXSSFColorProperty("summarySheetMonthColor");
	}

	/**
	 * Retrieves the XSSFColor object for the sum cell color from
	 * the color configuration properties.
	 *
	 * @return the XSSFColor object for the sum cell color
	 */

	public XSSFColor checkSumXSSFColor() {
		return checkColConfigXSSFColorProperty("sumColor");
	}

	/**
	 * Retrieves the XSSFColor object for a category's associated color from
	 * the color configuration properties.
	 *
	 * @param category - category that has an associated color coding
	 * @return the XSSFColor object for a category's associated color
	 */

	public XSSFColor checkCategoryXSSFColor(String category) {
		return checkColConfigXSSFColorProperty(category);
	}

    /**
     * Checks the color property for the summary sheet table header color
     */

	public Color checkSummarySheetMonthColor() {
		return checkColConfigColorProperty("summarySheetMonthColor");
	}

    /**
     * Checks the color property for the sum cell colors
     */

	public Color checkSumColor() {
		return checkColConfigColorProperty("sumColor");

	}

	/** 
	 * 
	 * 
	 * @param category - category which has an associated color coding
	 * @return
	 */
	public Color checkCategoryColor(String category) {
		return checkColConfigColorProperty(category);
	}

	public int[] checkSummarySheetMonthColorRGB() {
		return checkColorRGBProperty("summarySheetMonthColor");
	}

	public int[] checkSumColorRGB() {
		return checkColorRGBProperty("sumColor");
	}

	public int[] checkCategoryRGB(String category) {
		return checkColorRGBProperty(category);
	}

	/*
	 * ========= SAVE PROPERTY METHODS ===============
	 */

	public void saveSummarySheetMonthColor() {
		savePropertyColor("summarySheetMonthColor", summarySheetMonthColor);
	}

	public void saveSumColor() {
		savePropertyColor("sumColor", sumColor);
	}

	public void saveCategoryColor(String category) {
		savePropertyColor(category, categoryColor);
	}
}
