package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

public class CategoryConfig {
	
	private AppConfig config;
	private File CONFIG_FILE;
	private boolean appendOrCreateConfirm;
	private boolean categoryNameConfirm;
	private boolean categoryMenuChoiceConfirm;
	private Logger logger;
	/*
	 * ===============CONSTRUCTORS================
	 */
	
	public CategoryConfig(AppConfig config) {
		this.config=config;
		this.CONFIG_FILE=config.getConfigFile();
		this.logger=config.getLogger();
	}
	
	
	/*
	 * ===============GETTERS===============
	 */
	
	public AppConfig getConfig() {
		return config;
	}

	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public boolean getAppendOrCreateConfirm() {
		return appendOrCreateConfirm;
	}

	public boolean getCategoryNameConfirm() {
		return categoryNameConfirm;
	}

	public boolean getCategoryMenuChoiceConfirm() {
		return categoryMenuChoiceConfirm;
	}


	/*
	 * ===============SETTERS===============
	 */
	
	public void setAppendOrCreateConfirm(boolean appendOrCreateConfirm) {
		this.appendOrCreateConfirm = appendOrCreateConfirm;
	}
	
	public void setCategoryNameConfirm(boolean categoryNameConfirm) {
		this.categoryNameConfirm = categoryNameConfirm;
	}
	
	public void setCategoryMenuChoiceConfirm(boolean categoryMenuChoiceConfirm) {
		this.categoryMenuChoiceConfirm = categoryMenuChoiceConfirm;
	}
	
	/*
	 * ==============METHODS==================
	 */
	 
	public void saveAppendOrCreateConfirm() {
		try {
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
		
			//set AppendOrCreateConfirm property
			properties.setProperty("appendOrCreateConfirm", String.valueOf(appendOrCreateConfirm));
		
			//save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info("appendOrCreateConfirm setting updated: " + appendOrCreateConfirm);

		}  catch(IOException e) {
			logger.error("Failed to update setting appendOrCreateConfirm: "+e.getMessage());
		}
	}
	
	public void saveCategoryNameConfirm() {
		try {
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
		
			//set AppendOrCreateConfirm property
			properties.setProperty("categoryNameConfirm", String.valueOf(categoryNameConfirm));
		
			//save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info("categoryNameConfirm setting updated: " + appendOrCreateConfirm);

		}  catch(IOException e) {
			logger.error("Failed to update setting categoryNameConfirm: "+e.getMessage());

		}
	}
	
	public void saveCategoryMenuChoiceConfirm() {
		try {
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
		
			//set AppendOrCreateConfirm property
			properties.setProperty("categoryMenuChoiceConfirm", String.valueOf(categoryNameConfirm));
		
			//save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsolutePath());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info("categoryMenuChoiceConfirm setting updated: " + appendOrCreateConfirm);

		}  catch(IOException e) {
			logger.error("Failed to update setting categoryMenuChoiceConfirm: "+e.getMessage());
		}
	}

}
