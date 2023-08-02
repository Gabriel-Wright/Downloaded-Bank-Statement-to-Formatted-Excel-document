package optionMenu;

/**
 * The GoBack class implements the {@link Command} interface and represents a
 * command that allows the user to go back to the previous menu in an options
 * menu system. In actuality it does not call the menu again, meaning that a
 * previous menu will be called.
 *
 * @author LORD GABRIEL
 * @see Command
 * @see OptionMenu
 * @see Process Block
 */

public class GoBack implements Command {

	/**
	 * Executes the GoBack command and prints a message indicating that the user is
	 * returning to the previous menu.
	 */

	@Override
	public void execute() {
		System.out.println("Returning to previous menu");
	}
}
