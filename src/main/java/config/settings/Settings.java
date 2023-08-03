package config.settings;

import java.util.Map;

import config.category.CategoryConfig;
import config.category.commands.CategoryConfigCommand;
import config.color.ColorCodingConfig;
import config.color.commands.ColorCodingCommand;
import java.util.LinkedHashMap;

import optionMenu.Command;
import optionMenu.GoBack;
import optionMenu.Input;
import optionMenu.MenuBlock;
import optionMenu.ProcessBlock;
import sqliteData.tables.TableCategory;

/**
 * The Settings class extends the ProcessBlock class, which provides a
 * foundation for handling processes and menu options. This class is responsible
 * for allowing the user to choose between options within the Settings menu. The
 * class contains references to the CategoryConfig, ColorCodingConfig, and
 * TableCategory objects, allowing access to the configuration properties for
 * categories and color coding. The Settings class also includes command objects
 * for category and color coding configurations, as well as a "Go Back" command
 * to return to the previous menu.
 *
 * @see optionMenu.ProcessBlock
 * @see config.category.CategoryConfig
 * @see config.color.ColorCodingConfig
 * @see sqliteData.tables.TableCategory
 */
public class Settings extends ProcessBlock {

	private MenuBlock menuBlock;
	private Input input;
	private CategoryConfig catConfig;
	private ColorCodingConfig colConfig;
	private TableCategory tC;

	/*
	 * ================ COMMAND OBJECTS ==============
	 */

	private Command colorCodingCommand = new ColorCodingCommand(this);
	private Command categoryConfigCommand = new CategoryConfigCommand(this);
	private Command goBackCommand = new GoBack();

	/*
	 * ==================== CONSTRUCTORS =================
	 */

	/**
	 * Constructs a new Settings object with the provided input, TableCategory,
	 * CategoryConfig, and ColorCodingConfig.
	 *
	 * @param input     the Input object for handling user input
	 * @param tC        the TableCategory object providing access to table
	 *                  categories
	 * @param catConfig the CategoryConfig object for category configuration
	 *                  settings
	 * @param colConfig the ColorCodingConfig object for color coding configuration
	 *                  settings
	 */

	public Settings(Input input, TableCategory tC, CategoryConfig catConfig, ColorCodingConfig colConfig) {
		this.input = input;
		this.tC = tC;
		this.catConfig = catConfig;
		this.colConfig = colConfig;
	}

	/*
	 * ================= GETTERS =====================
	 */

	public Input getInput() {
		return input;
	}

	public TableCategory getTableCategory() {
		return tC;
	}

	public CategoryConfig getCatConfig() {
		return catConfig;
	}

	public ColorCodingConfig getColorConfig() {
		return colConfig;
	}

	/*
	 * ==================== LOAD METHODS ================
	 */

	/**
	 * Loads the available options and command objects for configuration settings
	 * into the menu block.
	 *
	 * The Settings class loads the available options and command objects related to
	 * category selection and color coding configuration into the menu block. The
	 * menu block enables users to interactively modify settings through the Input
	 * class.
	 */

	@Override
	protected void loadMenuCommandMap() {
		Map<String, Command> tempMap = new LinkedHashMap<>();
		tempMap.put("Category Selection Settings: affects settings for confirmation toggles when assigning categories",
				categoryConfigCommand);
		tempMap.put("Color Coding Selection Settings: alter color coding of Cells within excel sheet output",
				colorCodingCommand);
		tempMap.put("Go Back", goBackCommand);

		setMenuCommandMap(tempMap);
	}

}
