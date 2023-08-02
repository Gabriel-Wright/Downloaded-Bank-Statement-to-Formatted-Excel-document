package config.color;

import java.util.List;

import config.ColorPalette;
import sqliteData.tables.TableCategory;

import java.util.ArrayList;

/**
 * The LoadColorConfig class is responsible for checking and assigning default
 * color coding properties for the application. It ensures that the color
 * settings are properly configured and, if not, assigns default colors to the
 * properties. The class interacts with the ColorCodingConfig class to access
 * and save color coding settings within the application's configuration.
 *
 * @see ColorCodingConfig
 * @see config.ColorPalette
 * @see sqlite.TableCategory
 * @author LORD GABRIEL
 */

public class LoadColorConfig {

	/*
	 * ========= PROPERTY CONFIG CHECKS ===========
	 */

	private boolean checkSummarySheetMonthColor(ColorCodingConfig config) {
		// Returns true if there is a value assigned for summarySheetMonthColor
		return (config.checkSummarySheetMonthColor() != null);
	}

	private boolean checkSumColor(ColorCodingConfig config) {
		// Returns true if there is a value assigned for sumColor
		return (config.checkSumColor() != null);
	}

	private boolean checkCategory(ColorCodingConfig config, String category) {
		// Returns true if there is a value assigned for suggested category
		return (config.checkCategoryColor(category) != null);
	}

	private String[] checkAllCategories(ColorCodingConfig config, TableCategory tC) {
		List<String> unsetCategories = new ArrayList<>();
		tC.refreshCategoryOptions();
		String[] categories = tC.getCategoryMenu().getOptions();

		for (String category : categories) {
			if (!checkCategory(config, category)) {
				System.out.println("Color coding unset for category:" + category + ".\n "
						+ "Assigning a default color, this can be changed in the settings menu.");
				unsetCategories.add(category);
			}
		}
		return unsetCategories.toArray(new String[0]);
	}

	/*
	 * =========== ASSIGN DEFAULT PROPERTIES ============
	 */

	private void assignDefaultSummarySheetMonthColor(ColorCodingConfig config, ColorPalette colorPalette) {
		config.setSummarySheetMonthColor(colorPalette.getGrey());
		config.saveSummarySheetMonthColor();
	}

	private void assignDefaultSumColor(ColorCodingConfig config, ColorPalette colorPalette) {
		config.setSumColor(colorPalette.getGold());
		config.saveSumColor();
	}

	/*
	 * ========== CHECK AND ASSIGN ALL COLOR PROPERTIES =========
	 */

	private void checkAndAssignSummarySheetMonthColorProperty(ColorCodingConfig config, ColorPalette colorPalette) {
		if (!checkSummarySheetMonthColor(config)) {
			System.out.println("Assigning default color coding of Summary Sheet columns");
			assignDefaultSummarySheetMonthColor(config, colorPalette);
		}
	}

	private void checkAndAssignSumColorProperty(ColorCodingConfig config, ColorPalette colorPalette) {
		if (!checkSumColor(config)) {
			System.out.println("Assigning default color coding of Summary Sheet columns");
			assignDefaultSumColor(config, colorPalette);
		}
	}

	private void checkAndAssignCategoryColorProperties(ColorCodingConfig config, ColorPalette colorPalette,
			TableCategory tC) {
		String[] unsetCategories = checkAllCategories(config, tC);
		if (unsetCategories != null) {
			config.autoAssignSettingColors(unsetCategories, colorPalette);
		}
	}

	/**
	 * Checks and assigns all color coding properties in the ColorCodingConfig that
	 * are not set.
	 *
	 * @param config      -  the ColorCodingConfig instance to check and assign the
	 *                     default colors
	 * @param colorPalette - the ColorPalette instance containing default color values
	 * @param tC          -  the TableCategory instance containing the category
	 *                     options
	 */

	public void checkAndCreateColorCodingConfigProperties(ColorCodingConfig config, ColorPalette colorPalette,
			TableCategory tC) {
		checkAndAssignSummarySheetMonthColorProperty(config, colorPalette);
		checkAndAssignSumColorProperty(config, colorPalette);
		checkAndAssignCategoryColorProperties(config, colorPalette, tC);
	}
}
