package optionMenu;

/**
 * The Command interface represents an executable command that can be used in an
 * options menu. Classes implementing this interface can be used to group
 * different actions as commands, allowing them to be executed based on user
 * choices in a menu.
 *
 * Implementing classes must provide a concrete implementation of the execute()
 * method, which defines the action to be taken when the command is executed.
 *
 * Example Usage: {@code
 * public class MyCommand implements Command {
 *     public void execute() {
 *         // Perform specific action for this command.
 *         // This method will be called when the command is executed in the menu.
 * } }
 *
 * @author LORD GABRIEL
 * 
 * @see OptionMenu
 * @see MenuBlock
 */

public interface Command {
	/**
	 * Executes the specific action associated with this command. The concrete
	 * implementation of this method defines what action will be performed when the
	 * command is executed.
	 *
	 */
	void execute();
}
