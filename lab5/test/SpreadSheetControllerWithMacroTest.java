import org.junit.Test;
import java.io.StringReader;
import java.io.StringWriter;

import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;
import spreadsheet.SpreadSheetControllerWithMacro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the SpreadSheetControllerWithMacro class.
 */
public class SpreadSheetControllerWithMacroTest {

  /**
   * Tests that the "bulk-assign-value" command is processed correctly and that the correct
   * value is applied to the specified range of cells.
   */
  @Test
  public void testBulkAssignCommand() {
    String input = "bulk-assign-value\n0\n0\n1\n1\n10.0\nquit";
    StringReader sr = new StringReader(input);
    StringWriter sw = new StringWriter();
    SpreadSheet sheet = new SparseSpreadSheet();
    SpreadSheetControllerWithMacro controller = new SpreadSheetControllerWithMacro(sheet, sr, sw);

    // Act: Run the controller
    controller.control();

    // Assert: Check if the values in the specified range have been correctly assigned
    assertEquals(10.0, sheet.get(0, 0), 0.01);
    assertEquals(10.0, sheet.get(1, 1), 0.01);
  }

  /**
   * Tests that providing invalid inputs (e.g., negative row values) to the bulk-assign-value
   * command results in an appropriate error message.
   */
  @Test
  public void testInvalidBulkAssignCommand() {
    String input = "bulk-assign-value\n-1\n0\n1\n1\n10.0\nquit";
    StringReader sr = new StringReader(input);
    StringWriter sw = new StringWriter();
    SpreadSheet sheet = new SparseSpreadSheet();
    SpreadSheetControllerWithMacro controller = new SpreadSheetControllerWithMacro(sheet, sr, sw);
    controller.control();

    String expectedOutput = "Error: Cannot be negative.";
    assertTrue(sw.toString().contains(expectedOutput));
  }

  /**
   * Tests that the menu command prints the list of supported instructions and that
   * the program can quit without errors.
   */
  @Test
  public void testPrintMenuAndQuit() {
    String input = "menu\nquit";
    StringReader sr = new StringReader(input);
    StringWriter sw = new StringWriter();
    SpreadSheet sheet = new SparseSpreadSheet();
    SpreadSheetControllerWithMacro controller = new SpreadSheetControllerWithMacro(sheet, sr, sw);
    controller.control();

    String expectedOutput = "Supported user instructions are:";
    assertTrue(sw.toString().contains(expectedOutput));
  }
}
