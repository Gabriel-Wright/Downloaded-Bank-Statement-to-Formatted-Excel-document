package applicationStart.misc;

public class PrintMessage {

	private String version;

	public PrintMessage(String version) {
		this.version = version;
	}

	/**
	 * Prints a welcome message explaining the purpose of the program.
	 */
	public void printWelcomeMessage() {
		System.out.println("Welcome to Gabriel's Bank Statement Converter");
		System.out.println("This program is designed to help you process your bank statements into a helpful format.");
		System.out.println("It takes downloaded bank statements as input, either in excel or csv format, and produces an SQLITE .db file based on this data");
		System.out.println(
				"From these SQLITE .db files, data entries can be read and formatted into a helpful excel document.");
		System.out.println(
				"The purpose of this program is to enable users to store and organise their data in multiple formats.");
		System.out.println("There are customisation options available, please visit Help to find out more");
	}

	public void printHelp() {
		System.out.println("Please see the attached manual, this should answer all questions related to your issues.");
		System.out.println("NOTE: Please do not make changes to any of the .properties or .db files without first consulting the manual");
	}
}
