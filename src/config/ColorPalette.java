package config;

import java.awt.Color;

public class ColorPalette {
	// Load colors to be assigned that are easily readible with black
	// Read from https://www.rapidtables.com/web/color/RGB_Color.html

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

	public Color getRed() {
		return red;
	}

	public Color getDeepSkyBlue() {
		return deepSkyBlue;
	}

	public Color getYellow() {
		return yellow;
	}

	public Color getLime() {
		return lime;
	}

	public Color getOrange() {
		return orange;
	}

	public Color getMediumPurple() {
		return mediumPurple;
	}

	public Color getPink() {
		return pink;
	}

	public Color getGrey() {
		return grey;
	}

	public Color getLightCoral() {
		return lightCoral;
	}

	public Color getBeige() {
		return beige;
	}

	public Color getLavender() {
		return lavender;
	}

	public Color getWhiteSmoke() {
		return whiteSmoke;
	}

	public Color getHoneydew() {
		return honeydew;
	}

	public Color getGold() {
		return gold;
	}

	public Color getOlive() {
		return olive;
	}

	public Color getBrown() {
		return brown;
	}

	/*
	 * ============== METHODS ===============
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
