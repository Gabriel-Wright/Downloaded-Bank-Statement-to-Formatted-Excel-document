import config.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigTest {

	//Unsure how to test since need fixed file location - and don't want to delete settings.
    @Test
    public void testCheckFolder() {
        AppConfig appConfig = new AppConfig();
        boolean result = appConfig.checkFolder();
        Assertions.assertTrue(result, "Config folder should exist");
    }

    @Test
    public void testCheckFile() {
        AppConfig appConfig = new AppConfig();
        boolean result = appConfig.checkFile();
        Assertions.assertTrue(result, "Config file should exist");
    }

    @Test
    public void testCreateFolder() {
        AppConfig appConfig = new AppConfig();
        appConfig.createFolder();
        boolean result = appConfig.checkFolder();
        Assertions.assertTrue(result, "Config folder should be created");
    }

    @Test
    public void testCreateFile() {
        AppConfig appConfig = new AppConfig();
        boolean result = appConfig.createFile();
        Assertions.assertTrue(result, "Config file should be created");
        boolean fileExists = appConfig.checkFile();
        Assertions.assertTrue(fileExists, "Config file should exist");
    }

    @Test
    public void testCheckSetting() {
        AppConfig appConfig = new AppConfig();
        String settingValue = appConfig.checkProperty("example.setting");
        Assertions.assertNull(settingValue, "Setting value should be null if not found");
    }
}
