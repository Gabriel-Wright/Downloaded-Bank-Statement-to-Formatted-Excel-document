package optionMenu;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sqliteData.Database;

public class Input {

	private Scanner scanner;
	protected static final Logger logger = LogManager.getLogger(Input.class.getName());

	/*
	 * ============= CONSTRUCTORS ================
	 */
	
	public Input(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public Input() {
		
	}
	
	/*
	 * =========================== GETTERS ============================
	 */

	public Scanner getScanner() {
		return scanner;
	}	
	
	/*
	 * ========================== SETTERS =============================
	 */
	
	public void setScanner(Scanner scanner) {
		this.scanner=scanner;
	}
	
	
	/*
	 * =========================== METHODS ===========================
	 */
	
	//User inputs a string, this method raises a confirmation choice - where the user
	//decides whether they are comfortable with their input.
	
	public boolean confirmInput(String input, Scanner scanner) {
		String confirm;
		while(true) {
			System.out.println("Please enter Y if comfortable with input:" +input+", N if not.");
    		confirm = scanner.nextLine().toLowerCase();
    		if(confirm.equals("n")) {
    			logger.info("Not confirming input, input:"+input);
    			return false;
    		}
    		else if(confirm.equals("y")){
    			logger.info("Input confirmed, input:"+input);
    			return true;
    		}
		}
	}
	
	//User inputs an integer, prompt is the message that will be displayed.
	//Confirm is a toggle for whether confirmInput is required.
	public int inputInteger(String prompt, boolean confirm) {
		int choice = 0;
		boolean isIntegerInputted = false;
		
	    while (!isIntegerInputted) {
	        System.out.println(prompt);
	    	if (scanner.hasNextInt()) {
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character
	            //need to confirm input
	            if(confirm) {
	            	if(confirmInput(choice+"",scanner)) {
	            		isIntegerInputted = true;
	            	}
	            }
	            
	            //No need to confirm input
	            if(!confirm) {
	            	logger.info("Integer inputted, returned:"+choice+".Prompt:"+prompt);
	            	return choice;
	            }
	             
	        } else {
	            scanner.nextLine(); // Consume invalid input
	            System.out.println("Invalid input format. Please enter a number.");
	        }
	    }
    	logger.info("Integer inputted, returned:"+choice+".Prompt:"+prompt);
	    return choice;
	}
	
	//Checks whether intOptionIndex lies in appropriate indexRange
	public boolean isValidNum(int optionIndex, int maxIndex) {
		return (optionIndex<maxIndex)&&(optionIndex>=0);
	}

	//User inputs a String.Prompt is the message that will be displayed.
	//Confirm is a toggle for whether confirmInput is required.

	public String inputOriginalString(String prompt, boolean confirm) {
		boolean isStringInputted = false;
		String stringInput=null;
		while(!isStringInputted) {
			System.out.println(prompt);
			stringInput = scanner.nextLine();
			//If we need to confirm
			if(confirm) {
				if(confirmInput(stringInput,scanner)) {
					isStringInputted=true;
				}
			}
			//If no need for confirmation
			if(!confirm) {
				isStringInputted=true;

			}
		}		    	
		logger.info("String inputted, returned:"+stringInput+".Prompt:"+prompt);
		return stringInput;
	}



}
