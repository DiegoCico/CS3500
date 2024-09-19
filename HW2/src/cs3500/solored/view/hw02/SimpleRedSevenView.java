package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedModel;

import java.util.List;

/**
 * Simple view for the RedSeven game.
 */
public class SimpleRedSevenView implements RedSevenView {
  private final SoloRedModel model;

  public SimpleRedSevenView(SoloRedModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Canvas: ").append(model.getCanvas().getColor()).append("\n");

    // Append each palette
    for (int i = 0; i < model.numPalettes(); i++) {
      sb.append("P").append(i + 1).append(": ");
      List<CardModel> palette = model.getPalette(i);
      for (CardModel card : palette) {
        sb.append(card.toString()).append(" ");
      }
      if (i == model.winningPaletteIndex()) {
        sb.append("> ");
      }
      sb.append("\n");
    }

    sb.append("Hand: ");
    List<CardModel> hand = model.getHand();
    for (CardModel card : hand) {
      sb.append(card.toString()).append(" ");
    }

    return sb.toString();
  }
}
