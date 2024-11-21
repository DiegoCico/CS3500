package cs3500.threetrios.gui;

import cs3500.threetrios.controller.AbstractController;

/**
 * Represents the implementation of the Features interface.
 * This class delegates user interactions to the appropriate methods
 * in the specified AbstractController.
 */
public class FeaturesImpl implements Features {
  private final AbstractController controller;

  /**
   * Constructs a FeaturesImpl instance with the specified controller.
   *
   * @param controller the controller responsible for handling game actions.
   */
  public FeaturesImpl(AbstractController controller) {
    this.controller = controller;
  }

  /**
   * Handles the action when a cell on the game grid is clicked by a player.
   *
   * @param row the row of the clicked cell.
   * @param col the column of the clicked cell.
   */
  @Override
  public void handleCellClick(int row, int col) {
    controller.handleCellClick(row, col);
  }

  /**
   * Handles the action when a card is selected by the player.
   *
   * @param cardIndex the index of the selected card in the player's hand.
   */
  @Override
  public void handleCardSelection(int cardIndex) {
    controller.handleCardSelection(cardIndex);
  }

  /**
   * Handles the AI's move when it is the AI's turn.
   * Processes the logic for the AI to make its move automatically.
   */
  @Override
  public void handleAIMove() {
    controller.handleAIMove();
  }
}
