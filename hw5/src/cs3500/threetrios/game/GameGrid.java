package cs3500.threetrios.game;

import cs3500.threetrios.card.Card;

/**
 * This class represents a GameGrid.
 */
public class GameGrid implements Grid {
  private final Cell[][] cells;
  private final int rows;
  private final int cols;

  /**
   * GameGrid constructor.
   * @param row grid rows
   * @param col grid col
   * @param cells grid
   */
  public GameGrid(int row, int col, Cell[][] cells) {
    /* INVARIANT: Grid rows and cells must be non-zero and positive. */
    if (row <= 0 || col <= 0) {
      throw new IllegalStateException("Invalid row or column");
    }
    if (cells == null) {
      throw new IllegalArgumentException("cells cannot be null");
    }
    this.rows = row;
    this.cols = col;
    this.cells = cells;

    initializeGrid();
  }

  /**
   * Another constructor that takes in a grid object.
   * @param grid game grid
   */
  public GameGrid(Grid grid) {
    // INVARIANT:Grid cannot be null
    if (grid == null) {
      throw new IllegalArgumentException("grid cannot be null");
    }
    this.rows = grid.getRows();
    this.cols = grid.getCols();
    this.cells = grid.getCells();
  }

  /**
   * GameGrid constructor.
   * @param row grid rows
   * @param col grid col
   */
  public GameGrid(int row, int col) {
    // INVARIANT: Grid rows and cells must be non-zero and positive
    if (row < 0 || col < 0) {
      throw new IllegalStateException("Invalid row or column");
    }
    this.rows = row;
    this.cols = col;
    this.cells = new Cell[row][col];
    initializeGrid();
  }

  /**
   * Initializes a grid.
   */
  public void initializeGrid() {
    /* INVARIANT: All cells are initialized as CARD_CELL by default */
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
  }

  /**
   * Gets the grid columns.
   * @return cols
   */
  @Override
  public int getCols() {
    return cols;
  }

  /**
   * Will place card if possible.
   * @param row grid row
   * @param col grid column
   * @param card the card
   */
  @Override
  public void placeCard(int row, int col, Card card) {
    if (validPosition(row, col) && cells[row][col].isCardCell() &&
            cells[row][col].isEmpty()) {
      cells[row][col].placeCard(card);
      System.out.println(cells[row][col].toString());
    } else {
      throw new IllegalStateException("Invalid position");
    }
  }

  /**
   * Gets the grid rows.
   * @return rows
   */
  @Override
  public int getRows() {
    return rows;
  }


  /**
   * Checks if it is a valid spot for a card.
   * @param row row of grid.
   * @param col col of grid.
   * @return if it's a valid spot for a card
   */
  @Override
  public boolean validPosition(int row, int col) {
    return row >= 0 && row < cells.length && col >= 0 && col < cells[0].length;
  }


  /**
   * gets the area of the grid.
   * @return area of grid
   */
  @Override
  public int getNumCardsCells() {
    return rows * cols;
  }


  /**
   * Get current card.
   * @param row grid row
   * @param col grid column
   * @return the current card
   */
  @Override
  public Card getCard(int row, int col) {
    if (validPosition(row, col)) {
      return cells[row][col].getCard();
    } else {
      throw new IllegalStateException("Invalid position");
    }
  }

  /**
   * Checks if cell is empty.
   * @param row grid row
   * @param col grid column
   * @return if cell is empty
   */
  @Override
  public boolean isEmpty(int row, int col) {
    if (validPosition(row, col)) {
      return cells[row][col].isEmpty();
    }
    return false;
  }

  /**
   * Gets the cell type.
   * @param row grid row
   * @param col grid column
   * @return cell type
   */
  @Override
  public Cell.CellType getCellType(int row, int col) {
    if (validPosition(row, col)) {
      return cells[row][col].getType();
    } else {
      throw new IllegalStateException("Invalid CellType");
    }
  }

  /**
   * Gets all the cells in the grid.
   * @return a copy of the grid
   */
  public Cell[][] getCells() {
    Cell[][] copy = new Cell[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Cell original = cells[i][j];
        copy[i][j] = original.isEmpty() ? new Cell(original.getType())
                : new Cell(original.getCard(), original.getType());
      }
    }
    return copy;
  }

  /**
   * This will make a cell - CARD_CELL or HOLE.
   * @param row specific row
   * @param col specific column
   * @param type the cell type ENUM
   */
  @Override
  public void setCellType(int row, int col, Cell.CellType type) {
    if (validPosition(row, col)) {
      cells[row][col] = new Cell(type);
    }
  }


}
