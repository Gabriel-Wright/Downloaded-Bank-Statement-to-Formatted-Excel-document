package test;

import org.apache.poi.xssf.usermodel.XSSFColor;

import config.AppConfig;
import config.ColourConfig;

public class ColConfigIntegrationTest {
    public static void main(String[] args) {

        AppConfig config = new AppConfig();
        ColourConfig colConfig = new ColourConfig(config);
        colConfig.chooseColour("Test");
        colConfig.saveCategoryColor("Test",colConfig.getSelectedColour());
        XSSFColor convertedColour = colConfig.convertColourToXSSF(colConfig.getSelectedColour());
        if((colConfig.checkColConfigSetting("Test")).equals(convertedColour)) {
        	System.out.println("Yay!");
        }
    }
}
