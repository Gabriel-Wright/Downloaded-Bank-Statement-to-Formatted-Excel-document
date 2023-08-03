import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import sqliteData.Database;

class DatabaseTest {

	private Database DB;
	private static String testFilePath = System.getProperty("user.dir")+"\\fileTest\\";
	
	
	void setup(String dbTestName) {
		DB = new Database(dbTestName, testFilePath);
	}
	
	@BeforeAll
	static void cleanseTests() {
		//delete previous Test DBs.
		undoTest("CheckLoadDB");
		undoTest("DeleteDB");
	}
	static void undoTest(String testName) {
		String filePath = testFilePath+testName+".db";
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
	void testCheckLoadDB() {
		setup("CheckLoadDB");
		Assertions.assertFalse(DB.checkDB(),"DB should not exist yet");
		DB.loadDB();
		Assertions.assertTrue(DB.checkDB(),"DB should exist now");
	}
	
	@Test
	void testDeleteDB() {
		setup("DeleteDB");
		DB.loadDB();
		Assertions.assertTrue(DB.checkDB(),"DB created");
		DB.deleteDB();
		Assertions.assertFalse(DB.checkDB(),"DB deleted");
	}

}
