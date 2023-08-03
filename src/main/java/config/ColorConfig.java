package config;

import java.io.File;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.*;

/**
 * 
 * The ColorConfig class handles the configuration settings related to colors
 * used in the application. Specifically related to the color coding of cells
 * within excel spreadsheets.
 * 
 * It manages the configuration file, provides methods for retrieving and saving
 * color settings, and includes functionality to convert between Color and
 * XSSFColor objects. NOTE: Settings are saved as 3 separate values Setting.red,
 * Setting.green and Setting.blue. These 3 values are returned as a new Color
 * object when reading a "Setting" from the Config file.
 * 
 * It is specifically designed for use with the ColorPalette class, to call
 * relevant colors.
 * 
 * @see AppConfig
 * @see ColorPalette
 * @see excelWriter.workbook.StatementSheet
 * @author LORD GABRIEL
 */

public class ColorConfig {

	private AppConfig config;
	private File CONFIG_FILE;
	private Color selectedcolor;
	private Logger logger;

	/*
	 * ================= CONSTRUCTORS ==================
	 */

	/**
	 * Constructs a new ColorConfig instance.
	 * 
	 * @param config       the AppConfig instance
	 */

	public ColorConfig(AppConfig config) {
		this.config = config;
		this.CONFIG_FILE = config.getConfigFile();
		this.logger = config.getLogger();
	}

	/*
	 * ================== GETTERS =================
	 */

	/**
	 * Retrieves the AppConfig instance.
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

	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	/**
	 * Retrieves the selected color.
	 * 
	 * @return the selected color
	 */

	public Color getSelectedcolor() {
		return selectedcolor;
	}

	/*
	 * ================ METHODS ====================
	 */
	
	public int[] checkColorRGBProperty(String property) {
		String redString = config.checkProperty(property + ".red");
		String greenString = config.checkProperty(property + ".green");
		String blueString = config.checkProperty(property + ".blue");

		if (redString == null || greenString == null || blueString == null) {
			logger.error(String.format("Color settings not assigned for property: %s", property));
			return null;
		}

		int red = Integer.parseInt(redString);
		int green = Integer.parseInt(greenString);
		int blue = Integer.parseInt(blueString);

		int[] rgb = new int[3];
		
		rgb[0] = red;
		rgb[1] = green;
		rgb[2] = blue;
		
		return rgb;
	}
	
	/**
	 * Checks the color property for the given property name in the configuration
	 * file.
	 * 
	 * @param property - the name of the property
	 * 
	 * @return the Color object representing the color property, or null if the color
	 *         property are not assigned
	 */

	public Color checkColConfigColorProperty(String property) {
		String redString = config.checkProperty(property + ".red");
		String greenString = config.checkProperty(property + ".green");
		String blueString = config.checkProperty(property + ".blue");

		if (redString == null || greenString == null || blueString == null) {
			logger.error(String.format("Color settings not assigned for property: %s", property));
			return null;
		}

		int red = Integer.parseInt(redString);
		int green = Integer.parseInt(greenString);
		int blue = Integer.parseInt(blueString);

		Color readColor = new Color(red, green, blue);

		return readColor;
	}

	/**
	 * Checks the XSSFColor setting for the given setting name in the configuration
	 * file.
	 * 
	 * @param property - the name of the setting
	 * @return the XSSFColor object representing the color setting, or null if the
	 *         color settings are not assigned
	 */

	public XSSFColor checkColConfigXSSFColorProperty(String property) {

		Color readcolor = checkColConfigColorProperty(property);

		XSSFColor readXSSFcolor = convertColorToXSSF(readcolor);
		return readXSSFcolor;
	}

	/**
	 * 
	 * Saves the color setting for the given setting name to the configuration file.
	 * 
	 * @param property - the name of the setting
	 * @param color   - the Color object representing the color setting
	 */

	public void savePropertyColor(String property, Color color) {
		try {
			// Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
			// convert XSSFColor to hexcolor so can be stored as string
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();

			// Setting RGB values of categories
			properties.setProperty(property + ".red", String.valueOf(red));
			properties.setProperty(property + ".green", String.valueOf(green));
			properties.setProperty(property + ".blue", String.valueOf(blue));
			// save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsoluteFile());
			properties.store(outputStream, null);
			outputStream.close();
			String log = String.format("Updated RGBColor for property: %s, RGB:(%d,%d,%d)", property, red, green, blue);
			logger.info(log);
		} catch (IOException e) {
			String log = String.format("Failed to update RGBColor for property: %s. %s", property, e.getMessage());
			logger.error(log);
		}
	}

	/**
	 * 
	 * Automatically assigns colors to an array of settings based on the
	 * autoAssigncolors in the ColorPalette instance.
	 * 
	 * @param settings - the array of setting names
	 */

	public void autoAssignSettingColors(String[] settings, ColorPalette colorPalette) {
		int numCategories = settings.length;
		Color[] colors = colorPalette.returnAutoAssigncolors();
		int numAutoColors = colors.length;
		// Auto assign colors to settings, looping through autoAssignColors array to
		// assign properly
		for (int i = 0; i < numCategories; i++) {
			Color autoColor = colors[i % numAutoColors];
			savePropertyColor(settings[i], autoColor);
		}
	}

	/**
	 * Converts a Color object to an XSSFColor object.
	 * 
	 * @param color - the Color object to be converted
	 * @return the corresponding XSSFColor object
	 */

	public XSSFColor convertColorToXSSF(Color color) {

		byte[] rgb = new byte[3];

		rgb[0] = (byte) color.getRed();
		rgb[1] = (byte) color.getGreen();
		rgb[2] = (byte) color.getBlue();

		IndexedColorMap colorMap = new DefaultIndexedColorMap();
		return new XSSFColor(rgb, colorMap);
	}
}
