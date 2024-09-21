package cs3500.solored.hw2.filesystem;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Test suite for the SoloRedModel class with more comprehensive coverage.
 * Covers game starting, playing to palettes, playing to canvas, and various exceptions.
 */
public class SoloRedModelTest {

  private SoloRedGameModel model;
  private List<CardModel> deck;

  @Before
  public void setUp() {
    // Setup mock game deck and model
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
   * Test starting the game successfully with 3 palettes and 5 cards in hand.
   */
  @Test
  public void testStartGameSuccess() {
    model.startGame(deck, false, 3, 5);
    assertEquals("Deck reduced correctly after starting the game.", 4, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly.", 3, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 5, model.getHand().size());
  }

  /**
   * Test starting the game successfully with shuffle enabled.
   */
  @Test
  public void testStartGameWithShuffle() {
    model.startGame(deck, true, 2, 4);
    assertEquals("Deck reduced after starting the game with shuffle.", 6, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly.", 2, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 4, model.getHand().size());
  }

  /**
   * Test that starting the game again throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartGameExceptionAlreadyStarted() {
    model.startGame(deck, false, 2, 5);
    model.startGame(deck, false, 2, 5);
  }

  /**
   * Test that starting the game with too few cards throws an exception.
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
   * Test playing a card successfully to a palette.
   */
  @Test
  public void testPlayToPaletteSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    int initialPaletteSize = model.getPalette(2).size();
    int initialHandSize = model.getHand().size();
    model.playToPalette(2, 0);
    assertEquals("Palette should have one more card after playing.", initialPaletteSize + 1, model.getPalette(2).size());
    assertEquals("Hand should have one less card after playing.", initialHandSize - 1, model.getHand().size());
  }

  /**
   * Test that playing a card to a palette before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteExceptionGameNotStarted() {
    model.playToPalette(1, 0);
  }

  /**
   * Test that playing to an invalid palette index throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidPaletteIdx() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToPalette(4, 0);
  }

  /**
   * Test that playing a card from an invalid hand index throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidCardIdx() {
    model.startGame(deck, false, 3, 5);
    model.playToPalette(1, 10);
  }

  /**
   * Test playing a card successfully to the canvas.
   */
  @Test
  public void testPlayToCanvasSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    int initialHandSize = model.getHand().size();
    model.playToCanvas(0);
    assertEquals("Canvas should have the played card's number.", 1, model.getCanvas().getNumber());
    assertEquals("Hand should have one less card.", initialHandSize - 1, model.getHand().size());
  }

  /**
   * Test that playing a card to the canvas twice in the same turn throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionAlreadyPlayedThisTurn() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToCanvas(0);
    model.playToCanvas(1);
  }

  /**
   * Test that playing to the canvas before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionGameNotStarted() {
    model.playToCanvas(0);
  }

  /**
   * Test drawing cards successfully until the hand is full.
   */
  @Test
  public void testDrawForHandSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    assertEquals("Hand should be filled to 7 cards.", 7, model.getHand().size());
    assertFalse("Game should not be over yet.", model.isGameOver());
  }

  /**
   * Test that drawing cards when the deck is empty fills the hand only up to its maximum.
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
   * Test that drawing cards before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testDrawForHandExceptionGameNotStarted() {
    model.drawForHand();
  }

  /**
   * Test getting the number of cards in the deck after starting the game.
   */
  @Test
  public void testNumOfCardsInDeck() {
    model.startGame(deck, false, 3, 5);
    assertEquals("numOfCardsInDeck should return correct count.", 4, model.numOfCardsInDeck());
  }

  /**
   * Test that getting the number of cards in the deck before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testNumOfCardsInDeckExceptionGameNotStarted() {
    model.numOfCardsInDeck();
  }

  /**
   * Test getting the number of palettes after starting the game.
   */
  @Test
  public void testNumPalettes() {
    model.startGame(deck, false, 4, 5);
    assertEquals("numPalettes should return correct number of palettes.", 4, model.numPalettes());
  }

  /**
   * Test that getting the number of palettes before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testNumPalettesExceptionGameNotStarted() {
    model.numPalettes();
  }

  /**
   * Test getting the index of the winning palette after starting the game.
   */
  @Test
  public void testWinningPaletteIndex() {
    model.startGame(deck, false, 3, 5);
    int winningIndex = model.winningPaletteIndex();
    assertTrue("Winning palette index should be in range.", winningIndex >= 0 && winningIndex < 3);
  }

  /**
   * Test that getting the winning palette index before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexExceptionGameNotStarted() {
    model.winningPaletteIndex();
  }

  /**
   * Test that the game is not over at the start.
   */
  @Test
  public void testIsGameOverFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be over at the start.", model.isGameOver());
  }

  /**
   * Test that checking if the game is over before starting throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverExceptionGameNotStarted() {
    model.isGameOver();
  }

  /**
   * Test that the game is not won if it is not over.
   */
  @Test
  public void testIsGameWonFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be won when it is not over.", model.isGameWon());
  }

  /**
   * Test getting the hand after starting the game.
   */
  @Test
  public void testGetHand() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> hand = model.getHand();
    assertEquals("getHand should return the correct number of cards.", 5, hand.size());
  }

  /**
   * Test that getting the hand before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetHandExceptionGameNotStarted() {
    model.getHand();
  }

  /**
   * Test getting a palette after starting the game.
   */
  @Test
  public void testGetPalette() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> palette = model.getPalette(1);
    assertNotNull("getPalette should return a non-null list.", palette);
    assertFalse("Palette should contain at least one card.", palette.isEmpty());
  }

  /**
   * Test that getting a palette with an invalid index throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteExceptionInvalidIndex() {
    model.startGame(deck, false, 3, 5);
    model.getPalette(5);
  }

  /**
   * Test that getting a palette before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetPaletteExceptionGameNotStarted() {
    model.getPalette(0);
  }

  /**
   * Test getting the canvas after starting the game.
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
   * Test that getting the canvas before starting the game throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetCanvasExceptionGameNotStarted() {
    model.getCanvas();
  }

  /**
   * Test that getAllCards returns the correct number of unique cards.
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
