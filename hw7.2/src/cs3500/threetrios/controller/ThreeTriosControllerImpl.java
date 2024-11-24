package cs3500.threetrios.gui;

import java.util.HashSet;
import java.util.List;

import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Cell;

import cs3500.threetrios.game.Game;
import cs3500.threetrios.player.Player;

/**
 * Implementation of the Three Trios game controller,
 * responsible for managing user interactions,
 * processing game logic, and updating the view accordingly.
 */
public class ThreeTriosControllerImpl implements ThreeTriosGameController {
  private final Game model;
  private final ThreeTriosGameView view;
  private Card selectedCard = null;
  private final HybridStrategy strategy;

  /**
   * Constructs a controller with a strategy for AI moves.
   *
   * @param model the game model
   * @param view the game view
   * @param strategy the strategy controller for AI moves
   */
  public ThreeTriosControllerImpl(Game model, ThreeTriosGameView view, HybridStrategy strategy) {
    this.model = model;
    this.view = view;
    this.strategy = strategy;
    this.view.setFeatures(new FeaturesImpl());
  }

  /**
   * Constructs a controller for player-only moves.
   *
   * @param model the game model
   * @param view the game view
   */
  public ThreeTriosControllerImpl(Game model, ThreeTriosGameView view) {
    this(model, view, null);
  }

  /**
   * Checks over the player click and make sure there is a card selected
   * and as well there is a valid row and column to place the card. If
   * all is true then it places the card.
   * @param row the row of the clicked cell.
   * @param col the column of the clicked cell.
   */
  @Override
  public void handleCellClick(int row, int col) {
    Player currentPlayer = model.getCurrentPlayerModel();
    if ((currentPlayer.getColor() == COLOR.RED && !view.isRedPlayerView()) ||
            (currentPlayer.getColor() == COLOR.BLUE && !view.isBluePlayerView())) {
      view.displayErrorMessage("It is not your turn!");
      return;
    }

    System.out.println("Cell clicked: " + row + ", " + col);
    if (model.isGameOver()) {
      view.displayGameOverMessage();
      return;
    }

    try {
      Card cardToPlace = selectedCard != null ? selectedCard
              : (currentPlayer.getHand().isEmpty() ? null : currentPlayer.getHand().get(0));

      if (cardToPlace == null) {
        view.displayErrorMessage("No cards available for the current player.");
        return;
      }

      model.placeCard(row, col, cardToPlace);
      model.battleCards(row, col, new HashSet<>());
      selectedCard = null;
      view.refresh();

      if (model.isGameOver()) {
        view.displayGameOverMessage();
        view.displayErrorMessage("Game Over: " + model.getWinner());
      } else {
        model.switchTurns();
        view.displayCurrentPlayer(model.getCurrentPlayer().getName());

        ((ThreeTriosViewImpl) view).setSelectedCardIndex(-1);
        view.refresh();

        if (strategy != null && model.getCurrentPlayer().getColor() == COLOR.BLUE) {
          handleAIMove();
        }
      }
    } catch (IllegalStateException exception) {
      view.displayErrorMessage("Invalid move. Try again.");
    }
  }


  /**
   * Makes sure it selects a valid card index.
   * @param cardIndex the index of the selected card in the player's hand.
   */
  @Override
  public void handleCardSelection(int cardIndex) {
    System.out.println("Card Index: " + cardIndex
            + " Player: " + model.getCurrentPlayer().getColor());
    Player currentPlayer = model.getCurrentPlayerModel();
    if (cardIndex < 0 || cardIndex >= currentPlayer.getHand().size()) {
      view.displayErrorMessage("Invalid card selection.");
      return;
    }

    selectedCard = currentPlayer.getHand().get(cardIndex);
    view.refresh();
  }

  /**
   * Handles the move of the AI, sees if it is a valid move
   * and if it is not a valid move it sees what other moves it can
   * do.
   */
  @Override
  public void handleAIMove() {
    if (model.isGameOver()) {
      view.displayGameOverMessage();
      return;
    }

    int[] bestMove = strategy.choosePositions(model);
    boolean movePlayed = false;

    while (!movePlayed) {
      if (bestMove[0] != -1 && bestMove[1] != -1 && bestMove[2] != -1) {
        Card aiCard = model.getCurrentPlayerModel().getHand().get(bestMove[2]);

        System.out.println("Attempting move at: Row " + bestMove[0] + ", Col " + bestMove[1]);

        if (model.isMoveLegal(bestMove[0], bestMove[1])) {
          model.placeCard(bestMove[0], bestMove[1], aiCard);
          model.battleCards(bestMove[0], bestMove[1], new HashSet<>());
          view.refresh();
          movePlayed = true;

          if (model.isGameOver()) {
            view.displayGameOverMessage();
            view.displayErrorMessage("Game Over: " + model.getWinner());
          } else {
            model.switchTurns();
            view.displayCurrentPlayer(model.getCurrentPlayer().getName());
          }
        } else {
          System.out.println("Move invalid. Attempting fallback to top-right area.");

          bestMove = findTopRightAvailableMove(model);
          if (bestMove == null) {
            view.displayErrorMessage("AI could not find a valid move.");
            break;
          }
        }
      } else {
        view.displayErrorMessage("AI could not find a valid move.");
        break;
      }
    }
  }

  /**
   * Finds the top-right or nearest available legal spot to place a card.
   * @param model the game model
   * @return an array [row, col, cardIndex] for a legal move, or null if none found
   */
  private int[] findTopRightAvailableMove(Game model) {
    int numCols = model.getGrid().getCols();
    int numRows = model.getGrid().getRows();
    List<Card> hand = model.getCurrentPlayerModel().getHand();

    for (int row = 0; row < numRows; row++) {
      for (int col = numCols - 1; col >= 0; col--) {
        if (model.getGrid().getCellType(row, col) == Cell.CellType.CARD_CELL &&
                model.getGrid().isEmpty(row, col)) {

          for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
            Card card = hand.get(cardIndex);

            if (model.isMoveLegal(row, col)) {
              System.out.println("Found fallback move at Row " + row + ", Col " + col);
              return new int[] { row, col, cardIndex };
            }
          }
        }
      }
    }
    System.out.println("No available moves in the top-right area.");
    return null;
  }

  private class FeaturesImpl implements Features {

    /**
     * Handle the player cell click.
     * @param row the row of the clicked cell.
     * @param col the column of the clicked cell.
     */
    @Override
    public void handleCellClick(int row, int col) {
      ThreeTriosControllerImpl.this.handleCellClick(row, col);
    }

    /**
     * handle the player card selection click.
     * @param cardIndex the index of the selected card in the player's hand.
     */
    @Override
    public void handleCardSelection(int cardIndex) {
      ThreeTriosControllerImpl.this.handleCardSelection(cardIndex);
    }

    /**
     * handle the AI moves and logics.
     */
    @Override
    public void handleAIMove() {
      ThreeTriosControllerImpl.this.handleAIMove();
    }
  }
}
