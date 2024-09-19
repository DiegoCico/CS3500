// File: SoloRedModelTest.java
package soloredTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedModel;

/**
 * Test suite for the SoloRedModel class.
 */
public class SoloRedModelTest {
  private SoloRedModel model;
  private List<CardModel> deck;

  @Before
  public void setUp() {
    model = new SoloRedModel();
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

  @Test
  public void testStartGameSuccess() {
    model.startGame(deck, false, 3, 5);
    assertEquals("Deck size should be reduced correctly after starting the game.", 4, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly. ", 3, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 5, model.getHand().size());
  }

  @Test
  public void testStartGameWithShuffle() {
    model.startGame(deck, true, 2, 4);
    assertEquals("Deck size should be reduced correctly after starting the game with shuffle.", 6, model.numOfCardsInDeck());
    assertEquals("Number of palettes should be set correctly.", 2, model.numPalettes());
    assertEquals("Hand size should be set correctly.", 4, model.getHand().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameExceptionAlreadyStarted() {
    model.startGame(deck, false, 2, 5);
    model.startGame(deck, false, 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameExceptionNotEnoughCards() {
    List<CardModel> smallDeck = Arrays.asList(
            new CardModel("R", 1),
            new CardModel("O", 2)
    );
    model.startGame(smallDeck, false, 2, 2);
  }

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

  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteExceptionGameNotStarted() {
    model.playToPalette(1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidPaletteIdx() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToPalette(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteExceptionInvalidCardIdx() {
    model.startGame(deck, false, 3, 5);
    model.playToPalette(1, 10);
  }

  @Test
  public void testPlayToCanvasSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    int initialHandSize = model.getHand().size();
    model.playToCanvas(0);
    assertEquals("Canvas should have the played card's number.", 1, model.getCanvas().getNumber());
    assertEquals("Hand should have one less card after playing to canvas.", initialHandSize - 1, model.getHand().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionAlreadyPlayedThisTurn() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    model.playToCanvas(0);
    model.playToCanvas(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasExceptionGameNotStarted() {
    model.playToCanvas(0);
  }

  @Test
  public void testDrawForHandSuccess() {
    model.startGame(deck, false, 3, 5);
    model.drawForHand();
    assertEquals("Hand should be filled to 7 cards.", 7, model.getHand().size());
    assertFalse("Game should not be over yet.", model.isGameOver());
  }

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

  @Test(expected = IllegalStateException.class)
  public void testDrawForHandExceptionGameNotStarted() {
    model.drawForHand();
  }

  @Test
  public void testNumOfCardsInDeck() {
    model.startGame(deck, false, 3, 5);
    assertEquals("numOfCardsInDeck should return correct count.", 4, model.numOfCardsInDeck());
  }

  @Test(expected = IllegalStateException.class)
  public void testNumOfCardsInDeckExceptionGameNotStarted() {
    model.numOfCardsInDeck();
  }

  @Test
  public void testNumPalettes() {
    model.startGame(deck, false, 4, 5);
    assertEquals("numPalettes should return correct number of palettes.", 4, model.numPalettes());
  }

  @Test(expected = IllegalStateException.class)
  public void testNumPalettesExceptionGameNotStarted() {
    model.numPalettes();
  }

  @Test
  public void testWinningPaletteIndex() {
    model.startGame(deck, false, 3, 5);
    int winningIndex = model.winningPaletteIndex();
    assertTrue("Winning palette index should be within range.", winningIndex >= 0 && winningIndex < 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexExceptionGameNotStarted() {
    model.winningPaletteIndex();
  }

  @Test
  public void testIsGameOverFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be over at the start.", model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverExceptionGameNotStarted() {
    model.isGameOver();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNotEnoughCards() {
    List<CardModel> smallDeck = new ArrayList<>();
    model.startGame(smallDeck, false, 2, 0);
  }

  @Test
  public void testIsGameWonFalse() {
    model.startGame(deck, false, 3, 5);
    assertFalse("Game should not be won when it is not over.", model.isGameWon());
  }

  @Test
  public void testGetHand() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> hand = model.getHand();
    assertEquals("getHand should return the correct number of cards.", 5, hand.size());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetHandExceptionGameNotStarted() {
    model.getHand();
  }

  @Test
  public void testGetPalette() {
    model.startGame(deck, false, 3, 5);
    List<CardModel> palette = model.getPalette(1);
    assertNotNull("getPalette should return a non-null list.", palette);
    assertFalse("Palette should contain at least one card.", palette.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteExceptionInvalidIndex() {
    model.startGame(deck, false, 3, 5);
    model.getPalette(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPaletteExceptionGameNotStarted() {
    model.getPalette(0);
  }

  @Test
  public void testGetCanvas() {
    model.startGame(deck, false, 3, 5);
    CardModel canvas = model.getCanvas();
    assertNotNull("Canvas should not be null after game starts.", canvas);
    assertEquals("Canvas should be initialized correctly.", "R", canvas.getColor());
    assertEquals("Canvas should be initialized correctly.", 0, canvas.getNumber());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCanvasExceptionGameNotStarted() {
    model.getCanvas();
  }

  @Test
  public void testGetAllCards() {
    List<CardModel> allCards = model.getAllCards();
    assertEquals("There should be 35 unique cards.", 35, allCards.size());
    CardModel firstCard = allCards.getFirst();
    CardModel lastCard = allCards.getLast();
    assertTrue("AllCards should contain R1.", allCards.contains(firstCard));
    assertTrue("AllCards should contain V7.", allCards.contains(lastCard));
  }
}
