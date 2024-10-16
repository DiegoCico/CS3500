package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * The RedGameCreator class is responsible for creating
 * game models based on the given type.
 */
public class RedGameCreator {

  /**
   * Enum representing the available game types.
   * BASIC - the standard Solo Red game.
   * ADVANCED - a more complex version with additional rules.
   */
  public enum GameType {
    BASIC,
    ADVANCED
  }

  /**
   * Creates a new game model based on the provided game type.
   *
   * @param type the type of game model to create (BASIC or ADVANCED)
   * @return the corresponding RedGameModel implementation
   * @throws IllegalArgumentException if the provided game type is invalid
   */
  public static RedGameModel createGame(GameType type) {
    RedGameModel<?> model;

    switch (type) {
      case BASIC:
        model = new SoloRedGameModel();
        break;
      case ADVANCED:
        model = new AdvancedSoloRedGameModel();
        break;
      default:
        throw new IllegalArgumentException("Invalid game type");
    }

    return model;
  }
}
