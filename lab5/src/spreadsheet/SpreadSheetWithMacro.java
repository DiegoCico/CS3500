package spreadsheet;

/**
 * Executing the Macro.
 */
public interface SpreadSheetWithMacro extends SpreadSheet {

  /**
   * Exectutes the given SpreadSheet.
   * @param macro a marcro.
   */
  void execute(Macro macro);

}
