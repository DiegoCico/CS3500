package spreadsheet;

import java.util.Scanner;

/**
 * A controller that extends the basic spreadsheet controller to include support for macros.
 */
public class SpreadSheetControllerWithMacro extends SpreadSheetController {

  /**
   * Creates a controller to work with the specified sheet,
   * readable, and appendable.
   *
   * @param sheet      the sheet to work with
   * @param readable   the Readable object for inputs
   * @param appendable the Appendable object to transmit any output
   */
  public SpreadSheetControllerWithMacro(SpreadSheet sheet, Readable readable,
                                        Appendable appendable) {
    super(sheet, readable, appendable);
  }

  /**
   * Processes the user's command.
   *
   * @param userInstruction the user's command
   * @param sc              the scanner to parse additional input
   * @param sheet           the spreadsheet to apply the command to
   */
  @Override
  protected void processCommand(String userInstruction, Scanner sc, SpreadSheet sheet) {
    if (userInstruction.equals("bulk-assign-value")) {
      try {
        int row1 = sc.nextInt();
        int col1 = sc.nextInt();
        int row2 = sc.nextInt();
        int col2 = sc.nextInt();
        double value1 = sc.nextDouble();
        Macro bulkAssign = new BulkAssignMacro(row1, col1, row2, col2, value1);
        bulkAssign.apply(sheet);
      } catch (IllegalArgumentException | IllegalStateException e) {
        writeMessage("Error: " + e.getMessage());
      }
    } else {
      super.processCommand(userInstruction, sc, sheet);
    }
  }

  /**
   * Prints the menu of available commands, then prints an empty line afterward.
   *
   * @throws IllegalStateException if writing the menu fails
   */
  @Override
  protected void printMenu() throws IllegalStateException {
    super.printMenu();
    super.writeMessage("");
  }
}
