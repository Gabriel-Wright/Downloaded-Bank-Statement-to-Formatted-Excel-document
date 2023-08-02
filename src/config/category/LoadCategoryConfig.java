package config.category;

/**
 * The LoadCategoryConfig class is responsible for checking and assigning
 * default values to category-related configuration properties in the
 * application. It ensures that essential category configuration properties are
 * present in the configuration file and assigns default values to them if they
 * are missing.
 *
 * This class includes methods for checking whether specific category-related
 * properties exist in the configuration file and assigning default values to
 * them if they are absent.
 * 
 * @see config.AppConfig
 * @see CategoryConfig
 * @author LORD GABRIEL
 */

public class LoadCategoryConfig {

	/*
	 * ============ CONSTRUCTORS =================
	 */

	/*
	 * Constructs LoadCategoryConfig object, no user input is required here. So no
	 * parameters are required.
	 */

	/*
	 * ============ PROPERTY CONFIG CHECKS ================
	 */

	/**
	 * Checks if the "appendOrCreateConfirm" property exists in the specified config
	 * file.
	 *
	 * @param config the CategoryConfig object to check
	 * @return true if the "appendOrCreateConfirm" property exists, false otherwise
	 */

	private Boolean checkAppendOrCreateConfirm(CategoryConfig config) {
		Boolean appendOrCreate = config.checkAppendOrCreateConfirm();
		// Returns true isf there is a value assigned
		return (appendOrCreate != null);
	}

	/**
	 * Checks if the "categoryNameConfirm" property exists in the specified config
	 * file.
	 *
	 * @param config the CategoryConfig object to check
	 * @return true if the "categoryNameConfirm" property exists, false otherwise
	 */

	private Boolean checkCategoryNameConfirm(CategoryConfig config) {
		Boolean categoryNameConfirm = config.checkCategoryNameConfirm();
		// Returns true if there is a value assigned
		return (categoryNameConfirm != null);
	}

	/**
	 * Checks if the "categoryMenuChoiceConfirm" property exists in the specified
	 * config file.
	 *
	 * @param config the CategoryConfig object to check
	 * @return true if the "categoryMenuChoiceConfirm" property exists, false
	 *         otherwise
	 */

	private Boolean checkCategoryMenuChoiceConfirm(CategoryConfig config) {
		Boolean categoryMenuChoiceConfirm = config.checkCategoryMenuChoiceConfirm();
		// Returns true if there is a value assigned
		return (categoryMenuChoiceConfirm != null);
	}

	/*
	 * =========== Assign True to Properties ================
	 */

	/**
	 * Assigns the value "true" to the "appendOrCreateConfirm" property in the
	 * specified CategoryConfig object and saves it to the configuration file.
	 *
	 * @param config the CategoryConfig object to update
	 */

	private void assignAppendOrCreateConfirmTrue(CategoryConfig config) {
		config.setAppendOrCreateConfirm(true);
		config.saveAppendOrCreateConfirm();
	}

	/**
	 * Assigns the value "true" to the "categoryNameConfirm" property in the
	 * specified CategoryConfig object and saves it to the configuration file.
	 *
	 * @param config the CategoryConfig object to update
	 */

	private void assignCategoryNameConfirmTrue(CategoryConfig config) {
		config.setCategoryNameConfirm(true);
		config.saveCategoryNameConfirm();
	}

	/**
	 * Assigns the value "true" to the "categoryMenuChoiceConfirm" property in the
	 * specified CategoryConfig object and saves it to the configuration file.
	 *
	 * @param config the CategoryConfig object to update
	 */

	private void assignCategoryMenuChoiceConfirmTrue(CategoryConfig config) {
		config.setCategoryMenuChoiceConfirm(true);
		config.saveCategoryMenuChoiceConfirm();
	}

	/*
	 * ============== CHECK AND ASSIGN EMPTY PROPERTIES METHOD ===========
	 */

	/**
	 * Checks if the "appendOrCreateConfirm" property exists in the specified
	 * CategoryConfig. If not, it assigns the value "true" and saves it to the
	 * configuration file.
	 *
	 * @param config the CategoryConfig object to check and update
	 */

	private void checkAndCreateAppendOrCreateProperty(CategoryConfig config) {
		if (!checkAppendOrCreateConfirm(config)) {
			System.out.println("No appendOrCreateConfig found, setting to true as default.");
			assignAppendOrCreateConfirmTrue(config);
		}
	}

	/**
	 * Checks if the "categoryNameConfirm" property exists in the specified
	 * CategoryConfig. If not, it assigns the value "true" and saves it to the
	 * configuration file.
	 *
	 * @param config the CategoryConfig object to check and update
	 */

	private void checkAndCreateCategoryNameConfirmProperty(CategoryConfig config) {
		if (!checkCategoryNameConfirm(config)) {
			System.out.println("No categoryNameConfirmConfig found, setting to true as default.");
			assignCategoryNameConfirmTrue(config);
		}
	}

	/**
	 * Checks if the "categoryMenuChoiceConfirm" property exists in the specified
	 * CategoryConfig. If not, it assigns the value "true" and saves it to the
	 * configuration file.
	 *
	 * @param config the CategoryConfig object to check and update
	 */

	private void checkAndCreateCategoryMenuChoiceConfirm(CategoryConfig config) {
		if (!checkCategoryMenuChoiceConfirm(config)) {
			System.out.println("No categoryMenuChoiceConfirmConfig found, setting to true as default.");
			assignCategoryMenuChoiceConfirmTrue(config);
		}
	}

	/**
	 * Checks and creates essential category configuration properties in the
	 * specified CategoryConfig.
	 *
	 * @param config the CategoryConfig object to check and update
	 */

	public void checkAndCreateCategoryConfigProperties(CategoryConfig config) {
		checkAndCreateAppendOrCreateProperty(config);
		checkAndCreateCategoryNameConfirmProperty(config);
		checkAndCreateCategoryMenuChoiceConfirm(config);
	}
}
