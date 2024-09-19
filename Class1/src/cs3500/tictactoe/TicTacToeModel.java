package cs3500.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TicTacToeModel implements TicTacToe {
  private Player currentPlayer;
  private Player[][] grid;

  /**
   * Constructor for TicTacToeModel.
   */
  public TicTacToeModel() {
    currentPlayer = Player.X;
    grid = new Player[3][3];
  }

  /**
   * Returns a string representation of the Tic-Tac-Toe board.
   *
   * @return a formatted string of the current board state
   */
  @Override
  public String toString() {
    return Arrays.stream(getBoard())
            .map(row -> " " + Arrays.stream(row)
                    .map(p -> p == null ? " " : p.toString())
                    .collect(Collectors.joining(" | ")))
            .collect(Collectors.joining("\n-----------\n"));
  }

  /**
   * Executes a move in the specified row and column.
   *
   * @param r the row of the intended move
   * @param c the column of the intended move
   * @throws IllegalArgumentException if the space is occupied or the position is invalid
   * @throws IllegalStateException if the game is over
   */
  @Override
  public void move(int r, int c) {
    // implementation
  }

  /**
   * Returns the current turn, i.e., the player who will make the next move.
   *
   * @return the player whose turn it is
   */
  @Override
  public Player getTurn() {
    return currentPlayer;
  }

  /**
   * Returns whether the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Returns the winner of the game, or null if there is no winner.
   *
   * @return the winner, or null if the game is not over or there is no winner
   */
  @Override
  public Player getWinner() {
    return null;
  }

  /**
   * Returns the current game state as a 2D array of Player.
   *
   * @return the current game board
   */
  @Override
  public Player[][] getBoard() {
    Player[][] copy = new Player[3][3];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        copy[i][j] = grid[i][j];
      }
    }
    return copy;
  }

  /**
   * Returns the player mark at a given row and column, or null if the position is empty.
   *
   * @param r the row
   * @param c the column
   * @return the player at the given position, or null if it's empty
   */
  @Override
  public Player getMarkAt(int r, int c) {
    return grid[r][c];
  }
}
