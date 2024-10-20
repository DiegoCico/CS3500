package cs3500.solored;

import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Tests Advance game.
 */
public class SoloRedGameModelTest extends AbstractSoloRedGameModelTest {
  @Override
  protected SoloRedGameModel createGameModel() {
    return new SoloRedGameModel();
  }
}
