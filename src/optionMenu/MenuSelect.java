package optionMenu;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MenuSelect{
	
	private Input input;
	protected static final Logger logger = LogManager.getLogger(MenuSelect.class.getName());
	private Menu menu;
	/*
	 * ============= CONSTRUCTORS ================
	 */
	
	public MenuSelect(Menu menu) {
		this.menu=menu;
		this.input=menu.getInput();
	}

	/*
	 * =========================== METHODS ===========================
	 */

	public void chooseFromMenu(String prompt, boolean confirm) {
		Scanner scanner = input.getScanner(); //getting scanner from Input object that belongs to Menu.
		boolean isChoiceMade = false;
		int selectOptionIndex = -1;
		while(!isChoiceMade) {  //checks whether choice made from Options yet
			selectOptionIndex=-1;
			menu.printOptions();
			System.out.println(prompt);
			try {
				selectOptionIndex = input.inputInteger("", false); //confirm toggle on int is completed later
				if(input.isValidNum(selectOptionIndex,menu.getOptions().length)) {
					//Checks whether to confirm input or not
					if(confirm) {
						if(input.confirmInput(menu.getOptions()[selectOptionIndex],scanner)) {
							isChoiceMade=true;
						}
					}
					if(!confirm) {
						isChoiceMade=true;
					}
				}
			} catch(Exception e) {
				//This will never run -- since inputInteger checks whether valid input?
				System.out.println("Invalid input format, please input a number.");
				scanner.nextLine(); //consume invalid input 
			}
		}
		menu.setChoiceIndex(selectOptionIndex); //setChoiceIndex and choice within Menu object
		String[] options = menu.getOptions();
		String choice = options[selectOptionIndex];
		menu.setChoice(options[selectOptionIndex]);
		System.out.println("Chose option:" +choice);
		logger.info("Choice:"+choice+"- this is option+"+selectOptionIndex+". Selected from Menu:"+menu.getMenuName()+".");
	}
	
}
