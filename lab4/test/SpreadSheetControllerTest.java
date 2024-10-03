import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetController;

/**
 * This is a test class for the SpreadSheetController.
 */
public class SpreadSheetControllerTest {

  /**
   * Test that checks if assigning a value to a cell works and if it prints the correct value back.
   */
  @Test
  public void testAssignValueAndPrint() {
    Readable readable = new StringReader("assign-value A 1 100.5 print-value A 1 q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Type instruction: Value: 100.5\n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * This test checks how the controller handles an invalid instruction.
   */
  @Test
  public void testInvalidInstruction() {
    Readable readable = new StringReader("invalid-command q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Undefined instruction: invalid-command\n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * Test for handling invalid row input.
   */
  @Test
  public void testInvalidRowInput() {
    Readable readable = new StringReader("assign-value 1 1 10 q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Error: Invalid row\n" +
            "Type instruction: Undefined instruction: 1\n" +
            "Type instruction: Undefined instruction: 10\n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * This test checks what happens when you try to print the value of an empty cell.
   */
  @Test
  public void testPrintEmptyCell() {
    Readable readable = new StringReader("print-value A 1 q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Value: 0.0\n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * Test that prints the menu when requested by the user.
   */
  @Test
  public void testPrintMenu() {
    Readable readable = new StringReader("menu q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }

  /**
   * Test that checks if assigning values to multiple cells works properly.
   */
  @Test
  public void testAssignMultipleCells() {
    Readable readable = new StringReader("assign-value A 1 10 assign-value B 2 20 " +
            "print-value A 1 print-value B 2 q");
    Appendable appendable = new StringBuilder();
    SpreadSheet spread = new SparseSpreadSheet();
    SpreadSheetController spreadSheetController =
            new SpreadSheetController(spread, readable, appendable);

    spreadSheetController.control();

    String expectedOutput = "Welcome to the spreadsheet program!\n" +
            "Supported user instructions are: \n" +
            "assign-value row-num col-num value (set a cell to a value)\n" +
            "print-value row-num col-num (print the value at a given cell)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Type instruction: Type instruction: Type instruction: Value: 10.0\n" +
            "Type instruction: Value: 20.0\n" +
            "Type instruction: Thank you for using this program!";

    Assert.assertEquals(expectedOutput, appendable.toString());
  }
}
