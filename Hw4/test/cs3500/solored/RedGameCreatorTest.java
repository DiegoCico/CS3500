package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * Test class for the RedGameCreator.
 */
public class RedGameCreatorTest {

  /**
   * Tests that RedGameCreator throws an IllegalArgumentException when given
   * an invalid game type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRedGameCreatorInvalid() {
    RedGameCreator.createGame(RedGameCreator.GameType.valueOf("N/A"));
  }

  /**
   * Tests that RedGameCreator creates an instance of AdvancedSoloRedGameModel
   * when the ADVANCED game type is passed.
   */
  @Test
  public void testRedGameCreatorAdvanced() {
    RedGameModel model = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    Assert.assertEquals("Classes match", AdvancedSoloRedGameModel.class, model.getClass());
  }

  /**
   * Tests that RedGameCreator creates an instance of AdvancedSoloRedGameModel
   * when the BASIC game type is passed.
   */
  @Test
  public void testRedGameCreatorBasic() {
    RedGameModel model = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    Assert.assertEquals("Classes match", AdvancedSoloRedGameModel.class, model.getClass());
  }
}
