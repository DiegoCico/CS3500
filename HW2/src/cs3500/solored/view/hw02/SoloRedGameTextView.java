package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;

import java.util.List;

/**
 * A simple view class for representing the current state of the RedSeven game.
 * It displays the canvas, each palette, and the cards in hand in a formatted string.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;

  /**
   * Constructs a view for the RedSeven game.
   *
   *    @param model the game model to generate the view for
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  /**
   * Provides a string representation of the current game state, including:
   * The canvas card, each palette's cards, the player's hand,
   * and the winning palette indicated by the ">" symbol.
   *
   * <p>Edge cases handled:
   * <ul>
   *   <li>No canvas card</li>
   *   <li>No palettes or empty palettes</li>
   *   <li>Empty hand</li>
   * </ul>
   *
   * @return a formatted string representing the game state
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    try {
      CardModel canvasCard = (CardModel) model.getCanvas();
      if (canvasCard == null) {
        throw new IllegalArgumentException("No canvas available.");
      } else {
        sb.append("Canvas: ").append(canvasCard.getColor()).append("\n");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not get canvas", e);
    }

    try {
      int numPalettes = model.numPalettes();
      if (numPalettes == 0) {
        throw new IllegalArgumentException("No palettes available.");
      } else {
        for (int i = 0; i < numPalettes; i++) {
          if (i == model.winningPaletteIndex()) {
            sb.append("> ");
          }
          sb.append("P").append(i + 1).append(": ");

          try {
            List<CardModel> palette = (List<CardModel>) model.getPalette(i);
            if (palette == null || palette.isEmpty()) {
              throw new IllegalArgumentException("No palettes available.");
            } else {
              for (int j = 0; j < palette.size(); j++) {
                sb.append(palette.get(j).toString());
                if (j < palette.size() - 1) {
                  sb.append(" ");
                }
              }
            }
          } catch (Exception e) {
            throw new IllegalArgumentException("Could not get palette", e);
          }
          if (i < numPalettes - 1) {
            sb.append("\n");
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not get palette", e);
    }

    try {
      sb.append("\nHand: ");
      List<CardModel> hand = (List<CardModel>) model.getHand();
      if (hand == null || hand.isEmpty()) {
        throw new IllegalArgumentException("No hand available.");
      } else {
        for (int i = 0; i < hand.size(); i++) {
          sb.append(hand.get(i).toString());
          if (i < hand.size() - 1) {
            sb.append(" ");
          }
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not get hand", e);
    }

    return sb.toString().trim();
  }

}
