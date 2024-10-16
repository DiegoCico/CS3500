package cs3500.solored;

import java.io.StringReader;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * This is the main class that runs the Solo Red game.
 */
public final class SoloRed {

  /**
   * The main method to run the Solo Red game.
   *
   * @param args command line arguments where:
   *             - the first argument is the game type ('basic' or 'advanced')
   *             - the second argument (optional) is the number of palettes (minimum 2)
   *             - the third argument (optional) is the hand size (greater than 0)
   * @throws IllegalArgumentException if the game type is invalid, or if the number of palettes
   *                                  or hand size are not valid integers
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Please specify a game type: 'basic' or 'advanced'.");
    }

    String gameTypeStr = args[0].toLowerCase();
    RedGameCreator.GameType gameType;

    if (gameTypeStr.equals("basic")) {
      gameType = RedGameCreator.GameType.BASIC;
    } else if (gameTypeStr.equals("advanced")) {
      gameType = RedGameCreator.GameType.ADVANCED;
    } else {
      throw new IllegalArgumentException("Invalid game type. Use 'basic' or 'advanced'.");
    }

    int palettes = 4;
    int handSize = 7;

    if (args.length > 1) {
      try {
        palettes = Integer.parseInt(args[1]);
        if (palettes < 2) {
          throw new IllegalArgumentException("Number of palettes must be at least 2.");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid number of palettes.");
      }
    }

    if (args.length > 2) {
      try {
        handSize = Integer.parseInt(args[2]);
        if (handSize <= 0) {
          throw new IllegalArgumentException("Hand size must be greater than 0.");
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid hand size.");
      }
    }

    RedGameModel<Card> model = new RedGameCreator().createGame(gameType);
    SoloRedTextController controller = new SoloRedTextController(new StringReader("q\n"),
            System.out);
    controller.playGame(model, model.getAllCards(), true, palettes, handSize);
  }
}
