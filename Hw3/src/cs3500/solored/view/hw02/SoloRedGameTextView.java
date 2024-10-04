package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;

import java.io.IOException;
import java.util.List;

/**
 * A simple view class for representing the current state of the RedSeven game.
 * It displays the canvas, each palette, and the cards in hand in a formatted string.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private final Appendable appendable;

  /**
   * Constructs a view for the RedSeven game.
   *
   * @param model      the game model to generate the view for
   * @param appendable appends the model in some matter
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable appendable) {
    if (appendable == null || model == null) {
      throw new IllegalArgumentException("Model and Appendable cannot be null.");
    }
    this.appendable = appendable;
    this.model = model;
  }

  public SoloRedGameTextView(RedGameModel<?> model) {
    this(model, new StringBuilder());
  }

  /**
   * Provides a string representation of the current game state, including:
   * The canvas card, each palette's cards, the player's hand,
   * and the winning palette indicated by the ">" symbol.
   *
   * @return a formatted string representing the game state
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    appendCanvas(sb);
    appendPalettes(sb);
    appendHand(sb);

    return sb.toString();
  }

  /**
   * Helper method to append the canvas state to the string builder.
   */
  private void appendCanvas(StringBuilder sb) {
    try {
      if (model.getCanvas() != null) {
        sb.append("Canvas: ").append(model.getCanvas().toString().charAt(0)).append("\n");
      } else {
        sb.append("Canvas: \n");
      }
    } catch (Exception e) {
      sb.append("\n");
    }
  }

  /**
   * Helper method to append the palettes state to the string builder.
   */
  private void appendPalettes(StringBuilder sb) {
    try {
      int numPalettes = model.numPalettes();
      if (numPalettes == 0) {
        throw new IllegalArgumentException("No palettes available.");
      }

      for (int i = 0; i < numPalettes; i++) {
        if (i == model.winningPaletteIndex()) {
          sb.append("> ");
        }
        sb.append("P").append(i + 1).append(": ");
        appendPaletteCards(sb, i);
        sb.append("\n");
      }
    } catch (Exception e) {
      sb.append("\n");
    }
  }

  /**
   * Helper method to append the cards of a specific palette to the string builder.
   */
  private void appendPaletteCards(StringBuilder sb, int paletteIndex) {
    try {
      List<CardModel> palette = (List<CardModel>) model.getPalette(paletteIndex);
      if (palette == null || palette.isEmpty()) {
        throw new IllegalArgumentException("No palettes available.");
      }

      for (int j = 0; j < palette.size(); j++) {
        sb.append(palette.get(j).toString());
        if (j < palette.size() - 1) {
          sb.append(" ");
        }
      }
    } catch (Exception e) {
      sb.append("");
    }
  }

  /**
   * Helper method to append the hand state to the string builder.
   */
  private void appendHand(StringBuilder sb) {
    try {
      sb.append("Hand: ");
      List<CardModel> hand = (List<CardModel>) model.getHand();
      if (hand == null || hand.isEmpty()) {
        sb.append("");
      } else {
        for (int i = 0; i < hand.size(); i++) {
          sb.append(hand.get(i).toString());
          if (i < hand.size() - 1) {
            sb.append(" ");
          }
        }
      }
    } catch (Exception e) {
      sb.append("");
    }
  }

  @Override
  public void render() throws IOException {
    appendable.append(this.toString());
  }
}
