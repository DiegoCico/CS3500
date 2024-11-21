package cs3500.threetrios.GUI;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.player.Player;
import java.io.FileNotFoundException;

import javax.swing.*;

public class ThreeTriosControllerImpl implements ThreeTriosGameController {
  private final GameModel model;
  private final ThreeTriosGameView view;
  private Card selectedCard = null;

  public ThreeTriosControllerImpl(GameModel model, ThreeTriosGameView view) {
    this.model = model;
    this.view = view;
    this.view.setFeatures(new FeaturesImpl());
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (selectedCard == null) {
      view.displayErrorMessage("No card selected. Please select a card first.");
      return;
    }

    if (model.isGameOver()) {
      view.displayErrorMessage("Game is already over.");
      return;
    }

    try {
      model.placeCard(row, col, selectedCard);
      selectedCard = null; // Deselect the card after playing it
      view.refresh();

      if (model.isGameOver()) {
        view.displayGameOverMessage();
      } else {
        model.switchTurns();
        view.displayCurrentPlayer(model.getCurrentPlayer().getName());
      }
    } catch (IllegalStateException exception) {
      view.displayErrorMessage("Invalid move. Try again.");
    }
  }

  public void handleCardSelection(int cardIndex) {
    Player currentPlayer = model.getCurrentPlayerModel();
    if (cardIndex < 0 || cardIndex >= currentPlayer.getHand().size()) {
      view.displayErrorMessage("Invalid card selection.");
      return;
    }

    selectedCard = currentPlayer.getHand().get(cardIndex);
    view.refresh();
    System.out.println("Selected card: " + selectedCard + " from player " + currentPlayer.getName());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        GameModel model = new GameModel("/Users/diegocicotoste/Documents/School/CS3500/hw6/docs/boardSufficentCards.config");
        ThreeTriosViewImpl view = new ThreeTriosViewImpl(model);
        new ThreeTriosControllerImpl(model, view);
      } catch (FileNotFoundException e) {
        System.err.println("Configuration file not found: " + e.getMessage());
      }
    });
  }



  private class FeaturesImpl implements Features {
    @Override
    public void handleCellClick(int row, int col) {
      ThreeTriosControllerImpl.this.handleCellClick(row, col);
    }
  }
}