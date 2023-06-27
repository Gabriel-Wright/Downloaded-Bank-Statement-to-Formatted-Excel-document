package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import config.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

class StatementConfigTest {

	
	
	private StatementConfig createMockStatementConfig() {
		AppConfig appConfig =Mockito.mock(AppConfig.class);
		File configFile = new File("config.properties");
		Mockito.when(appConfig.getConfig()).thenReturn(configFile);
		return new StatementConfig(appConfig);
	}
}
