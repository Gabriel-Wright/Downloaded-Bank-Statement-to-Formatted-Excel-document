package optionMenu;

import java.util.Map;

/**
 * The MenuBlock class represents a block of all objects required for full menu
 * functionality It provides the top-level options for the user to choose from
 * and retrieves their selection.
 * 
 * @see optionMenu.Input
 * @see optionMenu.Menu
 * @see optionMenu.MenuSelect
 * @author LORD GABRIEL
 */

public class MenuBlock {

	private Map<String, Command> menuCommandsMap;
	private String menuName;
	private Input menuInput;
	private Menu menu;
	private MenuSelect menuSelect;

	/*
	 * ============== CONSTRUCTORS =================
	 */
	/**
	 * Constructs a MenuBlock object with the specified Input object.
	 *
	 * @param input the Input object to handle user input.
	 */

	public MenuBlock(String menuName, Map<String, Command> menuCommandsMap, Input menuInput) {
		this.menuName = menuName;
		this.menuInput = menuInput;
		this.menuCommandsMap = menuCommandsMap;
	}

	/*
	 * =============== GETTERS ======================
	 */

	/**
	 * Returns the main menu object.
	 * 
	 * @return the main menu object.
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Returns the menu Select object of the block.
	 * 
	 * @return the menu select object
	 */
	
	public MenuSelect getMenuSelect() {
		return menuSelect;
	}
	
	/*
	 * =============== SETTERS ================
	 */
	
	public void setMenuCommandsMap(Map<String, Command> menuCommandMap) {
		this.menuCommandsMap = menuCommandsMap;
	}
	
	public void setInput(Input menuInput) {
		this.menuInput = menuInput;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void setMenuSelect(MenuSelect menuSelect) {
		this.menuSelect = menuSelect;
	}
	
	/*
	 * ================ METHODS =======================
	 */
	
	/**
	 * Loads the menu with the predefined menu options.
	 */

	private void loadMenu() {
		menu = new Menu(menuName, menuCommandsMap, menuInput);
		menu.loadOptions();
	}
	
	/**
	 * Loads the menu selector for the main menu.
	 */

	private void loadSelect() {
		menuSelect = new MenuSelect(menu);
	}
	
	/**
	 * Loads both the menu and menuSelector for the menu block.
	 */
	public void setUp() {
		loadMenu();
		loadSelect();
	}



}
