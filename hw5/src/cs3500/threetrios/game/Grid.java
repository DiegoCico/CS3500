package cs3500.threetrios;

public interface Grid {

  /**
   * This will place the card at a specific location on the grid.
   * @param row grid row
   * @param col grid column
   * @param card the card
   * @return if the card could be placed or not
   */
  boolean placeCard(int row, int col, Card card);

  /**
   * Gets the card at a specific location.
   * @param row grid row
   * @param col grid column
   * @return the card at that location
   */
  Card getCard(int row, int col);

  /**
   * Check if a cell at a specific location is empty.
   * @param row grid row
   * @param col grid column
   * @return if cell is empty or not
   */
  boolean isEmpty(int row, int col);

  /**
   * Get the type of cell at a specific location.
   * @param row grid row
   * @param col grid column
   * @return the CellType
   */
  Cell.CellType getCellType(int row, int col);

  /**
   * Get the number of rows in the grid.
   * @return number of rows
   */
  int getRows();

  /**
   * Get the number of cols in the grid.
   * @return number of cols
   */
  int getCols();


  /**
   * checks if it is a valid position in the grid
   * @param row row of grid.
   * @param col col of grid.
   * @return if the coordinates is a valid position.
   */
  boolean validPosition(int row, int col);

  /**
   * gets the area of the gird
   * @return area of grid
   */
  int getNumCardsCells();

  Cell[][] getCells();

  void addCardCell(int row, int col);
  void addHole(int row, int col);
}
