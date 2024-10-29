package cs3500.tictactoe;

import javax.swing.JFrame;

/**
 * This is the gui version of the view.
 */
public class TicTacToeView extends JFrame implements TTTView {

  private final TTTPanel panel;

  /**
   * This is the constructor for the class.
   */
  public TicTacToeView() {
    this.panel = new TTTPanel();
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.add(this.panel);
  }

  /**
   * ads a click listener.
   * @param listener the controller
   */
  @Override
  public void addClickListener(TicTacToeController listener) {
    this.panel.addClickListener(new TTTClickListener());
  }

  /**
   * refreshes the screen.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * making the screen visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  private class TTTClickListener implements TicTacToeController {
    /**
     * Execute a single game of tic tac toe given a tic tac toe Model. When the game is over,
     * the playGame method ends.
     *
     * @param model a non-null tic tac toe Model
     */
    @Override
    public void playGame(TicTacToe model) {
      // nothing here.
    }

    /**
     * Handle an action in a single cell of the board, such as to make a move.
     *
     * @param row the row of the clicked cell
     * @param col the column of the clicked cell
     */
    @Override
    public void handleCellClick(int row, int col) {
      // nothing here.
    }
  }
}
