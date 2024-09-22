package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

import java.util.List;

/**
 * A simple view class for representing the current state of the RedSeven game.
 * It displays the canvas, each palette, and the cards in hand in a formatted string.
 */
public class SimpleRedSevenView implements RedSevenView {
  private final SoloRedGameModel model;

  /**
   * Constructs a view for the RedSeven game.
   *
   * @param model the game model to generate the view for
   */
  public SimpleRedSevenView(SoloRedGameModel model) {
    this.model = model;
  }

  /**
   * Provides a string representation of the current game state, including:
   * The canvas card, Each palette's cards, The player's hand,
   * The winning palette is shown by ">" symbol.
   *
   * @return a formatted string representing the game state
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Canvas: ").append(model.getCanvas().getColor()).append("\n");

    for (int i = 0; i < model.numPalettes(); i++) {
      if (i == model.winningPaletteIndex()) {
        sb.append("> ");
      }
      sb.append("P").append(i + 1).append(": ");
      List<CardModel> palette = model.getPalette(i);
      for (CardModel card : palette) {
        sb.append(card.toString()).append(" ");
      }
      sb.append("\n");
    }

    sb.append("Hand: ");
    List<CardModel> hand = model.getHand();
    for (CardModel card : hand) {
      sb.append(card.toString()).append(" ");
    }

    return sb.toString().trim();
  }
}
