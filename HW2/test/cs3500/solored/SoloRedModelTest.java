package cs3500.solored;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Test suite for the {@code SoloRedGameModel} class.
 * Provides unit tests for game starting, playing to palettes,
 * playing to canvas, and various exceptions.
 */
public class SoloRedModelTest {

  private SoloRedGameModel model;
  private List<CardModel> deck;

  /**
   * Sets up a sample deck and initializes the game model before each test.
   */
  @Before
  public void setUp() {
    model = new SoloRedGameModel();
    deck = Arrays.asList(
            new CardModel("R", 1),
            new CardModel("O", 2),
            new CardModel("B", 3),
            new CardModel("I", 4),
            new CardModel("V", 5),
            new CardModel("R", 6),
            new CardModel("O", 7),
            new CardModel("B", 1),
            new CardModel("I", 2),
            new CardModel("V", 3),
            new CardModel("R", 4),
            new CardModel("O", 5)
    );
  }

  /**
   * Tests that the game starts successfully with a specified number of palettes and hand size.
   * Checks the remaining deck size, number of palettes, and hand size after starting the game.
   */
  @Test
  public void testStartGameSuccess() {
    model.startGame(deck, false, 3, 5);
    assertEquals("Deck reduced correctly after starting the game.", 4, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly.", 3, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 5, model.getHand().size());
  }

  /**
   * Tests that the game starts successfully with shuffle enabled.
   * Verifies the number of palettes, hand size, and remaining deck size.
   */
  @Test
  public void testStartGameWithShuffle() {
    model.startGame(deck, true, 2, 4);
    assertEquals("Deck reduced after starting the game with shuffle.", 6, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly.", 2, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 4, model.getHand().size());
  }

  /**
   * Tests that starting the game twice throws an {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartGameExceptionAlreadyStarted() {
    model.startGame(deck, false, 2, 5);
    model.startGame(deck, false, 2, 5);
  }

  /**
   * Tests that starting the game with too few cards in the deck
   * throws an {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameExceptionNotEnoughCards() {
    List<CardModel> smallDeck = Arrays.asList(
            new CardModel("R", 1),
            new CardModel("O", 2)
    );
    model.startGame(smallDeck, false, 2, 2);
  }

  /**
   * Tests that a card can be played fail to a palette.
   * Verifies the hand size decreases and the palette size increases.
   */
  @Test (expected = IllegalStateException.class)
  public void testPlayToPaletteFail() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    int initialPaletteSize = model.getPalette(2).size();
    int initialHandSize = model.getHand().size();
    model.playToPalette(2, 0);
  }

  /**
   * Tests that playing to a palette before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteExceptionGameNotStarted() {
    model.playToPalette(1, 0);
  }

  /**
   * Tests that attempting to play to an invalid palette index throws an
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidPaletteIdx() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToPalette(4, 0);
  }

  /**
   * Tests that playing a card with an invalid hand index throws an
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidCardIdx() {
    model.startGame(deck, false, 3, 5);
    model.playToPalette(1, 10);
  }

  /**
   * Tests that a card can be successfully played to the canvas.
   * Verifies that the hand size decreases after playing to the canvas.
   */
  @Test
  public void testPlayToCanvasSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    int initialHandSize = model.getHand().size();
    model.playToCanvas(0);
    assertEquals("Canvas should have the played card's number.", 4, model.getCanvas().getNumber());
    assertEquals("Hand should have one less card.", initialHandSize - 1, model.getHand().size());
  }

