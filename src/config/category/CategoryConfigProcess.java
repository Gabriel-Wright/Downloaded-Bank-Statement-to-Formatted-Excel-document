package config.category;

import optionMenu.MenuBlock;
import optionMenu.ProcessBlock;
import optionMenu.Command;

import java.util.Map;

import config.category.commands.AppendOrCreateConfirmCommand;
import config.category.commands.CategoryMenuChoiceConfirmCommand;
import config.category.commands.CategoryNameConfirmCommand;
import config.settings.Settings;
import config.settings.SettingsCommand;

import java.util.LinkedHashMap;

/**
 * The CategoryConfigProcess class is a subclass of ProcessBlock and handles the
 * changing of configuration properties related to category configuration in the
 * application. It provides methods for managing category-related properties,
 * such as toggling confirmation prompts for various category-related actions.
 *
 * This class includes command objects that represent different configuration
 * options related to category property, as well as methods to load and manage
 * these properties.
 *
 * @see CategoryConfig
 * @see config.settings.Settings
 * @see optionMenu.ProcessBlock
 */
public class CategoryConfigProcess extends ProcessBlock {

	private Settings settings;
	private CategoryConfig catConfig;

	/**
	 * Variable used as toggle for whether user should be prompted to confirm their
	 * decision when deciding whether to append a transaction to a pre-existing
	 * category, or create a new one.
	 */
	private boolean appendOrCreateConfirm;
	/**
	 * Variable used as a toggle for whether user should be prompted to confirm
	 * their decision after inputting a new category name.
	 */
	private boolean categoryNameConfirm;
	/**
	 * Variable used as a toggle for whether user should be prompted to confirm
	 * their decision after choosing a category option from the category menu.
	 */
	private boolean categoryMenuChoiceConfirm;

	/*
	 * ============== COMMAND OBJECTS ================
	 */

	Command appendOrCreateCommand = new AppendOrCreateConfirmCommand(this);
	Command categoryNameConfirmCommand = new CategoryNameConfirmCommand(this);
	Command categoryMenuChoiceConfirmCommand = new CategoryMenuChoiceConfirmCommand(this);
	Command settingsMenu;

	/*
	 * ============== CONSTRUCTORS =================
	 */

	/**
	 * Constructs a new CategoryConfigProcess instance with the provided Settings
	 * object.
	 *
	 * @param settings the Settings object used for configuration management
	 */

	public CategoryConfigProcess(Settings settings) {
		this.settings = settings;
	}

	/*
	 * ============== GETTERS ==================
	 */

	public Settings getSettings() {
		return settings;
	}

	/*
	 * ============== ABSTRACT IMPLEMENTATIONS ================
	 */

	@Override
	protected void loadMenuCommandMap() {
		Map<String, Command> tempMap = new LinkedHashMap<>();
		settingsMenu = new SettingsCommand(settings);
		tempMap.put("Append Or Create Confirm toggle: " + Boolean.toString(appendOrCreateConfirm),
				appendOrCreateCommand);
		tempMap.put("New Category Name Confirm toggle: " + Boolean.toString(categoryNameConfirm),
				categoryNameConfirmCommand);
		tempMap.put("Category Menu Choice confirm: " + Boolean.toString(categoryMenuChoiceConfirm),
				categoryMenuChoiceConfirmCommand);
		tempMap.put("Go back to Settings menu", settingsMenu);
		tempMap.put("Go Back to Main Menu", getGoBack());
		setMenuCommandMap(tempMap);
	}

	/*
	 * =============== LOAD METHODS =====================
	 */

	private void loadCategoryConfig() {
		this.catConfig = settings.getCatConfig();
	}

	private void loadAppendOrCreateConfirm() {
		String appendOrCreateConfirmString = catConfig.getConfig().checkProperty("appendOrCreateConfirm");
		appendOrCreateConfirm = Boolean.parseBoolean(appendOrCreateConfirmString);
	}

	private void loadCategoryNameConfirm() {
		String categoryNameConfirmString = catConfig.getConfig().checkProperty("categoryNameConfirm");
		categoryNameConfirm = Boolean.parseBoolean(categoryNameConfirmString);
	}

	private void loadCategoryMenuChoiceConfirm() {
		String categoryMenuChoiceConfirmString = catConfig.getConfig().checkProperty("categoryMenuChoiceConfirm");
		categoryMenuChoiceConfirm = Boolean.parseBoolean(categoryMenuChoiceConfirmString);
	}

	@Override
	public void additionalSetup() {
		loadCategoryConfig();
		loadAppendOrCreateConfirm();
		loadCategoryNameConfirm();
		loadCategoryMenuChoiceConfirm();
	}

	@Override
	public void reload() {

	}

	/*
	 * ============== CONFIG CHANGE METHODS ==============
	 */

	/**
	 * Toggles the "Append Or Create confirm" property in the configuration file. If
	 * the property is currently "true," it will be set to "false," and vice versa.
	 */

	public void changeAppendOrCreateConfirm() {
		catConfig.setAppendOrCreateConfirm(!appendOrCreateConfirm);
		catConfig.saveAppendOrCreateConfirm();
		loadAppendOrCreateConfirm();
	}

	/**
	 * Toggles the "Category Name confirm" property in the configuration file. If
	 * the property is currently "true," it will be set to "false," and vice versa.
	 */

	public void changeCategoryNameConfirm() {
		catConfig.setCategoryNameConfirm(!categoryNameConfirm);
		catConfig.saveCategoryNameConfirm();
		loadCategoryNameConfirm();
	}

	/**
	 * Toggles the "Category Menu choice confirm" property in the configuration file.
	 * If the property is currently "true," it will be set to "false," and vice
	 * versa.
	 */

	public void changeCategoryMenuChoiceConfirm() {
		catConfig.setCategoryMenuChoiceConfirm(!categoryMenuChoiceConfirm);
		catConfig.saveCategoryMenuChoiceConfirm();
		loadCategoryMenuChoiceConfirm();
	}
}
