package optionMenu;

import java.util.Map;

/**
 * The ProcessBlock class is an abstract class that represents a block of functionality
 * with a set of commands/options that can be selected by the user. It provides methods
 * to load and setup a menu block, choose and process statements, and handle additional
 * setup and reloading of the command map.
 *
 * Concrete subclasses of ProcessBlock can implement the {@link #loadMenuCommandMap()}
 * method to provide a map of menu options and their corresponding commands, and can also
 * override the {@link #additionalSetup()} method if additional setup is needed. The
 * ProcessBlock class can be used to create reusable blocks of functionality that can be
 * integrated into larger menu systems.
 *
 * @see MenuBlock
 * @see Command
 * @see Input
 * @see java.util.Map
 * @author LORD GABRIEL
 */
public abstract class ProcessBlock {

	private MenuBlock menuBlock;
	
	
	/*
	 * ============ COMMAND OBJECTS ==========
	 */
	
	private Map<String,Command> menuCommandMap;
	private Command goBack = new GoBack();
	
	/*
	 * ============== GETTERS ==================
	 */
	
	protected MenuBlock getMenuBlock() {
		return menuBlock;
	}
	
	protected Map<String,Command> getMenuCommandMap() {
		return menuCommandMap;
	}
	
	protected Command getGoBack() {
		return goBack;
	}
	
	/*
	 * ============== SETTERS ======================
	 */
	
	protected void setMenuCommandMap(Map<String,Command> menuCommandMap) {
		this.menuCommandMap = menuCommandMap;
	}
	
	/*
	 *  =========== LOAD MENU BLOCK METHODS =================
	 */

    /**
     * Loads the menu command map with options and their corresponding commands.
     * Concrete subclasses should implement this method to provide the map of
     * menu options and commands to be used in the menu block.
     */
	
	protected abstract void loadMenuCommandMap();
	
    /**
     * Loads and sets up the menu block with the provided menu name and input source.
     * This method should be called before using {@link #chooseProcessFirstRun(String, Input, String, boolean)}
     * or {@link #chooseProcess(String, boolean)}.
     *
     * @param menuName - the name of the menu block
     * @param input    - the input source for the menu block
     */

	protected void loadMenuBlock(String menuName, Input input) {
		menuBlock = new MenuBlock(menuName, menuCommandMap, input);
	}
	
    /**
     * Sets up the menu block and loads the menu command map.
     * This method is a convenience method that calls {@link #loadMenuCommandMap()}
     * and {@link #loadMenuBlock(String, Input)}, and then sets up the menu block
     * using {@link MenuBlock#setUp()}.
     *
     * @param menuName - the name of the menu block
     * @param input    - the input source for the menu block
     */

	protected void setupMenuBlock(String menuName, Input input) {
		loadMenuCommandMap();
		loadMenuBlock(menuName, input);
		menuBlock.setUp();
	}
		
	//empty - could set as abstract, but do not need all extensions to include it.
	protected void additionalSetup() {
		
	}
	
    /**
     * Reloads the menu command map by calling {@link #loadMenuCommandMap()}.
     * This method is useful for refreshing the menu options and commands after
     * changes have been made to the underlying data.
     */

	protected void reload() {
		loadMenuCommandMap();
	}
	/*
	 *  ========== MENU CHOOSE OPTIONS METHOD =================
	 */
	
	public void chooseProcessFirstRun(String menuName, Input input, String prompt, boolean confirmToggle) {
		additionalSetup();
		setupMenuBlock(menuName, input);
		menuBlock.getMenuSelect().chooseFromMenu(prompt, confirmToggle);
		menuBlock.getMenu().loadCommandChoice();
		menuBlock.getMenu().getCommandChoice().execute();
	}
	
	public void chooseProcess(String prompt, boolean confirmToggle) {
		reload();
		menuBlock.getMenuSelect().chooseFromMenu(prompt, confirmToggle);
		menuBlock.getMenu().loadCommandChoice();
		menuBlock.getMenu().getCommandChoice().execute();
	}
}
