package cs3500.threetrios;

import cs3500.threetrios.ai.GoForCorner;
import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.ai.MinMaxStrategy;
import cs3500.threetrios.ai.Flip;
import cs3500.threetrios.ai.PosnStrategy;
import cs3500.threetrios.game.GameModel;

import cs3500.threetrios.parser.BoardConfigParser;

import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;


/**
 * Tests for AI strategies using specific board configurations.
 */
public class AIStrategiesTest {

  @Test
  public void testHybridStrategySelectsFirstValidMove() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardNoHoles.config");
      System.out.println("Row " + game.getGrid().getRows());
      System.out.println("Col " + game.getGrid().getCols());

      PosnStrategy minMaxStrategy = new MinMaxStrategy();
      PosnStrategy flipMaxStrategy = new Flip();
      HybridStrategy hybridStrategy = new HybridStrategy(List.of(minMaxStrategy,
              flipMaxStrategy));

      int[] move = hybridStrategy.choosePositions(game);

      assertNotEquals(new int[]{-1, -1, -1}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testHybridStrategySelectsFirst() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardCanReachWithHoles.config");
      System.out.println("Row " + game.getGrid().getRows());
      System.out.println("Col " + game.getGrid().getCols());

      PosnStrategy minMaxStrategy = new MinMaxStrategy();
      HybridStrategy hybridStrategy = new HybridStrategy(List.of(minMaxStrategy));

      int[] move = hybridStrategy.choosePositions(game);
      assertArrayEquals(new int[] {0, 0, 0}, move);

      assertNotEquals(new int[]{-1, -1, -1}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testHybridStrategySelectsBetweenMultiple() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig(
              "docs/boardSufficentCards.config");
      System.out.println("Row " + game.getGrid().getRows());
      System.out.println("Col " + game.getGrid().getCols());

      PosnStrategy minMaxStrategy = new MinMaxStrategy();
      PosnStrategy flipMaxStrategy = new Flip();
      PosnStrategy goForCornerStrategy = new GoForCorner();
      HybridStrategy hybridStrategy = new HybridStrategy(List.of(minMaxStrategy,
              flipMaxStrategy, goForCornerStrategy));

      int[] move = hybridStrategy.choosePositions(game);
      assertArrayEquals(new int[] {0, 0, 0}, move);
      assertNotEquals(new int[]{-1, -1, -1}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testMinMaxStrategySelectsBestMove() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig(
              "docs/boardSufficentCards.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;


      MinMaxStrategy strategy = new MinMaxStrategy();
      int[] move = strategy.choosePositions(game);

      System.err.println("Actual move: " + Arrays.toString(move));
      System.err.println("Expected move: [" + expectedRow
              + ", " + expectedCol + ", " + expectedCardIndex + "]");


      assertArrayEquals("Flip strategy did not select the position with maximum flips",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testMinMaxStrategyTieBreaking() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig(
              "docs/boardTieBreaker.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      MinMaxStrategy strategy = new MinMaxStrategy();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("MinMax strategy did not resolve ties correctly",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }


  @Test
  public void testFlipStrategySelectsMostFlips() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardSufficentCards.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      Flip strategy = new Flip();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("Flip strategy did not select the position with maximum flips",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testGoForCornerSelectsCornerMove() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardSufficentCards.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 3;

      GoForCorner strategy = new GoForCorner();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("GoForCorner did not choose the correct corner position",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testHybridStrategyCombinesStrategies() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardComplexHybrid.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      PosnStrategy minMaxStrategy = new MinMaxStrategy();
      PosnStrategy flipStrategy = new Flip();
      HybridStrategy hybridStrategy = new HybridStrategy(List.of(minMaxStrategy, flipStrategy));

      int[] move = hybridStrategy.choosePositions(game);
      System.err.println(Arrays.toString(move));

      assertArrayEquals("Hybrid strategy did not correctly combine MinMax and Flip",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testHybridStrategyBreaksTies() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardComplexHybrid.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      PosnStrategy goForCorner = new GoForCorner();
      PosnStrategy flipStrategy = new Flip();
      HybridStrategy hybridStrategy = new HybridStrategy(List.of(goForCorner, flipStrategy));

      int[] move = hybridStrategy.choosePositions(game);
      System.err.println(Arrays.toString(move));

      assertArrayEquals("Hybrid strategy did not resolve ties by choosing upper-leftmost position",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testNoValidMoves() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardFull.config");

      int expectedRow = 2;
      int expectedCol = 2;
      int expectedCardIndex = 0;

      MinMaxStrategy strategy = new MinMaxStrategy();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("Strategy did not select the upper-left"
                      + " available position as fallback",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testHybridStrategyWithAllStrategies() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardFallbackTest.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      PosnStrategy minMaxStrategy = new MinMaxStrategy();
      PosnStrategy flipStrategy = new Flip();
      PosnStrategy goForCorner = new GoForCorner();
      HybridStrategy hybridStrategy = new HybridStrategy(
              List.of(minMaxStrategy, flipStrategy, goForCorner));

      int[] move = hybridStrategy.choosePositions(game);

      assertArrayEquals("Hybrid strategy with all strategies " +
                      "did not choose the optimal move",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testNoMovesAvailableAfterFullBoard() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardFallbackTest.config");

      MinMaxStrategy strategy = new MinMaxStrategy();
      strategy.choosePositions(game);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }


  @Test
  public void testFallbackToUpperLeftmostPosition() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardSufficentCards.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      MinMaxStrategy strategy = new MinMaxStrategy();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("MinMax did not fall back to the upper-leftmost " +
                      "position when no optimal move was found",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testStrategyPrefersLowerIndexCardOnTie() {
    try {
      GameModel game = BoardConfigParser.parseBoardConfig("docs/boardSufficentCards.config");

      int expectedRow = 0;
      int expectedCol = 0;
      int expectedCardIndex = 0;

      MinMaxStrategy strategy = new MinMaxStrategy();
      int[] move = strategy.choosePositions(game);

      assertArrayEquals("MinMax did not prefer the lower index card on a tie",
              new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
    } catch (FileNotFoundException e) {
      fail("Configuration file not found");
    }
  }

  @Test
  public void testMinMaxStrategyOnSparseBoard() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardCanReachWithHoles.config");

    int expectedRow = 0;
    int expectedCol = 0;
    int expectedCardIndex = 0;

    MinMaxStrategy strategy = new MinMaxStrategy();
    int[] move = strategy.choosePositions(game);

    assertArrayEquals("MinMax strategy did not select the only "
                    + "available position",
            new int[]{expectedRow, expectedCol, expectedCardIndex}, move);
  }

  @Test
  public void testMultiTurnBehavior() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardTieBreaker.config");

    MinMaxStrategy strategy = new MinMaxStrategy();

    for (int turn = 0; turn < 3; turn++) {
      int[] move = strategy.choosePositions(game);
      assertNotEquals("Strategy failed to select a valid move",
              new int[]{-1, -1, -1}, move);
    }
  }

  @Test
  public void testGoForCornerStrategyWithOnlyCornersOpen() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardOnlyCornersOpen.config");

    GoForCorner strategy = new GoForCorner();
    int[] move = strategy.choosePositions(game);

    assertEquals(true,
            (move[0] == 0 && move[1] == 0)
                    || (move[0] == 0 && move[1] == 2)
                    || (move[0] == 2 && move[1] == 0)
                    || (move[0] == 2 && move[1] == 2));
  }

  @Test
  public void testSingleMoveLeft() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardFull.config");
    MinMaxStrategy strategy = new MinMaxStrategy();
    int[] move = strategy.choosePositions(game);

    assertArrayEquals("Expected single available move to be chosen",
            new int[]{2, 2, 0}, move);
  }

  @Test(expected = NullPointerException.class)
  public void testStrategiesWithNullGameModel() {
    PosnStrategy flipStrategy = new Flip();
    flipStrategy.choosePositions(null);
  }

  @Test
  public void testHybridStrategyComplexTieBreaking() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardTieBreaker.config");

    PosnStrategy minMaxStrategy = new MinMaxStrategy();
    PosnStrategy flipStrategy = new Flip();
    PosnStrategy goForCornerStrategy = new GoForCorner();
    HybridStrategy hybridStrategy = new HybridStrategy(
            List.of(minMaxStrategy, flipStrategy, goForCornerStrategy));

    int[] move = hybridStrategy.choosePositions(game);
    assertArrayEquals("Hybrid strategy did not resolve" +
            " complex ties correctly", new int[]{0, 0, 0}, move);
  }

  @Test
  public void testMinMaxStrategyOptimalMove() throws FileNotFoundException {
    GameModel game = BoardConfigParser.parseBoardConfig("docs/boardSufficentCards.config");

    MinMaxStrategy minMaxStrategy = new MinMaxStrategy();
    int[] move = minMaxStrategy.choosePositions(game);

    assertArrayEquals("MinMax did not choose the" +
            " optimal move", new int[]{0, 0, 0}, move);
  }



}
