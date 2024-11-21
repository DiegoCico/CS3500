package cs3500.threetrios;

import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.parser.BoardConfigParser;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * board config test.
 */
public class BoardConfigParserTest {

  @Test
  public void testValidBoardWithSufficientCards() {
    try {
      GameModel gameModel = BoardConfigParser.parseBoardConfig(
              "docs/boardSufficentCards.config"
      );
      assertNotNull(gameModel);
    } catch (FileNotFoundException e) {
      fail("File not found");
    }
  }

  @Test
  public void testBoardWithReachableHoles() {
    try {
      GameModel gameModel = BoardConfigParser.parseBoardConfig(
              "docs/boardCanReachWithHoles.config"
      );
      assertNotNull(gameModel);
    } catch (FileNotFoundException e) {
      fail("File not found");
    }
  }

  @Test
  public void testBoardWithoutHoles() {
    try {
      GameModel gameModel = BoardConfigParser.parseBoardConfig("docs/boardNoHoles.config");
      assertNotNull(gameModel);
    } catch (FileNotFoundException e) {
      fail("File not found");
    }
  }

  @Test
  public void testBoardWithInsufficientCards() {
    assertThrows(IllegalStateException.class, () -> {
      BoardConfigParser.parseBoardConfig("docs/boardNotEnoughCards.config");
    });
  }

}
