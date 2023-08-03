import sqliteData.*;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TableTest {
	
	//Table tests Table abstract class, TableInbound class and TableOutbound class.
	
	private static Database DB;
	private static TableInbound tI;
	private static TableOutbound tO; 
	private static String testFilePath = System.getProperty("user.dir")+"\\fileTest\\";
	private static String dbName = "tableTests";
	
	@BeforeAll
	static void setup() {
		File folder = new File(testFilePath);
		
		if(!folder.exists()) {
            // If the folder does not exist, create it
            boolean folderCreated = folder.mkdirs();
            if (folderCreated) {
                System.out.println("Folder created successfully.");
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists.");
        }
		
		undoTest();
		DB = new Database(dbName,testFilePath);
		tI = new TableInbound(DB);
		tO = new TableOutbound(DB);
	}

	static void undoTest() {
		String filePath = testFilePath+dbName+".db";
		Path path = Paths.get(filePath);
		File file = new File(filePath);
		if(file.exists()) {
			try {
				Files.delete(path);
				System.out.printf("Deleted %s \n", filePath);
			} catch (IOException e) {
				System.out.println("Failed to delete file:" + filePath + e.getMessage());
				fail("Error occurred");
			}
		}
	}
		
	@Test
	public void testCreateTable() {
		Assertions.assertFalse(DB.checkTable(tI.getTableName()));
		Assertions.assertFalse(DB.checkTable(tO.getTableName()));
		tI.createTable();
		tO.createTable();
		Assertions.assertTrue(DB.checkTable(tI.getTableName()));
		Assertions.assertTrue(DB.checkTable(tO.getTableName()));
		
	}
	
	@Test
	public void testDeleteTable() {
		tI.createTable();
		tO.createTable();
		Assertions.assertTrue(DB.checkTable(tI.getTableName()));
		Assertions.assertTrue(DB.checkTable(tO.getTableName()));
		tI.deleteTable();
		tO.deleteTable();
		Assertions.assertFalse(DB.checkTable(tI.getTableName()));
		Assertions.assertFalse(DB.checkTable(tO.getTableName()));
	}
	
	@Test
	public void testReplaceTable() {
		tI.deleteTable();
		tO.deleteTable();
		Assertions.assertFalse(DB.checkTable(tI.getTableName()));
		Assertions.assertFalse(DB.checkTable(tO.getTableName()));
		tI.replaceTable();
		tO.replaceTable();
		Assertions.assertTrue(DB.checkTable(tI.getTableName()));
		Assertions.assertTrue(DB.checkTable(tO.getTableName()));
	}
	
	@Test public void testCheckColumns() {
		tI.createTable();
		List<String> expectedCols = tI.inboundHeaders();
		List<String> recievedCols = tI.tableColumns();
		Assertions.assertEquals(expectedCols, recievedCols);
		tO.createTable();
		List<String> tOexpectedCols = tO.outboundHeaders();
		List<String> tOrecievedCols = tO.tableColumns();
		Assertions.assertEquals(tOexpectedCols,tOrecievedCols);
	}
	
	
}
