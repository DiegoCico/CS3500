package cs3500.threetrios.controller;

import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.gui.AbstractPlayerView;

import java.util.HashSet;

/**
 * Represents the AI controller for the Three Trios game.
 * This controller handles the behavior of the AI player,
 * including choosing moves using a strategy,
 * handling fallback logic for invalid moves,
 * and managing the AI's turn progression.
 */

public class AIController extends AbstractController {
  private final HybridStrategy strategy;

  /**
   * Constructs an AIController with the specified game model, view, and strategy.
   *
   * @param model    the game model used to manage game state and logic
   * @param strategy the strategy used by the AI to decide its moves
   * @param view     the game view used to display updates to the user
   */
  public AIController(Game model, HybridStrategy strategy, AbstractPlayerView view) {
    super(model, view);
    this.strategy = strategy;
  }

  /**
   * Executes the AI's turn by choosing and playing a move
   * based on the provided strategy.
   * If the strategy fails to find a valid move,
   * a fallback method attempts to locate legal move.
   * Updates the view and switches turns after the move is made.
   */
  @Override
  public void makeMove() {
    if (model.isGameOver()) {
      view.displayGameOverMessage();
      return;
    }

    int[] bestMove = strategy.choosePositions(model);
    if (!playMove(bestMove)) {
      int[] fallbackMove = findFallbackMove();
      if (!playMove(fallbackMove)) {
        view.displayErrorMessage("AI could not find a valid move.");
      }
    }

    if (!model.isGameOver()) {
      model.switchTurns();
      view.displayCurrentPlayer(model.getCurrentPlayer().getName());
    } else {
      view.displayGameOverMessage();
    }
  }

  /**
   * Attempts to play a move for the AI.
   * @param move an array of {row, col, cardIndex}, or null if no move is available.
   * @return true if the move was successfully played, false otherwise.
   */
  private boolean playMove(int[] move) {
    if (move == null || move.length != 3) return false;

    try {
      Card aiCard = model.getCurrentPlayerModel().getHand().get(move[2]);
      if (model.isMoveLegal(move[0], move[1])) {
        model.placeCard(move[0], move[1], aiCard);
        model.battleCards(move[0], move[1], new HashSet<>());
        view.refresh();
        return true;
      }
    } catch (Exception e) {
      System.out.println("AI move failed: " + e.getMessage());
    }
    return false;
  }

  /**
   * Finds a fallback move by iterating over the grid to locate a legal move.
   * @return an array {row, col, cardIndex} for the fallback move, or null if none is found.
   */
  private int[] findFallbackMove() {
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        if (model.isMoveLegal(row, col)) {
          return new int[]{row, col, 0};
        }
      }
    }
    return null;
  }



  /**
   * Handles cell clicks, but AI does not process
   * manual input, so this method is ignored.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    System.out.println("AI cannot handle cell clicks. Ignoring the input.");
  }

  /**
   * Handles card selection, but AI does not process manual input,
   * so this method is ignored.
   *
   * @param cardIndex the index of the selected card in the player's hand
   */
  @Override
  public void handleCardSelection(int cardIndex) {
    System.out.println("AI cannot handle card selection. Ignoring the input.");
  }

  /**
   * Handles AI moves by delegating to the makeMove method.
   */
  @Override
  public void handleAIMove() {
    if (!model.isGameOver()) {
      model.switchTurns();
    }

  }



}
