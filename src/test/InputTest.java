package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import optionMenu.Input;
import java.io.ByteArrayInputStream;
import java.util.Scanner;


class InputTest {

    @Test
    public void testConfirmInput_ValidConfirmation() {
        Input input = new Input();
        ByteArrayInputStream in = new ByteArrayInputStream("y\n".getBytes());
        input.setScanner(new Scanner(in));

        boolean confirmed = input.confirmInput("Test", input.getScanner());
        assertTrue(confirmed);
    }

    @Test
    public void testConfirmInput_InvalidConfirmation() {
        Input input = new Input();
        ByteArrayInputStream in = new ByteArrayInputStream("n\n".getBytes());
        input.setScanner(new Scanner(in));

        boolean confirmed = input.confirmInput("Test", input.getScanner());
        assertFalse(confirmed);
    }

    @Test
    public void testInputInteger_ValidInputNoConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("5\n".getBytes());
    	input.setScanner(new Scanner(in));
    	//checking simple input with no confirm required
    	int inputInteger = input.inputInteger("TestInputInteger",false);
    	Assertions.assertEquals(inputInteger, 5);
    }
    
    @Test
    public void testInputInteger_ValidInputNeedsConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("11\ny\n".getBytes());
    	input.setScanner(new Scanner(in));
    	
    	int inputInteger = input.inputInteger("TestInputConfirm", true);
    	Assertions.assertEquals(inputInteger, 11);
    }
    
    @Test 
    public void testIntegerInput_InvalidInputNeedsConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("he\n10\ny\n".getBytes());
    	input.setScanner(new Scanner(in));
    	
    	int inputInteger = input.inputInteger("TestInvalidInput", true);
    	Assertions.assertEquals(inputInteger, 10);
    }
    
    @Test
    public void testIsValidNum() {
    	Input input = new Input();
    	boolean validNum = input.isValidNum(3, 5);
    	boolean invalidNum = input.isValidNum(-3,2);
    	Assertions.assertFalse(invalidNum);
    	Assertions.assertTrue(validNum);
    }
    
    @Test
    public void testInputOriginalString_tNoConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("Test\n".getBytes());
    	input.setScanner(new Scanner(in));
    	
    	String inputString = input.inputOriginalString("Test", false);
    	Assertions.assertEquals("Test", inputString);
    }
    
    @Test
    public void testInputOriginalString_NeedsConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("Test\ny\n".getBytes());
    	input.setScanner(new Scanner(in));
    	
    	String inputString = input.inputOriginalString("Test", true);
    	Assertions.assertEquals("Test", inputString);
    }
    
    @Test
    public void testInputOriginalString_NeedsConfirmCancelConfirm() {
    	Input input = new Input();
    	ByteArrayInputStream in = new ByteArrayInputStream("Test\nn\nTest\ny\n".getBytes());
    	input.setScanner(new Scanner(in));
    	
    	String inputString = input.inputOriginalString("Test", true);
    	Assertions.assertEquals("Test", inputString);    
    }
}
