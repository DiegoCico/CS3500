package cs3500.threetrios.provider.view;


import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.provider.controller.ViewFeatures;
import cs3500.threetrios.provider.model.Player;

/**
 * An adapter that adapts the provider's ViewFeatures interface with the
 * ThreeTriosControllerImpl and Features used in the main game logic.
 * Translates actions from the provider's view into corresponding
 * controller actions in the main.
 */
public class ViewFeaturesAdapter implements ViewFeatures {
  private final ThreeTriosControllerImpl controller;

  /**
   * Constructs a ViewFeaturesAdapter.
   *
   * @param controller the ThreeTriosControllerImpl to delegate actions to
   */
  public ViewFeaturesAdapter(ThreeTriosControllerImpl controller) {
    this.controller = controller;
  }

  /**
   * Indicates that there was an attempt to play to the grid at a specific location.
   * @param row the row that the user is trying to place a card on
   * @param col the column that the user to place a card on
   */
  @Override
  public void handleGridPlay(int row, int col) {
    try {
      controller.handleCellClick(row, col);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid move: " + e.getMessage());
    }
  }

  /**
   * Indicates that there was an attempt to select a different card.
   * @param player the player whose deck it comes form
   * @param cardIdx the index of the card in the hand
   * @throws IllegalArgumentException if player is null or Player.None;
   */
  @Override
  public void handleSelectCard(Player player, int cardIdx) {
    try {
      if (player == null || player.equals(Player.NONE)) {
        throw new IllegalArgumentException("Invalid player.");
      }
      controller.handleCardSelection(cardIdx);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid card selection: " + e.getMessage());
    }
  }
}
