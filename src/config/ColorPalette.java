package config;

import java.awt.Color;

/**
 * The ColorPalette class provides a collection of predefined colors that can be
 * used for assigning colors in the application.
 * 
 * These colors are selected for readability and are easily distinguishable when
 * used with black text.
 * 
 * Read from https://www.rapidtables.com/web/color/RGB_Color.html
 * 
 * @see ColorConfig
 * @author LORD GABRIEL
 */

public class ColorPalette {

	/*
	 * ================ COLOR CONSTANTS================
	 */

	private final Color red = new Color(250, 0, 0);
	private final Color deepSkyBlue = new Color(0, 191, 255);
	private final Color yellow = new Color(255, 255, 0);
	private final Color lime = new Color(0, 255, 0);
	private final Color orange = new Color(255, 165, 0);
	private final Color mediumPurple = new Color(147, 112, 219);
	private final Color pink = new Color(255, 192, 203);
	private final Color grey = new Color(215, 219, 217);
	private final Color lightCoral = new Color(240, 128, 128);
	private final Color beige = new Color(245, 245, 220);
	private final Color lavender = new Color(230, 230, 250);
	private final Color whiteSmoke = new Color(245, 245, 245);
	private final Color honeydew = new Color(240, 255, 240);
	private final Color gold = new Color(255, 215, 0);
	private final Color olive = new Color(128, 128, 0);
	private final Color brown = new Color(165, 42, 42);

	/*
	 * ============ GETTERS ==============
	 */

	/**
	 * Retrieves a red color.
	 * 
	 * @return the red color
	 */

	public Color getRed() {
		return red;
	}

	/**
	 * Retrieves a deep sky blue color.
	 * 
	 * @return the deep sky blue color
	 */

	public Color getDeepSkyBlue() {
		return deepSkyBlue;
	}

	/**
	 * Retrieves a yellow color.
	 * 
	 * @return the yellow color
	 */

	public Color getYellow() {
		return yellow;
	}

	/**
	 * Retrieves a lime color.
	 * 
	 * @return the lime color
	 */

	public Color getLime() {
		return lime;
	}

	/**
	 * Retrieves an orange color.
	 * 
	 * @return the orange color
	 */

	public Color getOrange() {
		return orange;
	}

	/**
	 * Retrieves a medium purple color.
	 * 
	 * @return the medium purple color
	 */

	public Color getMediumPurple() {
		return mediumPurple;
	}

	/**
	 * Retrieves a pink color.
	 * 
	 * @return the pink color
	 */

	public Color getPink() {
		return pink;
	}

	/**
	 * Retrieves a grey color.
	 * 
	 * @return the grey color
	 */

	public Color getGrey() {
		return grey;
	}

	/**
	 * Retrieves a light coral color.
	 * 
	 * @return the light coral color
	 */

	public Color getLightCoral() {
		return lightCoral;
	}

	/**
	 * Retrieves a beige color.
	 * 
	 * @return the beige color
	 */

	public Color getBeige() {
		return beige;
	}

	/**
	 * Retrieves a lavender color.
	 * 
	 * @return the lavender color
	 */

	public Color getLavender() {
		return lavender;
	}

	/**
	 * Retrieves a white smoke color.
	 * 
	 * @return the white smoke color
	 */

	public Color getWhiteSmoke() {
		return whiteSmoke;
	}

	/**
	 * Retrieves the honeydew color.
	 * 
	 * @return the honeydew color
	 */

	public Color getHoneydew() {
		return honeydew;
	}

	/**
	 * Retrieves the gold color.
	 * 
	 * @return the gold color
	 */

	public Color getGold() {
		return gold;
	}

	/**
	 * Retrieves an olive color.
	 * 
	 * @return the olive color
	 */

	public Color getOlive() {
		return olive;
	}

	/**
	 * Retrieves a brown color.
	 * 
	 * @return the brown color
	 */

	public Color getBrown() {
		return brown;
	}

	/*
	 * ============== METHODS ===============
	 */

	/**
	 * Returns an array of auto-assigned colors.
	 * 
	 * These colors are used for assigning colors to specific elements.
	 * 
	 * @return an array of auto-assigned colors
	 */

	public Color[] returnAutoAssigncolors() {
		Color[] autoAssignColors = new Color[16];

		autoAssignColors[0] = red;
		autoAssignColors[1] = deepSkyBlue;
		autoAssignColors[2] = yellow;
		autoAssignColors[3] = lime;
		autoAssignColors[4] = orange;
		autoAssignColors[5] = mediumPurple;
		autoAssignColors[6] = pink;
		autoAssignColors[7] = grey;
		autoAssignColors[8] = lightCoral;
		autoAssignColors[9] = beige;
		autoAssignColors[10] = lavender;
		autoAssignColors[11] = whiteSmoke;
		autoAssignColors[12] = honeydew;
		autoAssignColors[13] = gold;
		autoAssignColors[14] = olive;
		autoAssignColors[15] = brown;

		return autoAssignColors;
	}

}
