package cs3500.threetrios;

import cs3500.threetrios.ai.Flip;
import cs3500.threetrios.ai.GoForCorner;
import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.ai.LeastFlippableStrategy;
import cs3500.threetrios.ai.MinMaxStrategy;
import cs3500.threetrios.ai.NoPlay;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.game.MockGameModel;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerModel;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Tests the mock model with strategies.
 */
public class MockModelTests {

  private MockGameModel mockGameModel;
  private Player playerRed;
  private Player playerBlue;
  private Grid grid;

  @Before
  public void setup() {
    grid = new GameGrid(3, 3);
    List<Card> redHand = List.of(new CardModel("RedCard1", 1, 2, 3, 4, COLOR.RED));
    List<Card> blueHand = List.of(new CardModel("BlueCard1", 5, 6, 7, 8, COLOR.BLUE));

    playerRed = new PlayerModel("Player Red", COLOR.RED, redHand);
    playerBlue = new PlayerModel("Player Blue", COLOR.BLUE, blueHand);

    Player[] players = {playerRed, playerBlue};

    mockGameModel = new MockGameModel(playerRed, grid, players);
  }

  @Test
  public void testFlipStrategyChecksAllPositions() {
    Flip flipStrategy = new Flip();
    flipStrategy.choosePositions(mockGameModel);

    assertEquals(9, mockGameModel.getCheckedCoordinates().size());
  }

  @Test
  public void testGoForCornerChecksCornersOnly() {
    GoForCorner goForCornerStrategy = new GoForCorner();
    goForCornerStrategy.choosePositions(mockGameModel);

    List<String> expectedCoordinates = List.of(
            "isMoveLegal(0, 0)", "isMoveLegal(0, 2)", "isMoveLegal(2, 0)", "isMoveLegal(2, 2)"
    );

    assertEquals(expectedCoordinates, mockGameModel.getCheckedCoordinates());
  }

  @Test
  public void testLeastFlippableStrategyChoosesLowRiskMove() {
    mockGameModel.setMoveLegal(true);

    LeastFlippableStrategy leastFlippableStrategy = new LeastFlippableStrategy();
    leastFlippableStrategy.choosePositions(mockGameModel);

    List<String> methodCalls = mockGameModel.getMethodCalls();

    assertTrue(methodCalls.contains("getCurrentPlayer"));
    assertTrue(methodCalls.contains("getGrid"));
  }

