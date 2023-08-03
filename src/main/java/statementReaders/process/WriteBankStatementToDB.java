package statementReaders.process;


import config.AppConfig;
import optionMenu.Command;
import optionMenu.GoBack;
import optionMenu.Input;
import optionMenu.MenuSelect;
import optionMenu.ProcessBlock;
import regex.RegexMethods;
import sqliteData.tables.TableCategory;
import sqliteData.tables.TableInbound;
import sqliteData.tables.TableOutbound;
import sqliteData.tables.readers.TableCategoryReader;
import sqliteData.tables.writers.TableCategoryWriter;
import sqliteData.tables.writers.TableInboundWriter;
import sqliteData.tables.writers.TableOutboundWriter;
import statementReaders.NationwideCSVReader;
import statementReaders.StatementReader;
import transactions.Transaction;


import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The WriteBankStatementToDB class is responsible for writing bank statement data to the SQLite Database tables.
 * It handles the loading of necessary components and the conversion and import of transaction data.
 *
 * @see AppConfig
 * @see RegexMethods
 * @see StatementMenu
 * @see TableInbound
 * @see TableOutbound
 * @see TableCategory
 * @see TableInboundWriter
 * @see TableOutboundWriter
 * @see MenuSelect
 * @see TableCategoryWriter
 * @see TableCategoryReader
 * @see StatementReader
 * @author LORD GABRIEL
 */

public class WriteBankStatementToDB extends ProcessBlock{
	
	private AppConfig     config;
	private RegexMethods  regex;
	private Input input;

	private TableInbound  tI;
	private TableOutbound tO;
	private TableCategory tC;
	
	private TableInboundWriter tIW;
	private TableOutboundWriter tOW;
	
	private MenuSelect categoryMenuSelect;
	private TableCategoryWriter tCW;
	private TableCategoryReader tCR;
	
	/**
	 * The StatementReader object through which statementFiles are translated into an array of transactions
	 */
	private StatementReader reader;
	
	/*
	 * ===================== COMMAND OBJECTS =================================
	 */
	
	private Command nationwideCSVCommand = new NationwideCSVCommand(this);
	private Command goBackCommand        = new GoBack();

	
	/*
	 * ===================== CONSTRUCTOR ===========================
	 */
	
    /**
     * Constructs a WriteBankStatementToDB object with the specified configuration, regex methods,
     * statement menu, inbound table, outbound table, and category table.
     *
     * @param config the application configuration
     * @param regex the regex methods for data processing
     * @param menu the statement menu
     * @param tI the inbound table
     * @param tO the outbound table
     * @param tC the category table
     */
	
	public WriteBankStatementToDB(AppConfig config, RegexMethods regex, Input input, TableInbound tI, TableOutbound tO, TableCategory tC) {
		this.config = config;
		this.regex  = regex;
		this.input  = input;
		
		this.tI = tI;
		this.tO = tO;
		this.tC = tC;
	}
	
	/*
	 * ===================== GETTERS ==============================
	 */
	
	public Input getInput() {
		return input;
	}
	
	/*
	 * ===================== GENERIC LOAD METHODS ========================
	 */
		
	@Override
	protected void loadMenuCommandMap() {
		Map<String, Command> tempMap = new LinkedHashMap<>();
		tempMap.put("NationwideCSV", nationwideCSVCommand);
		tempMap.put("Go Back", goBackCommand);
		
		setMenuCommandMap(tempMap);
	}

    /**
     * Loads the menu select for category selection.
     */

	private void loadCategoryMenuSelect() {
		categoryMenuSelect = new MenuSelect(tC.getCategoryMenu());
	}
	
    /**
     * Loads the table writers for inbound and outbound tables.
     */

	private void loadTableWriters() {
		tIW = new TableInboundWriter(tI);
		tOW = new TableOutboundWriter(tO);
		
		//Add in section for appendOrCreate confirm loading
		tCW = new TableCategoryWriter(tC,categoryMenuSelect,false,false,false);
	}
	
    /**
     * Loads the table category reader.
     */

	private void loadTableCategoryReader() {
		tCR = new TableCategoryReader(tC);
	}
	
    /**
     * Sets up the necessary components for writing the bank statement data.
     */

	@Override
	protected void additionalSetup() {
		loadCategoryMenuSelect();
		loadTableWriters();
		loadTableCategoryReader();
	}
	
	private void userChooseStatement() {
		System.out.println("Please select an appropriate bank statement");
		reader.selectStatement();
	}

	/*
	 * ======================== CONVERT & IMPORT METHODS ====================
	 */
	
    /**
     * Converts the bank statement data into transactions.
     *
     * @return an array of Transaction objects
     */

	private Transaction[] convertStatement() {
		Transaction[] transactions = reader.convertStatement(reader.getStatement(),tCR, tCW, regex);
		return transactions;
	}
	

    /**
     * Imports transaction data into the inbound and outbound tables.
     *
     * @param transactions the array of Transaction objects to import
     */

	private void importTransactionData(Transaction[] transactions) {
		tIW.importData(transactions);
		tOW.importData(transactions);
	}

	/*
	 * ========================= NATIONWIDE METHODS =======================
	 */
	
    /**
     * Loads the Nationwide CSV statement reader.
     */

	private void loadNationWideCSV() {
		reader = new NationwideCSVReader(config);
		reader.loadStatementFolder();
		userChooseStatement();
	}
	
	public void importNationwideCSV() {
		//setup();
		loadNationWideCSV();
		if(reader.getStatement()==null) {
			System.out.println("No file selected");
			return;
		}
		Transaction[] transactions = convertStatement();
		importTransactionData(transactions);
		System.out.println("Imported NationwideCSV into .db file");
	}

}
