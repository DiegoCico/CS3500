package cs3500.threetrios.controller;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.gui.AbstractPlayerView;

import java.util.HashSet;

/**
 * Represents the human controller for the Three Trios game.
 * This controller handles user input for the human player,
 * including selecting cards,
 * clicking on cells to place cards, and
 * managing the human player's turn.
 */
public class HumanController extends AbstractController {
  private Card selectedCard = null;

  /**
   * Constructs a HumanController with the specified game model and view.
   *
   * @param model the game model used to manage game state and logic
   * @param view  the game view used to display updates to the user
   */
  public HumanController(Game model, AbstractPlayerView view) {
    super(model, view);
  }

  /**
   * Displays the current player to indicate it is the human's turn.
   * User input via GUI interactions is awaited during this phase.
   */
  @Override
  public void makeMove() {
    view.displayCurrentPlayer(model.getCurrentPlayerModel().getName());
  }

  /**
   * Handles cell clicks by validating the move, placing the card if valid,
   * and switching turns. Triggers the AI's turn if the human's move is completed.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    if (model.isGameOver()) {
      view.displayGameOverMessage();
      return;
    }

    try {
      if (!model.isMoveLegal(row, col)) {
        view.displayErrorMessage("Invalid move. Cell is occupied or out of bounds.");
        return;
      }

      Card cardToPlace = selectedCard != null ? selectedCard : getDefaultCard();
      if (cardToPlace == null) {
        view.displayErrorMessage("No card selected or available.");
        return;
      }

      model.placeCard(row, col, cardToPlace);
      model.battleCards(row, col, new HashSet<>());
      selectedCard = null;
      view.refresh();

      if (model.isGameOver()) {
        view.displayGameOverMessage();
      } else {
        model.switchTurns();
        view.displayCurrentPlayer(model.getCurrentPlayerModel().getName());
        if (model.getCurrentPlayerModel().getColor() == COLOR.BLUE) {
          handleAIMove();
        }
      }
    } catch (IllegalStateException e) {
      view.displayErrorMessage("Error: " + e.getMessage());
    }
  }

  /**
   * Handles card selection by validating the index and updating the selected card.
   *
   * @param cardIndex the index of the selected card in the player's hand
   */
  @Override
  public void handleCardSelection(int cardIndex) {
    try {
      if (cardIndex < 0 || cardIndex >= model.getCurrentPlayerModel().getHand().size()) {
        view.displayErrorMessage("Invalid card selection.");
        return;
      }
      selectedCard = model.getCurrentPlayerModel().getHand().get(cardIndex);
      view.refresh();
    } catch (Exception e) {
      view.displayErrorMessage("Error selecting card: " + e.getMessage());
    }
  }

  /**
   * Gets the default card to play if no card is selected.
   * @return the first card in the player's hand, or null if no cards are available.
   */
  private Card getDefaultCard() {
    if (!model.getCurrentPlayerModel().getHand().isEmpty()) {
      return model.getCurrentPlayerModel().getHand().get(0);
    }
    return null;
  }

  /**
   * The HumanController does not handle AI moves. Throws an exception if called.
   */
  @Override
  public void handleAIMove() {
    if (!model.isGameOver()) {
      makeMove();
      if (model.getCurrentPlayerModel().getColor() == COLOR.BLUE) {
        handleAIMove();
      }
    }
  }
}
