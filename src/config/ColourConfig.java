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

public class ColourConfig {

	private AppConfig config;
	private File CONFIG_FILE;
	private Color selectedColour;
	private Logger logger;
	
	/*
	 * ================= CONSTRUCTORS ==================
	 */
	public ColourConfig() {

	}

	public ColourConfig(AppConfig config) {
		this.config = config;
		this.CONFIG_FILE = config.getConfigFile();
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

	public Color getSelectedColour() {
		return selectedColour;
	}

	/*
	 * ================ METHODS ====================
	 */

	public Color chooseColour(String category) {
		// Create a new JFrame as parent component
		JFrame frame = new JFrame();

		// Show colour chooser dialogue and get the selected colour
		Color colour = JColorChooser.showDialog(frame, "Choose a colour for transactions of category:" + category,
				Color.WHITE);
		// Return selected colour
		this.selectedColour = colour;
		return colour;
	}

	public XSSFColor convertColourToXSSF(Color colour) {

		byte[] rgb = new byte[3];
		rgb[0] = (byte) colour.getRed();
		rgb[1] = (byte) colour.getGreen();
		rgb[2] = (byte) colour.getBlue();
		IndexedColorMap colorMap = new DefaultIndexedColorMap();
		return new XSSFColor(rgb, colorMap);
	}

	public void saveCategoryColor(String category, Color colour) {
		try {
			// Load existing properties within config file
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(CONFIG_FILE.getAbsolutePath());
			properties.load(inputStream);
			// convert XSSFColor to hexColour so can be stored as string
			int red = colour.getRed();
			int green = colour.getGreen();
			int blue = colour.getBlue();

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

	public XSSFColor checkColConfigSetting(String category) {

		String redString = config.checkSetting(category + ".red");
		int red = Integer.parseInt(redString);

		String greenString = config.checkSetting(category + ".green");
		int green = Integer.parseInt(greenString);

		String blueString = config.checkSetting(category + ".blue");
		int blue = Integer.parseInt(blueString);

		Color readColour = new Color(red, green, blue);

		XSSFColor readXSSFColour = convertColourToXSSF(readColour);
		return readXSSFColour;
	}

}
