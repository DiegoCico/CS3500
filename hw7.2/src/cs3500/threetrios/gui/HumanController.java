package cs3500.threetrios.gui;

import java.util.HashSet;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.player.Player;

/**
 * Human Controller implementation for handling player input.
 */
public class HumanController implements ThreeTriosGameController {
  private final Game model;
  private final ThreeTriosGameView view;

  public HumanController(Game model, ThreeTriosGameView view) {
    this.model = model;
    this.view = view;
    this.view.setFeatures(new FeaturesImpl());
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (model.isGameOver()) {
      view.displayGameOverMessage();
      return;
    }
    try {
      Player currentPlayer = model.getCurrentPlayerModel();
      Card selectedCard = currentPlayer.getHand().get(0); // Use the first card for simplicity.

      if (model.isMoveLegal(row, col)) {
        model.placeCard(row, col, selectedCard);
        model.battleCards(row, col, new HashSet<>());
        model.switchTurns();
        view.refresh();

        if (model.isGameOver()) {
          view.displayGameOverMessage();
        }
      } else {
        view.displayErrorMessage("Invalid move. Try again.");
      }
    } catch (Exception e) {
      view.displayErrorMessage("Error: " + e.getMessage());
    }
  }

  @Override
  public void handleCardSelection(int cardIndex) {
    view.displayErrorMessage("Human controller does not allow card selection yet.");
  }

  @Override
  public void handleAIMove() {
    view.displayErrorMessage("AI moves are not supported in the Human controller.");
  }

  private class FeaturesImpl implements Features {
    @Override
    public void handleCellClick(int row, int col) {
      HumanController.this.handleCellClick(row, col);
    }

    @Override
    public void handleCardSelection(int cardIndex) {
      HumanController.this.handleCardSelection(cardIndex);
    }

    @Override
    public void handleAIMove() {
      HumanController.this.handleAIMove();
    }
  }
}
