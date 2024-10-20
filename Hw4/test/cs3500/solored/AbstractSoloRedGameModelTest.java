package cs3500.solored;

import org.junit.Before;
import org.junit.Test;
import java.util.List;


import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Abstract test class for testing SoloRedGameModel implementations.
 */
public abstract class AbstractSoloRedGameModelTest {
  protected SoloRedGameModel model;
  protected List<CardModel> defaultDeck;

  /**
   * Initializes the testing environment.
   */
  @Before
  public void setup() {
    defaultDeck = initializeDefaultDeck();
    model = createGameModel();
  }

  /**
   * Creates the specific game model instance to be tested.
   *
   * @return the instance of SoloRedGameModel to be tested
   */
  protected abstract SoloRedGameModel createGameModel();

  /**
   * Initializes a default deck of cards for testing.
   *
   * @return a list containing all cards in the deck
   */
  protected List<CardModel> initializeDefaultDeck() {
    SoloRedGameModel tempModel = new SoloRedGameModel();
    return tempModel.getAllCards();
  }

  /**
   * Tests that playToPalette throws an IllegalStateException
   * if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteBeforeGameStart() {
    model.playToPalette(0, 0);
  }

  /**
   * Tests that starting the game works correctly.
   */
  @Test
  public void testStartGameSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    assertEquals(7, model.getHand().size());
    assertEquals(2, model.numPalettes());
  }

  /**
   * Tests that starting the game with an invalid hand size throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHandSizeStartGame() {
    model.startGame(defaultDeck, false, 2, 0);
  }

  /**
   * Tests that playing to the canvas works correctly after the game starts.
   */
  @Test
  public void testPlayToCanvasSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToCanvas(0);
    assertNotNull(model.getCanvas());
  }

  /**
   * Tests that playToCanvas throws
   * an IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasBeforeGameStart() {
    model.playToCanvas(0);
  }

  /**
   * Tests that playToPalette throws an IllegalArgumentException
   * when given an invalid palette index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPaletteIndexPlayToPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(10, 0);
  }

  /**
   * Tests that playToPalette throws an
   * IllegalArgumentException when given an invalid card index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardIndexPlayToPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(0, 10);
  }

  /**
   * Tests that playToPalette throws an
   * IllegalStateException when trying to play to a winning palette.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToWinningPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(0, 0);
    model.playToPalette(0, 1);
  }

  /**
   * Tests that drawing cards works correctly.
   */
  @Test
  public void testDrawCardsSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    model.drawForHand();
    assertEquals(7, model.getHand().size());
  }

  /**
   * Tests that drawForHand throws an
   * IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testDrawCardsBeforeGameStart() {
    model.drawForHand();
  }

  /**
   * Tests that the deck size is reduced correctly after drawing cards.
   */
  @Test
  public void testDeckSizeReductionAfterDraw() {
    model.startGame(defaultDeck, false, 2, 7);
    int initialDeckSize = model.numOfCardsInDeck();
    model.drawForHand();
    assertTrue(model.numOfCardsInDeck() < initialDeckSize);
  }

  /**
   * Tests that startGame throws an IllegalArgumentException if the deck size is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithInvalidDeckSize() {
    List<CardModel> smallDeck = defaultDeck.subList(0, 5);
    model.startGame(smallDeck, false, 2, 7);
  }

  /**
   * Tests that the game is over when the hand and deck are empty.
   */
  @Test
  public void testIsGameOverWhenHandAndDeckEmpty() {
    model.startGame(defaultDeck, false, 2, 1);
    model.playToPalette(0, 0);
    assertTrue(model.isGameOver());
  }

  /**
   * Tests that winningPaletteIndex returns the correct value when the game starts.
   */
  @Test
  public void testWinningPaletteIndex() {
    model.startGame(defaultDeck, false, 2, 7);
    assertEquals(0, model.winningPaletteIndex());
  }

  /**
   * Tests that winningPaletteIndex throws an
   * IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexBeforeGameStart() {
    model.winningPaletteIndex();
  }

  /**
   * Tests that all cards can be retrieved correctly.
   */
  @Test
  public void testGetAllCards() {
    List<CardModel> allCards = model.getAllCards();
    assertEquals(35, allCards.size());
  }

  /**
   * Tests that getPalette throws an IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetPaletteBeforeGameStart() {
    model.getPalette(0);
  }

  /**
   * Tests that getPalette works correctly after the game starts.
   */
  @Test
  public void testGetPaletteSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    List<CardModel> palette = model.getPalette(0);
    assertEquals(1, palette.size());
  }

  /**
   * Tests that getPalette throws an
   * IllegalArgumentException when given an invalid palette index.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPaletteIndexGetPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.getPalette(10);
  }

  /**
   * Tests that the game is won after all cards are played.
   */
  @Test
  public void testGameWinCondition() {
    model.startGame(defaultDeck, false, 2, 1);
    model.playToPalette(0, 0);
    assertTrue(model.isGameWon());
  }

  /**
   * Tests that isGameWon throws an IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testGameWinConditionBeforeGameStart() {
    model.isGameWon();
  }

  /**
   * Tests that getCanvas works correctly after the game starts.
   */
  @Test
  public void testGetCanvasCard() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToCanvas(0);
    assertNotNull(model.getCanvas());
  }

  /**
   * Tests that getCanvas throws an IllegalStateException if the game hasn't started.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetCanvasCardBeforeGameStart() {
    model.getCanvas();
  }
}