  @Test
  public void testHybridStrategyCombinesStrategies() {
    HybridStrategy hybridStrategy = new HybridStrategy(List.of(new Flip(), new GoForCorner()));
    hybridStrategy.choosePositions(mockGameModel);

    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 0)"));
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(1, 1)"));
  }

  @Test
  public void testGoForCornersStrategy() {
    GoForCorner goForCornerStrategy = new GoForCorner();
    goForCornerStrategy.choosePositions(mockGameModel);

    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 0)"));
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 2)"));
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(2, 0)"));
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(2, 2)"));
  }

  @Test
  public void testNoPlayerStrategyInvalidMoves() {
    mockGameModel.setMoveLegal(false);
    NoPlay strategy = new NoPlay();
    int[] move = strategy.choosePositions(mockGameModel);

    System.out.println(Arrays.toString(move));
    assertArrayEquals(new int[] {-1, -1, -1}, move);
  }

  @Test
  public void testNoPlayerStrategyValidMoves() {
    mockGameModel.setMoveLegal(true);
    NoPlay strategy = new NoPlay();
    int[] move = strategy.choosePositions(mockGameModel);

    System.out.println(Arrays.toString(move));
    assertNotEquals(new int[]{-1, -1, -1}, move);
  }

  @Test
  public void setFirstLegalMoveNoPlay() {
    mockGameModel.setMoveLegal(true);
    NoPlay strategy = new NoPlay();
    int[] move = strategy.choosePositions(mockGameModel);
    assertArrayEquals(new int[] {0, 0, 0}, move);
  }

  @Test
  public void testStrategyController() {
    mockGameModel.setMoveLegal(true);
    HybridStrategy strategyController = new HybridStrategy(Arrays.asList(new Flip(),
            new GoForCorner()));
    int[] move = strategyController.choosePositions(mockGameModel);

    assertArrayEquals(new int[] {0, 0, 0}, move);
    assertTrue(move[0] >= 0 && move[1] >= 0);
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 0)"));

  }


  @Test
  public void testStrategyControllerInvalidMoves() {
    mockGameModel.setMoveLegal(false);
    HybridStrategy strategyController = new HybridStrategy(Arrays.asList(new Flip(),
            new GoForCorner()));
    int[] move = strategyController.choosePositions(mockGameModel);
    assertArrayEquals(new int[] {-1, -1, -1}, move);

  }

  @Test
  public void testStrategyControllerValidMoves() {
    mockGameModel.setMoveLegal(true);
    HybridStrategy strategyController = new HybridStrategy(Arrays.asList(new Flip(),
            new GoForCorner()));
    int[] move = strategyController.choosePositions(mockGameModel);

    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 0)"));
    assertTrue(move[0] == 0 && move[1] == 0);
  }


  @Test
  public void testFlipStrategyLogsMethodCalls() {
    Flip flipStrategy = new Flip();
    flipStrategy.choosePositions(mockGameModel);

    List<String> methodCalls = mockGameModel.getMethodCalls();
    assertTrue("Expected getGrid method to be logged",
            methodCalls.contains("getGrid"));
    assertTrue("Expected getCurrentPlayer method to be logged",
            methodCalls.contains("getCurrentPlayer"));
  }


  @Test
  public void testLeastFlippableStrategyTieBreaking() {
    mockGameModel.setMoveLegal(true);
    LeastFlippableStrategy leastFlippableStrategy = new LeastFlippableStrategy();
    int[] move = leastFlippableStrategy.choosePositions(mockGameModel);

    assertArrayEquals("Expected upper-leftmost cell due to tie-breaking",
            new int[] {0, 0, 0}, move);
  }

  @Test
  public void testFlipStrategyFullCoverage() {
    Flip flipStrategy = new Flip();
    flipStrategy.choosePositions(mockGameModel);

    assertEquals("Expected all cells to be checked for flipping potential",
            9, mockGameModel.getCheckedCoordinates().size());
    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(2, 2)"));
  }

  @Test
  public void testHybridStrategyWithMultipleLayers() {
    HybridStrategy hybridStrategy = new HybridStrategy(List.of(new Flip(),
            new LeastFlippableStrategy(), new GoForCorner()));
    int[] move = hybridStrategy.choosePositions(mockGameModel);

    assertArrayEquals("left-most cell because of tie-breaking ",
            new int[] {0, 0, 0}, move);
  }

  @Test
  public void testNoPlayStrategyAllInvalidMoves() {
    mockGameModel.setMoveLegal(false);
    NoPlay strategy = new NoPlay();
    int[] move = strategy.choosePositions(mockGameModel);

    assertArrayEquals("Expected {-1, -1, -1} when no valid moves",
            new int[] {-1, -1, -1}, move);
  }

  @Test
  public void testStrategyControllerInValidMoves() {
    mockGameModel.setMoveLegal(false);
    HybridStrategy strategyController = new HybridStrategy(Arrays.asList(new Flip(),
            new GoForCorner()));
    int[] move = strategyController.choosePositions(mockGameModel);

    assertTrue(mockGameModel.getCheckedCoordinates().contains("isMoveLegal(0, 0)"));
    assertArrayEquals("Expected {-1, -1, -1} when no valid moves",
            new int[] {-1, -1, -1}, move);
  }

  @Test
  public void testStrategyControllerOrder() {
    HybridStrategy strategyController = new HybridStrategy(Arrays.asList(new GoForCorner(),
            new Flip()));
    int[] move = strategyController.choosePositions(mockGameModel);

    List<String> checkedCoordinates = mockGameModel.getCheckedCoordinates();
    int goForCornerIndex = checkedCoordinates.indexOf("isMoveLegal(0, 0)");
    int flipIndex = checkedCoordinates.indexOf("isMoveLegal(1, 1)");

    assertTrue("Expected GoForCorner to be executed before Flip",
            goForCornerIndex < flipIndex);
    assertTrue("Expected a valid move to be chosen",
            move[0] >= 0 && move[1] >= 0);
  }


  @Test
  public void testStrategyPrefersLowerIndexCardOnTie() {
    mockGameModel.setMoveLegal(true);

    MinMaxStrategy strategy = new MinMaxStrategy();
    int[] move = strategy.choosePositions(mockGameModel);

    assertArrayEquals("Expected lower index card on tie", new int[]{0, 0, 0}, move);
  }

  @Test
  public void testHybridStrategyMultiLayerConflictResolution() {
    MockGameModel mockGame = new MockGameModel(playerRed, grid,
            new Player[]{playerRed, playerBlue});

    mockGame.setMoveLegal(true);
    HybridStrategy hybridStrategy = new HybridStrategy(List.of(new GoForCorner(), new Flip()));

    int[] move = hybridStrategy.choosePositions(mockGame);
    List<String> checkedCoordinates = mockGame.getCheckedCoordinates();

    int goForCornerIndex = checkedCoordinates.indexOf("isMoveLegal(0, 0)");
    int flipIndex = checkedCoordinates.indexOf("isMoveLegal(1, 1)");

    assertTrue("Expected GoForCorner to be evaluated before"
            + " Flip in HybridStrategy", goForCornerIndex < flipIndex);
  }


  @Test
  public void testIsMoveLegalLogging() {
    mockGameModel.isMoveLegal(0, 0);
    mockGameModel.isMoveLegal(1, 1);
    List<String> checkedCoordinates = mockGameModel.getCheckedCoordinates();
    assertEquals("Checked coordinates size", 2, checkedCoordinates.size());
  }

  @Test
  public void testSimpleStrategyTranscript() {
    Grid grid = new GameGrid(3, 3);
    Player playerRed = new PlayerModel("Player Red", COLOR.RED,
            List.of(new CardModel("RedCard1",
                    1, 2, 3, 4, COLOR.RED)));
    Player playerBlue = new PlayerModel("Player Blue", COLOR.BLUE,
            List.of(new CardModel("BlueCard1",
                    5, 6, 7, 8, COLOR.BLUE)));
    Player[] players = {playerRed, playerBlue};

    MockGameModel mockGame = new MockGameModel(playerRed, grid, players);

    int row = 0;
    int col = 0;
    if (mockGame.isMoveLegal(row, col)) {
      Card redCard = new CardModel("RedCard1",
              1, 2, 3, 4, COLOR.RED);
      mockGame.placeCard(row, col, redCard);
    }
    mockGame.checkWinCondition();
    mockGame.isGameOver();
    mockGame.switchTurns();

    List<String> expectedCalls = Arrays.asList(
            "isMoveLegal(0, 0)",
            "placeCard(0, 0, RedCard1: 1 2 3 4 RED)",
            "checkWinCondition",
            "isGameOver",
            "switchTurns"
    );

    List<String> actualCalls = mockGame.getMethodCalls();
    assertEquals("The method calls do not match" +
            " the expected sequence.", expectedCalls, actualCalls);
  }


  /**
   * Main in test is used to create the strategy-transcript.txt
   * @param args method calls for the mock
   */
  public static void main(String[] args) {

    Grid grid = new GameGrid(3, 3);

    Player playerRed = new PlayerModel("Player Red", COLOR.RED,
            List.of(new CardModel("RedCard1",
                    1, 2, 3, 4, COLOR.RED)));
    Player playerBlue = new PlayerModel("Player Blue", COLOR.BLUE,
            List.of(new CardModel("BlueCard1",
                    5, 6, 7, 8, COLOR.BLUE)));

    Player[] players = {playerRed, playerBlue};

    MockGameModel mockGame = new MockGameModel(playerRed, grid, players);

    int row = 0;
    int col = 0;
    if (mockGame.isMoveLegal(row, col)) {
      Card redCard = new CardModel("RedCard1",
              1, 2, 3, 4, COLOR.RED);
      mockGame.placeCard(row, col, redCard);
    }

    mockGame.checkWinCondition();
    mockGame.isGameOver();
    mockGame.switchTurns();

    List<String> transcript = mockGame.getMethodCalls();

    System.out.println(transcript);
  }
}
