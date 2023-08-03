package config.category;

import config.AppConfig;
import config.BooleanConfig;

import java.io.File;
import org.apache.logging.log4j.Logger;

/**
 * The CategoryConfig class handles the configuration settings related to
 * categories. It manages the configuration file,
 * 
 * provides methods for saving and retrieving category-related settings, and
 * contains boolean variables that are used in
 * 
 * the TableCategoryWriter class to toggle whether the user should be prompted
 * to confirm at certain stages.
 * 
 * @see AppConfig
 * @see TableCategory
 * @see TableCategoryWriter
 */

public class CategoryConfig extends BooleanConfig{

	private AppConfig config;
	private File CONFIG_FILE;
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
	private Logger logger;
	/*
	 * ===============CONSTRUCTORS================
	 */

	/**
	 * Constructs a new CategoryConfig instance.
	 * 
	 * @param config - the AppConfig instance
	 */

	public CategoryConfig(AppConfig config) {
		super(config);
	}

	/*
	 * ===============GETTERS===============
	 */

	/**
	 * Retrieves the value of the appendOrCreateConfirm setting that is saved to this object.
	 * NOTE: this will not return the value of appendOrCreateConfirm property saved to config.properties file
	 * 
	 * @return true if appendOrCreateConfirm is enabled, false otherwise
	 */

	public boolean getAppendOrCreateConfirm() {
		return appendOrCreateConfirm;
	}

	/**
	 * Retrieves the value of the categoryNameConfirm setting that is saved to this object.
	 * NOTE: this will not return the value of categoryNameConfirm property saved to config.properties file
	 * 
	 * @return true if categoryNameConfirm is enabled, false otherwise
	 */

	public boolean getCategoryNameConfirm() {
		return categoryNameConfirm;
	}

	/**
	 * Retrieves the value of the categoryMenuChoiceConfirm setting that is saved to this object.
	 * NOTE: this will not return the value of categoryMenuChoiceConfirm property saved to config.properties file
	 * 
	 * @return true if categoryMenuChoiceConfirm is enabled, false otherwise
	 */

	public boolean getCategoryMenuChoiceConfirm() {
		return categoryMenuChoiceConfirm;
	}

	/*
	 * ===============SETTERS===============
	 */

	/**
	 * Sets the value of the appendOrCreateConfirm setting within this object.
	 * 
	 * @param appendOrCreateConfirm - set boolean value for appendOrCreateConfirm
	 *                              setting in this CategoryConfig instance
	 */

	public void setAppendOrCreateConfirm(boolean appendOrCreateConfirm) {
		this.appendOrCreateConfirm = appendOrCreateConfirm;
	}

	/**
	 * Sets the value of the categoryNameConfirm setting within this object.
	 * 
	 * @param categoryNameConfirm - set boolean value for categoryNameConfirm
	 *                            setting in this CategoryConfig instance
	 */

	public void setCategoryNameConfirm(boolean categoryNameConfirm) {
		this.categoryNameConfirm = categoryNameConfirm;
	}

	/**
	 * 
	 * Sets the value of the categoryMenuChoiceConfirm within this object.
	 * 
	 * @param categoryMenuChoiceConfirm - set boolean value for
	 *                                  categoryMenuChoiceConfirm setting in this
	 *                                  CategoryConfig instance
	 */

	public void setCategoryMenuChoiceConfirm(boolean categoryMenuChoiceConfirm) {
		this.categoryMenuChoiceConfirm = categoryMenuChoiceConfirm;
	}

    /*
     * ============= PROPERTY CONFIG CHECK METHODS ==============
     */

	public Boolean checkAppendOrCreateConfirm() {
		return checkBooleanProperty("appendOrCreateConfirm");
	}
	
	public Boolean checkCategoryNameConfirm() {
		return checkBooleanProperty("categoryNameConfirm");
	}
	
	public Boolean checkCategoryMenuChoiceConfirm() {
		return checkBooleanProperty("categoryMenuChoiceConfirm");
	}
	/*
	 * ============== SAVE PROPERTY METHODS ==================
	 */

	/**
	 * Saves the value of the appendOrCreateConfirm setting to the configuration
	 * file by calling saveBooleanProperty method from abstract parent class.
	 */

	public void saveAppendOrCreateConfirm() {
		saveBooleanProperty("appendOrCreateConfirm",appendOrCreateConfirm);
	}

	/**
	 * Saves the value of the categoryNameConfirm setting to the configuration file
	 * by calling saveBooleanProperty method from abstract parent class.
	 */

	public void saveCategoryNameConfirm() {
		saveBooleanProperty("categoryNameConfirm",categoryNameConfirm);
	}

	/**
	 * Saves the value of the categoryMenuChoiceConfirm setting to the configuration
	 * file by calling saveBooleanProperty method from abstract parent class.
	 */

	public void saveCategoryMenuChoiceConfirm() {
		saveBooleanProperty("categoryMenuChoiceConfirm", categoryMenuChoiceConfirm);
	}

}
