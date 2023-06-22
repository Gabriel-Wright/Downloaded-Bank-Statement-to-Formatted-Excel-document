package optionMenu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sqliteData.Database;

public class Menu {
	
	private String menuName;
	private String[] options;
	private String choice;
	private int choiceIndex;
	private Input input;

	/*
	 * ============= CONSTRUCTORS ================
	 */
	
	public Menu(String menuName,String options[],Input input) {
		this.menuName=menuName;
		this.setOptions(options);
		this.input= input;
	}
	
	/*
	 * =========================== GETTERS ============================
	 */
	
	public String getMenuName() {
		return this.menuName;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public String getChoice() {
		return choice;
	}
	
	public int getChoiceIndex() {
		return choiceIndex;
	}
	
	public Input getInput() {
		return input;
	}
	
	/*
	 * =========================== SETTERS ============================
	 */
	
	public void setMenuName(String name) {
		this.menuName=name;
	}
	
	public void setOptions(String[] options) {
		this.options = options;
	}
	

	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	public void setChoiceIndex(int choiceIndex) {
		this.choiceIndex = choiceIndex;
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	/*
	 * =========================== METHODS ===========================
	 */
	
	public void printOptions() {
		int numOptions = options.length;			
		System.out.println("\nPlease see the following options for the "+menuName +" menu:");
		for(int i=0;i<numOptions;i++) {
			System.out.println("["+i+"] - "+options[i]);
		}
	}

}
