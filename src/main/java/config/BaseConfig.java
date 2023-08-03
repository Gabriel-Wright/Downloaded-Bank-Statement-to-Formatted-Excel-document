package config;

import java.io.File;

import org.apache.logging.log4j.Logger;

/**
 * The BaseConfig class is an abstract class that serves as a base for other
 * configuration classes. It encapsulates the common functionalities and
 * attributes related to configuration management for different parts of the
 * application.
 * 
 * Each subclass of BaseConfig is expected to provide specific configurations
 * for a particular aspect of the application.
 * 
 * @see AppConfig
 * The following are examples of subclasses that utilise this class:
 * @see BooleanConfig 
 * @see ColorConfig
 * @see StringConfig
 * @author LORD GABRIEL
 */

public abstract class BaseConfig {

	private AppConfig config;
	private File configFile;
	protected Logger logger;

	/*
	 * ============ CONSTRUCTORS =============
	 */

	public BaseConfig(AppConfig config) {
		this.config = config;
		this.configFile = config.getConfigFile();

		this.logger = config.getLogger();
	}

	/*
	 * ============ GETTERS =============
	 */

	/**
	 * Retrieves the AppConfig instance
	 * 
	 * @return the AppConfig instance
	 */

	public AppConfig getConfig() {
		return config;
	}

	/**
	 * Retrieves the configuration file.
	 * 
	 * @return the configuration file
	 */

	public File getConfigFile() {
		return configFile;
	}

}
