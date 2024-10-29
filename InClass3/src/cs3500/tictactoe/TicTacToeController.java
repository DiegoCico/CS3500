package cs3500.tictactoe;

/**
 * Represents a Controller for Tic Tac Toe: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface TicTacToeController {

  /**
   * Execute a single game of tic tac toe given a tic tac toe Model. When the game is over,
   * the playGame method ends.
   *
   * @param model a non-null tic tac toe Model
   */
  void playGame(TicTacToe model);

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  void handleCellClick(int row, int col);
}
