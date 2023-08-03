import optionMenu.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MenuSelectTest {

	@Test
	public void testChooseFromMenu_ValidNumNoConfirm() {
		String[] options = {"option1","option2","option3"};
		Menu menu = new Menu("testMenu", options, new Input());
		
		Input input = new Input();
        ByteArrayInputStream in = new ByteArrayInputStream("0\n".getBytes());
        input.setScanner(new Scanner(in));
        
        menu.setInput(input);
		MenuSelect menuSelect = new MenuSelect(menu);
		
		menuSelect.chooseFromMenu("testChooseFromMenu_ValidNumNoConfirm", false);
		String choice = menu.getChoice();
		Assertions.assertEquals(choice,"option1");
	}
	
	@Test
	public void testChooseFromMenu_ValidNumConfirm() {
		String[] options = {"option1","option2","option3"};
		Menu menu = new Menu("testMenu", options, new Input());
		
		Input input = new Input();
        ByteArrayInputStream in = new ByteArrayInputStream("0\nY\n".getBytes());
        input.setScanner(new Scanner(in));
        
        menu.setInput(input);
		MenuSelect menuSelect = new MenuSelect(menu);
		
		menuSelect.chooseFromMenu("testChooseFromMenu_ValidNumConfirm", true);
		String choice = menu.getChoice();
		Assertions.assertEquals(choice,"option1");

	}
	
	@Test
	public void testChooseFromMenu_InvalidNumNoConfirm() {
		String[] options = {"option1","option2","option3"};
		Menu menu = new Menu("testMenu", options, new Input());
		
		Input input = new Input();
        ByteArrayInputStream in = new ByteArrayInputStream("hello\n2\n".getBytes());
        input.setScanner(new Scanner(in));
        
        menu.setInput(input);
		MenuSelect menuSelect = new MenuSelect(menu);
		
		menuSelect.chooseFromMenu("testChooseFromMenu_InvalidNumNoConfirm", false);
		String choice = menu.getChoice();
		Assertions.assertEquals(choice,"option3");
	}

}
