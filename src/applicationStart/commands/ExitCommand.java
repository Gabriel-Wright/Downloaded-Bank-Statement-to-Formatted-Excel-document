package applicationStart.commands;

import optionMenu.Command;

public class ExitCommand implements Command {

	@Override
	public void execute() {
		
		System.out.println("Closing the program");
		System.exit(0);
	}
	
}