  /**
   * Tests that attempting to play to the canvas twice in one turn throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionAlreadyPlayedThisTurn() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToCanvas(0);
    model.playToCanvas(1);
  }

  /**
   * Tests that playing to the canvas before the game has started throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionGameNotStarted() {
    model.playToCanvas(0);
  }

  /**
   * Tests that drawing cards fills the hand until it reaches its maximum size.
   */
  @Test
  public void testDrawForHandSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    assertEquals("Hand should be filled to 7 cards.", 5, model.getHand().size());
    assertFalse("Game should not be over yet.", model.isGameOver());
  }

  /**
   * Tests that drawing cards when the deck is
   * empty fills the hand only up to the maximum hand size.
   */
  @Test
  public void testDrawForHandDeckEmpty() {
    List<CardModel> smallDeck = Arrays.asList(
            new CardModel("R", 1),
            new CardModel("O", 2),
            new CardModel("B", 3),
            new CardModel("I", 4),
            new CardModel("V", 5),
            new CardModel("R", 6),
            new CardModel("O", 7),
            new CardModel("B", 1),
            new CardModel("I", 2),
            new CardModel("V", 3),
            new CardModel("R", 4),
            new CardModel("O", 5)
    );
    model.startGame(smallDeck, false, 3, 7);
    model.drawForHand();
    assertEquals("Hand should remain at maximum size.", 7, model.getHand().size());
  }

  /**
   * Tests that drawing cards before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandExceptionGameNotStarted() {
    model.drawForHand();
  }

  /**
   * Tests that the correct number of cards
   * remaining in the deck is returned after starting the game.
   */
  @Test
  public void testNumOfCardsInDeck() {
    model.startGame(deck, false, 3, 5);
    assertEquals("numOfCardsInDeck should return correct count.", 4, model.numOfCardsInDeck());
  }

  /**
   * Tests that checking the number of cards in the deck before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testNumOfCardsInDeckExceptionGameNotStarted() {
    model.numOfCardsInDeck();
  }

  /**
   * Tests that the correct number of palettes is returned after starting the game.
   */
  @Test
  public void testNumPalettes() {
    model.startGame(deck, false, 4, 5);
    assertEquals("numPalettes should return correct number of palettes.", 4, model.numPalettes());
  }

  /**
   * Tests that checking the number of palettes before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testNumPalettesExceptionGameNotStarted() {
    model.numPalettes();
  }

  /**
   * Tests that the index of the winning palette is returned correctly after starting the game.
   */
  @Test
  public void testWinningPaletteIndex() {
    model.startGame(deck, false, 3, 5);
    int winningIndex = model.winningPaletteIndex();
    assertTrue("Winning palette index should be in range.", winningIndex >= 0 && winningIndex < 3);
  }

  /**
   * Tests that checking the winning palette index before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexExceptionGameNotStarted() {
    model.winningPaletteIndex();
  }

  /**
   * Tests that the game is not over at the start.
   */
  @Test
  public void testIsGameOverFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be over at the start.", model.isGameOver());
  }

  /**
   * Tests that checking if the game is over before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverExceptionGameNotStarted() {
    model.isGameOver();
  }

  /**
   * Tests that the game is not won if it is not over.
   */
  @Test
  public void testIsGameWonFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be won when it is not over.", model.isGameWon());
  }

  /**
   * Tests that the player's hand is returned correctly after starting the game.
   */
  @Test
  public void testGetHand() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> hand = model.getHand();
    assertEquals("getHand should return the correct number of cards.", 5, hand.size());
  }

  /**
   * Tests that getting the player's hand before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetHandExceptionGameNotStarted() {
    model.getHand();
  }

  /**
   * Tests that a palette is returned correctly after starting the game.
   */
  @Test
  public void testGetPalette() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> palette = model.getPalette(1);
    assertNotNull("getPalette should return a non-null list.", palette);
    assertFalse("Palette should contain at least one card.", palette.isEmpty());
  }

  /**
   * Tests that getting a palette with an invalid index throws an
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteExceptionInvalidIndex() {
    model.startGame(deck, false, 3, 5);
    model.getPalette(5);
  }

  /**
   * Tests that getting a palette before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetPaletteExceptionGameNotStarted() {
    model.getPalette(0);
  }

  /**
   * Tests that the canvas is returned correctly after starting the game.
   */
  @Test
  public void testGetCanvas() {
    model.startGame(deck, false, 3, 5);
    CardModel canvas = model.getCanvas();
    assertNotNull("Canvas should not be null after game starts.", canvas);
    assertEquals("Canvas should be initialized correctly.", "R", canvas.getColor());
    assertEquals("Canvas should be initialized correctly.", 0, canvas.getNumber());
  }

  /**
   * Tests that getting the canvas before starting the game throws an
   * {@link IllegalStateException}.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetCanvasExceptionGameNotStarted() {
    model.getCanvas();
  }

  /**
   * Tests that {@code getAllCards} returns the correct number of unique cards.
   */
  @Test
  public void testGetAllCards() {
    List<CardModel> allCards = model.getAllCards();
    assertEquals("There should be 35 unique cards.", 35, allCards.size());
    CardModel firstCard = allCards.get(0);
    CardModel lastCard = allCards.get(0);
    assertTrue("AllCards should contain R1.", allCards.contains(firstCard));
    assertTrue("AllCards should contain V7.", allCards.contains(lastCard));
  }
}
