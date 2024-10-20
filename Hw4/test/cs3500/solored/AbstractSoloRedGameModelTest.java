package cs3500.solored.model.hw02;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Random;

public abstract class AbstractSoloRedGameModelTest {
  protected SoloRedGameModel model;
  protected List<CardModel> defaultDeck;

  // Initialize the testing environment
  @Before
  public void setup() {
    defaultDeck = initializeDefaultDeck();
    model = createGameModel(); // Call abstract method to create the game model
  }

  // Abstract method to create the game model for different test classes
  protected abstract SoloRedGameModel createGameModel();

  // Helper method to create a default deck of cards for testing
  protected List<CardModel> initializeDefaultDeck() {
    SoloRedGameModel tempModel = new SoloRedGameModel();
    return tempModel.getAllCards();
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToPaletteBeforeGameStart() {
    model.playToPalette(0, 0);
  }

  @Test
  public void testStartGameSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    assertEquals(7, model.getHand().size());
    assertEquals(2, model.numPalettes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHandSizeStartGame() {
    model.startGame(defaultDeck, false, 2, 0); // Hand size of 0 is invalid
  }

  @Test
  public void testPlayToCanvasSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToCanvas(0);
    assertNotNull(model.getCanvas());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasBeforeGameStart() {
    model.playToCanvas(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPaletteIndexPlayToPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(10, 0); // Invalid palette index
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardIndexPlayToPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(0, 10); // Invalid card index in hand
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayToWinningPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToPalette(0, 0); // First move is valid
    model.playToPalette(0, 1); // Trying to play on a winning palette
  }

  @Test
  public void testDrawCardsSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    model.drawForHand();
    assertEquals(7, model.getHand().size()); // Ensure hand is still 7 after draw
  }

  @Test(expected = IllegalStateException.class)
  public void testDrawCardsBeforeGameStart() {
    model.drawForHand(); // Should fail since game hasn't started
  }

  @Test
  public void testDeckSizeReductionAfterDraw() {
    model.startGame(defaultDeck, false, 2, 7);
    int initialDeckSize = model.numOfCardsInDeck();
    model.drawForHand();
    assertTrue(model.numOfCardsInDeck() < initialDeckSize); // Ensure deck size reduces
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithInvalidDeckSize() {
    List<CardModel> smallDeck = defaultDeck.subList(0, 5); // Deck too small
    model.startGame(smallDeck, false, 2, 7); // Should throw exception
  }

  @Test
  public void testIsGameOverWhenHandAndDeckEmpty() {
    model.startGame(defaultDeck, false, 2, 1); // Start with 1 card in hand
    model.playToPalette(0, 0); // Play card to palette
    assertTrue(model.isGameOver()); // Game should be over
  }

  @Test
  public void testWinningPaletteIndex() {
    model.startGame(defaultDeck, false, 2, 7);
    assertEquals(0, model.winningPaletteIndex()); // Palette 0 should be winning at the start
  }

  @Test(expected = IllegalStateException.class)
  public void testWinningPaletteIndexBeforeGameStart() {
    model.winningPaletteIndex(); // Should throw exception since game hasn't started
  }

  @Test
  public void testGetAllCards() {
    List<CardModel> allCards = model.getAllCards();
    assertEquals(35, allCards.size()); // Ensure all 35 cards are present
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPaletteBeforeGameStart() {
    model.getPalette(0); // Should throw exception since game hasn't started
  }

  @Test
  public void testGetPaletteSuccess() {
    model.startGame(defaultDeck, false, 2, 7);
    List<CardModel> palette = model.getPalette(0);
    assertEquals(1, palette.size()); // Ensure palette has the expected number of cards
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPaletteIndexGetPalette() {
    model.startGame(defaultDeck, false, 2, 7);
    model.getPalette(10); // Should throw exception since the palette index is invalid
  }

  @Test
  public void testGameWinCondition() {
    model.startGame(defaultDeck, false, 2, 1); // Start with 1 card
    model.playToPalette(0, 0); // Play last card to palette
    assertTrue(model.isGameWon()); // Game should be won after playing the last card
  }

  @Test(expected = IllegalStateException.class)
  public void testGameWinConditionBeforeGameStart() {
    model.isGameWon(); // Should throw exception since game hasn't started
  }

  @Test
  public void testGetCanvasCard() {
    model.startGame(defaultDeck, false, 2, 7);
    model.playToCanvas(0);
    assertNotNull(model.getCanvas()); // Ensure the canvas card is set
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCanvasCardBeforeGameStart() {
    model.getCanvas(); // Should throw exception since game hasn't started
  }
}
