import java.io.IOException;
import java.util.Objects;

import cs3500.tictactoe.Player;
import cs3500.tictactoe.TicTacToe;

/**
 * A mock implementation of the Tic-Tac-Toe game
 * to log moves and other inputs.
 */
public class MockTTTConfirmInputs implements TicTacToe {

  private final Appendable log;

  /**
   * Creates a mock Tic-Tac-Toe game that logs input moves to the provided {@link Appendable}.
   *
   * @param log an {@link Appendable} where moves are logged
   * @throws NullPointerException if the log is null
   */
  public MockTTTConfirmInputs(Appendable log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Pretends to make a move on the board by logging the row and column to the log.
   *
   * @param r the row where the move is made (0-indexed)
   * @param c the column where the move is made (0-indexed)
   */
  @Override
  public void move(int r, int c) {
    try {
      log.append(r + " " + c + "\n");
    } catch (IOException ignored) {

    }
  }

  /**
   * Always returns {@link Player#O}.
   *
   * @return {@link Player#O}, because why not?
   */
  @Override
  public Player getTurn() {
    return Player.O;
  }

  /**
   * Always returns false.
   *
   * @return false
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * returns null.
   *
   * @return null
   */
  @Override
  public Player getWinner() {
    return null;
  }

  /**
   * Returns an empty 2D array of players.
   *
   * @return an empty 2D array of {@link Player}s
   */
  @Override
  public Player[][] getBoard() {
    return new Player[0][];
  }

  /**
   * Always returns null.
   *
   * @param r the row to check (0-indexed)
   * @param c the column to check (0-indexed)
   * @return null
   */
  @Override
  public Player getMarkAt(int r, int c) {
    return null;
  }
}
