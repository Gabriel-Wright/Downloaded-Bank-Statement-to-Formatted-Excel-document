package applicationStart;

import java.util.Scanner;

import config.AppConfig;
import config.ColorPalette;
import config.db.*;
import config.statement.*;
import config.category.*;
import config.output.*;
import config.color.*;
import config.settings.Settings;
import config.settings.SettingsCommand;
import excelWriter.process.WriteDBToExcel;
import excelWriter.process.commands.WriteDBToExcelCommand;
import sqliteData.Database;
import sqliteData.tables.TableCategory;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;
import sqliteData.tables.TableUtils;
import optionMenu.Input;
import optionMenu.Command;
import optionMenu.Menu;
import optionMenu.MenuBlock;
import statementReaders.process.WriteBankStatementToDB;
import statementReaders.process.WriteBankStatementToDBCommand;
import regex.RegexMethods;

import java.util.Map;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import applicationStart.commands.ExitCommand;
import applicationStart.commands.FileOpener;
import applicationStart.load.LoadDatabase;
import applicationStart.load.LoadTables;
import applicationStart.misc.PrintMessage;


public class Main {
		
	
	private static Logger logger = LogManager.getLogger(Main.class.getName());

	private final static String VERSION = "0.0.1-SNAPSHOT";
	private final static String HELP_FILE_PATH = System.getProperty("user.dir") + "//config//Guide"+VERSION+".pdf";
	
	public static void main(String[] args) {
		// Initialise PrintMessage object
		PrintMessage printMessage = new PrintMessage(VERSION);
		
		// Initialise config objects
		AppConfig config = new AppConfig();
		
		DBNameConfig 	dbNameConfig 	   = new DBNameConfig(config);
		DBFolderConfig 	dbFolderConfig 	   = new DBFolderConfig(config);
		
		// Initialise Scanner and Input object
		Scanner scanner = new Scanner(System.in);
		Input input = new Input(scanner);

		// Initialise RegexMethods object
		RegexMethods regex = new RegexMethods();

		// Check whether DBConfig assigned
		LoadDBConfig loadDBConfig = new LoadDBConfig(regex, input);
		loadDBConfig.checkAndCreateDBSettings(dbNameConfig, dbFolderConfig);

		// Loads Database object based on DBConfig settings
		String dbName = config.checkProperty("dbName");
		String dbFolder = config.checkProperty("dbFolder");
		Database DB = new Database(dbName, dbFolder);

		// Checks whether DB loaded.
		LoadDatabase loadDatabase = new LoadDatabase();
		loadDatabase.checkAndCreateDB(DB);

		// Initialise Inbound, Outbound Tables
		TableInbound tI = new TableInbound(DB);
		TableOutbound tO = new TableOutbound(DB);

		// Initialise Category Table
		String[] options = new String[0];
		Menu categoryMenu = new Menu("CategoryMenu", options, input);
		TableCategory tC = new TableCategory(DB, categoryMenu);
		
		//Initialise tableUtils
		TableUtils tU = new TableUtils(logger);
		tU.setDB(DB);


		
		
		// Check Tables have already been created. If they haven't been they are
		// initially created
		LoadTables loadTables = new LoadTables();
		loadTables.checkAndCreateTables(DB, tI, tO, tC);

		//Checks whether category assign checks in place
		CategoryConfig catConfig 		   = new CategoryConfig(config);
		LoadCategoryConfig loadCategoryConfig = new LoadCategoryConfig();
		loadCategoryConfig.checkAndCreateCategoryConfigProperties(catConfig);
		
		// Checks whether StatementConfig settings assigned		
		StatementConfig stConfig 		   = new StatementConfig(config);
		LoadStatementConfig loadStatementConfig = new LoadStatementConfig(input);
		loadStatementConfig.checkAndCreateStatementSettings(stConfig);

		// Checks whether OutputConfig settings assigned		
		OutputConfig outConfig 			   = new OutputConfig(config);
		LoadOutputConfig loadOutputConfig = new LoadOutputConfig(input);
		loadOutputConfig.checkAndCreateOutputSettings(outConfig);

		// Load colorCoding config - check whether settings for color assignments can be found
		ColorCodingConfig colCodingConfig 	= new ColorCodingConfig(config);
		ColorPalette colPalette 		    = new ColorPalette();
		LoadColorConfig loadColorConfig		= new LoadColorConfig();
		loadColorConfig.checkAndCreateColorCodingConfigProperties(colCodingConfig, colPalette, tC);
		
		//Load WriteBankStatementToDB options
		WriteBankStatementToDB stToDB = new WriteBankStatementToDB(config, regex, input, tI,tO,tC);
		WriteBankStatementToDBCommand stToDBCommand = new WriteBankStatementToDBCommand(stToDB);
		
		//Load WriteDBToExcel option
		WriteDBToExcel DBToXls = new WriteDBToExcel(input, regex, colCodingConfig, tO, tI, tC, tU);
		WriteDBToExcelCommand DBToXlsCommand = new WriteDBToExcelCommand(DBToXls);
		
		//Load Settings menu
		Settings settings = new Settings(input,tC,catConfig,colCodingConfig);
		SettingsCommand settingCommand = new SettingsCommand(settings);
		
		//Load exit command
		ExitCommand exitCommand = new ExitCommand();
		
		//Load help option
		FileOpener guideOpener = new FileOpener(HELP_FILE_PATH);
		// Welcome message

		printMessage.printWelcomeMessage();
		
		// Options menu :))
		Map<String, Command> mainMenuMap = new LinkedHashMap<>();
		mainMenuMap.put("Write Bank Statement to DB: Convert your bank statements into a local database", stToDBCommand); //should put StatementToDatabaseCommand
		mainMenuMap.put("Convert SQLite table to Excel document: Export data from your SQLite Database into an excel document", DBToXlsCommand); // should put DBToXlsCommand
		mainMenuMap.put("Settings: Change the settings of this program, including excel sheet color coding and menu choice confirmation toggles.", settingCommand);
		mainMenuMap.put("Help: a PDF will be opened that can act as a guide for this software", guideOpener);
		mainMenuMap.put("Exit: the program will be closed", exitCommand);
		MenuBlock titleMenuBlock = new MenuBlock("Title Menu", mainMenuMap, input);
		titleMenuBlock.setUp();
		
		while(true) {
			loadColorConfig.checkAndCreateColorCodingConfigProperties(colCodingConfig, colPalette, tC);
			titleMenuBlock.getMenuSelect().chooseFromMenu("Please choose from the following options", false);
			titleMenuBlock.getMenu().loadCommandChoice();
			titleMenuBlock.getMenu().getCommandChoice().execute();
		}
	}
}
