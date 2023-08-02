package test;

import statementReaders.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import javax.swing.JFileChooser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import config.AppConfig;

public class StatementReaderTest {

	private AppConfig mockConfig;

	@Before
	public void setup() {
		mockConfig = mock(AppConfig.class);
	}

	@Test
	public void testStatementReaderLoadStatementFolder() {
		//Stubbing config.getConfigFile method --> not important for this test
		when(mockConfig.getConfigFile()).thenReturn(null);
		when(mockConfig.checkProperty(anyString())).thenReturn("//fileTest//");
		NationwideCSVReader reader = new NationwideCSVReader(mockConfig);
		
		reader.loadStatementFolder();
		
		Assertions.assertEquals(reader.getStatementFolder(),new File("//fileTest//"));
	}
	
	@Test
	public void testStatementReaderLoadStatementFolderNoSetting() {
		when(mockConfig.getConfigFile()).thenReturn(null);
		when(mockConfig.checkProperty(anyString())).thenReturn(null);
		NationwideCSVReader reader = new NationwideCSVReader(mockConfig);
		Assertions.assertFalse(reader.loadStatementFolder());
	}

	@Test
	public void testStatementReaderSelectStatementOptionSelected() {
		// Create an instance of NationwideCSVReader and use Mockito.spy to create a
		// partial mock
		// Spy method wraps original object and allows stubbing of very specific methods
		// while
		// original implementation of other methods
		NationwideCSVReader reader = Mockito.spy(new NationwideCSVReader(mockConfig));
		JFileChooser fileChooser = Mockito.mock(JFileChooser.class);
		// When createFileChooser() used, implement a mock
		Mockito.doReturn(fileChooser).when(reader).createFileChooser();
		
	    // Set up the stubbed behavior
	    File selectedFile = new File("//fileTest//file.csv");
	    Mockito.when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
	    Mockito.when(fileChooser.getSelectedFile()).thenReturn(selectedFile);
	    
	    Assertions.assertTrue(reader.selectStatement());
	    Assertions.assertEquals(reader.getStatement(), selectedFile);
	}
	
	@Test
	public void testStatementReaderSelectStatementOptionNotSelected() {
		NationwideCSVReader reader = Mockito.spy(new NationwideCSVReader(mockConfig));
		JFileChooser fileChooser = Mockito.mock(JFileChooser.class);
		// When createFileChooser() used, implement a mock
		Mockito.doReturn(fileChooser).when(reader).createFileChooser();
	    Mockito.when(fileChooser.showOpenDialog(null)).thenReturn(JFileChooser.CANCEL_OPTION);
	    
	    Assertions.assertFalse(reader.selectStatement());
	}

}
