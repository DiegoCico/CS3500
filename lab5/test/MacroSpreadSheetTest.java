import org.junit.Test;
import spreadsheet.BulkAssignMacro;
import spreadsheet.Macro;
import spreadsheet.MacroSpreadSheet;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the MacroSpreadSheet class.
 */
public class MacroSpreadSheetTest {

  /**
   * Tests that the execute method applies the macro correctly, setting the specified value
   * in the given range of cells.
   */
  @Test
  public void testExecuteMacro() {
    MacroSpreadSheet sheet = new MacroSpreadSheet();
    Macro macro = new BulkAssignMacro(0, 0, 1, 1, 10.0);

    sheet.execute(macro);
    assertEquals(10.0, sheet.get(0, 0), 0.01);
    assertEquals(10.0, sheet.get(1, 1), 0.01);
  }

  /**
   * Tests that applying a macro does not affect cells outside the range specified by the macro.
   */
  @Test
  public void testExecuteMacroDoesNotAffectOtherCells() {
    MacroSpreadSheet sheet = new MacroSpreadSheet();
    sheet.set(2, 2, 20.0);

    Macro macro = new BulkAssignMacro(0, 0, 1, 1, 10.0);
    sheet.execute(macro);

    assertEquals(20.0, sheet.get(2, 2), 0.01);
  }
}
