
import cs3500.tictactoe.TTTView;
import cs3500.tictactoe.TicTacToeController;
import cs3500.tictactoe.TicTacToeModel;
import cs3500.tictactoe.TicTacToeView;
import cs3500.tictactoe.TicTacToeGuiController;

/**
 * This is where the user runs the board.
 */
public class Main {

  /**
   * This is the constructor for the class.
   * @param args this is args
   */
  public static void main(String[] args) {

    TicTacToeModel model = new TicTacToeModel();
    TTTView view = new TicTacToeView();
    TicTacToeController controller = new TicTacToeGuiController(view);
    controller.playGame(model);
  }
}