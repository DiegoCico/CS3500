import java.awt.*;

/**
 * Class that is controller of game.
 */
public class GameController {
  private Game game;
  private GameGUI gameGUI;
  private boolean isGameOver;

  /**
   * Constructor for the controller of game.
   * @param game the game grid
   */
  public GameController(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("Game cannot be null");
    }
    this.game = game;
    this.isGameOver = false;

    this.gameGUI = new GameGUI(game.getGrid(), this);
  }

  /**
   * Handles each player move.
   * @param row grid row
   * @param col grid col
   */
  public void handleMove(int row, int col) {
    if (isGameOver) {
      return;
    }

    Player currentPlayer = game.getCurrentPlayer();


    if (!currentPlayer.getHand().isEmpty()) {
      Card card = currentPlayer.getHand().getFirst();
      try {
        game.placeCard(row, col, card);
        gameGUI.updateGrid();

        if (isGridFull()) {
          endGame();
        } else {
          game.switchTurns();
          gameGUI.showMessage(game.getCurrentPlayer().getName());
        }
      } catch (IllegalStateException exception) {
        gameGUI.showMessage("Invalid move. Try again bitch");
      }
    } else {
      gameGUI.showMessage(currentPlayer.getName() + " has no more moves");
    }

  }

  //TODO: FIX THIS
  /**
   * Ends the game, calculates the winner and returns result.
   */
  public void endGame() {
    isGameOver = true;

    int player1CardCount = 0;
    int player2CardCount = 0;

    for (int row = 0; row < game.getGrid().getRows(); row++) {
      for (int col = 0; col < game.getGrid().getCols(); col++) {
        Card card = game.getGrid().getCard(row, col);
        if (card != null ) {
          if (card.getColor().equals("RED")) {
            player1CardCount++;
          } else if (card.getColor().equals(Color.BLUE)) {
            player2CardCount++;
          }
        }
      }
    }
    if (player1CardCount == player2CardCount) {
      gameGUI.showMessage("RED WINS");
    } else if (player2CardCount > player1CardCount) {
      gameGUI.showMessage("BLUE WINS");
    } else {
      gameGUI.showMessage("Tie");
    }
  }


  /**
   * Switch players turn and update GUI.
   */
  private void swapTurn() {
   game.switchTurns();
  }

  /**
   * Check if the grid is fully occupied.
   * @return if grid is full or not
   */
  private boolean isGridFull() {
    for (int row = 0; row < game.getGrid().getRows(); row++) {
      for (int col = 0; col < game.getGrid().getCols(); col++) {
        if (game.getGrid().isEmpty(row, col) && game.getGrid().getCellType(row, col)
                == Cell.CellType.CARD_CELL) {
          return false;
        }
      }
    }
    return true;
  }

}
