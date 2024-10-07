package spreadsheet;

/**
 * A macro that assigns a specified value to a range of cells in a spreadsheet.
 */
public class BulkAssignMacro implements Macro {
  private int row1;
  private int col1;
  private int row2;
  private int col2;
  private double value;

  /**
   * Constructs a BulkAssignMacro that assigns a value to a range of cells.
   *
   * @param row1   the starting row index (inclusive)
   * @param col1   the starting column index (inclusive)
   * @param row2   the ending row index (inclusive)
   * @param col2   the ending column index (inclusive)
   * @param value  the value to assign to the specified range
   * @throws IllegalArgumentException if any of the row or column indices are negative
   */
  public BulkAssignMacro(int row1, int col1, int row2, int col2, double value) {
    if (row1 < 0 || row2 < 0 || col1 < 0 || col2 < 0) {
      throw new IllegalArgumentException("Row and column indices cannot be negative.");
    }
    this.row1 = row1;
    this.col1 = col1;
    this.row2 = row2;
    this.col2 = col2;
    this.value = value;
  }

  /**
   * Applies the macro to the given spreadsheet, assigning the value to all cells
   * in the specified range.
   *
   * @param spreadSheet the spreadsheet on which the macro is applied
   */
  @Override
  public void apply(SpreadSheet spreadSheet) {
    for (int i = row1; i <= row2; i++) {
      for (int j = col1; j <= col2; j++) {
        spreadSheet.set(i, j, value);
      }
    }
  }
}
