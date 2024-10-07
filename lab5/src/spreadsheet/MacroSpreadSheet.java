package spreadsheet;

/**
 * class for MacroSpreadSheet to execute the method.
 */
public class MacroSpreadSheet extends SparseSpreadSheet implements SpreadSheetWithMacro {

  /**
   * Constructor for MacroSpread.
   */
  public MacroSpreadSheet() {
    super();
  }

  /**
   * Exectutes the given SpreadSheet.
   *
   * @param macro a marcro.
   */
  @Override
  public void execute(Macro macro) {
    macro.apply(this);
  }
}
