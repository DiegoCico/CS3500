package cs3500.solored;

import java.util.Random;

import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

/**
 * Tests Advance game.
 */
public class AdvancedSoloRedGameModelTest extends AbstractSoloRedGameModelTest {
  @Override
  protected SoloRedGameModel createGameModel() {
    return new AdvancedSoloRedGameModel(new Random());
  }
}
