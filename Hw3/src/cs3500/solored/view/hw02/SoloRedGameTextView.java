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
   *    @param model the game model to generate the view for
   *    @param appendable appends the model in some matter
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
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
      if (model.getCanvas() == null) {
        throw new IllegalArgumentException("No canvas available.");
      } else {
        sb.append("Canvas: ").append(model.getCanvas().toString().charAt(0)).append("\n");
      }
    } catch (Exception e) {
      sb.append("\n");
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

          }
          sb.append("\n");
        }
      }
    } catch (Exception e) {

    }

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

    }

    return sb.toString();
  }

  @Override
  public void render() throws IOException {
    String result = this.toString();
//    if (!result.endsWith("\n")) {
//      result += "\n";
//    }
//    appendable.append(result);
//
    appendable.append(result);
  }



}
