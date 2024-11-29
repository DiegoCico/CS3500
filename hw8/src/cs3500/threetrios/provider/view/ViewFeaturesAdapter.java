package cs3500.threetrios.provider.view;


import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.provider.controller.ViewFeatures;
import cs3500.threetrios.provider.model.Player;


public class ViewFeaturesAdapter implements ViewFeatures {
  private final ThreeTriosControllerImpl controller;

  public ViewFeaturesAdapter(ThreeTriosControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public void handleGridPlay(int row, int col) {
    try {
      controller.handleCellClick(row, col);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid move: " + e.getMessage());
    }
  }

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
