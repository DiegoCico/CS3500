import org.junit.Test;

import spreadsheet.BulkAssignMacro;
import spreadsheet.Macro;
import spreadsheet.SparseSpreadSheet;
import spreadsheet.SpreadSheet;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the BulkAssignMacro class.
 */
public class BulkAssignMacroTest {

  /**
   * Tests that constructing a BulkAssignMacro with a
   * negative row1 throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRow1ThrowsException() {
    new BulkAssignMacro(-1, 0, 1, 1, 5.0);
  }

  /**
   * Tests that constructing a BulkAssignMacro with a
   * negative col1 throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCol1ThrowsException() {
    new BulkAssignMacro(0, -1, 1, 1, 5.0);
  }

  /**
   * Tests that constructing a BulkAssignMacro with
   * a negative row2 throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRow2ThrowsException() {
    new BulkAssignMacro(0, 0, -1, 1, 5.0);
  }

  /**
   * Tests that constructing a BulkAssignMacro with a
   * negative col2 throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCol2ThrowsException() {
    new BulkAssignMacro(0, 0, 1, -1, 5.0);
  }

  /**
   * Tests that applying the macro sets the correct
   * values in the specified range.
   */
  @Test
  public void testApplySetsValuesInRange() {
    SpreadSheet sheet = new SparseSpreadSheet();
    Macro macro = new BulkAssignMacro(0, 0, 1, 1, 10.0);
    macro.apply(sheet);

    assertEquals(10.0, sheet.get(0, 0), 0.01);
    assertEquals(10.0, sheet.get(1, 1), 0.01);
  }

  /**
   * Tests that applying the macro does not affect
   * other cells outside the specified range.
   */
  @Test
  public void testApplyDoesNotAffectOtherCells() {
    SpreadSheet sheet = new SparseSpreadSheet();
    sheet.set(2, 2, 20.0);
    Macro macro = new BulkAssignMacro(0, 0, 1, 1, 10.0);
    macro.apply(sheet);

    assertEquals(20.0, sheet.get(2, 2), 0.01);
  }
}
