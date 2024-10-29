package cs3500.tictactoe;

/**
 * This is the class for the gui controller.
 */
public class TicTacToeGuiController {

  private final TTTView view;
  private TicTacToe model;

  /**
   * This is the constructor for the class.
   * @param view takes in the view interface
   */
  public TicTacToeGuiController(TTTView view) {
    if (view == null) {
      throw new IllegalArgumentException("view is null");
    }
    this.view = view;
    view.addClickListener((TicTacToeController) this);
  }

  /**
   * This implements the model and view.
   * @param model the tic tac toe model
   */
  public void playGame(TicTacToe model) {
    if (model == null) {
      throw new IllegalArgumentException("model is null");
    }
    this.model = model;
    view.makeVisible();
  }

  public void handleCellClick(int row, int col) {
    model.move(row, col);
    view.refresh();
  }
}
