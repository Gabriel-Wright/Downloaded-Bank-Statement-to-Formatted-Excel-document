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
import org.openxmlformats.schemas.drawingml.x2006.main.CTColor;

import javax.swing.*;
import java.awt.*;

public class ColorConfig {

	private AppConfig config;
	private File CONFIG_FILE;
	private Color selectedcolor;
	private ColorPalette colorPalette;
	private Logger logger;
	

	/*
	 * ================= CONSTRUCTORS ==================
	 */

	public ColorConfig(AppConfig config, ColorPalette colorPalette) {
		this.config = config;
		this.CONFIG_FILE = config.getConfigFile();
		this.colorPalette=colorPalette;
		this.logger = config.getLogger();
	}

	/*
	 * ================== GETTERS =================
	 */

	public AppConfig getConfig() {
		return config;
	}

	public File getCONFIG_FILE() {
		return CONFIG_FILE;
	}

	public Color getSelectedcolor() {
		return selectedcolor;
	}

	public ColorPalette getColorPalette() {
		return colorPalette;
	}

	/*
	 * ================ METHODS ====================
	 */

	public Color checkColConfigColorSetting(String category) {
		String redString = config.checkSetting(category + ".red");
		String greenString = config.checkSetting(category + ".green");
		String blueString = config.checkSetting(category + ".blue");

		if (redString == null || greenString==null || blueString==null) {
			logger.error("Color settings not assigned for category:"+category);
			throw new IllegalStateException("Setting 'my setting is not defined'");
		}
		
		int red = Integer.parseInt(redString);
		int green = Integer.parseInt(greenString);
		int blue = Integer.parseInt(blueString);

		Color readColor = new Color(red, green, blue);

		return readColor;
	}

	public XSSFColor checkColConfigXSSFColorSetting(String category) {

		Color readcolor = checkColConfigColorSetting(category);

		XSSFColor readXSSFcolor = convertColorToXSSF(readcolor);
		return readXSSFcolor;
	}


	public void saveCategoryColor(String category, Color color) {
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
			properties.setProperty(category + ".red", String.valueOf(red));
			properties.setProperty(category + ".green", String.valueOf(green));
			properties.setProperty(category + ".blue", String.valueOf(blue));
			// save updated properties
			FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE.getAbsoluteFile());
			properties.store(outputStream, null);
			outputStream.close();
			logger.info("update RGBColor setting for category:" + category + " RGB(" + red + "," + green + "," + blue
					+ ").");
		} catch (IOException e) {
			logger.error("Failed to update RGBColor setting for category:" + category + "." + e.getMessage());
		}
	}

	public void autoAssignCategoryColors(String[] categories) {
		int numCategories = categories.length;
		Color[] colors = colorPalette.returnAutoAssigncolors();
		int numAutoColors = colors.length;
		// Auto assign colors to categories, looping through autoAssignColors array to
		// assign properly
		for (int i = 0; i < numCategories; i++) {
			Color autoColor = colors[i % numAutoColors];
			saveCategoryColor(categories[i], autoColor);
		}
	}


	public XSSFColor convertColorToXSSF(Color color) {

		byte[] rgb = new byte[3];

		rgb[0] = (byte) color.getRed();
		rgb[1] = (byte) color.getGreen();
		rgb[2] = (byte) color.getBlue();

		IndexedColorMap colorMap = new DefaultIndexedColorMap();
		return new XSSFColor(rgb, colorMap);
	}
}
