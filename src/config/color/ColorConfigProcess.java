package config.color;

import config.color.commands.CategoryColorCommand;
import config.color.commands.StatementSheetSumColorCommand;
import config.color.commands.SummarySheetMonthColorCommand;
import config.settings.Settings;
import config.settings.SettingsCommand;
import optionMenu.Command;
import optionMenu.ProcessBlock;
import sqliteData.tables.TableCategory;
import optionMenu.Input;

import java.util.Map;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Arrays;

/**
 * The ColorConfigProcess class represents the process of configuring color
 * properties for the application. It extends the ProcessBlock class and
 * implements the required abstract methods to handle color configuration
 * options.
 * 
 * The class is called from within the Settings class, another extension of the
 * ProcessBlock, which handles the application's configuration settings, and the
 * ColorCodingConfig class, which specifically manages color configuration
 * settings. It provides methods to change and save various color settings for
 * different aspects of the application, such as the summary sheet table header
 * color, the sum cells color within the Excel document, and individual color
 * codings for specific categories.
 *
 * @see optionMenu.ProcessBlock
 * @see config.settings.Settings
 * @see ColorCodingConfig
 * @author LORD GABRIEL
 * 
 */

public class ColorConfigProcess extends ProcessBlock {

	private Settings settings;
	private ColorCodingConfig colConfig;

	private Map<String, int[]> categoriesRGBMap;

	private int[] summarySheetMonthColorRGB;
	private int[] sumColorRGB;

	/*
	 * ============ COMMAND OBJECTS ============
	 */

	/*
	 * Command objects used to handle color configuration options.
	 */

	Command summarySheetMonthColor = new SummarySheetMonthColorCommand(this);
	Command sumColor = new StatementSheetSumColorCommand(this);
	Command settingsMenu;

	/*
	 * ============== CONSTRUCTORS =================
	 */

	/**
	 * Constructs a new ColorConfigProcess object with the provided Settings
	 * instance.
	 *
	 * @param settings - the Settings instance associated with this configuration
	 *                 process
	 */

	public ColorConfigProcess(Settings settings) {
		this.settings = settings;
	}

	/*
	 * ============== GETTERS ==================
	 */

	public Settings getSettings() {
		return settings;
	}

	/*
	 * ============== ABSTRACT IMPLEMENTATIONS ==============
	 */

	@Override
	protected void loadMenuCommandMap() {
		Map<String, Command> tempMap = new LinkedHashMap<>();
		settingsMenu = new SettingsCommand(settings);
		tempMap.put("Change Summary Sheet Table's header colour, currently set to RGB:"
				+ Arrays.toString(summarySheetMonthColorRGB), summarySheetMonthColor);
		tempMap.put("Change the colour of Sum cells within excel document, currently set to RGB:"
				+ Arrays.toString(sumColorRGB), sumColor);

		for (String category : categoriesRGBMap.keySet()) {
			Command categoryColorCommand = new CategoryColorCommand(this, category);
			tempMap.put("Change the color coding of category: " + category
					+ ". Within excel document, currently set to:" + Arrays.toString(categoriesRGBMap.get(category)),
					categoryColorCommand);
		}
		tempMap.put("Go back to Settings menu", settingsMenu);
		tempMap.put("Go Back to Main Menu", getGoBack());
		setMenuCommandMap(tempMap);
	}

	@Override
	public void additionalSetup() {
		loadColorCodingConfig();
		loadSummarySheetMonthColorRGB();
		loadSumColorRGB();
		loadCategoryOptions();
	}

	/*
	 * =============== LOAD METHODS ==============
	 */

	/**
	 * Loads the color coding options for individual categories from the associated
	 * Settings instance.
	 */

	public void loadCategoryOptions() {
		TableCategory tC = settings.getTableCategory();
		tC.refreshCategoryOptions();
		String[] categories = tC.getCategoryMenu().getOptions();
		categoriesRGBMap = new HashMap<>();

		for (String category : categories) {
			categoriesRGBMap.put(category, colConfig.checkCategoryRGB(category));
		}
	}

	/**
	 * Loads the color coding configuration from the associated Settings instance.
	 */

	public void loadColorCodingConfig() {
		this.colConfig = settings.getColorConfig();
	}

	/**
	 * Loads the RGB values for the summary sheet table header color from the color
	 * coding configuration.
	 */

	public void loadSummarySheetMonthColorRGB() {
		this.summarySheetMonthColorRGB = colConfig.checkSummarySheetMonthColorRGB();
	}

	/**
	 * Loads the RGB values for the sum cells color within the Excel document from
	 * the color coding configuration.
	 */

	public void loadSumColorRGB() {
		this.sumColorRGB = colConfig.checkSumColorRGB();
	}

	/*
	 * ================ CONFIG CHANGE METHODS ===============
	 */

	/**
	 * Changes the summary sheet table header color based on user input and saves
	 * the new color configuration.
	 */

	public void changeSummarySheetMonthColor() {
		Color color = settings.getInput().chooseColor(
				"Please select a new color for the header row of the SummarySheet.",
				colConfig.checkSummarySheetMonthColor());
		if (color == null) {
			System.out.println("No color selected");
			return;
		}
		colConfig.setSummarySheetMonthColor(color);
		colConfig.saveSummarySheetMonthColor();
		System.out.println("New RGB assigned for summarySheetMonthColor:"
				+ Arrays.toString(colConfig.checkSummarySheetMonthColorRGB()));
	}

	/**
	 * Changes the sum cells color within the Excel document based on user input and
	 * saves the new color configuration.
	 */

	public void changeSumColor() {
		Color color = settings.getInput().chooseColor(
				"Please select a new color for the Sum Cells in all excel sheets.", colConfig.checkSumColor());
		if (color == null) {
			System.out.println("No color selected");
			return;
		}
		colConfig.setSumColor(color);
		colConfig.saveSumColor();
		System.out.println("New RGB assigned for sum cell:" + Arrays.toString(colConfig.checkSumColorRGB()));
	}

	/**
	 * Changes the color coding for a specific category based on user input and
	 * saves the new color configuration.
	 *
	 * @param category the category for which to change the color coding
	 */

	public void changeCategoryColor(String category) {
		Color color = settings.getInput().chooseColor("Please select a new color coding for category:" + category,
				colConfig.checkCategoryColor(category));
		if (color == null) {
			System.out.println("No color selected");
			return;
		}
		colConfig.setCategoryColor(color);
		colConfig.saveCategoryColor(category);
		System.out.println("New RGB color coding assigned for category:" + category + ". RGB:"
				+ Arrays.toString(colConfig.checkCategoryRGB(category)));
	}

}
